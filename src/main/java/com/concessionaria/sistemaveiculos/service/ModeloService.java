package com.concessionaria.sistemaveiculos.service;

import com.concessionaria.sistemaveiculos.dto.ModeloRequest;
import com.concessionaria.sistemaveiculos.exception.RecursoNaoEncontradoException;
import com.concessionaria.sistemaveiculos.exception.RegraNegocioException;
import com.concessionaria.sistemaveiculos.model.Marca;
import com.concessionaria.sistemaveiculos.model.Modelo;
import com.concessionaria.sistemaveiculos.repository.ModeloRepository;
import com.concessionaria.sistemaveiculos.repository.VeiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Regras de negocio dos modelos. */
@Service
public class ModeloService {

    private final ModeloRepository modeloRepository;
    private final VeiculoRepository veiculoRepository;
    private final MarcaService marcaService;

    public ModeloService(ModeloRepository modeloRepository, VeiculoRepository veiculoRepository,
                         MarcaService marcaService) {
        this.modeloRepository = modeloRepository;
        this.veiculoRepository = veiculoRepository;
        this.marcaService = marcaService;
    }

    public List<Modelo> listar(Long marcaId) {
        if (marcaId != null) {
            return modeloRepository.findByMarcaIdOrderByNomeAsc(marcaId);
        }
        return modeloRepository.findAllByOrderByNomeAsc();
    }

    public Modelo buscarPorId(Long id) {
        return modeloRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Modelo nao encontrado: id " + id));
    }

    @Transactional
    public Modelo cadastrar(ModeloRequest dados) {
        Marca marca = marcaService.buscarPorId(dados.marcaId());
        Modelo modelo = new Modelo(dados.nome(), dados.tipo(), marca);
        return modeloRepository.save(modelo);
    }

    @Transactional
    public Modelo atualizar(Long id, ModeloRequest dados) {
        Modelo modelo = buscarPorId(id);
        modelo.setNome(dados.nome());
        modelo.setTipo(dados.tipo());
        modelo.setMarca(marcaService.buscarPorId(dados.marcaId()));
        return modeloRepository.save(modelo);
    }

    @Transactional
    public void remover(Long id) {
        Modelo modelo = buscarPorId(id);
        if (veiculoRepository.existsByModeloId(id)) {
            throw new RegraNegocioException("Nao e possivel remover o modelo " + modelo.getNome()
                    + " porque existem veiculos cadastrados com ele.");
        }
        modeloRepository.delete(modelo);
    }
}
