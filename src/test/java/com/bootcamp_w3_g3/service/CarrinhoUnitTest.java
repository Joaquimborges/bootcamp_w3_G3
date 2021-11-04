package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.model.entity.*;
import com.bootcamp_w3_g3.repository.CarrinhoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
            .nome("Alface")
            .preco(2.69)
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


    /**
     * Criado teste unitário de método que atende ao requisito 2
     * @autor Alex Cruz
     */
    @Test
    void salvarTest(){
        Mockito.when(carrinhoRepository.save(Mockito.any(Carrinho.class))).thenReturn(carrinho);

        carrinhoService = new CarrinhoService(carrinhoRepository);
        Carrinho salvo = carrinhoService.salvar(carrinho);

        Mockito.verify(carrinhoRepository, Mockito.times(1)).save(carrinho);

        assertNotNull(salvo);
    }

    /**
     * Criado teste unitário de método que atende ao requisito 2
     * @autor Alex Cruz
     */
    @Test
    void listarTest(){
        carrinhoList.add(carrinho);
        carrinhoList.add(carrinho2);
        Mockito.when(carrinhoRepository.findAll()).thenReturn(carrinhoList);

        carrinhoService = new CarrinhoService(carrinhoRepository);
        List<Carrinho> lista = carrinhoService.listar();

        Mockito.verify(carrinhoRepository, Mockito.times(1)).findAll();

        assertEquals(lista.size(),carrinhoList.size());

    }

    /**
     * Criado teste unitário de método que atende ao requisito 2
     * @autor Alex Cruz
     */
    @Test
    void atualizarTest(){
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

    /**
     * Criado teste unitário de método que atende ao requisito 2
     * @autor Alex Cruz
     */
    @Test
    void retornaPrecoDosItensTest(){
        itensList.add(item2);
        carrinho.setItensList(itensList);
        Double valorEsperado = 0.00;
        for(Itens item : carrinho.getItensList()){
            valorEsperado += item.getProduto().getPreco() * item.getQuantidade();
        }

        carrinhoService = new CarrinhoService(carrinhoRepository);
        BigDecimal valotTotal = carrinhoService.retornaPrecoDosItens(carrinho);

        assertNotNull(valotTotal);
        assertEquals(new BigDecimal(valorEsperado), valotTotal);
    }

    /**
     * Criado teste unitário de método que atende ao requisito 2
     * @autor Alex Cruz
     */
    @Test
    void mostrarProdutosDoPedidoTest(){
        List<Produto> produtoDoPedido = new ArrayList<>();
        itensList.add(item);
        itensList.add(item2);
        carrinho.setItensList(itensList);
        carrinho.setId(34234L);
        for (Itens itens : itensList) {
            produtoDoPedido.add(itens.getProduto());
        }

        Mockito.when(carrinhoRepository.getById(Mockito.any(Long.class))).thenReturn(carrinho);

        carrinhoService = new CarrinhoService(carrinhoRepository);
        List<Produto> listaDeProdutosRetornada = carrinhoService.mostrarProdutosDoPedido(carrinho.getId());

        Mockito.verify(carrinhoRepository,Mockito.times(1)).getById(carrinho.getId());

        assertNotNull(listaDeProdutosRetornada);
        assertEquals(produtoDoPedido.size(),listaDeProdutosRetornada.size());
    }

    /**
     * Criado teste unitário de método que atende ao requisito 2
     * @autor Alex Cruz
     */
    @Test
    void alterarPedidoTest(){
        itensList.add(item);
        itensList.add(item2);
        carrinho.setId(34234L);
        carrinho.setDataDeOrdem(LocalDate.now());
        carrinho.setItensList(itensList);
        carrinho.setStatusCompra(StatusCompra.CANCELADO);
        Mockito.when(carrinhoRepository.getById(Mockito.any(Long.class))).thenReturn(carrinho);
        Mockito.when(carrinhoRepository.save(Mockito.any(Carrinho.class))).thenReturn(carrinho);

        carrinhoService = new CarrinhoService(carrinhoRepository);
        Carrinho carrinhoAlterado = carrinhoService.alterarPedido(carrinho, carrinho.getId());

        Mockito.verify(carrinhoRepository, Mockito.times(1)).getById(carrinho.getId());
        Mockito.verify(carrinhoRepository, Mockito.times(1)).save(carrinho);

        assertEquals(carrinhoAlterado.getStatusCompra(), carrinho.getStatusCompra());

    }
}

