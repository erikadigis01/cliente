package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.Controller;

import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Result;
import com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML.Usuario;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class LoginController {
    
    public String url = "http://localhost:8080";
    
    @GetMapping("/login")
    public String Login(){
        return "Login";

    }
    
//    @PostMapping("/login")
//    public String PostLogin(String username, String password){
//        
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON); // O el tipo que necesites
//        Usuario usuario = new Usuario();
//        usuario.setUserName(username);
//        usuario.setPassword(password);
//        
//        HttpEntity<Usuario> requestEntity = new HttpEntity<>(usuario, headers);
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        ResponseEntity<Result<Usuario>> responseEntityUsuario =
//           restTemplate.exchange(
//               url + "/login",
//               HttpMethod.POST,
//               requestEntity,
//               new ParameterizedTypeReference<Result<Usuario>>() {}
//           );
//
//        Result result = responseEntityUsuario.getBody();
//    
//        return "redirect:/usuario";
//    }
    
    @PostMapping("/logout")
    public String Logout(){
    
        return "Login";
    }

}
