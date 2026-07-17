package com.cortae.cortae.repository;

import com.cortae.cortae.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    
    List<Agendamento> findByBarbeariaId(Long barbeariaId);

    List<Agendamento> findByClienteId(Long clienteId);

    List<Agendamento> findByBarbeiroIdAndDataHoraInicioBetween(
        Long barbeiroId, LocalDateTime inicio, LocalDateTime fim
    );
}
