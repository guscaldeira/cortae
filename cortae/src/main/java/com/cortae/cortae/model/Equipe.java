package com.cortae.cortae.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "equipe")
@Data
public class Equipe {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cargo;

    private String especialidade;

    private Double avaliacao;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVO;

    private String fotoUrl;

    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "barbearia_id", nullable = false)
    private Barbearia barbearia;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

    public enum Status {
        ATIVO, INATIVO
    }
}
