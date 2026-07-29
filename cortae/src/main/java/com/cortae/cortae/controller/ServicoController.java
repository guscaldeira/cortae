package com.cortae.cortae.controller;

import com.cortae.cortae.model.Servico;
import com.cortae.cortae.service.ServicoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/servico")
public class ServicoController {


    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @GetMapping("/barbearia/{barbeariaId}")
    public String listarServicos(@PathVariable Long barbeariaId, Model model) {
        model.addAttribute("servicos", servicoService.listarPorBarbearia(barbeariaId));
        model.addAttribute("barbeariaId", barbeariaId);
        return "servico-lista";
    }

    @GetMapping("/novo/{barbeariaId}")
    public String mostrarFormularioCriacao(@PathVariable Long barbeariaId, Model model) {
        model.addAttribute("servico", new Servico());
        model.addAttribute("barbeariaId", barbeariaId);
        return "servico-novo";
    }

    @PostMapping("/novo/{barbeariaId}")
    public String processarCriacao(@PathVariable Long barbeariaId, @ModelAttribute Servico servico, Model model) {
        try {
            servicoService.criarServico(servico);
            return "redirect:/servico/barbearia/" + barbeariaId;
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            return "servico-novo";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        servicoService.buscarPorId(id).ifPresentOrElse(servico -> model.addAttribute("servico", servico), () -> model.addAttribute("erro", "Serviço não encontrado"));
        return "servico-editar";
    }

    @PostMapping("/editar/{id}")
    public String processarAtualizacao(@PathVariable Long id, @ModelAttribute Servico dadosAtualizados, Model model) {
        try {
            Servico servicoAtualizado = servicoService.atualizarServico(id, dadosAtualizados);
            return "redirect:/servico/barbearia/" + servicoAtualizado.getBarbearia().getId();
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            return "servico-editar";
        }
    }

    @PostMapping("/deletar/{id}")
    public String deletarServico(@PathVariable Long id, @RequestParam Long barbeariaId) {
        servicoService.deletarServico(id);
        return "redirect:/servico/barbearia/" + barbeariaId;
    }
    
}
