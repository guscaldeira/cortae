package com.cortae.cortae.controller;

import com.cortae.cortae.model.Usuario;
import com.cortae.cortae.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
         this.usuarioService = usuarioService;
    }

    @GetMapping("/cadastro")
    public String mostrarCadastro(Model model) {

        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String processarCadastro(@ModelAttribute Usuario usuario, Model model) {
        try {
            usuarioService.cadastrarUsuario(usuario);
            return "redirect:/login"; 
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            return "cadastro";
        }
    }


    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; 
    }

    @PostMapping("/login")
    public String processarLogin(@ModelAttribute Usuario usuario, Model model) {
        
        Optional<Usuario> usuarioEncontrado = usuarioService.buscarPorEmail(usuario.getEmail());

        if (usuarioEncontrado.isPresent() && usuarioEncontrado.get().getSenha().equals(usuario.getSenha())) {
            
            return "redirect:/dashboard";
        } else {

            model.addAttribute("erro", "Email ou senha invalidos.");
            return "login";
        }
    }

}


