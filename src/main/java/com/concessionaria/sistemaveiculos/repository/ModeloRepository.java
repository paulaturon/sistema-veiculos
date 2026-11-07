package com.concessionaria.sistemaveiculos.repository;

import com.concessionaria.sistemaveiculos.model.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {

    List<Modelo> findAllByOrderByNomeAsc();

    List<Modelo> findByMarcaIdOrderByNomeAsc(Long marcaId);

    boolean existsByMarcaId(Long marcaId);
}
