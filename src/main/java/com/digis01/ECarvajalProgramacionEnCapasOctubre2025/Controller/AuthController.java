/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.Controller;

import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;

@Controller
@RequestMapping("/auth")
public class AuthController {
    
    private final String apiUrl = "http://localhost:8080/api/auth";
    private final String url = "http://localhost:8080/";
    private final RestTemplate restTemplate;
    
    public AuthController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginData", new HashMap<String, String>());
        return "Login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String username, 
                       @RequestParam String password,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        
        try {
            Map<String, String> credentials = new HashMap<>();
            credentials.put("username", username);
            credentials.put("password", password);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, String>> request = new HttpEntity<>(credentials, headers);
            
            ResponseEntity<Result> response = restTemplate.exchange(
                apiUrl + "/login",
                HttpMethod.POST,
                request,
                Result.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Result result = response.getBody();
                
                if (result.correct) {
                    
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> responseData = mapper.convertValue(result.object, Map.class);
                    
                    //buscar el rol
                    
                    session.setAttribute("token", responseData.get("token"));
                    session.setAttribute("username", responseData.get("username"));
                    session.setAttribute("userId", responseData.get("userId"));
                    session.setAttribute("rol", responseData.get("rol"));
                    
                    
                    String rol = (String) responseData.get("rol");
                    if (rol.contains("ROLE_Administrador") || rol.contains("ROLE_Maestro")) {
                        return "redirect:/usuario";
                    } else if (rol.contains("ROLE_Alumno")) {
                        Integer userId = (Integer) responseData.get("userId");
                        return "redirect:/usuario/detail/" + userId;
                    }
                }
            }
            
            redirectAttributes.addFlashAttribute("error", "Credenciales incorrectas");
            return "redirect:/auth/login";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error en la autenticación");
            return "redirect:/auth/login";
        }
    }
    
    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            String token = (String) session.getAttribute("token");
            
            if (token != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + token);
                HttpEntity<?> request = new HttpEntity<>(headers);
                
                restTemplate.exchange(
                    apiUrl + "/logout",
                    HttpMethod.POST,
                    request,
                    Result.class
                );
            }
            
            session.invalidate();
            redirectAttributes.addFlashAttribute("success", "Sesión cerrada correctamente");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al cerrar sesión");
        }
        
        return "redirect:/auth/login";
    }
    
    @GetMapping("resetPassword")
    public String ResetPassword(){
    
        return "ResetPassword";
    }
    
    @PostMapping("resetPasswordPost")
    public String ResetPasswordPost(@RequestParam String campo){
        
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> requestEntity = new HttpEntity<>(campo, headers);
        
        RestTemplate restTemplate = new RestTemplate();
        
        ResponseEntity<Result> responseEntityUsuario =
        restTemplate.exchange(
            url + "/resetPassword",
            HttpMethod.POST,
            requestEntity,
            new ParameterizedTypeReference<Result>() {}
        );
        
        //si si existe el usuario
        if (responseEntityUsuario.getStatusCode().value() == 200) {
            //aqui viene el token
            Result resultUsuario = responseEntityUsuario.getBody();
            //redireccion a vista para el codigo
            return "redirect:/auth/resetPasswordConfirmCode/" + resultUsuario.object;
        
        } else {
            //si  no existe otra vez a la redireccion
            return "ResetPassword";
        
        }        
    }
    
    @GetMapping("resetPasswordConfirmCode/{token}") 
    public String ResetPasswordConfirmCode(@PathVariable ("token") String token){
        
        //confirmar que el token es valido mandandolo al servidor
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> requestEntity = new HttpEntity<>(token, headers);
        
        RestTemplate restTemplate = new RestTemplate();
        
        ResponseEntity<Result> responseEntityUsuario =
        restTemplate.exchange(
            url + "/resetPassword/verifyToken",
            HttpMethod.POST,
            requestEntity,
            new ParameterizedTypeReference<Result>() {}
        );
        
        //si es valido
        if (responseEntityUsuario.getStatusCode().value() == 200) {
        
            return "ResetConfirmCode";
        
        } else {
        
            return "ResetPassword";
        }
    }
    
    @PostMapping("resetPasswordConfirmCode")
    public String MandarCodigoResetPassword(@RequestParam("codigo")  String codigo) {
    
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> requestEntity = new HttpEntity<>(codigo, headers);
        
        RestTemplate restTemplate = new RestTemplate();
        
        ResponseEntity<Result> responseEntityUsuario =
        restTemplate.exchange(
            url + "/resetPassword/changePassword",
            HttpMethod.POST,
            requestEntity,
            new ParameterizedTypeReference<Result>() {}
        );
        
        //si es valido
        if (responseEntityUsuario.getStatusCode().value() == 200) {
            
            //se agregan atributos de cambiar password
            return "ChangePassword";
        
        } else {
            //atributos que definan que el code es incorrecto
            return "ChangePassword";
        }
    }
}