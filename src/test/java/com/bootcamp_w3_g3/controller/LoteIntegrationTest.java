package com.bootcamp_w3_g3.controller;


import com.bootcamp_w3_g3.model.entity.Lote;
import com.bootcamp_w3_g3.model.entity.Produto;
import com.bootcamp_w3_g3.repository.LoteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;




import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Joaquim Borges
 */

@SpringBootTest
@AutoConfigureMockMvc
public class LoteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoteRepository loteRepository;

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    void iniciarMocks(){
        MockitoAnnotations.openMocks(this);
    }

    private Lote alterarDadosDoPayloadValido(){

        Produto produto = Produto.builder()
                .codigoDoProduto(123)
                .nome("carne")
                .preco(60.0)
                .build();

        return Lote.builder()
                .id(1L)
                .numero(9)
                .dataDeValidade(LocalDate.now())
                .produto(produto)
                .quantidadeAtual(3)
                .build();
    }

    private Lote criarPayloadValido(){
        Produto produto = Produto.builder()
                .codigoDoProduto(123)
                .nome("carne")
                .preco(60.0)
                .build();

        return    Lote.builder()
                .id(1L)
                .numero(9)
                .dataDeValidade(LocalDate.now())
                .produto(produto)
                .quantidadeAtual(1)
                .build();
    }

    /**
     * teste deve criar um lote se o payload estiver valido
     *
     */
    @Test
    void deveSalvarUmLote() throws Exception{
        Lote loteForm = this.criarPayloadValido();

        String requestPayload = objectMapper.writeValueAsString(loteForm);

        this.mockMvc.perform(post("http://localhost:8080/lote/salvar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestPayload))
                .andExpect(status().isCreated());

    }

    @Test
    void deveObterUmLote() throws Exception {
        Lote lote = this.criarPayloadValido();
        loteRepository.save(lote);

        this.mockMvc.perform(get("http://localhost:8080/lote/obter/" + lote.getNumero()))
                .andExpect(status().isOk());

    }

    @Test
    void deveListarOsLotes() throws Exception {
        Lote lote = this.criarPayloadValido();
        loteRepository.save(lote);


        this.mockMvc.perform(get("http://localhost:8080/lote/listar"))
                .andExpect(status().isOk());

    }


    @Test
    void deveAlterar_dadosDoLote() throws Exception {
        Lote lote = this.criarPayloadValido();
        loteRepository.save(lote);

        Lote loteAlterado = this.alterarDadosDoPayloadValido();
        String requestPayload = objectMapper.writeValueAsString(loteAlterado);


        this.mockMvc.perform(put("http://localhost:8080/lote/alterar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestPayload))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.quantidadeAtual", is(loteAlterado.getQuantidadeAtual())));







    }






}
