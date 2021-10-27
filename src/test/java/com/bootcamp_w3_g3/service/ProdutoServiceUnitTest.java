package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.model.entity.Produto;
import com.bootcamp_w3_g3.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Criado teste unitário referente a classe ProdutosService.
 * Desenvolvido testes para o CRUD.
 * @autor Alex Cruz
 */

public class ProdutoServiceUnitTest {

    ProdutoService produtoService;

    ProdutoRepository produtoRepository = Mockito.mock(ProdutoRepository.class);
    Produto produto = Produto.builder()
            .codigoDoProduto(123)
            .nome("carne")
            .preco(new BigDecimal(60))
            .dataDeValidadae(LocalDate.now()).build();

    Produto produto2 = Produto.builder()
            .codigoDoProduto(123)
            .nome("Arroz")
            .preco(new BigDecimal(60))
            .dataDeValidadae(LocalDate.now()).build();


    List<Produto> produtosList = new ArrayList<>();

    @Test
    void salvarTest(){
        Mockito.when(produtoRepository.save(Mockito.any(Produto.class))).thenReturn(produto);

        produtoService = new ProdutoService(produtoRepository);
        Produto salvo = produtoService.salvar(produto);

        Mockito.verify(produtoRepository, Mockito.times(1)).save(produto);

        assertNotNull(salvo);
    }

    @Test
    void obterTest(){
        Mockito.when(produtoRepository.findByCodigoDoProduto(Mockito.any(Integer.class))).thenReturn(produto);

        produtoService = new ProdutoService(produtoRepository);
        Produto obtido = produtoService.obter(produto.getCodigoDoProduto());

        Mockito.verify(produtoRepository,
                Mockito.times(1))
                .findByCodigoDoProduto(produto.getCodigoDoProduto());

        assertEquals(obtido.getPreco(), produto.getPreco());
    }

    @Test
    void listarTest(){
        produtosList.add(produto);
        produtosList.add(produto2);
        Mockito.when(produtoRepository.findAll()).thenReturn(produtosList);

        produtoService = new ProdutoService(produtoRepository);
        List<Produto> lista = produtoService.listar();

        Mockito.verify(produtoRepository, Mockito.times(1)).findAll();

        assertEquals(lista.size(),produtosList.size());

    }

    @Test
    void atualizarTest(){
        produto.setPreco(new BigDecimal("15.05"));
        produto.setTemperaturaIndicada(15.00);
        Mockito.when(produtoRepository.getByCodigoDoProduto(Mockito.any(Integer.class))).thenReturn(produto);
        Mockito.when(produtoRepository.save(Mockito.any(Produto.class))).thenReturn(produto);

        produtoService = new ProdutoService(produtoRepository);
        Produto atualizado = produtoService.atualizar(produto);

        Mockito.verify(produtoRepository, Mockito.times(1)).getByCodigoDoProduto(produto.getCodigoDoProduto());
        Mockito.verify(produtoRepository, Mockito.times(1)).save(produto);

        assertNotNull(atualizado);
        assertEquals(atualizado.getPreco(), produto.getPreco());

    }

    @Test
    void apagarTest(){
        produto.setCodigoDoProduto(23);
        Mockito.when(produtoRepository.deleteByCodigoDoProduto(Mockito.any(Integer.class))).thenReturn(null);

        produtoService = new ProdutoService(produtoRepository);
        Produto deletado = produtoService.apagar(produto.getCodigoDoProduto());

        Mockito.verify(produtoRepository, Mockito.times(1)).deleteByCodigoDoProduto(produto.getCodigoDoProduto());

        assertNotEquals(deletado,produto);
    }
}
