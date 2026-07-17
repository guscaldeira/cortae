package com.cortae.cortae.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clientes")
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String telefone;

    private String email;

    private LocalDate dataNascimento;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVO;

    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "barbearia_id", nullable = false)
    private Barbearia barbearia;

    @ManyToOne
    @JoinColumn(name = "barbeiro_preferido_id")
    private Equipe barbeiroPreferido;

    @ManyToMany
    @JoinTable(
        name = "cliente_servico_interesse", 
        joinColumns = @JoinColumn(name = "cliente_id"),
        inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    private Set<Servico> servicos = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }
    
    public enum Status {
        ATIVO, INATIVO
    }
}
