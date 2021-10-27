package com.bootcamp_w3_g3.controller;

import com.bootcamp_w3_g3.model.dtos.request.OrdemDeEntradaDTO;
import com.bootcamp_w3_g3.model.entity.*;
import com.bootcamp_w3_g3.repository.*;
import com.bootcamp_w3_g3.service.*;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @autor Joaquim Borges
 */
@SpringBootTest
@AutoConfigureMockMvc
public class OrdemEntradaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrdemDeEntradaRepository ordemRepository;
    @Autowired
    private LoteService loteService;
    @Autowired
    private RepresentanteService representanteService;
    @Autowired
    private ArmazemService armazemService;
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private SetorService setorService;
    @Autowired
    private VendedorService vendedorService;



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
    Lote lote = payloadLote();
    private Produto payloadProduto() {
        return Produto.builder()
                .codigoDoProduto(1234)
                .lote(lote)
                .dataDeValidadae(LocalDate.of(2021, 9, 2))
                .temperaturaIndicada(lote.getTemperaturaMinima())
                .nome("carne")
                .preco(new BigDecimal(60)).build();

    }
    Produto produto = payloadProduto();
    private Lote payloadLote() {
        return Lote.builder()
                .numero(123)
                .dataDeValidade(LocalDate.of(2021, 8, 20))
                .dataDeFabricacao(LocalDate.of(2021, 11, 12))
                .horaFabricacao(LocalTime.of(9, 12))
                .quantidadeAtual(10)
                .quantidadeMinina(2)
                .temperaturaAtual(16.1)
                .temperaturaMinima(13.0)
                .produtos(produto)
                .build();
    }

    private Representante payloadRepresentante() {
        Representante representante = new Representante();
        representante.setCodigo("R-123");
        representante.setNome("Pedro");
        representante.setSobrenome("Sousa");
        representante.setCpf("12375648898");
        representante.setEndereco("rua 1");
        representante.setTelefone("1198353749");

        return representante;
    }

    private Vendedor payloadVendedor() {
        Vendedor vendedor = new Vendedor();
        vendedor.setCodigo("V-123");
        vendedor.setCpf("111888777226");
        vendedor.setNome("Alex");
        vendedor.setSobrenome("Cruz");
        vendedor.setTelefone("1177384959");
        vendedor.setEndereco("rua 2");

        return vendedor;
    }
    Setor setor = payloadSetor();
    private Armazem payloadArmazem(){
        List<Setor> setores = new ArrayList<>();
        setores.add(setor);

        Representante representante = payloadRepresentante();

        return Armazem.builder()
                .setoresDoArmazem(setores)
                .codArmazem("Ar-123")
                .representante(representante)
                .nome("AR1")
                .endereco("rua 10")
                .uf("SP").build();
    }
    Armazem armazem = payloadArmazem();

    private Setor payloadSetor() {
        List<Lote> lotes = new ArrayList<>();
        lotes.add(lote);

        return Setor.builder()
                .codigo("S-123")
                .nome("gene")
                .tipoProduto("congelados")
                .espacoDisponivel(100)
                .lote(lotes)
                .armazem(armazem).build();

    }
    Representante representante = payloadRepresentante();
    Vendedor vendedor = payloadVendedor();
    private OrdemDeEntradaDTO payloadOrdemEntrada() {


        return OrdemDeEntradaDTO.builder()
                .dataOrdem(LocalDate.now())
                .codigoRepresentante(representante.getCodigo())
                .codigoSetor(setor.getCodigo())
                .numeroOrdem(456)
                .codigoVendedor(vendedor.getCodigo())
                .dataFabricacao(LocalDate.of(2021, 10, 12))
                .dataVencimento(LocalDate.of(2021, 12, 12))
                .produto(produto)
                .quantidade(2)
                .build();
    }


    @BeforeEach
    public void initForEach(){
        vendedorService.salvar(vendedor);
        Representante r = representanteService.salvar(representante);
        armazem.setRepresentante(r);
        armazemService.criarArmazem(armazem);
        setor.setArmazem(armazem);
        setorService.salvarSetor(setor);
        lote.setSetor(setor);
        loteService.salvar(lote);
        produtoService.salvar(produto);
    }

    @Test
    public void deveRegistrarUmaOrdemDeEntrada() throws Exception{

        OrdemDeEntradaDTO dto = payloadOrdemEntrada();
        String requestPayload = objectMapper.writeValueAsString(dto);
        dto.converte(vendedorService, armazemService);

        this.mockMvc.perform(post("http://localhost:8080/api/ordem-entrada/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestPayload))
                .andExpect(status().isCreated());
    }

}
