package com.cortae.cortae.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos")
@Data
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataHoraInicio;

    @Column(nullable = false)
    private LocalDateTime dataHoraFim;

    @Column(nullable = false)
    private BigDecimal valorCobrado;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDENTE;

    @Column(columnDefinition = "TEXT")
    private String observacoes; 

    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "equipe_id", nullable = false)
    private Equipe barbeiro;

    @ManyToOne
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;

    @ManyToOne
    @JoinColumn(name = "barbearia_id", nullable = false)
    private Barbearia barbearia;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

    public enum Status {
        PENDENTE, CONFIRMADO, EM_PROGRESSO, CONCLUIDO, CANCELADO
    }

    
}
