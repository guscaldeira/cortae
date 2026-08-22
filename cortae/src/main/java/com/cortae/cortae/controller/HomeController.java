package com.cortae.cortae.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String mostrarInicio() {
        return "inicio";
    }

    @GetMapping("/saiba-mais")
    public String mostrarSaibamais() {
        return "saibamais";
    }

    @GetMapping("/politica-de-privacidade")
    public String mostrarPoliticaPrivacidade() {
        return "politicaprivacidade";
    }

    @GetMapping("/termos-de-uso")
    public String mostrarTermosuso() {
        return "termosuso";
    }
}
