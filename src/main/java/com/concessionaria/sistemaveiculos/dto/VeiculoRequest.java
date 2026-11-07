package com.concessionaria.sistemaveiculos.dto;

import com.concessionaria.sistemaveiculos.model.StatusVeiculo;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/** Dados recebidos para cadastrar ou editar (completo) um veiculo. */
public record VeiculoRequest(
        @NotNull(message = "O modelo e obrigatorio") Long modeloId,
        @NotNull(message = "O ano e obrigatorio")
        @Min(value = 1950, message = "Ano minimo: 1950")
        @Max(value = 2100, message = "Ano invalido") Integer ano,
        @NotBlank(message = "A cor e obrigatoria") String cor,
        @NotNull(message = "O preco e obrigatorio")
        @DecimalMin(value = "0.0", message = "O preco nao pode ser negativo") BigDecimal preco,
        @NotNull(message = "A quilometragem e obrigatoria")
        @Min(value = 0, message = "A quilometragem nao pode ser negativa") Integer quilometragem,
        StatusVeiculo status
) {
}
