package com.concessionaria.sistemaveiculos.controller;

import com.concessionaria.sistemaveiculos.dto.ModeloRequest;
import com.concessionaria.sistemaveiculos.model.Modelo;
import com.concessionaria.sistemaveiculos.service.ModeloService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Endpoints REST de modelos: /api/modelos */
@RestController
@RequestMapping("/api/modelos")
@CrossOrigin(origins = "*")
public class ModeloController {

    private final ModeloService modeloService;

    public ModeloController(ModeloService modeloService) {
        this.modeloService = modeloService;
    }

    /** GET /api/modelos ou GET /api/modelos?marcaId=1 */
    @GetMapping
    public List<Modelo> listar(@RequestParam(name = "marcaId", required = false) Long marcaId) {
        return modeloService.listar(marcaId);
    }

    @GetMapping("/{id}")
    public Modelo buscar(@PathVariable("id") Long id) {
        return modeloService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Modelo cadastrar(@RequestBody @Valid ModeloRequest dados) {
        return modeloService.cadastrar(dados);
    }

    @PutMapping("/{id}")
    public Modelo atualizar(@PathVariable("id") Long id, @RequestBody @Valid ModeloRequest dados) {
        return modeloService.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable("id") Long id) {
        modeloService.remover(id);
    }
}
