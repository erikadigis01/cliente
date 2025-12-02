package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.Configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(authentication);
        new DefaultRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private String determineTargetUrl(Authentication authentication) {
        
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_Administrador"))) {
            return "/usuario";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_Alumno"))) {
            return "/login";
        } else {
            return "/";
        }
    }
        

}
