package com.cortae.cortae.service;

import com.cortae.cortae.model.Equipe;
import com.cortae.cortae.repository.EquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipeService {
    
    @Autowired
    private EquipeRepository equipeRepository;

    public Equipe criarMembro(Equipe novoMembro) {

        return equipeRepository.save(novoMembro);
    }

    public List<Equipe> listarPorBarbearia(Long barbeariaId) {

        return equipeRepository.findByBarbeariaId(barbeariaId);
    }

    public Optional<Equipe> buscarPorId(Long id) {

        return equipeRepository.findById(id);
    }

    public Equipe atualizarMembro(Long id, Equipe dadosAtualizados) {
        Equipe membro = equipeRepository.findById(id).orElseThrow(() -> new RuntimeException("Membro da equipe não encontrado."));

        membro.setNome(dadosAtualizados.getNome());
        membro.setCargo(dadosAtualizados.getCargo());
        membro.setEspecialidade(dadosAtualizados.getEspecialidade());
        membro.setAvaliacao(dadosAtualizados.getAvaliacao());
        membro.setFotoUrl(dadosAtualizados.getFotoUrl());

        return equipeRepository.save(membro);
    }

    public Equipe alterarStatus(Long id, Equipe.Status novoStatus) {
        Equipe membro = equipeRepository.findById(id).orElseThrow(() -> new RuntimeException("Membro da equipe não encontrado."));

        membro.setStatus(novoStatus);
        return equipeRepository.save(membro);
    }
}
