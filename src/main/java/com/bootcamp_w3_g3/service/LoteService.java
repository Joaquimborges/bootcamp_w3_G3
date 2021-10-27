package com.bootcamp_w3_g3.service;


import com.bootcamp_w3_g3.model.entity.Lote;
import com.bootcamp_w3_g3.repository.LoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


/**
 * @author Joaquim Borges
 * @autor  Alex Cruz
 */

@Service
public class LoteService {


    private final LoteRepository loteRepository;

    private final ArmazemService armazemService;

    @Autowired
    public LoteService(LoteRepository loteRepository, ArmazemService armazemService) {
        this.loteRepository = loteRepository;
        this.armazemService = armazemService;
    }




    @Transactional
    public Lote salvar(Lote lote) {
        return loteRepository.save(lote);
    }

    public Lote obter(Integer numeroDoLote) {
        return loteRepository.findByNumero(numeroDoLote);
    }

    public List<Lote> listar() {
        return loteRepository.findAll();
    }

    public Lote atualizar(Lote lote) {
        Lote editedLote = loteRepository.findByNumero(lote.getNumero());

        editedLote.setTemperaturaAtual(lote.getTemperaturaAtual());
        editedLote.setTemperaturaMinima(lote.getTemperaturaMinima());
        editedLote.setQuantidadeAtual(lote.getQuantidadeAtual());
        editedLote.setQuantidadeMinina(lote.getQuantidadeMinina());
        editedLote.setDataDeFabricacao(lote.getDataDeFabricacao());
        editedLote.setHoraFabricacao(lote.getHoraFabricacao());
        editedLote.setDataDeValidade(lote.getDataDeValidade());

        return loteRepository.save(editedLote);
    }

    public Lote apagar(Integer numeroDoLote) {
        return loteRepository.deleteByNumero(numeroDoLote);
    }



}
