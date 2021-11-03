package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.advisor.EntityNotFoundException;
import com.bootcamp_w3_g3.model.entity.Representante;
import com.bootcamp_w3_g3.repository.RepresentanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public Representante salvar(Representante representante) {
        return representanteRepository.save(representante);
    }

    public Representante obter(String codigo) {
        Representante representante = representanteRepository.findByCodigo(codigo);
        if (representante != null ){
            return representante;
        }
        throw new EntityNotFoundException("representante não encontrado");
    }

    public List<Representante> listar() {
        return representanteRepository.findAll();
    }

    public Representante atualizar(Representante representante) {
        Representante representanteEdited = representanteRepository.getByCodigo(representante.getCodigo());
        representanteEdited.setNome(representante.getNome());
        representanteEdited.setSobrenome(representante.getSobrenome());
        representanteEdited.setTelefone(representante.getTelefone());
        representanteEdited.setEndereco(representante.getEndereco());

        return representanteRepository.save(representanteEdited);
    }

    public Representante apagar(Long id){
        representanteRepository.deleteById(id);
        return null;
    }
}
