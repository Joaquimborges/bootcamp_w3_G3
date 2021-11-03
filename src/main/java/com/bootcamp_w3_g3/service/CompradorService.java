package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.model.entity.Comprador;
import com.bootcamp_w3_g3.repository.CompradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 *
 * @author Alex Cruz
 */
@Service
public class CompradorService {


    private CompradorRepository compradorRepository;

    @Autowired
    public CompradorService(CompradorRepository compradorRepository){
        this.compradorRepository = compradorRepository;
    }

    @Transactional
    public Comprador salvar(Comprador comprador) { return compradorRepository.save(comprador); }

    public Comprador obter(String usuario) {
        return compradorRepository.findByUsuario(usuario);
    }

    public List<Comprador> listar() {
        return compradorRepository.findAll();
    }

    public Comprador atualizar(Comprador comprador) {
        Comprador compradorEdited = compradorRepository.findByUsuario(comprador.getUsuario());
        compradorEdited.setUsuario(comprador.getUsuario());
        compradorEdited.setSenha(comprador.getSenha());

        return compradorRepository.save(compradorEdited);
    }

}
