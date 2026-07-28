package com.cortae.cortae.controller;

import com.cortae.cortae.model.Barbearia;
import com.cortae.cortae.service.BarbeariaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/barbearia")
public class BarbeariaController {
    
    private final BarbeariaService barbeariaService;

    public BarbeariaController(BarbeariaService barbeariaService) {
        this.barbeariaService = barbeariaService;
    }

    @GetMapping("/nova")
    public String mostrarFormularioCriacao(Model model) {
        
        model.addAttribute("barbearia", new Barbearia());
        return "barbearia-nova";
    }

    @PostMapping("/nova")
    public String processarCriacao(@ModelAttribute Barbearia barbearia, Model model) {

        try {
            barbeariaService.criarBarbearia(barbearia);
            return "redirect:/dashboard";
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            return "barbearia-nova";
        }
    }

    @GetMapping("/{id}")
    public String mostrarBarbearia(@PathVariable Long id, Model model) {
        barbeariaService.buscarPorId(id).ifPresentOrElse(barbearia -> model.addAttribute("barbearia", barbearia), () -> model.addAttribute("erro", "Barbearia não encontrada."));
        return "barbearia-detalhes";
    }

    @PostMapping("/{id}")
    public String processarAtualizacao(@PathVariable Long id, @ModelAttribute Barbearia dadosAtualizados, Model model) {

        try {
            barbeariaService.atualizarBarbearia(id, dadosAtualizados);
            return "redirect:/barbearia/" + id;
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            return "barbearia-detalhes";
        }
    }
}
