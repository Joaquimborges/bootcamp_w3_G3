package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.model.entity.Produto;
import com.bootcamp_w3_g3.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Criado teste unitário referente a classe ProdutosService.
 * Desenvolvido testes para o CRUD.
 * @autor Alex Cruz
 */

public class ProdutoServiceUnitTest {


    ProdutoRepository produtoRepository = Mockito.mock(ProdutoRepository.class);
    ProdutoService produtoService = new ProdutoService(produtoRepository);

    Produto produto = Produto.builder()
            .codigoDoProduto(123)
            .nome("carne")
            .preco(60.0)
            .build();

    Produto produto2 = Produto.builder()
            .codigoDoProduto(123)
            .nome("Arroz")
            .preco(60.0)
            .build();


    List<Produto> produtosList = new ArrayList<>();

    @Test
    void salvarTest(){
        Mockito.when(produtoService.salvar(Mockito.any(Produto.class))).thenReturn(produto);


        Produto salvo = produtoService.salvar(produto);

        Mockito.verify(produtoRepository, Mockito.times(1)).save(produto);

        assertNotNull(salvo);
    }

    @Test
    void obterTest(){
        Mockito.when(produtoService.obter(Mockito.any(Integer.class))).thenReturn(produto);

        Produto obtido = produtoService.obter(produto.getCodigoDoProduto());

        assertNotNull(produto);
        assertEquals(obtido.getPreco(), produto.getPreco());
    }

    @Test
    void listarTest(){
        produtosList.add(produto);
        produtosList.add(produto2);
        Mockito.when(produtoService.listar()).thenReturn(produtosList);

        produtoService = new ProdutoService(produtoRepository);
        List<Produto> lista = produtoService.listar();

        Mockito.verify(produtoRepository, Mockito.times(1)).findAll();

        assertEquals(lista.size(),produtosList.size());

    }

    @Test
    void atualizarTest(){
        produto.setPreco(15.05);
        produto.setTemperaturaIndicada(15.00);
        Mockito.when(produtoRepository.findProdutoByCodigoDoProduto(Mockito.any(Integer.class))).thenReturn(produto);
        Mockito.when(produtoRepository.save(Mockito.any(Produto.class))).thenReturn(produto);

        produtoService = new ProdutoService(produtoRepository);
        Produto atualizado = produtoService.atualizar(produto);

        Mockito.verify(produtoRepository, Mockito.times(1)).findProdutoByCodigoDoProduto(produto.getCodigoDoProduto());
        Mockito.verify(produtoRepository, Mockito.times(1)).save(produto);

        assertNotNull(atualizado);
        assertEquals(atualizado.getPreco(), produto.getPreco());

    }

    @Test
    void apagarTest(){
        produto.setCodigoDoProduto(23);
        Mockito.when(produtoRepository.deleteProdutoByCodigoDoProduto(Mockito.any(Integer.class))).thenReturn(produto);

        produtoService = new ProdutoService(produtoRepository);
        produtoService.apagar(produto.getId());


        Mockito.verify(produtoRepository, Mockito.times(1)).deleteById(produto.getId());

        Produto deletado = produtoService.obter(23);
        assertEquals(null, deletado);
    }
}
