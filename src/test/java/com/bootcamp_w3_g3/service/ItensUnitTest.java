package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.model.entity.Itens;
import com.bootcamp_w3_g3.model.entity.Produto;
import com.bootcamp_w3_g3.model.entity.TipoProduto;
import com.bootcamp_w3_g3.repository.ItensRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Criado teste unitário referente a classe itensService.
 * Desenvolvido testes para o CRUD.
 * @autor Alex Cruz
 */
public class ItensUnitTest{

    ItensService itensService;

    ItensRepository itensRepository = Mockito.mock(ItensRepository.class);

    Produto produto = Produto.builder()
            .codigoDoProduto(25)
            .nome("Batata Doce")
            .preco(12.69)
            .temperaturaIndicada(18.5)
            .tipoProduto(TipoProduto.FRESCOS)
            .build();

    Produto produto2 = Produto.builder()
            .codigoDoProduto(25)
            .nome("Fruta Pão")
            .preco(9.69)
            .temperaturaIndicada(12.5)
            .tipoProduto(TipoProduto.CONGELADOS)
            .build();
    Itens itens  = Itens.builder()
            .produto(produto)
            .quantidade(25)
            .build();

    Itens itens2  = Itens.builder()
            .produto(produto2)
            .quantidade(52)
            .build();

    List<Itens> itensList = new ArrayList<>();

    @Test
    void salvarCarrinhoTest(){
        Mockito.when(itensRepository.save(Mockito.any(Itens.class))).thenReturn(itens);

        itensService = new ItensService(itensRepository);
        Itens salvo = itensService.salvar(itens);

        Mockito.verify(itensRepository, Mockito.times(1)).save(itens);

        assertNotNull(salvo);
    }

    @Test
    void listarCarrinhoTest(){
        itensList.add(itens);
        itensList.add(itens2);
        Mockito.when(itensRepository.findAll()).thenReturn(itensList);

        itensService = new ItensService(itensRepository);
        List<Itens> lista = itensService.listar();

        Mockito.verify(itensRepository, Mockito.times(1)).findAll();

        assertEquals(lista.size(),itensList.size());

    }

    @Test
    void atualizarCarrinhoTest(){
        itens.setId(22222L);
        itens.setQuantidade(12);
        itens.setProduto(produto);

        Mockito.when(itensRepository.findItensById(Mockito.any(Long.class))).thenReturn(itens);
        Mockito.when(itensRepository.save(Mockito.any(Itens.class))).thenReturn(itens);

        itensService = new ItensService(itensRepository);
        Itens carrinhoAtualizado = itensService.atualizar(itens);

        Mockito.verify(itensRepository, Mockito.times(1)).findItensById(itens.getId());
        Mockito.verify(itensRepository, Mockito.times(1)).save(itens);

        assertEquals(carrinhoAtualizado.getQuantidade(), itens.getQuantidade());

    }
}
