package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.Controller;


import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Direccion;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Estado;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Pais;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Result;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Roll;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Usuario;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.ValidationGroup;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect.Type;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;
import org.modelmapper.TypeToken;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("usuario")
public class UsuarioController {
    
    public String url = "http://localhost:8080";
    
   
    @GetMapping
    public String Index(Model model) {
        
        
        
        RestTemplate restTemplate = new RestTemplate();
        
        ResponseEntity<Result<Usuario>> responseEntityUsuario =
            restTemplate.exchange(
                url + "/usuario",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result<Usuario>>() {}
            );
        

        String error = "error usuario : " + responseEntityUsuario.getBody().errorMessage;
//        String error = responseEntityUsuario.getBody().errorMessage;
        if(responseEntityUsuario.getStatusCode().value() == 200) {
            
            
            
            Result resultUsuario = responseEntityUsuario.getBody();
            
            
            model.addAttribute("Usuario", new Usuario()); 
            model.addAttribute("usuarios", resultUsuario.objects);
         }
            else {
            
            model.addAttribute("error", error);
        
             return "Error";
        
        }
        
        return "UsuarioIndex";

    }
    
    
    @GetMapping("/detail/{idUsuario}")
    public String GetById(@PathVariable("idUsuario") int idUsuario, Model model) {
        
        RestTemplate restTemplate = new RestTemplate(); 
        
        String id = Integer.toString(idUsuario);
        ResponseEntity<Result<Usuario>> responseEntityUsuario =
            restTemplate.exchange(
                url + "/usuario/" + id,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result<Usuario>>() {}
            );
         ResponseEntity<Result<Roll>> responseEntityRoll =
            restTemplate.exchange(
                url + "/roll",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result<Roll>>() {}
            );
          ResponseEntity<Result<Pais>> responseEntityPais =
            restTemplate.exchange(
                url + "/pais",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result<Pais>>() {}
            );
        
        if (responseEntityUsuario.getStatusCode().value() == 200 && 
               responseEntityRoll.getStatusCode().value() == 200 && 
               responseEntityPais.getStatusCode().value() == 200) {
            
            Result resultUsuario = responseEntityUsuario.getBody();
            Result resultRoll = responseEntityRoll.getBody();
            Result resultPais = responseEntityPais.getBody();

            model.addAttribute("usuario", resultUsuario.object);
            model.addAttribute("rolles", resultRoll.objects);
            model.addAttribute("paises", resultPais.objects);
            model.addAttribute("direccion", new Direccion());
            
            return "UsuarioDetail";
            
        } else {

            return "redirect:/usuario?error=Usuario no encontrado";

        }

    }
    
    @GetMapping("delete/{id}")
    public String Delete(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {

        RestTemplate restTemplate = new RestTemplate();
        
        ResponseEntity<Result<Usuario>> responseEntityUsuario =
            restTemplate.exchange(
                url + "/usuario/" + id,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result<Usuario>>() {}
            );
        
        if (responseEntityUsuario.getStatusCode().value() == 204) {
            
            Result resultUsuario = responseEntityUsuario.getBody();
           
            redirectAttributes.addFlashAttribute("successDeleteMessage", "usuario eliminado");
            
            
        } else {

            return "redirect:/usuario?error=Usuario no encontrado";

        }
        
        
        return "redirect:/usuario";
    }
    
    @GetMapping("add")
    public String Add(Model model) {
        
        RestTemplate restTemplate = new RestTemplate();
        
         ResponseEntity<Result<Roll>> responseEntityRoll =
            restTemplate.exchange(
                url + "/roll",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result<Roll>>() {}
            );
         ResponseEntity<Result<Pais>> responseEntityPais =
            restTemplate.exchange(
                url + "/pais",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result<Pais>>() {}
            );
        
        Result resultRoll = responseEntityRoll.getBody();
        Result resultPais = responseEntityPais.getBody();
         
        model.addAttribute("rolles", resultRoll.objects);
        model.addAttribute("paises", resultPais.objects);
        
        Usuario usuario = new Usuario();
        model.addAttribute("Usuario", usuario);
        
        model.addAttribute("direccion", new Direccion());
        return "UsuarioForm";
    }
    
    
    
    @PostMapping("add")
    public String Add(@Valid @ModelAttribute("Usuario") Usuario usuario,
            BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes,
            @RequestParam("imagenFile") MultipartFile imagenFile) {


        if (imagenFile != null) {

            try {

                //vuelvo a asegurarme que es jpg o png
                String extension = imagenFile.getOriginalFilename().split("\\.")[1];

                if (extension.equals("jpg") || extension.equals("png")) {

                    byte[] byteImagen = imagenFile.getBytes();

                    String imagenBase64 = Base64.getEncoder().encodeToString(byteImagen);

                    usuario.setImagen(imagenBase64);

                }

            } catch (IOException ex) {

                Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);

            }

        }
        
        if (!bindingResult.hasErrors()) {
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON); // O el tipo que necesites
            HttpEntity<Usuario> requestEntity = new HttpEntity<>(usuario, headers);
            
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<Result<Usuario>> responseEntityUsuario =
               restTemplate.exchange(
                   url + "/usuario",
                   HttpMethod.POST,
                   requestEntity,
                   new ParameterizedTypeReference<Result<Usuario>>() {}
               );
            
            Result result = responseEntityUsuario.getBody();

            if (result.correct) {

                redirectAttributes.addFlashAttribute("successMessage", "El usuario " + usuario.getUserName() + "se creo con exito.");
            } else {

                redirectAttributes.addFlashAttribute("successMessage", "El usuario " + usuario.getUserName() + "no se creo");
            }
            
           
        }
        return "redirect:/usuario";

    }
    
    @GetMapping("{idUsuario}/deleteDireccion/{idDireccion}")
    public String DeleteDireccion(@PathVariable("idDireccion") int idDireccion,
            @PathVariable("idUsuario") int idUsuario,
            Model model, RedirectAttributes redirectAttributes) {

        
        RestTemplate restTemplate = new RestTemplate();
        
        ResponseEntity<Result> responseEntityDireccion =
            restTemplate.exchange(
                url + "/direccion/" + idDireccion,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result>() {}
            );
        Result resultDireccion = responseEntityDireccion.getBody();
        
        
        if(resultDireccion.correct == true){
            
            redirectAttributes.addFlashAttribute("successDeleteDireccionMessage", "Direccion eliminada");

        } 
       
        return "redirect:/usuario/detail/" + idUsuario;
    }
    
    @PostMapping("actiondireccion/{idUsuario}")
    public String ActionDireccion(@PathVariable("idUsuario") int idUsuario, @ModelAttribute("direccion") Direccion direccion,
            BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {
        
        direccion.Usuario = new Usuario();
        direccion.Usuario.setIdUsuario(idUsuario);
        
        RestTemplate restTemplate = new RestTemplate();
        
        HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON); // O el tipo que necesites
            HttpEntity<Direccion> requestEntityDireccion = new HttpEntity<>(direccion, headers);
            

        if (direccion.getIdDireccion() == 0) { // agregar direccion a usuario

            ResponseEntity<Result<Direccion>> responseEntityDireccion =
               restTemplate.exchange(
                   url + "/direccion",
                   HttpMethod.POST,
                   requestEntityDireccion,
                   new ParameterizedTypeReference<Result<Direccion>>() {}
               );
            
            redirectAttributes.addFlashAttribute("successAddDireccionMessage", "Dirección agregada correctamente");

        } else { // editar la direccion a usuario

            ResponseEntity<Result<Direccion>> responseEntityDireccion =
               restTemplate.exchange(
                   url + "/direccion/update",
                   HttpMethod.PUT,
                   requestEntityDireccion,
                   new ParameterizedTypeReference<Result<Direccion>>() {}
               );
            
            redirectAttributes.addFlashAttribute("successUpdateDireccionMessage", "Dirección actualizada correctamente");
        }

        return "redirect:/usuario/detail/" + idUsuario;

    }
    
    
    @PostMapping("/updateImagen/{idUsuario}")
    public String UpdateImagen(@PathVariable("idUsuario") int idUsuario, 
        @RequestParam("imagen") MultipartFile imagenFile) {
        
         Usuario usuario = new Usuario();
         
        
        try {
            //Conversion de imagen;
            String imagen = Base64.getEncoder().encodeToString(imagenFile.getBytes());
           
            usuario.setImagen(imagen);
            
            RestTemplate restTemplate = new RestTemplate();
            
            HashMap<String, Object> mapa = new HashMap<>();
            mapa.put("imagen", imagen);
            
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(mapa);
//            String jsonString = JSON.stringify(mapa);
            
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON); 
            
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, headers);
            
            ResponseEntity <Result<Usuario>> responseEntityUpdateImagen =
               restTemplate.exchange(
                   url + "/usuario/" + idUsuario,
                   HttpMethod.PATCH,
                   requestEntity,
                   new ParameterizedTypeReference<Result<Usuario>>() {}
               );
            Result result = responseEntityUpdateImagen.getBody();
            
        } catch (IOException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "redirect:/usuario/detail/" + usuario.getIdUsuario();
    }
    
    
    @PostMapping("filtro")
    public String filtrar(@ModelAttribute("Usuario") Usuario usuario,
                      Model model) {
        
        RestTemplate restTemplate = new RestTemplate();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<Usuario> requestUsuario = new HttpEntity<>(usuario, headers);
        
        ResponseEntity<Result<Usuario>> responseEntityUsuario =
               restTemplate.exchange(
                   url + "/usuario/dinamico",
                   HttpMethod.POST,
                   requestUsuario,
                   new ParameterizedTypeReference<Result<Usuario>>() {}
               );
            
        Result result = responseEntityUsuario.getBody();
        
        model.addAttribute("usuarios", result.objects);
        model.addAttribute("errores", new ArrayList<>());
        model.addAttribute("isCorrect", false);
        return "UsuarioIndex";
    
    }
    
    @PostMapping("/detail")
    public String Update(@Validated(ValidationGroup.OnUpdate.class) @ModelAttribute("usuario") Usuario usuario,
            BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {
        
        RestTemplate restTemplate = new RestTemplate();

        if (bindingResult.hasErrors()) {
            
             ResponseEntity<Result<Roll>> responseEntityRoll =
            restTemplate.exchange(
                url + "/roll",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result<Roll>>() {}
            );
            
            Result resultRoll = responseEntityRoll.getBody();
            
            model.addAttribute("error", "Error al actualizar usuario: " );
            model.addAttribute("rolles", resultRoll.objects);
            redirectAttributes.addFlashAttribute("errorMessage", "El usuario " + usuario.getUserName() + "no se creo" + "Error:" + bindingResult.getAllErrors().toString());
            
        } else {
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON); // O el tipo que necesites
            HttpEntity<Usuario> requestEntity = new HttpEntity<>(usuario, headers);
            
            
            ResponseEntity<Result<Usuario>> responseEntityUsuario =
               restTemplate.exchange(
                   url + "/usuario/update",
                   HttpMethod.PUT,
                   requestEntity,
                   new ParameterizedTypeReference<Result<Usuario>>() {}
               );
            
            Result result = responseEntityUsuario.getBody();
            Usuario usuarioUpdate = (Usuario) result.object;
            
             if (result.correct) {
                redirectAttributes.addFlashAttribute("successMessage", "El usuario " + usuarioUpdate.getNombre() + "se actualizo con exito.");
                

            }
        }
        
        return "redirect:/usuario/detail/" + usuario.getIdUsuario();

    }
}
