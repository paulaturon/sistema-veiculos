package com.concessionaria.sistemaveiculos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

/** Marca do veiculo (ex.: Toyota, Honda, Fiat). */
@Entity
@Table(name = "marca")
public class Marca extends EntidadeBase {

    @NotBlank(message = "O nome da marca e obrigatorio")
    @Column(nullable = false, unique = true, length = 60)
    private String nome;

    @Column(name = "pais_origem", length = 60)
    private String paisOrigem;

    public Marca() {
    }

    public Marca(String nome, String paisOrigem) {
        this.nome = nome;
        this.paisOrigem = paisOrigem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPaisOrigem() {
        return paisOrigem;
    }

    public void setPaisOrigem(String paisOrigem) {
        this.paisOrigem = paisOrigem;
    }
}
