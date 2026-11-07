package com.concessionaria.sistemaveiculos.service;

import com.concessionaria.sistemaveiculos.exception.RecursoNaoEncontradoException;
import com.concessionaria.sistemaveiculos.exception.RegraNegocioException;
import com.concessionaria.sistemaveiculos.model.Marca;
import com.concessionaria.sistemaveiculos.repository.MarcaRepository;
import com.concessionaria.sistemaveiculos.repository.ModeloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Regras de negocio das marcas. */
@Service
public class MarcaService {

    private final MarcaRepository marcaRepository;
    private final ModeloRepository modeloRepository;

    public MarcaService(MarcaRepository marcaRepository, ModeloRepository modeloRepository) {
        this.marcaRepository = marcaRepository;
        this.modeloRepository = modeloRepository;
    }

    public List<Marca> listar() {
        return marcaRepository.findAllByOrderByNomeAsc();
    }

    public Marca buscarPorId(Long id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Marca nao encontrada: id " + id));
    }

    @Transactional
    public Marca cadastrar(Marca marca) {
        if (marcaRepository.existsByNomeIgnoreCase(marca.getNome())) {
            throw new RegraNegocioException("Ja existe uma marca com o nome " + marca.getNome());
        }
        marca.setId(null);
        return marcaRepository.save(marca);
    }

    @Transactional
    public Marca atualizar(Long id, Marca dados) {
        Marca marca = buscarPorId(id);
        marca.setNome(dados.getNome());
        marca.setPaisOrigem(dados.getPaisOrigem());
        return marcaRepository.save(marca);
    }

    @Transactional
    public void remover(Long id) {
        Marca marca = buscarPorId(id);
        if (modeloRepository.existsByMarcaId(id)) {
            throw new RegraNegocioException("Nao e possivel remover a marca " + marca.getNome()
                    + " porque existem modelos cadastrados nela.");
        }
        marcaRepository.delete(marca);
    }
}
