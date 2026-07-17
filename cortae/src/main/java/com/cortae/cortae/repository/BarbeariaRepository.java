package com.cortae.cortae.repository;

import com.cortae.cortae.model.Barbearia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface BarbeariaRepository extends JpaRepository<Barbearia, Long> {

    Optional<Barbearia> findByUsuarioId(Long usuarioId);
}
