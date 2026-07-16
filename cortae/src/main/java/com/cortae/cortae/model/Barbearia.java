package com.cortae.cortae.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "barbearias")
@Data
public class Barbearia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(nullable = false)
    private String nomeBarbearia;

    private String cnpj;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String rua;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    private String logoUrl;

    @Enumerated(EnumType.STRING)
    private Tema tema = Tema.ESCURO;

    private LocalDateTime dataCriacao;

    @OneToOne

    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;
    
    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

    public enum Tema {
        CLARO, ESCURO
    }
}
