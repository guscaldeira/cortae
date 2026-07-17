package com.cortae.cortae.repository;

import com.cortae.cortae.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServicoRepository  extends JpaRepository<Servico, Long> {
    
    List<Servico> findByBarbeariaId(Long barbeariaId);
}
