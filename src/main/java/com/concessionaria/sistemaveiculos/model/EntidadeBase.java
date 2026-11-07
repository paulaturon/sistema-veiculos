package com.concessionaria.sistemaveiculos.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

/**
 * HERANCA: classe abstrata (superclasse) com os atributos comuns
 * a todas as entidades. Marca, Modelo e Veiculo herdam dela (extends),
 * entao nao precisamos repetir id e dataCadastro em cada classe.
 */
@MappedSuperclass
public abstract class EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_cadastro", updatable = false)
    private LocalDateTime dataCadastro;

    /** Executado automaticamente antes de salvar pela primeira vez. */
    @PrePersist
    protected void aoCadastrar() {
        this.dataCadastro = LocalDateTime.now();
    }

    // ENCAPSULAMENTO: atributos privados, acesso apenas por getters/setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
}
