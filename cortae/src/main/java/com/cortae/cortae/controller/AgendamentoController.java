package com.cortae.cortae.controller;

import com.cortae.cortae.model.Agendamento;
import com.cortae.cortae.model.Barbearia;
import com.cortae.cortae.service.AgendamentoService;
import com.cortae.cortae.service.BarbeariaService;
import com.cortae.cortae.exception.NegocioException;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/agendamento")
public class AgendamentoController {
    
    private final AgendamentoService agendamentoService;
    private final BarbeariaService barbeariaService;

    public AgendamentoController(AgendamentoService agendamentoService, BarbeariaService barbeariaService) {
        this.agendamentoService = agendamentoService;
        this.barbeariaService = barbeariaService;
    }

    @GetMapping("/barbearia/{barbeariaId}")
    public String listarPorBarbearia(@PathVariable Long barbeariaId, Model model) {
        model.addAttribute("agendamentos", agendamentoService.listarPorBarbearia(barbeariaId));
        model.addAttribute("barbeariaId", barbeariaId);
        return "agendamento-lista";
    }

    @GetMapping("/cliente/{clienteId}")
    public String listarPorCliente(@PathVariable Long clienteId, Model model) {
        model.addAttribute("agendamentos", agendamentoService.listarPorCliente(clienteId));
        return "agendamento-historico-cliente";
    }

    @GetMapping("/novo/{barbeariaId}")
    public String mostrarFormularioCriacao(@PathVariable Long barbeariaId, Model model) {
        model.addAttribute("agendamento", new Agendamento());
        model.addAttribute("barbeariaId", barbeariaId);
        return "agendamento-novo";
    }

    @PostMapping("/novo/{barbeariaId}")
    public String processarCriacao(@PathVariable Long barbeariaId, @ModelAttribute Agendamento agendamento, Model model) {
        try {
            Barbearia barbearia = barbeariaService.buscarPorId(barbeariaId).orElseThrow(() -> new NegocioException("Barbearia não encontrada."));
            agendamento.setBarbearia(barbearia);
            agendamentoService.criarAgendamento(agendamento);
            return "redirect:/agendamento/barbearia/" + barbeariaId;
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("barbeariaId", barbeariaId);
            return "agendamento-novo";
        }
    }

    @PostMapping("/reagendar/{id}")
    public String reagendar(@PathVariable Long id, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime novoInicio, @RequestParam Long barbeariaId, RedirectAttributes redirectAttributes) {
        try {
            agendamentoService.reagendar(id, novoInicio);
            return "redirect:/agendamento/barbearia/" + barbeariaId;
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/agendamento/barbearia/" + barbeariaId;
        }
    }

    @PostMapping("/status/{id}")
    public String alterarStatus(@PathVariable Long id, @RequestParam Agendamento.Status novoStatus, @RequestParam Long barbeariaId, RedirectAttributes redirectAttributes) {
        try {
            agendamentoService.alterarStatus(id, novoStatus);
            return "redirect:/agendamento/barbearia/" + barbeariaId;
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/agendamento/barbearia/" + barbeariaId;
        }
    }

    @PostMapping("/cancelar/{id}")
    public String cancelar(@PathVariable Long id, @RequestParam Long barbeariaId) {
        agendamentoService.cancelar(id);
        return "redirect:/agendamento/barbearia/" + barbeariaId;
    }
}
