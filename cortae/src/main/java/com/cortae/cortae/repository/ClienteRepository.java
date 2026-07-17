package com.cortae.cortae.repository;

import com.cortae.cortae.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    List<Cliente> findByBarbeariaId(Long barbeariaId);

    List<Cliente> findByNomeContainingIgnoreCase(String nome);
}
