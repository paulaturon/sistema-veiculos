package com.concessionaria.sistemaveiculos.repository;

import com.concessionaria.sistemaveiculos.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * JpaSpecificationExecutor permite montar consultas com filtros dinamicos
 * (usado na busca por marca, modelo, preco, ano, status).
 */
public interface VeiculoRepository extends JpaRepository<Veiculo, Long>, JpaSpecificationExecutor<Veiculo> {

    boolean existsByModeloId(Long modeloId);
}
