package com.cortae.cortae.service;

import com.cortae.cortae.model.Agendamento;
import com.cortae.cortae.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService {


    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public Agendamento criarAgendamento(Agendamento novoAgendamento) {

        int duracaoMinutos = novoAgendamento.getServico().getDuracaoMinutos();
        LocalDateTime inicio = novoAgendamento.getDataHoraInicio();
        LocalDateTime fim = inicio.plusMinutes(duracaoMinutos);
        novoAgendamento.setDataHoraFim(fim);

        validarConflitoDeHorario(novoAgendamento.getBarbeiro().getId(), inicio, fim, null);

        novoAgendamento.setStatus(Agendamento.Status.PENDENTE);

        return agendamentoRepository.save(novoAgendamento);
    }

    private void validarConflitoDeHorario(Long barbeiroId, LocalDateTime novoInicio, LocalDateTime novoFim, Long agendamentoIdIgnorar) {

        List<Agendamento> agendamentosConflitantes = agendamentoRepository
                .findConflitosDeHorario(barbeiroId, novoInicio, novoFim);

        for (Agendamento existente : agendamentosConflitantes) {

            if (agendamentoIdIgnorar != null && existente.getId().equals(agendamentoIdIgnorar)) {
                continue;
            }

            if (existente.getStatus() == Agendamento.Status.CANCELADO) {
                continue;
            }

            throw new RuntimeException("Esse barbeiro já possui um agendamento nesse horário.");
        }
    }

    public List<Agendamento> listarPorBarbearia(Long barbeariaId) {
        return agendamentoRepository.findByBarbeariaId(barbeariaId);
    }

    public List<Agendamento> listarPorCliente(Long clienteId) {
        return agendamentoRepository.findByClienteId(clienteId);
    }

    public Optional<Agendamento> buscarPorId(Long id) {
        return agendamentoRepository.findById(id);
    }

    public Agendamento reagendar(Long id, LocalDateTime novoInicio) {
        Agendamento agendamento = agendamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Agendamento não encontrado."));

        if (agendamento.getStatus() == Agendamento.Status.CONCLUIDO || agendamento.getStatus() == Agendamento.Status.CANCELADO) {
            throw new RuntimeException("Não é possível reagendar um agendamento já " + agendamento.getStatus() + ".");
        }

        int duracaoMinutos = agendamento.getServico().getDuracaoMinutos();
        LocalDateTime novoFim = novoInicio.plusMinutes(duracaoMinutos);

        validarConflitoDeHorario(agendamento.getBarbeiro().getId(), novoInicio, novoFim, id);

        agendamento.setDataHoraInicio(novoInicio);
        agendamento.setDataHoraFim(novoFim);

        return agendamentoRepository.save(agendamento);
    }

    public Agendamento alterarStatus(Long id, Agendamento.Status novoStatus) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado."));

        Agendamento.Status statusAtual = agendamento.getStatus();

        if (statusAtual == Agendamento.Status.CONCLUIDO || statusAtual == Agendamento.Status.CANCELADO) {
            throw new RuntimeException(
                    "Não é possível alterar o status de um agendamento já " + statusAtual + ".");
        }

        agendamento.setStatus(novoStatus);
        return agendamentoRepository.save(agendamento);
    }

    public Agendamento cancelar(Long id) {
        return alterarStatus(id, Agendamento.Status.CANCELADO);
    }
}
