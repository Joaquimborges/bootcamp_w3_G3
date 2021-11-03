package com.bootcamp_w3_g3.controller;

import com.bootcamp_w3_g3.model.dtos.request.ProdutoForm;
import com.bootcamp_w3_g3.model.entity.Produto;
import com.bootcamp_w3_g3.service.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;


/**
 * @autor Joaquim Borges
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoService produtoService;

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    private ProdutoForm payloadProduto() {
        return ProdutoForm.builder()
                .codigoDoProduto(1234)
                .nome("carne seca")
                .preco(50.0)
                .temperaturaIndicada(16.0)
                .build();
    }

    private ProdutoForm PersisteProduto(ProdutoForm produtoForm) {
        Produto novoProduto = Produto.builder()
                .codigoDoProduto(produtoForm.getCodigoDoProduto())
                .nome(produtoForm.getNome())
                .build();
        this.produtoService.salvar(novoProduto);
        return ProdutoForm.builder()
                .codigoDoProduto(novoProduto.getCodigoDoProduto())
                .temperaturaIndicada(null)
                .preco(60.0).build();
    }

    private Produto converte(ProdutoForm produtoForm) {
        return Produto.builder()
                .codigoDoProduto(produtoForm.getCodigoDoProduto())
                .nome(produtoForm.getNome())
                .preco(produtoForm.getPreco())
                .temperaturaIndicada(produtoForm.getTemperaturaIndicada()).build();
    }

    /**
     * teste deve cadastrar um produto caso
     * o payload seja valido
     */
    @Test
    void deveCadastrarUmProduto() throws Exception {
        ProdutoForm produto = payloadProduto();
        String requestPayload = objectMapper.writeValueAsString(produto);

        this.mockMvc.perform(post("http://localhost:8080/produtos/cadastra")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestPayload))
                .andExpect(status().isCreated());
    }

    /**
     * teste deve obter um produto caso ele exista
     * no banco de dados
     */

    @Test
    void deveObterUmProdutoCadastrado() throws Exception {
        ProdutoForm produto = ProdutoForm.builder()
                .codigoDoProduto(1234)
                .nome("carne seca")
                .preco(60.0)
                .temperaturaIndicada(16.0)
                .build();

        this.PersisteProduto(produto);

        this.mockMvc.perform(get("http://localhost:8080/produtos/obter/" + produto.getCodigoDoProduto()))
                .andExpect(status().isOk());
    }


    /**
     * teste deve alterar dos dados de um produto
     * caso ele exista no banco e o payload seja valida
     * retorno comparado pelo nome
     */
    @Test
    void deveAlterarDadosDeUmProduto() throws Exception {
        ProdutoForm produto = this.payloadProduto();

        this.PersisteProduto(produto);

        ProdutoForm produtoAlterado = ProdutoForm.builder()
                .codigoDoProduto(1234)
                .nome("carne de sol")
                .preco(60.0)
                .temperaturaIndicada(16.0)
                .build();

        String payloadRequest = objectMapper.writeValueAsString(produtoAlterado);
        this.mockMvc.perform(put("http://localhost:8080/produtos/alterar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(produtoAlterado.getNome())));
    }



    /**
     * teste deve apagar um produto caso ele exista
     * no banco de dados
     */
    @Test
    void deveApagarUmProduto() throws Exception {
        ProdutoForm produtoForm = this.payloadProduto();
        Produto produto = this.converte(produtoForm);
        this.produtoService.salvar(produto);

        this.mockMvc.perform(delete("http://localhost:8080/produtos/deletar/" + produto.getId()))
                .andExpect(status().isOk());
    }
}
