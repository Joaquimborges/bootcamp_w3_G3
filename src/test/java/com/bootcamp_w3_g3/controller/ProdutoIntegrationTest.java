package com.bootcamp_w3_g3.controller;

import com.bootcamp_w3_g3.BootcampW3G3Application;
import com.bootcamp_w3_g3.model.dtos.request.*;
import com.bootcamp_w3_g3.model.dtos.response.TokenDTO;
import com.bootcamp_w3_g3.model.entity.*;
import com.bootcamp_w3_g3.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;


/**
 * @autor Joaquim Borges
 */
@SpringBootTest(classes = BootcampW3G3Application.class)
@WebAppConfiguration
@AutoConfigureMockMvc
public class ProdutoIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private LoteService loteService;
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

    private ProdutoForm payloadProduto() {
        return ProdutoForm.builder()
                .codigoDoProduto(111)
                .nome("carne seca")
                .preco(50.0)
                .temperaturaIndicada(16.0)
                .build();
    }

    private RepresentanteForm payloadRepresentante(){
        return RepresentanteForm.builder()
                .codigo("R-111")
                .nome("Joao")
                .sobrenome("Gomes")
                .endereco("rua qualquer")
                .cpf("123.234.345-04")
                .telefone("11-2473648")
                .build();
    }

    private RepresentanteForm payloadRepresentante2(){
        return RepresentanteForm.builder()
                .codigo("R-444")
                .nome("Joao")
                .sobrenome("Gomes")
                .endereco("rua qualquer")
                .cpf("123.234.345-04")
                .telefone("11-2473648")
                .build();
    }

    private RepresentanteForm payloadRepresentante3(){
        return RepresentanteForm.builder()
                .codigo("R-177")
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
                .codArmazem("AR-777")
                .nome("armazem central")
                .representante(representanteForm)
                .endereco("qualquer lugar")
                .numero(100)
                .uf("SP").build();
    }

    private ArmazemForm payloadArmazem2() {
        RepresentanteForm representanteForm = this.payloadRepresentante2();
        this.persisteRepresentante(representanteForm);

        return ArmazemForm.builder()
                .codArmazem("AR-776")
                .nome("armazem central")
                .representante(representanteForm)
                .endereco("qualquer lugar")
                .numero(100)
                .uf("SP").build();
    }

    private ArmazemForm payloadArmazem3() {
        RepresentanteForm representanteForm = this.payloadRepresentante3();
        this.persisteRepresentante(representanteForm);

        return ArmazemForm.builder()
                .codArmazem("AR-887")
                .nome("armazem central")
                .representante(representanteForm)
                .endereco("qualquer lugar")
                .numero(100)
                .uf("SP").build();
    }

    private void persisteArmazem(ArmazemForm armazemForm) {

        Representante representante = this.representanteService.obter(armazemForm.getRepresentante().getCodigo());

        Armazem armazem = Armazem.builder()
                .codArmazem(armazemForm.getCodArmazem())
                .nome(armazemForm.getNome())
                .representante(representante)
                .endereco(armazemForm.getEndereco())
                .uf(armazemForm.getUf())
                .build();

        this.armazemService.criarArmazem(armazem);
    }

    private void persisteSetor(SetorForm setorForm){
        Armazem armazemSetor = this.armazemService.obterArmazem(setorForm.getArmazem().getCodArmazem());

        Setor setor = Setor.builder()
                .tipoProduto(setorForm.getTipoProduto())
                .nome(setorForm.getNome())
                .armazem(armazemSetor)
                .espacoDisponivel(setorForm.getEspacoDisponivel())
                .codigo(setorForm.getCodigo()).build();

        this.setorService.salvarSetor(setor);
    }

    private LoteForm payloadPersistidoLote(LoteForm loteForm){
        ArmazemForm armazemForm = this.payloadArmazem();
        this.persisteArmazem(armazemForm);

        SetorForm setorForm = SetorForm.builder()
                .tipoProduto(TipoProduto.CONGELADOS)
                .nome("Setor de congelados")
                .codigo("S-198")
                .armazem(armazemForm)
                .espacoDisponivel(10).build();

        this.persisteSetor(setorForm);

        Setor setorDoLote = setorService.obterSetor(setorForm.getCodigo());

        Lote loteEnviado = Lote.builder()
                .setor(setorDoLote)
                .numero(loteForm.getNumero())
                .dataDeValidade(loteForm.getDataDeValidade())
                .dataDeFabricacao(loteForm.getDataDeFabricacao())
                .horaFabricacao(loteForm.getHoraFabricacao())
                .quantidadeAtual(loteForm.getQuantidadeAtual())
                .quantidadeMinina(loteForm.getQuantidadeMinina())
                .temperaturaMinima(loteForm.getTemperaturaMinima())
                .temperaturaAtual(loteForm.getTemperaturaAtual()).build();

        loteService.salvar(loteEnviado);

        return LoteForm.builder()
                .setorForm(setorForm)
                .numero(loteEnviado.getNumero())
                .dataDeValidade(loteEnviado.getDataDeValidade())
                .dataDeFabricacao(loteEnviado.getDataDeFabricacao())
                .horaFabricacao(loteEnviado.getHoraFabricacao())
                .quantidadeAtual(loteEnviado.getQuantidadeAtual())
                .quantidadeMinina(loteEnviado.getQuantidadeMinina())
                .temperaturaMinima(loteEnviado.getTemperaturaMinima())
                .temperaturaAtual(loteEnviado.getTemperaturaAtual()).build();
    }

    private LoteForm payloadPersistidoLote2(LoteForm loteForm){
        ArmazemForm armazemForm = this.payloadArmazem2();
        this.persisteArmazem(armazemForm);

        SetorForm setorForm = SetorForm.builder()
                .tipoProduto(TipoProduto.CONGELADOS)
                .nome("Setor de congelados")
                .codigo("S-197")
                .armazem(armazemForm)
                .espacoDisponivel(10).build();

        this.persisteSetor(setorForm);

        Setor setorDoLote = setorService.obterSetor(setorForm.getCodigo());

        Lote loteEnviado = Lote.builder()
                .setor(setorDoLote)
                .numero(loteForm.getNumero())
                .dataDeValidade(loteForm.getDataDeValidade())
                .dataDeFabricacao(loteForm.getDataDeFabricacao())
                .horaFabricacao(loteForm.getHoraFabricacao())
                .quantidadeAtual(loteForm.getQuantidadeAtual())
                .quantidadeMinina(loteForm.getQuantidadeMinina())
                .temperaturaMinima(loteForm.getTemperaturaMinima())
                .temperaturaAtual(loteForm.getTemperaturaAtual()).build();

        loteService.salvar(loteEnviado);

        return LoteForm.builder()
                .setorForm(setorForm)
                .numero(loteEnviado.getNumero())
                .dataDeValidade(loteEnviado.getDataDeValidade())
                .dataDeFabricacao(loteEnviado.getDataDeFabricacao())
                .horaFabricacao(loteEnviado.getHoraFabricacao())
                .quantidadeAtual(loteEnviado.getQuantidadeAtual())
                .quantidadeMinina(loteEnviado.getQuantidadeMinina())
                .temperaturaMinima(loteEnviado.getTemperaturaMinima())
                .temperaturaAtual(loteEnviado.getTemperaturaAtual()).build();
    }

    private LoteForm payloadPersistidoLote3(LoteForm loteForm){
        ArmazemForm armazemForm = this.payloadArmazem3();
        this.persisteArmazem(armazemForm);

        SetorForm setorForm = SetorForm.builder()
                .tipoProduto(TipoProduto.CONGELADOS)
                .nome("Setor de congelados")
                .codigo("S-196")
                .armazem(armazemForm)
                .espacoDisponivel(10).build();

        this.persisteSetor(setorForm);

        Setor setorDoLote = setorService.obterSetor(setorForm.getCodigo());

        Lote loteEnviado = Lote.builder()
                .setor(setorDoLote)
                .numero(loteForm.getNumero())
                .dataDeValidade(loteForm.getDataDeValidade())
                .dataDeFabricacao(loteForm.getDataDeFabricacao())
                .horaFabricacao(loteForm.getHoraFabricacao())
                .quantidadeAtual(loteForm.getQuantidadeAtual())
                .quantidadeMinina(loteForm.getQuantidadeMinina())
                .temperaturaMinima(loteForm.getTemperaturaMinima())
                .temperaturaAtual(loteForm.getTemperaturaAtual()).build();

        loteService.salvar(loteEnviado);

        return LoteForm.builder()
                .setorForm(setorForm)
                .numero(loteEnviado.getNumero())
                .dataDeValidade(loteEnviado.getDataDeValidade())
                .dataDeFabricacao(loteEnviado.getDataDeFabricacao())
                .horaFabricacao(loteEnviado.getHoraFabricacao())
                .quantidadeAtual(loteEnviado.getQuantidadeAtual())
                .quantidadeMinina(loteEnviado.getQuantidadeMinina())
                .temperaturaMinima(loteEnviado.getTemperaturaMinima())
                .temperaturaAtual(loteEnviado.getTemperaturaAtual()).build();
    }


    private void persisteProduto(ProdutoForm produtoForm) {
        Produto novoProduto = Produto.builder()
                .codigoDoProduto(produtoForm.getCodigoDoProduto())
                .nome(produtoForm.getNome())
                .tipoProduto(produtoForm.getTipoProduto())
                .temperaturaIndicada(produtoForm.getTemperaturaIndicada())
                .build();
        this.produtoService.salvar(novoProduto);
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
        LoteForm loteForm = LoteForm.builder()
                .setorForm(null).produtoForm(null).numero(456)
                .dataDeValidade(LocalDate.of(2021, 12, 10))
                .dataDeFabricacao(LocalDate.now()).horaFabricacao(LocalTime.now()).quantidadeAtual(2)
                .quantidadeMinina(2).temperaturaAtual(18.1).temperaturaMinima(13.2).build();

        LoteForm loteDoProduto = this.payloadPersistidoLote(loteForm);
        ProdutoForm produto = payloadProduto();
        produto.setNumeroDoLote(loteDoProduto.getNumero());
        String requestPayload = objectMapper.writeValueAsString(produto);

        this.mockMvc.perform(post("http://localhost:8080/produtos/cadastra")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestPayload))
                .andExpect(status().isCreated());
    }

    /**
     * teste deve obter um produto caso ele exista
     * no banco de dados, exigindo autenticacao do usuario.
     */

    @Test
    void deveObterUmProdutoCadastrado() throws Exception {
        LoteForm loteForm = LoteForm.builder()
                .setorForm(null).produtoForm(null).numero(765)
                .dataDeValidade(LocalDate.of(2021, 12, 10))
                .dataDeFabricacao(LocalDate.now()).horaFabricacao(LocalTime.now()).quantidadeAtual(2)
                .quantidadeMinina(2).temperaturaAtual(18.1).temperaturaMinima(13.2).build();

        LoteForm loteDoProduto = this.payloadPersistidoLote2(loteForm);
        ProdutoForm produto = ProdutoForm.builder()
                .codigoDoProduto(654)
                .nome("carne seca")
                .preco(60.0)
                .numeroDoLote(loteDoProduto.getNumero())
                .temperaturaIndicada(16.0)
                .build();

        this.persisteProduto(produto);

        String login = "joaquim";
        String senha = "123";
        UsuarioForm payload = UsuarioForm.builder().login(login).senha(senha).build();
        String isso = objectMapper.writeValueAsString(payload);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("http://localhost:8080/auth")
                        .content(isso)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        TokenDTO tokenDTO = objectMapper.readValue(response, TokenDTO.class);


        this.mockMvc.perform(get("http://localhost:8080/produtos/obter/" + produto.getCodigoDoProduto())
                        .header("Authorization", "Bearer " + tokenDTO.getToken()))
                        .andExpect(status().isOk());
    }


    /**
     * teste deve alterar dos dados de um produto
     * caso ele exista no banco e o payload seja valida
     * retorno comparado pelo nome
     */
    @Test
    void deveAlterarDadosDeUmProduto() throws Exception {

        ProdutoForm produto = ProdutoForm.builder()
                .codigoDoProduto(12341)
                .nome("carne de sol")
                .preco(60.0)
                .numeroDoLote(675)
                .temperaturaIndicada(16.0)
                .build();

        LoteForm loteForm = LoteForm.builder()
                .setorForm(null).produtoForm(null).numero(675)
                .dataDeValidade(LocalDate.of(2021, 12, 10))
                .dataDeFabricacao(LocalDate.now()).horaFabricacao(LocalTime.now()).quantidadeAtual(2)
                .quantidadeMinina(2).temperaturaAtual(18.1).temperaturaMinima(13.2).build();

        LoteForm loteDoProduto = this.payloadPersistidoLote3(loteForm);
        produto.setNumeroDoLote(loteDoProduto.getNumero());
        this.persisteProduto(produto);

        ProdutoForm produtoAlterado = ProdutoForm.builder()
                .codigoDoProduto(12341)
                .nome("carne de sol")
                .preco(60.0)
                .numeroDoLote(675)
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

    /**
     * teste deve listar todos os produtos da mesma
     * categoria
     * @autor Joaquim Borges
     */
    @Test
    void deveListarProdutosPorCategoria() throws Exception {
        ProdutoForm produto = ProdutoForm.builder()
                .codigoDoProduto(4679)
                .tipoProduto(TipoProduto.CONGELADOS)
                .nome("sorvete")
                .preco(30.0)
                .temperaturaIndicada(12.2).build();

        this.persisteProduto(produto);

        this.mockMvc.perform(get("http://localhost:8080/produtos/listar/" + produto.getTipoProduto()))
                .andExpect(status().isOk());
    }

    @Test
    void naoDeveObterUmProdutoCadastrado() throws Exception {
        ProdutoForm produto = ProdutoForm.builder()
                .codigoDoProduto(333)
                .nome("carne seca")
                .preco(60.0)
                .temperaturaIndicada(16.0)
                .build();

        this.persisteProduto(produto);

        this.mockMvc.perform(get("http://localhost:8080/produtos/obter/" + produto.getCodigoDoProduto()))
                .andExpect(status().isForbidden());
    }


}
