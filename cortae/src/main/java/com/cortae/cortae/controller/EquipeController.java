package com.cortae.cortae.controller;

import com.cortae.cortae.model.Equipe;
import com.cortae.cortae.service.EquipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/equipe")
public class EquipeController {

    private final EquipeService equipeService;

    public EquipeController(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    @GetMapping("/barbearia/{barbeariaId}")
    public String listarEquipe(@PathVariable Long barbeariaId, Model model) {
        model.addAttribute("equipe", equipeService.listarPorBarbearia(barbeariaId));
        model.addAttribute("barbeariaId", barbeariaId);
        return "equipe-lista";
    }

    @GetMapping("/nova/{barbeariaId}")
    public String mostrarFormularioCriacao(@PathVariable Long barbeariaId, Model model) {
        model.addAttribute("membro", new Equipe());
        model.addAttribute("barbeariaId", barbeariaId);
        return "equipe-nova";
    }

    @PostMapping("/nova/{barbeariaId}")
    public String processarCriacao(@PathVariable Long barbeariaId, @ModelAttribute Equipe membro, Model model) {
        try {
            equipeService.criarMembro(membro);
            return "redirect:/equipe/barbearia/" + barbeariaId;
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            return "equipe-nova";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        equipeService.buscarPorId(id).ifPresentOrElse(
                membro -> model.addAttribute("membro", membro),
                () -> model.addAttribute("erro", "Membro não encontrado.")
        );
        return "equipe-editar";
    }

    @PostMapping("/editar/{id}")
    public String processarAtualizacao(@PathVariable Long id, @ModelAttribute Equipe dadosAtualizados, Model model) {
        try {
            Equipe membroAtualizado = equipeService.atualizarMembro(id, dadosAtualizados);
            return "redirect:/equipe/barbearia/" + membroAtualizado.getBarbearia().getId();
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            return "equipe-editar";
        }
    }

    @PostMapping("/status/{id}")
    public String alterarStatus(@PathVariable Long id, @RequestParam Equipe.Status novoStatus) {
        Equipe membro = equipeService.alterarStatus(id, novoStatus);
        return "redirect:/equipe/barbearia/" + membro.getBarbearia().getId();
    }
    
}
