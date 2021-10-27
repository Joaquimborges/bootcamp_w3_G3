package com.bootcamp_w3_g3.service;


import com.bootcamp_w3_g3.model.entity.Lote;
import com.bootcamp_w3_g3.model.entity.Produto;
import com.bootcamp_w3_g3.repository.LoteRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author Joaquim Borges
 */

public class LoteUnitTest {


   private LoteService loteService;

   private final ArmazemService armazemService = Mockito.mock(ArmazemService.class);
   private final LoteRepository loteRepository = Mockito.mock(LoteRepository.class);

    Produto produto = Produto.builder()
            .codigoDoProduto(123)
            .nome("carne")
            .preco(new BigDecimal(60))
            .dataDeValidadae(LocalDate.now()).build();


   Lote lote = Lote.builder()
           .numero(10)
           .dataDeValidade(LocalDate.now())
           .produtos(produto)
           .quantidadeAtual(5)
           .build();

   Lote lote1 = Lote.builder()
           .numero(9)
           .dataDeValidade(LocalDate.now())
           .produtos(produto)
           .quantidadeAtual(5)
           .build();

    /**
     * teste deve salvar um lote
     */
   @Test
   void should_save_lote_whenPayloadIsValid(){

        Mockito.when(loteRepository.save(Mockito.any(Lote.class))).thenReturn(lote);
        loteService = new LoteService(loteRepository, armazemService);
        Lote loteSalvo = loteService.salvar(lote);

        assertNotNull(loteSalvo);
    }

   @Test
   void should_get_a_lote(){
      Mockito.when(loteRepository.findByNumero(Mockito.any(Integer.class))).thenReturn(lote);
      loteService = new LoteService(loteRepository, armazemService);

      Lote getLote = loteService.obter(lote.getNumero());

      assertEquals(10, getLote.getNumero());
   }


   @Test
   void should_getAll_lotes(){
      List<Lote> lotes = new ArrayList<>();

      Mockito.when(loteRepository.findAll()).thenReturn(lotes);
      lotes.add(lote);
      lotes.add(lote1);

      loteService = new LoteService(loteRepository, armazemService);
      List<Lote> loteList = loteService.listar();

      assertEquals(loteList.size(), lotes.size());
   }


}
