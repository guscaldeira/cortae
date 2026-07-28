package com.cortae.cortae.service;

import com.cortae.cortae.model.Servico;
import com.cortae.cortae.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    public Servico criarServico(Servico novoServico) {

        return servicoRepository.save(novoServico);
    }

    public List<Servico> listarPorBarbearia(Long barbeariaId) {

        return servicoRepository.findByBarbeariaId(barbeariaId);
    }

    public Optional<Servico> buscarPorId(Long id) {

        return servicoRepository.findById(id);
    }
    
    public Servico atualizarServico(Long id, Servico dadosAtualizados) {

        Servico servico = servicoRepository.findById(id).orElseThrow(() -> new RuntimeException("Serviço não encontrado."));

        servico.setNome(dadosAtualizados.getNome());
        servico.setPreco(dadosAtualizados.getPreco());
        servico.setDuracaoMinutos(dadosAtualizados.getDuracaoMinutos());

        return servicoRepository.save(servico);
    }

    public void deletarServico(Long id) {

        if (!servicoRepository.existsById(id)) {
            
            throw new RuntimeException("Serviço não encontrado.");
        }
        servicoRepository.deleteById(id);
    }
}
