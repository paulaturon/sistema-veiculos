package com.concessionaria.sistemaveiculos.dto;

import com.concessionaria.sistemaveiculos.model.TipoVeiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/** Dados recebidos para cadastrar/editar um modelo. */
public record ModeloRequest(
        @NotBlank(message = "O nome do modelo e obrigatorio") String nome,
        @NotNull(message = "O tipo e obrigatorio") TipoVeiculo tipo,
        @NotNull(message = "A marca e obrigatoria") Long marcaId
) {
}
