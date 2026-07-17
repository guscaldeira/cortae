package com.cortae.cortae.repository;

import com.cortae.cortae.model.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EquipeRepository extends JpaRepository<Equipe, Long> {
 
    List<Equipe> findByBarbeariaId(Long barbeariaId);
}
