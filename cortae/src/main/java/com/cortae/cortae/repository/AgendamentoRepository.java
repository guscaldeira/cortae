package com.cortae.cortae.repository;

import com.cortae.cortae.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    
    List<Agendamento> findByBarbeariaId(Long barbeariaId);

    List<Agendamento> findByClienteId(Long clienteId);

    List<Agendamento> findByBarbeiroIdAndDataHoraInicioBetween(
        Long barbeiroId, LocalDateTime inicio, LocalDateTime fim
    );
    @Query("SELECT a FROM Agendamento a " + "WHERE a.barbeiro.id = :barbeiroId " + "AND a.dataHoraInicio < :novoFim " +"AND a.dataHoraFim > :novoInicio")List<Agendamento> findConflitosDeHorario(@Param("barbeiroId") Long barbeiroId, @Param("novoInicio") LocalDateTime novoInicio,@Param("novoFim") LocalDateTime novoFim);
}
