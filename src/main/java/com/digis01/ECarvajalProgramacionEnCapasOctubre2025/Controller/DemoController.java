package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("demo")
public class DemoController {

    @GetMapping("saludo")
    public String Saludo(@RequestParam("nombre") String nombre, Model model){
        model.addAttribute("nombre", nombre);
        return "HolaMundo";
    }
    
}
