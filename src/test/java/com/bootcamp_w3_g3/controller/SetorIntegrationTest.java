package com.bootcamp_w3_g3.controller;

import com.bootcamp_w3_g3.model.dtos.request.ArmazemForm;
import com.bootcamp_w3_g3.model.dtos.request.RepresentanteForm;
import com.bootcamp_w3_g3.model.dtos.request.SetorForm;
import com.bootcamp_w3_g3.model.entity.Armazem;
import com.bootcamp_w3_g3.model.entity.Representante;
import com.bootcamp_w3_g3.model.entity.Setor;
import com.bootcamp_w3_g3.model.entity.TipoProduto;
import com.bootcamp_w3_g3.service.ArmazemService;
import com.bootcamp_w3_g3.service.RepresentanteService;
import com.bootcamp_w3_g3.service.SetorService;
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

/**
 * @autor Joaquim Borges
 */
@SpringBootTest
@AutoConfigureMockMvc
public class SetorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SetorService setorService;
    @Autowired
    private ArmazemService armazemService;
    @Autowired
    private RepresentanteService representanteService;

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    private RepresentanteForm payloadRepresentante(){
        return RepresentanteForm.builder()
                .codigo("R-123")
                .nome("Joao")
                .sobrenome("Gomes")
                .endereco("rua qualquer")
                .cpf("123.234.345-04")
                .telefone("11-2473648")
                .build();
    }

    private RepresentanteForm payloadRepresentante2(){
        return RepresentanteForm.builder()
                .codigo("R-456")
                .nome("Joao")
                .sobrenome("Gomes")
                .endereco("rua qualquer")
                .cpf("123.234.345-04")
                .telefone("11-2473648")
                .build();
    }

    private void persisteRepresentante(RepresentanteForm representanteForm) {
        Representante representante = Representante.builder()
                .nome(representanteForm.getNome())
                .sobrenome(representanteForm.getSobrenome())
                .cpf(representanteForm.getCpf())
                .telefone(representanteForm.getTelefone())
                .endereco(representanteForm.getEndereco())
                .codigo(representanteForm.getCodigo()).build();

        this.representanteService.salvar(representante);
    }


    private ArmazemForm payloadArmazem() {
    RepresentanteForm representanteForm = this.payloadRepresentante();
        this.persisteRepresentante(representanteForm);

        return ArmazemForm.builder()
                .codArmazem("AR-123")
                .nome("central")
                .representante(representanteForm)
                .endereco("rua qualquer")
                .uf("SP")
                .build();
    }

    private ArmazemForm payloadArmazem2() {
        RepresentanteForm representante2 = this.payloadRepresentante2();
        this.persisteRepresentante(representante2);

        return ArmazemForm.builder()
                .codArmazem("AR-098")
                .nome("central")
                .representante(representante2)
                .endereco("rua qualquer")
                .uf("SP")
                .build();
    }


    private void persisteSetor(SetorForm setorForm){
       ArmazemForm armazemForm = this.payloadArmazem2();
       Representante representante = this.representanteService.obter(armazemForm.getRepresentante().getCodigo());

        Armazem armazem = Armazem.builder()
                .codArmazem(armazemForm.getCodArmazem())
                .nome(armazemForm.getNome())
                .representante(representante)
                .endereco(armazemForm.getEndereco())
                .uf(armazemForm.getUf())
                .build();

        this.armazemService.criarArmazem(armazem);

        Armazem armazemSetor = this.armazemService.obterArmazem(armazem.getCodArmazem());

        Setor setor = Setor.builder()
                .tipoProduto(setorForm.getTipoProduto())
                .nome(setorForm.getNome())
                .armazem(armazemSetor)
                .espacoDisponivel(setorForm.getEspacoDisponivel())
                .codigo(setorForm.getCodigo()).build();

        this.setorService.salvarSetor(setor);
    }



    private SetorForm payloadSetor() {
        ArmazemForm armazemForm = this.payloadArmazem();
        Representante representante = this.representanteService.obter(armazemForm.getRepresentante().getCodigo());

        Armazem armazem = Armazem.builder()
                .codArmazem(armazemForm.getCodArmazem())
                .nome(armazemForm.getNome())
                .representante(representante)
                .endereco(armazemForm.getEndereco())
                .uf(armazemForm.getUf())
                .build();

        this.armazemService.criarArmazem(armazem);
        RepresentanteForm representanteArmazem = RepresentanteForm.builder()
                .codigo(representante.getCodigo())
                .cpf(representante.getCpf())
                .endereco(representante.getEndereco())
                .sobrenome(representante.getSobrenome())
                .telefone(representante.getTelefone())
                .nome(representante.getNome()).build();

        Armazem armazemLocalizado = this.armazemService.obterArmazem(armazem.getCodArmazem());

        ArmazemForm armazemSetor = ArmazemForm.builder()
                .codArmazem(armazemLocalizado.getCodArmazem())
                .nome(armazemLocalizado.getNome())
                .representante(representanteArmazem)
                .endereco(armazemLocalizado.getEndereco())
                .uf(armazemLocalizado.getUf())
                .build();


        return SetorForm.builder()
                .tipoProduto(TipoProduto.FRESCOS)
                .nome("Setor de frescos")
                .armazem(armazemSetor)
                .espacoDisponivel(100)
                .codigo("S-234").build();
    }



    /**
     * teste deve cadastrar um setor
     * caso o armazem já exista e tenha um representante
     * cadastrado.
     */
    @Test
    void deveCadastrarUmSetor() throws Exception {
        SetorForm setorForm = this.payloadSetor();
        String requestPayload = objectMapper.writeValueAsString(setorForm);

        this.mockMvc.perform(post("http://localhost:8080/setor/salvar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestPayload))
                .andExpect(status().isCreated());

    }


    /**
     * teste deve obter as informacoes de
     * um setor especifico.
     */
    @Test
    void deveObterUmSetor() throws Exception {
        SetorForm setorForm = SetorForm.builder()
                .codigo("S-987")
                .nome("carne")
                .tipoProduto(TipoProduto.FRESCOS)
                .espacoDisponivel(100).build();

        this.persisteSetor(setorForm);

        this.mockMvc.perform(get("http://localhost:8080/setor/obter/"+ setorForm.getCodigo()))
                .andExpect(status().isOk());
    }











}
