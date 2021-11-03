package com.bootcamp_w3_g3.controller;

import com.bootcamp_w3_g3.model.dtos.request.*;
import com.bootcamp_w3_g3.model.entity.*;
import com.bootcamp_w3_g3.repository.*;
import com.bootcamp_w3_g3.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;


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
    private OrdemDeEntradaService ordemDeEntradaService;
    @Autowired
    private OrdemDeEntradaRepository ordemDeEntradaRepository;
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
    private OrdemDeEntradaForm ordemDeEntradaForm;
    private OrdemDeEntradaForm ordemDeEntradaAlteradaForm;

    @BeforeAll
    static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    private ProdutoForm payloadProduto(ProdutoForm produtoForm) {
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

    private LoteForm payloadLote(LoteForm loteForm) {
        Produto produto = this.produtoService.obter(loteForm.getProdutoForm().getCodigoDoProduto());
        ProdutoForm produtoForm = ProdutoForm.builder().codigoDoProduto(produto.getCodigoDoProduto()).nome(produto.getNome()).build();
        Lote lote = Lote.builder().numero(loteForm.getNumero()).produto(produto).setor(loteForm.getSetorForm().converte(this.armazemService)).build();
        this.loteService.salvar(lote);
        return LoteForm.builder()
                .numero(lote.getNumero())
                .dataDeValidade(lote.getDataDeValidade())
                .dataDeFabricacao(lote.getDataDeFabricacao())
                .horaFabricacao(lote.getHoraFabricacao())
                .quantidadeAtual(lote.getQuantidadeAtual())
                .quantidadeMinina(lote.getQuantidadeMinina())
                .temperaturaAtual(lote.getTemperaturaAtual())
                .temperaturaMinima(lote.getTemperaturaMinima())
                .produtoForm(produtoForm).build();
    }



    private VendedorForm payloadVendedor(VendedorForm vendedorForm) {
        Vendedor novoVendedor = Vendedor.builder().codigo(vendedorForm.getCodigo()).build();
        novoVendedor.setNome( vendedorForm.getNome());
        this.vendedorService.salvar(novoVendedor);
        return VendedorForm.builder()
                .codigo(novoVendedor.getCodigo())
                .nome(novoVendedor.getNome())
                .build();
    }


    private void persisteSetor(Armazem armazem, Setor setor){
        Setor s = Setor.builder().codigo(setor.getCodigo()).nome(setor.getNome()).armazem(armazem).build();
        this.setorService.salvarSetor(s);
    }

    private void persisteRepresentante(ArmazemForm armazemForm){
        Representante representante = Representante.builder().codigo(armazemForm.getRepresentante().getCodigo()).build();
        representante.setNome( armazemForm.getRepresentante().getNome());
        this.representanteService.salvar(representante);
    }


    private void persisteArmazem(ArmazemForm armazemForm, Representante representante){
        Armazem armazem = Armazem.builder().codArmazem(armazemForm.getCodArmazem()).nome(armazemForm.getNome()).representante(representante).build();
        this.armazemService.criarArmazem(armazem);
    }


    private ArmazemForm payloadArmazem(ArmazemForm armazemForm){
        persisteRepresentante(armazemForm);
        Representante representante = this.representanteService.obter(armazemForm.getRepresentante().getCodigo());
        RepresentanteForm representanteForm = RepresentanteForm.builder().codigo(representante.getCodigo()).nome(representante.getNome()).build();

        persisteArmazem(armazemForm, representante);
        Armazem armazem = this.armazemService.obterArmazem(armazemForm.getCodArmazem());


        return ArmazemForm.builder()
                .codArmazem(armazem.getCodArmazem())
                .representante(representanteForm)
                .nome(armazem.getNome())
                .endereco(armazem.getEndereco())
                .uf(armazem.getUf()).build();
    }


    private SetorForm payloadSetor(SetorForm setorForm) {
        Armazem armazem = this.armazemService.obterArmazem(setorForm.getArmazem().getCodArmazem());
        Setor setor = Setor.builder().codigo(setorForm.getCodigo()).nome(setorForm.getNome()).armazem(armazem).build();
        this.setorService.salvarSetor(setor);
        ArmazemForm armazemForm = ArmazemForm.builder().codArmazem(armazem.getCodArmazem()).nome(armazem.getNome()).build();
        return SetorForm.builder()
                .codigo(setor.getCodigo())
                .nome(setor.getNome())
                .tipoProduto(setor.getTipoProduto())
                .espacoDisponivel(100)
                .armazem(armazemForm).build();
    }

    private OrdemDeEntradaForm payloadOrdemEntrada(ArmazemForm armazemForm, SetorForm setorForm, VendedorForm vendedorForm, LoteForm loteForm) {
        return OrdemDeEntradaForm.builder()
                .dataOrdem(LocalDate.now())
                .codigoRepresentante(armazemForm.getRepresentante().getCodigo())
                .codigoSetor(setorForm.getCodigo())
                .numeroOrdem(456)
                .codigoVendedor(vendedorForm.getCodigo())
                .dataFabricacao(LocalDate.of(2021, 10, 12))
                .dataVencimento(LocalDate.of(2021, 12, 12))
                .produtoForm(loteForm.getProdutoForm())
                .loteForm(loteForm)
                .quantidade(2)
                .build();
    }

    @Test
    public void deveRegistrarUmaOrdemDeEntrada() throws Exception{

        RepresentanteForm joaquim = RepresentanteForm.builder().codigo("rp-345").nome("joaquim").build();
        ArmazemForm a = ArmazemForm.builder().codArmazem("ar-123").nome("armazem").representante(joaquim).build();
        ArmazemForm armazemForm = payloadArmazem(a);

        SetorForm setor_de_carnes = SetorForm.builder().codigo("999").nome("setor de carnes").armazem(armazemForm).build();
        setor_de_carnes = payloadSetor(setor_de_carnes);

        ProdutoForm picanha = payloadProduto(ProdutoForm.builder().codigoDoProduto(999).nome("picanha").build());
        LoteForm loteForm = payloadLote(LoteForm.builder().setorForm(setor_de_carnes).produtoForm(picanha).dataDeValidade(LocalDate.now()).numero(777).build());

        VendedorForm v = VendedorForm.builder().codigo("mlb-452").nome("matheus").build();
        VendedorForm vendedorForm = payloadVendedor(v);

        this.ordemDeEntradaForm = OrdemDeEntradaForm.builder()
                .numeroOrdem(100)
                .codigoSetor( setor_de_carnes.getCodigo())
                .loteForm(loteForm)
                .dataOrdem(LocalDate.now())
                .codigoRepresentante( armazemForm.getRepresentante().getCodigo())
                .codigoVendedor(vendedorForm.getCodigo()).build();


        String requestPayload = objectMapper.writeValueAsString(ordemDeEntradaForm);

        System.out.println(requestPayload);
        this.mockMvc.perform(post("http://localhost:8080/api/ordem-entrada/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestPayload))
                .andExpect(status().isCreated());

    }



    @Test
    public void deveAlterarUmaOrdemDeEntrada() throws Exception {


        Vendedor vendedor = Vendedor.builder()
                .codigo("MLB-123")
                .build();
        this.vendedorService.salvar(vendedor);

        Representante representante = this.representanteService.salvar(
                Representante.builder()
                        .codigo("MLB-456")
                        .build());

        Armazem armazem = this.armazemService.criarArmazem(
                Armazem.builder()
                        .codArmazem("MLB-543")
                        .nome("armazem daaaa")
                        .representante(representante)
                        .endereco("algo")
                        .uf("SP")
                        .build());

        Setor setor = this.setorService.salvarSetor(
                Setor.builder()
                        .codigo("S-456")
                        .nome("Setor de frescos")
                        .armazem(armazem)
                        .espacoDisponivel(40)
                        .tipoProduto("FRESCOS")
                        .build());

        Produto produto = this.produtoService.salvar(
                Produto.builder()
                        .codigoDoProduto(222)
                        .preco(60.0)
                        .nome("picanha")
                        .temperaturaIndicada(12.1)
                        .build());


        Lote lote = this.loteService.salvar(
                Lote.builder()
                        .numero(333)
                        .setor(setor)
                        .produto(produto)
                        .dataDeValidade(LocalDate.now())
                        .dataDeFabricacao(LocalDate.now())
                        .build());

        OrdemDeEntrada ordemDeEntrada = OrdemDeEntrada.builder()
                .dataDaOrdem(LocalDate.now())
                .numeroDaOrdem(100)
                .lote(lote)
                .vendedor(vendedor)
                .representante(representante)
                .setor(setor).build();

        this.ordemDeEntradaService.registra(ordemDeEntrada);

        RepresentanteForm joaquim = RepresentanteForm.builder().codigo("rp-345").nome("joaquim").build();
        ArmazemForm a = ArmazemForm.builder().codArmazem("ar-123").nome("armazem").representante(joaquim).build();
        ArmazemForm armazemForm = payloadArmazem(a);

        SetorForm setor_de_peixes = SetorForm.builder().codigo("888").nome("setor de peixes").armazem(armazemForm).build();
        setor_de_peixes = payloadSetor(setor_de_peixes);

        ProdutoForm robalo = payloadProduto(ProdutoForm.builder().codigoDoProduto(777).nome("robalo").build());
        LoteForm loterobaloForm = payloadLote(LoteForm.builder().setorForm(setor_de_peixes).produtoForm(robalo).dataDeValidade(LocalDate.now()).numero(666).build());

        VendedorForm v = VendedorForm.builder().codigo("mlb-452").nome("matheus").build();
        VendedorForm vendedorForm = payloadVendedor(v);

        this.ordemDeEntradaAlteradaForm = OrdemDeEntradaForm.builder()
                .numeroOrdem(100)
                .codigoSetor( setor_de_peixes.getCodigo())
                .loteForm(loterobaloForm)
                .dataOrdem(LocalDate.now())
                .codigoRepresentante( armazemForm.getRepresentante().getCodigo())
                .codigoVendedor(vendedorForm.getCodigo()).build();





        String requestPayload = objectMapper.writeValueAsString(ordemDeEntradaAlteradaForm);

        System.out.println(requestPayload);

        this.mockMvc.perform(put("http://localhost:8080/api/ordem-entrada/alterar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestPayload))
                .andExpect(status().isCreated());
    }


}
