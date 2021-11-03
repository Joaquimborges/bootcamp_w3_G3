package com.bootcamp_w3_g3.service;


import com.bootcamp_w3_g3.model.entity.Lote;
import com.bootcamp_w3_g3.model.entity.Produto;
import com.bootcamp_w3_g3.repository.LoteRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;


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


    private LoteRepository loteRepository = Mockito.mock(LoteRepository.class);
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private SetorService setorService;

    @Autowired
   private LoteService loteService = new LoteService(loteRepository,produtoService, setorService);

   //private ArmazemService armazemService = Mockito.mock(ArmazemService.class);


    Produto produto = Produto.builder()
            .codigoDoProduto(123)
            .nome("carne")
            .preco(60.0)
            .build();


   Lote lote = Lote.builder()
           .numero(10)
           .dataDeValidade(LocalDate.now())
           .produto(produto)
           .quantidadeAtual(5)
           .build();

   Lote lote1 = Lote.builder()
           .numero(9)
           .dataDeValidade(LocalDate.now())
           .produto(produto)
           .quantidadeAtual(5)
           .build();

    /**
     * teste deve salvar um lote
     */
   @Test
   void should_save_lote_whenPayloadIsValid(){
        loteService = Mockito.mock(LoteService.class);
        Mockito.when(loteService.salvar(Mockito.any(Lote.class))).thenReturn(lote);
        Lote loteSalvo = loteService.salvar(lote);
        assertNotNull(loteSalvo);
        assertEquals(lote, loteSalvo);
    }

   @Test
   void should_get_a_lote(){

       Mockito.when(loteService.obter(Mockito.any(Integer.class))).thenReturn(lote);

      assertEquals(10, loteService.obter(lote.getNumero()).getNumero());
   }


   @Test
   void should_getAll_lotes(){
      List<Lote> lotes = new ArrayList<>();

      Mockito.when(loteService.listar()).thenReturn(lotes);
      lotes.add(lote);
      lotes.add(lote1);


      List<Lote> loteList = new ArrayList<>();
      loteList.addAll(loteService.listar());


      assertEquals(loteList.size(), lotes.size());
      assertNotEquals(lotes.size(), 0);
   }


}
