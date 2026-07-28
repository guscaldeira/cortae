package com.cortae.cortae.service;

import com.cortae.cortae.model.Agendamento;
import com.cortae.cortae.model.Cliente;
import com.cortae.cortae.repository.AgendamentoRepository;
import com.cortae.cortae.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public Cliente criarCliente(Cliente novoCliente) {
        
        return clienteRepository.save(novoCliente);
    }    

    public List<Cliente> listarPorBarbearia(Long barbeariaId) {

        return clienteRepository.findByBarbeariaId(barbeariaId);
    }

    public List<Cliente> buscarPorNome(String nome) {

        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Optional<Cliente> buscarPorId(Long id) {

        return clienteRepository.findById(id);
    }

    public Cliente atualizarCliente(Long id, Cliente dadosatualizados) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado."));

        cliente.setNome(dadosatualizados.getNome());
        cliente.setTelefone(dadosatualizados.getTelefone());
        cliente.setEmail(dadosatualizados.getEmail());
        cliente.setDataNascimento(dadosatualizados.getDataNascimento());
        cliente.setObservacoes(dadosatualizados.getObservacoes());

        return clienteRepository.save(cliente);
    }

    public long calcularTotalVisitas(Long clienteId) {

        List<Agendamento> agendamentos = agendamentoRepository.findByClienteId(clienteId);

        return agendamentos.stream().filter(a -> a.getStatus() == Agendamento.Status.CONCLUIDO).count();
    }

    public BigDecimal calcularValorTotalGasto(Long clienteId) {

        List<Agendamento> agendamentos = agendamentoRepository.findByClienteId(clienteId);

        return agendamentos.stream().filter(a -> a.getStatus() == Agendamento.Status.CONCLUIDO).map(Agendamento::getValorCobrado).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Optional<LocalDateTime> buscarUltimaVisita(Long clienteId) {

        List<Agendamento> agendamentos = agendamentoRepository.findByClienteId(clienteId);

        return agendamentos.stream().filter(a -> a.getStatus() == Agendamento.Status.CONCLUIDO).map(Agendamento::getDataHoraInicio).max(Comparator.naturalOrder());
    }
}
