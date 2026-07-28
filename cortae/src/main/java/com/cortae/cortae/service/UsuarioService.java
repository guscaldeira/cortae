package com.cortae.cortae.service;

import com.cortae.cortae.model.Usuario;
import com.cortae.cortae.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public Usuario cadastrarUsuario(Usuario novoUsuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(novoUsuario.getEmail());

        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("Já existe um usuário cadastrado com esse email.");
        } try {
            return usuarioRepository.save(novoUsuario);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Já existe um usuário cadastrado com esse email.");
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
