package com.cortae.cortae.controller;

import com.cortae.cortae.exception.NegocioException;
import com.cortae.cortae.model.Barbearia;
import com.cortae.cortae.model.Servico;
import com.cortae.cortae.service.BarbeariaService;
import com.cortae.cortae.service.ServicoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/servico")
public class ServicoController {


    private final ServicoService servicoService;
    private final BarbeariaService barbeariaService;

    public ServicoController(ServicoService servicoService, BarbeariaService barbeariaService) {
        this.servicoService = servicoService;
        this.barbeariaService = barbeariaService;
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
            Barbearia barbearia = barbeariaService.buscarPorId(barbeariaId).orElseThrow(() -> new NegocioException("Barbearia não encontrada."));
            servico.setBarbearia(barbearia);
            servicoService.criarServico(servico);
            return "redirect:/servico/barbearia/" + barbeariaId;
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("barbeariaId", barbeariaId);
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
