package com.cortae.cortae.service;

import com.cortae.cortae.exception.NegocioException;
import com.cortae.cortae.model.Usuario;
import com.cortae.cortae.repository.UsuarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario cadastrarUsuario(Usuario novoUsuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(novoUsuario.getEmail());

        if (usuarioExistente.isPresent()) {
            throw new NegocioException("Já existe um usuário cadastrado com esse email.");
        }

        try {
            String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
            novoUsuario.setSenha(senhaCriptografada);

            return usuarioRepository.save(novoUsuario);
        } catch (DataIntegrityViolationException e) {
            throw new NegocioException("Já existe um usuário cadastrado com esse email.");
        }
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // --- BUSCAR POR ID ---
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
    
}
