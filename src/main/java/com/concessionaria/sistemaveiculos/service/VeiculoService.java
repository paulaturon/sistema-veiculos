package com.concessionaria.sistemaveiculos.service;

import com.concessionaria.sistemaveiculos.dto.VeiculoAtualizacaoRequest;
import com.concessionaria.sistemaveiculos.dto.VeiculoRequest;
import com.concessionaria.sistemaveiculos.exception.RecursoNaoEncontradoException;
import com.concessionaria.sistemaveiculos.exception.RegraNegocioException;
import com.concessionaria.sistemaveiculos.model.Modelo;
import com.concessionaria.sistemaveiculos.model.StatusVeiculo;
import com.concessionaria.sistemaveiculos.model.TipoVeiculo;
import com.concessionaria.sistemaveiculos.model.Veiculo;
import com.concessionaria.sistemaveiculos.repository.VeiculoRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Regras de negocio dos veiculos (CRUD + filtros). */
@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ModeloService modeloService;

    public VeiculoService(VeiculoRepository veiculoRepository, ModeloService modeloService) {
        this.veiculoRepository = veiculoRepository;
        this.modeloService = modeloService;
    }

    /**
     * Consulta com filtros opcionais. So os filtros preenchidos (diferentes de null)
     * entram na consulta SQL.
     */
    public List<Veiculo> buscar(Long marcaId, Long modeloId, TipoVeiculo tipo, StatusVeiculo status,
                                BigDecimal precoMin, BigDecimal precoMax, Integer anoMin, Integer anoMax) {

        if (precoMin != null && precoMax != null && precoMin.compareTo(precoMax) > 0) {
            throw new RegraNegocioException("O preco minimo nao pode ser maior que o preco maximo.");
        }
        if (anoMin != null && anoMax != null && anoMin > anoMax) {
            throw new RegraNegocioException("O ano minimo nao pode ser maior que o ano maximo.");
        }

        Specification<Veiculo> filtros = (root, query, cb) -> {
            List<Predicate> condicoes = new ArrayList<>();
            var modelo = root.join("modelo");

            if (marcaId != null) {
                condicoes.add(cb.equal(modelo.get("marca").get("id"), marcaId));
            }
            if (modeloId != null) {
                condicoes.add(cb.equal(modelo.get("id"), modeloId));
            }
            if (tipo != null) {
                condicoes.add(cb.equal(modelo.get("tipo"), tipo));
            }
            if (status != null) {
                condicoes.add(cb.equal(root.get("status"), status));
            }
            if (precoMin != null) {
                condicoes.add(cb.greaterThanOrEqualTo(root.<BigDecimal>get("preco"), precoMin));
            }
            if (precoMax != null) {
                condicoes.add(cb.lessThanOrEqualTo(root.<BigDecimal>get("preco"), precoMax));
            }
            if (anoMin != null) {
                condicoes.add(cb.greaterThanOrEqualTo(root.<Integer>get("ano"), anoMin));
            }
            if (anoMax != null) {
                condicoes.add(cb.lessThanOrEqualTo(root.<Integer>get("ano"), anoMax));
            }
            return cb.and(condicoes.toArray(new Predicate[0]));
        };

        return veiculoRepository.findAll(filtros, Sort.by("id"));
    }

    public Veiculo buscarPorId(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Veiculo nao encontrado: id " + id));
    }

    @Transactional
    public Veiculo cadastrar(VeiculoRequest dados) {
        Veiculo veiculo = new Veiculo();
        preencher(veiculo, dados);
        if (veiculo.getStatus() == null) {
            veiculo.setStatus(StatusVeiculo.DISPONIVEL); // padrao ao cadastrar
        }
        return veiculoRepository.save(veiculo);
    }

    /** PUT: atualiza todos os dados do veiculo. */
    @Transactional
    public Veiculo atualizar(Long id, VeiculoRequest dados) {
        Veiculo veiculo = buscarPorId(id);
        preencher(veiculo, dados);
        return veiculoRepository.save(veiculo);
    }

    /** PATCH: atualiza apenas preco, quilometragem e/ou status. */
    @Transactional
    public Veiculo atualizarParcial(Long id, VeiculoAtualizacaoRequest dados) {
        Veiculo veiculo = buscarPorId(id);
        if (dados.preco() != null) {
            veiculo.setPreco(dados.preco());
        }
        if (dados.quilometragem() != null) {
            if (dados.quilometragem() < veiculo.getQuilometragem()) {
                throw new RegraNegocioException("A nova quilometragem nao pode ser menor que a atual ("
                        + veiculo.getQuilometragem() + " km).");
            }
            veiculo.setQuilometragem(dados.quilometragem());
        }
        if (dados.status() != null) {
            veiculo.setStatus(dados.status());
        }
        return veiculoRepository.save(veiculo);
    }

    /** Marca como vendido (atalho usado pelo botao "Vender"). */
    @Transactional
    public Veiculo vender(Long id) {
        Veiculo veiculo = buscarPorId(id);
        if (!veiculo.estaDisponivel() && veiculo.getStatus() != StatusVeiculo.RESERVADO) {
            throw new RegraNegocioException("So e possivel vender veiculos disponiveis ou reservados.");
        }
        veiculo.vender();
        return veiculoRepository.save(veiculo);
    }

    @Transactional
    public void remover(Long id) {
        Veiculo veiculo = buscarPorId(id);
        veiculoRepository.delete(veiculo);
    }

    private void preencher(Veiculo veiculo, VeiculoRequest dados) {
        Modelo modelo = modeloService.buscarPorId(dados.modeloId());
        veiculo.setModelo(modelo);
        veiculo.setAno(dados.ano());
        veiculo.setCor(dados.cor());
        veiculo.setPreco(dados.preco());
        veiculo.setQuilometragem(dados.quilometragem());
        if (dados.status() != null) {
            veiculo.setStatus(dados.status());
        }
    }
}
