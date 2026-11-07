package com.concessionaria.sistemaveiculos.controller;

import com.concessionaria.sistemaveiculos.dto.VeiculoAtualizacaoRequest;
import com.concessionaria.sistemaveiculos.dto.VeiculoRequest;
import com.concessionaria.sistemaveiculos.model.StatusVeiculo;
import com.concessionaria.sistemaveiculos.model.TipoVeiculo;
import com.concessionaria.sistemaveiculos.model.Veiculo;
import com.concessionaria.sistemaveiculos.service.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/** Endpoints REST de veiculos: /api/veiculos */
@RestController
@RequestMapping("/api/veiculos")
@CrossOrigin(origins = "*")
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    /**
     * Lista com filtros opcionais. Exemplos:
     * GET /api/veiculos
     * GET /api/veiculos?marcaId=1&status=DISPONIVEL
     * GET /api/veiculos?precoMin=50000&precoMax=120000&anoMin=2020
     */
    @GetMapping
    public List<Veiculo> listar(@RequestParam(name = "marcaId", required = false) Long marcaId,
                                @RequestParam(name = "modeloId", required = false) Long modeloId,
                                @RequestParam(name = "tipo", required = false) TipoVeiculo tipo,
                                @RequestParam(name = "status", required = false) StatusVeiculo status,
                                @RequestParam(name = "precoMin", required = false) BigDecimal precoMin,
                                @RequestParam(name = "precoMax", required = false) BigDecimal precoMax,
                                @RequestParam(name = "anoMin", required = false) Integer anoMin,
                                @RequestParam(name = "anoMax", required = false) Integer anoMax) {
        return veiculoService.buscar(marcaId, modeloId, tipo, status, precoMin, precoMax, anoMin, anoMax);
    }

    @GetMapping("/{id}")
    public Veiculo buscar(@PathVariable("id") Long id) {
        return veiculoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Veiculo cadastrar(@RequestBody @Valid VeiculoRequest dados) {
        return veiculoService.cadastrar(dados);
    }

    @PutMapping("/{id}")
    public Veiculo atualizar(@PathVariable("id") Long id, @RequestBody @Valid VeiculoRequest dados) {
        return veiculoService.atualizar(id, dados);
    }

    /** Atualiza so preco, quilometragem e/ou status. */
    @PatchMapping("/{id}")
    public Veiculo atualizarParcial(@PathVariable("id") Long id, @RequestBody @Valid VeiculoAtualizacaoRequest dados) {
        return veiculoService.atualizarParcial(id, dados);
    }

    @PatchMapping("/{id}/vender")
    public Veiculo vender(@PathVariable("id") Long id) {
        return veiculoService.vender(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable("id") Long id) {
        veiculoService.remover(id);
    }
}
