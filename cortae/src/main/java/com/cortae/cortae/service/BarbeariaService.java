package com.cortae.cortae.service;

import com.cortae.cortae.model.Barbearia;
import com.cortae.cortae.repository.BarbeariaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BarbeariaService {

    private final BarbeariaRepository barbeariaRepository;

    public BarbeariaService(BarbeariaRepository barbeariaRepository) {
        this.barbeariaRepository = barbeariaRepository;
    }

    public Barbearia criarBarbearia(Barbearia novaBarbearia) {

        Long usuarioId = novaBarbearia.getUsuario().getId();

        Optional<Barbearia> barbeariaExistente = barbeariaRepository.findByUsuarioId(usuarioId);
    
        if (barbeariaExistente.isPresent()) {
        throw new RuntimeException("Esse usuário já possuiu uma barbearia cadastrada.");

        }

        return barbeariaRepository.save(novaBarbearia);
    }

    public Optional<Barbearia> buscarPorUsuarioId(Long usuarioId) {
        return barbeariaRepository.findByUsuarioId(usuarioId);
    }

    public Optional<Barbearia> buscarPorId (Long id) {
        return barbeariaRepository.findById(id);
    }

    public Barbearia atualizarBarbearia(Long id, Barbearia dadosAtualizados) {

        Barbearia barbearia = barbeariaRepository.findById(id).orElseThrow(() -> new RuntimeException("Barbearia não encontrada."));

        barbearia.setNomeBarbearia(dadosAtualizados.getNomeBarbearia());
        barbearia.setCnpj(dadosAtualizados.getCnpj());
        barbearia.setCep(dadosAtualizados.getCep());
        barbearia.setRua(dadosAtualizados.getRua());
        barbearia.setNumero(dadosAtualizados.getNumero());
        barbearia.setBairro(dadosAtualizados.getBairro());
        barbearia.setCidade(dadosAtualizados.getCidade());
        barbearia.setEstado(dadosAtualizados.getEstado());
        barbearia.setLogoUrl(dadosAtualizados.getLogoUrl());
        barbearia.setTema(dadosAtualizados.getTema());

        return barbeariaRepository.save(barbearia);
    }

}
