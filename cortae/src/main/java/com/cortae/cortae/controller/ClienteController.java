package com.cortae.cortae.controller;

import com.cortae.cortae.model.Cliente;
import com.cortae.cortae.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cliente")
public class ClienteController {


    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/barbearia/{barbeariaId}")
    public String listarClientes(@PathVariable Long barbeariaId, Model model) {
        List<Cliente> clientes = clienteService.listarPorBarbearia(barbeariaId);
        model.addAttribute("clientes", clientes);
        model.addAttribute("barbeariaId", barbeariaId);
        return "cliente-lista";
    }

    @GetMapping("/buscar")
    public String buscarPorNome(@RequestParam String nome, Model model) {
        model.addAttribute("clientes", clienteService.buscarPorNome(nome));
        return "cliente-lista";
    }

    @GetMapping("/novo/{barbeariaId}")
    public String mostrarFormularioCriacao(@PathVariable Long barbeariaId, Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("barbeariaId", barbeariaId);
        return "cliente-novo";
    }

    @PostMapping("/novo/{barbeariaId}")
    public String processarCriacao(@PathVariable Long barbeariaId, @ModelAttribute Cliente cliente, Model model) {
        try {
            clienteService.criarCliente(cliente);
            return "redirect:/cliente/barbearia/" + barbeariaId;
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            return "cliente-novo";
        }
    }

    @GetMapping("/{id}")
    public String mostrarDetalhes(@PathVariable Long id, Model model) {
        clienteService.buscarPorId(id).ifPresentOrElse(cliente -> {
            model.addAttribute("cliente", cliente);
            model.addAttribute("totalVisitas", clienteService.calcularTotalVisitas(id));
            model.addAttribute("valorTotalGasto", clienteService.calcularValorTotalGasto(id));
            model.addAttribute("ultimaVisita", clienteService.buscarUltimaVisita(id).orElse(null));

        }, () -> model.addAttribute("erro", "Cliente não encontrado."));

        return "cliente-detalhes";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        clienteService.buscarPorId(id).ifPresentOrElse(cliente -> model.addAttribute("cliente", cliente), () -> model.addAttribute("erro", "Cliente não encontrado."));
        return "cliente-editar";
    }

    @PostMapping("/editar/{id}")
    public String processarAtualizacao(@PathVariable Long id, @ModelAttribute Cliente dadosAtualizados, Model model) {
        try {
            Cliente clienteAtualizado = clienteService.atualizarCliente(id, dadosAtualizados);
            return "redirect:/cliente/" + clienteAtualizado.getId();
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            return "cliente-editar";
        }
    }
}
