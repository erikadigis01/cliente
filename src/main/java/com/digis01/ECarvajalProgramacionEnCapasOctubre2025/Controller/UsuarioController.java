package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.Controller;


import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Direccion;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Pais;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Result;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Roll;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.ProcessBuilder.Redirect.Type;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.TypeToken;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;


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
        ResponseEntity<Result<Roll>> responseEntityRoll =
            restTemplate.exchange(
                url + "/roll",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result<Roll>>() {}
            );

        String error = "error usuario : " + responseEntityUsuario.getBody().errorMessage + "error roll : " + responseEntityRoll.getBody();
//        String error = responseEntityUsuario.getBody().errorMessage;
        if(responseEntityUsuario.getStatusCode().value() == 200 && responseEntityRoll.getStatusCode().value() == 200) {
            
            
            
            Result resultUsuario = responseEntityUsuario.getBody();
            Result resultRoll = responseEntityRoll.getBody();
            
            
            
            model.addAttribute("usuarios", resultUsuario.objects);
            model.addAttribute("rolles", resultRoll.objects);
      
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
        ResponseEntity<Result<List<Usuario>>> responseEntityUsuario =
            restTemplate.exchange(
                url + "/usuario/" + id,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result<List<Usuario>>>() {}
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
        
        if (responseEntityUsuario.getStatusCode().value() == 200) {
            
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
    

}
