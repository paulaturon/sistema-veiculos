package com.concessionaria.sistemaveiculos.exception;

/** Lancada quando um id nao existe no banco (vira erro HTTP 404). */
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
