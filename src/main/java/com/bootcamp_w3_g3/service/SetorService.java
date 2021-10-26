package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.model.entity.Setor;
import com.bootcamp_w3_g3.repository.SetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author hugo damm
 */

@Service
public class SetorService {

    private final SetorRepository setorRepository;

    @Autowired
    public SetorService(SetorRepository setorRepository){
        this.setorRepository = setorRepository;
    }

    public Setor salvarSetor(Setor setor){
        return setorRepository.save(setor);
    }

    public Setor obterSetor(String codigo){
        return setorRepository.findByCodigo(codigo);
    }

    public List<Setor> listarSetores(){
        return setorRepository.findAll();
    }

    public Setor atualizarSetor(Setor setor){
        Setor editedSetor = setorRepository.findByCodigo(setor.getCodigo());

        editedSetor.setCodigo(setor.getCodigo());
        editedSetor.setNome(setor.getNome());
        editedSetor.setTipoProduto(setor.getTipoProduto());
        editedSetor.setArmazem(setor.getArmazem());

        return setorRepository.save(editedSetor);
    }

    public Setor removerSetor(String codigo) {
        return setorRepository.deleteByCodigo(codigo);
    }

}
