package com.concessionaria.sistemaveiculos.dto;

import com.concessionaria.sistemaveiculos.model.StatusVeiculo;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

/**
 * Atualizacao parcial (PATCH): so preco, quilometragem e/ou status.
 * Campos nao enviados (null) continuam como estao.
 */
public record VeiculoAtualizacaoRequest(
        @DecimalMin(value = "0.0", message = "O preco nao pode ser negativo") BigDecimal preco,
        @Min(value = 0, message = "A quilometragem nao pode ser negativa") Integer quilometragem,
        StatusVeiculo status
) {
}
