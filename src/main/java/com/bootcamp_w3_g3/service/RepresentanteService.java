package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.model.entity.Representante;
import com.bootcamp_w3_g3.repository.RepresentanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Classe RepresentanteService criada para implmentação do CRUD através das chamadas em métodos da representanteRepository.
 * Bem como aplicar camada dde regra de negócios neccessária.
 *
 * @author Alex Cruz
 */
@Service
public class RepresentanteService {

    private final RepresentanteRepository representanteRepository;

    @Autowired
    public RepresentanteService(RepresentanteRepository representanteRepository) {
        this.representanteRepository = representanteRepository;
    }

    public Representante salvar(Representante representante) {
        return representanteRepository.save(representante);
    }

    public Representante obter(Integer codigo) { return representanteRepository.findByCodigo(codigo); }

    public List<Representante> listar() {
        return representanteRepository.findAll();
    }

    public Representante atualizar(Representante representante) {
        Representante representanteEdited = representanteRepository.getByCodigo(representante.getCodigo());
        representanteEdited.setTelefone(representante.getTelefone());
        representanteEdited.setEndereco(representante.getEndereco());

        return representanteRepository.save(representanteEdited);
    }

    public Representante apagar(Integer codigo) {
        return representanteRepository.deleteByCodigo(codigo);
    }
}
