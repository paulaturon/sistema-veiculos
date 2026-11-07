package com.concessionaria.sistemaveiculos.repository;

import com.concessionaria.sistemaveiculos.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio: o Spring cria sozinho os metodos de banco
 * (save, findAll, findById, deleteById...).
 */
public interface MarcaRepository extends JpaRepository<Marca, Long> {

    List<Marca> findAllByOrderByNomeAsc();

    boolean existsByNomeIgnoreCase(String nome);
}
