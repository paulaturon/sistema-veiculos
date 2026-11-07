package com.concessionaria.sistemaveiculos.exception;

/** Lancada quando uma regra do sistema e violada (vira erro HTTP 400). */
public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
