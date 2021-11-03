package com.bootcamp_w3_g3.service;


import com.bootcamp_w3_g3.advisor.EntityNotFoundException;
import com.bootcamp_w3_g3.model.entity.Lote;
import com.bootcamp_w3_g3.model.entity.Produto;
import com.bootcamp_w3_g3.model.entity.Setor;
import com.bootcamp_w3_g3.repository.LoteRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Joaquim Borges
 * @autor  Alex Cruz
 */
@AllArgsConstructor

@Service
public class LoteService {



    private LoteRepository loteRepository;



    private ArmazemService armazemService;



    private ProdutoService produtoService;


    private SetorService setorService;

    @Autowired
    public LoteService(LoteRepository loteRepository, ProdutoService produtoService, SetorService setorService)
    {
        this.loteRepository = loteRepository;
        this.produtoService = produtoService;
        this. setorService = setorService;
    }



    @Transactional
    public Lote salvar( Lote lote) {
        Produto produto = this.produtoService.obter(lote.getProduto().getCodigoDoProduto());
        Setor setor = this.setorService.obterSetor(lote.getSetor().getCodigo());
        lote.setSetor(setor);
        lote.setProduto(produto);
        return loteRepository.save(lote);
    }

    public Lote obter(Integer numero) {
        Lote lote = loteRepository.getLoteByNumero(numero);
        if (lote != null){
            return lote;
        }
        {
            throw new EntityNotFoundException("lote não encontrado");
        }
    }

    public List<Lote> listar() {
        return loteRepository.findAll().stream().collect(Collectors.toList());
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
