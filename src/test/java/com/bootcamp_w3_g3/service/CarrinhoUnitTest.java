package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.model.entity.*;
import com.bootcamp_w3_g3.repository.CarrinhoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Criado teste unitário referente a classe carrinhoService.
 * Desenvolvido testes para o CRUD.
 * @autor Alex Cruz
 */
public class CarrinhoUnitTest{

    CarrinhoService carrinhoService;

    CarrinhoRepository carrinhoRepository = Mockito.mock(CarrinhoRepository.class);

    Carrinho carrinho  = Carrinho.builder()
            .codigo("12345")
            .dataDeOrdem(LocalDate.now())
            .statusCompra(StatusCompra.PENDENTE)
            .build();

    Carrinho carrinho2  = Carrinho.builder()
            .codigo("23423")
            .dataDeOrdem(LocalDate.now())
            .statusCompra(StatusCompra.CONCLUIDO)
            .build();

    Produto produto = Produto.builder()
            .codigoDoProduto(25)
            .nome("Batata Doce")
            .preco(12.69)
            .temperaturaIndicada(18.5)
            .tipoProduto(TipoProduto.FRESCOS)
            .build();


    Produto produto2 = Produto.builder()
            .codigoDoProduto(25)
            .nome("Batata Doce")
            .preco(12.69)
            .temperaturaIndicada(18.5)
            .tipoProduto(TipoProduto.FRESCOS)
            .build();

    Itens item = Itens.builder()
            .produto(produto)
            .quantidade(25)
            .build();

    Itens item2 = Itens.builder()
            .produto(produto2)
            .quantidade(10)
            .build();

    List<Carrinho> carrinhoList = new ArrayList<>();

    List<Itens> itensList = new ArrayList<>();

    @Test
    void salvarCarrinhoTest(){
        Mockito.when(carrinhoRepository.save(Mockito.any(Carrinho.class))).thenReturn(carrinho);

        carrinhoService = new CarrinhoService(carrinhoRepository);
        Carrinho salvo = carrinhoService.salvar(carrinho);

        Mockito.verify(carrinhoRepository, Mockito.times(1)).save(carrinho);

        assertNotNull(salvo);
    }

    @Test
    void listarCarrinhoTest(){
        carrinhoList.add(carrinho);
        carrinhoList.add(carrinho2);
        Mockito.when(carrinhoRepository.findAll()).thenReturn(carrinhoList);

        carrinhoService = new CarrinhoService(carrinhoRepository);
        List<Carrinho> lista = carrinhoService.listar();

        Mockito.verify(carrinhoRepository, Mockito.times(1)).findAll();

        assertEquals(lista.size(),carrinhoList.size());

    }

    @Test
    void atualizarCarrinhoTest(){
        itensList.add(item);
        itensList.add(item2);
        carrinho.setDataDeOrdem(LocalDate.now());
        carrinho.setItensList(itensList);
        carrinho.setStatusCompra(StatusCompra.CANCELADO);
        Mockito.when(carrinhoRepository.getByCodigo(Mockito.any(String.class))).thenReturn(carrinho);
        Mockito.when(carrinhoRepository.save(Mockito.any(Carrinho.class))).thenReturn(carrinho);

        carrinhoService = new CarrinhoService(carrinhoRepository);
        Carrinho carrinhoAtualizado = carrinhoService.atualizar(carrinho);

        Mockito.verify(carrinhoRepository, Mockito.times(1)).getByCodigo(carrinho.getCodigo());
        Mockito.verify(carrinhoRepository, Mockito.times(1)).save(carrinho);

        assertEquals(carrinhoAtualizado.getStatusCompra(), carrinho.getStatusCompra());

    }
}

