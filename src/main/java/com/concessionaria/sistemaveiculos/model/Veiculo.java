package com.concessionaria.sistemaveiculos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

/**
 * Veiculo do estoque. A marca e obtida atraves do modelo
 * (veiculo -> modelo -> marca).
 */
@Entity
@Table(name = "veiculo")
public class Veiculo extends EntidadeBase {

    @ManyToOne(optional = false)
    @JoinColumn(name = "modelo_id", nullable = false)
    private Modelo modelo;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false, length = 30)
    private String cor;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal preco;

    @Column(nullable = false)
    private Integer quilometragem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusVeiculo status;

    public Veiculo() {
    }

    // ===== Metodos de negocio (comportamento do objeto) =====

    /** Marca o veiculo como vendido. */
    public void vender() {
        this.status = StatusVeiculo.VENDIDO;
    }

    /** Indica se o veiculo pode ser vendido. */
    public boolean estaDisponivel() {
        return this.status == StatusVeiculo.DISPONIVEL;
    }

    /** Atalho para a marca, usado no JSON de resposta. */
    public Marca getMarca() {
        return modelo != null ? modelo.getMarca() : null;
    }

    // ===== Getters e Setters =====

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(Integer quilometragem) {
        this.quilometragem = quilometragem;
    }

    public StatusVeiculo getStatus() {
        return status;
    }

    public void setStatus(StatusVeiculo status) {
        this.status = status;
    }
}
