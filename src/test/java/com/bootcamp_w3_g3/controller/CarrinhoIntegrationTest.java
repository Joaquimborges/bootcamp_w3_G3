package com.bootcamp_w3_g3.controller;

import com.bootcamp_w3_g3.BootcampW3G3Application;
import com.bootcamp_w3_g3.model.dtos.request.*;
import com.bootcamp_w3_g3.model.dtos.response.TokenDTO;
import com.bootcamp_w3_g3.model.entity.*;
import com.bootcamp_w3_g3.repository.UsuarioRepository;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
*  Classe que testa a integração da CarrinhoController
 * @autor alex cruz
 */
@SpringBootTest(classes = BootcampW3G3Application.class)
@AutoConfigureMockMvc
public class CarrinhoIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private RepresentanteService representanteService;
    @Autowired
    private SetorService setorService;
    @Autowired
    private ArmazemService armazemService;
    @Autowired
    private LoteService loteService;
    @Autowired
    private CompradorService compradorService;
    @Autowired
    private CarteiraService carteiraService;
    @Autowired
    private ItensService itensService;
    @Autowired
    private CarrinhoService carrinhoService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    private CompradorForm payLoadComprador111(){
        return CompradorForm.builder()
                .codigo("C-1001")
                .nome("Henrique Alfonse")
                .sobrenome("Zang")
                .cpf("55544466660")
                .telefone("51 5555-7777")
                .endereco("Av. João do meio fio.")
                .build()
        ;
    }

    private CompradorForm payLoadComprador1110(){
        return CompradorForm.builder()
                .codigo("C-5004")
                .nome("Henrique Alfonse")
                .sobrenome("Zang")
                .cpf("12309876590")
                .telefone("51 5555-7777")
                .endereco("Av. João do meio fio.")
                .build()
        ;
    }


    private CompradorForm payLoadComprador222(){
        return CompradorForm.builder()
                .codigo("C-2001")
                .nome("Pedro Alfonse")
                .sobrenome("Zang")
                .cpf("55544466690")
                .telefone("51 5555-7777")
                .endereco("Av. João do meio fio.")
                .build();
    }

    private CompradorForm payLoadComprador333(){
        return CompradorForm.builder()
                .codigo("C-3001")
                .nome("Pedro Alfonse")
                .sobrenome("Zang")
                .cpf("55544466098")
                .telefone("51 5555-7777")
                .endereco("Av. João do meio fio.")
                .build();
    }

    private void persisteComprador(CompradorForm compradorForm){
        Comprador novoComprador = Comprador.builder()
                .codigo(compradorForm.getCodigo())
                .nome(compradorForm.getNome())
                .sobrenome(compradorForm.getSobrenome())
                .cpf(compradorForm.getCpf())
                .telefone(compradorForm.getTelefone())
                .endereco(compradorForm.getEndereco())
                .build()
        ;
        this.compradorService.salvar(novoComprador);

        Carteira carteira = carteiraService.obter(novoComprador.getCarteira().getNumero());
        carteira.setSaldo(1000.0);
        carteiraService.atualizar(carteira);
    }

    private ProdutoForm payLoadProduto111() {
        return ProdutoForm.builder()
                .codigoDoProduto(1111)
                .nome("carne seca")
                .tipoProduto(TipoProduto.FRESCOS)
                .preco(50.00)
                .temperaturaIndicada(16.00)
                .build();
    }

    private ProdutoForm payLoadProduto222() {
        return ProdutoForm.builder()
                .codigoDoProduto(2222)
                .nome("sorvete")
                .tipoProduto(TipoProduto.CONGELADOS)
                .preco(50.00)
                .temperaturaIndicada(16.00)
                .build();
    }

    private ProdutoForm payLoadProduto333() {
        return ProdutoForm.builder()
                .codigoDoProduto(3333)
                .nome("refri")
                .preco(50.00)
                .tipoProduto(TipoProduto.REFRIGERADOS)
                .temperaturaIndicada(16.00)
                .build();
    }

    private ProdutoForm payLoadProduto444() {
        return ProdutoForm.builder()
                .codigoDoProduto(4444)
                .nome("frango")
                .preco(35.00)
                .tipoProduto(TipoProduto.CONGELADOS)
                .temperaturaIndicada(16.00)
                .build();
    }

    private ProdutoForm payLoadProduto555() {
        return ProdutoForm.builder()
                .codigoDoProduto(5555)
                .nome("alface")
                .preco(3.50)
                .tipoProduto(TipoProduto.FRESCOS)
                .temperaturaIndicada(16.00)
                .build();
    }

    private ProdutoForm payLoadProduto777() {
        return ProdutoForm.builder()
                .codigoDoProduto(7777)
                .nome("alface")
                .preco(3.50)
                .tipoProduto(TipoProduto.FRESCOS)
                .temperaturaIndicada(16.00)
                .build();
    }

    private ProdutoForm payLoadProduto888() {
        return ProdutoForm.builder()
                .codigoDoProduto(8888)
                .nome("alface")
                .preco(3.50)
                .tipoProduto(TipoProduto.FRESCOS)
                .temperaturaIndicada(16.00)
                .build();
    }

    private void persisteProduto(ProdutoForm produtoForm) {
        Produto novoProduto = Produto.builder()
                .codigoDoProduto(produtoForm.getCodigoDoProduto())
                .nome(produtoForm.getNome())
                .tipoProduto(produtoForm.getTipoProduto())
                .preco(produtoForm.getPreco())
                .temperaturaIndicada(produtoForm.getTemperaturaIndicada())
                .codLote(99)
                .build();
        this.produtoService.salvar(novoProduto);
    }

    private ItensForm payLoadItens111(ProdutoForm produtoForm){
        this.persisteProduto(produtoForm);

        return ItensForm.builder()
                .codigoDoProduto(produtoForm.getCodigoDoProduto())
                .quantidade(5)
                .build();
    }

    private ItensForm payLoadItens222(ProdutoForm produtoForm){
        this.persisteProduto(produtoForm);

        return ItensForm.builder()
                .codigoDoProduto(produtoForm.getCodigoDoProduto())
                .quantidade(7)
                .build();
    }

    private ItensForm payLoadItens333(ProdutoForm produtoForm){
        this.persisteProduto(produtoForm);

        return ItensForm.builder()
                .codigoDoProduto(produtoForm.getCodigoDoProduto())
                .quantidade(10)
                .build();
    }

    private ItensForm payLoadItens444(ProdutoForm produtoForm){
        this.persisteProduto(produtoForm);

        return ItensForm.builder()
                .codigoDoProduto(produtoForm.getCodigoDoProduto())
                .quantidade(5)
                .build();
    }

    private ItensForm payLoadItens555(ProdutoForm produtoForm){
        this.persisteProduto(produtoForm);

        return ItensForm.builder()
                .codigoDoProduto(produtoForm.getCodigoDoProduto())
                .quantidade(4)
                .build();
    }

    private void persisteItens(ItensForm itensForm){
        Produto produto = this.produtoService.obter(itensForm.getCodigoDoProduto());
        Itens novoItens = Itens.builder()
                .produto(produto)
                .quantidade(itensForm.getQuantidade())
                .build();
        this.itensService.salvar(novoItens);
    }

    private RepresentanteForm payLoadRepresentante111(){
        return RepresentanteForm.builder()
                .codigo("R-3010")
                .nome("Alex")
                .sobrenome("Gomes")
                .endereco("rua qualquer")
                .cpf("12323434524")
                .telefone("11-2473648")
                .build()
        ;
    }

    private RepresentanteForm payLoadRepresentante222(){
        return RepresentanteForm.builder()
                .codigo("R-2100")
                .nome("Alex")
                .sobrenome("Gomes")
                .endereco("rua qualquer")
                    .cpf("12323434513")
                .telefone("11-2473648")
                .build();
    }

    private RepresentanteForm payLoadRepresentante333(){
        return RepresentanteForm.builder()
                .codigo("R-3100")
                .nome("Alex")
                .sobrenome("Gomes")
                .endereco("rua qualquer")
                .cpf("12323434505")
                .telefone("11-2473648")
                .build()
        ;
    }

    private RepresentanteForm payLoadRepresentante22(){
        return RepresentanteForm.builder()
                .codigo("R-400")
                .nome("Alexia")
                .sobrenome("Gomes")
                .endereco("rua qualquer")
                .cpf("12323434556")
                .telefone("11-2473648")
                .build();
    }

    private RepresentanteForm payLoadRepresentante23(){
        return RepresentanteForm.builder()
                .codigo("R-4010")
                .nome("Alexia")
                .sobrenome("Gomes")
                .endereco("rua qualquer")
                .cpf("12345678908")
                .telefone("11-2473648")
                .build()
        ;
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

    private ArmazemForm payLoadArmazem111(RepresentanteForm representanteForm) {

        this.persisteRepresentante(representanteForm);

        return ArmazemForm.builder()
                .codArmazem("AR-1000")
                .nome("armazem central")
                .codigoRepresentante(representanteForm.getCodigo())
                .endereco("qualquer lugar")
                .numero(100)
                .uf("SP")
                .build()
        ;
    }

    private ArmazemForm payLoadArmazem222(RepresentanteForm representanteForm) {
        this.persisteRepresentante(representanteForm);

        return ArmazemForm.builder()
                .codArmazem("AR-2000")
                .nome("armazem central")
                .codigoRepresentante(representanteForm.getCodigo())
                .endereco("qualquer lugar")
                .numero(100)
                .uf("SP").build();
    }

    private ArmazemForm payLoadArmazem333(RepresentanteForm representanteForm) {
        this.persisteRepresentante(representanteForm);

        return ArmazemForm.builder()
                .codArmazem("AR-3000")
                .nome("armazem central")
                .codigoRepresentante(representanteForm.getCodigo())
                .endereco("qualquer lugar")
                .numero(100)
                .uf("SP").build();
    }

    private ArmazemForm payLoadArmazem22(RepresentanteForm representanteForm) {
        this.persisteRepresentante(representanteForm);

        return ArmazemForm.builder()
                .codArmazem("AR-400")
                .nome("armazem central")
                .codigoRepresentante(representanteForm.getCodigo())
                .endereco("qualquer lugar")
                .numero(100)
                .uf("SP").build();
    }

    private ArmazemForm payLoadArmazem23(RepresentanteForm representanteForm) {
        this.persisteRepresentante(representanteForm);

        return ArmazemForm.builder()
                .codArmazem("AR-401")
                .nome("armazem central")
                .codigoRepresentante(representanteForm.getCodigo())
                .endereco("qualquer lugar")
                .numero(100)
                .uf("SP").build();
    }

    private void persisteArmazem(ArmazemForm armazemForm) {

        Representante representante = this.representanteService.obter(armazemForm.getCodigoRepresentante());

        Armazem armazem = Armazem.builder()
                .codArmazem(armazemForm.getCodArmazem())
                .nome(armazemForm.getNome())
                .representante(representante)
                .endereco(armazemForm.getEndereco())
                .uf(armazemForm.getUf())
                .build();

        this.armazemService.criarArmazem(armazem);
    }

    private void persisteSetor111(SetorForm setorForm){

        Armazem armazemSetor = this.armazemService.obterArmazem(setorForm.getCodigoArmazem());

        Setor setor = Setor.builder()
                .tipoProduto(setorForm.getTipoProduto())
                .nome(setorForm.getNome())
                .armazem(armazemSetor)
                .espacoDisponivel(setorForm.getEspacoDisponivel())
                .codigo(setorForm.getCodigo()).build();

        this.setorService.salvarSetor(setor);
    }

    private void persisteSetor222(SetorForm setorForm){

        Armazem armazemSetor = this.armazemService.obterArmazem(setorForm.getCodigoArmazem());

        Setor setor = Setor.builder()
                .tipoProduto(setorForm.getTipoProduto())
                .nome(setorForm.getNome())
                .armazem(armazemSetor)
                .espacoDisponivel(setorForm.getEspacoDisponivel())
                .codigo(setorForm.getCodigo()).build();

        this.setorService.salvarSetor(setor);
    }

    private void persisteSetor333(SetorForm setorForm){

        Armazem armazemSetor = this.armazemService.obterArmazem(setorForm.getCodigoArmazem());

        Setor setor = Setor.builder()
                .tipoProduto(setorForm.getTipoProduto())
                .nome(setorForm.getNome())
                .armazem(armazemSetor)
                .espacoDisponivel(setorForm.getEspacoDisponivel())
                .codigo(setorForm.getCodigo()).build();

        this.setorService.salvarSetor(setor);
    }

    private void persisteLote111(LoteForm loteForm) {

        Setor setorDoLote = setorService.obterSetor(loteForm.getCodigoSetor());
        Produto produto = produtoService.obter(loteForm.getCodigoProduto());

        Lote loteEnviado = Lote.builder()
                .setor(setorDoLote)
                .produto(produto)
                .numero(loteForm.getNumero())
                .dataDeValidade(loteForm.getDataDeValidade())
                .dataDeFabricacao(loteForm.getDataDeFabricacao())
                .horaFabricacao(loteForm.getHoraFabricacao())
                .quantidadeAtual(loteForm.getQuantidadeAtual())
                .quantidadeMinina(loteForm.getQuantidadeMinina())
                .temperaturaMinima(loteForm.getTemperaturaMinima())
                .temperaturaAtual(loteForm.getTemperaturaAtual()).build();

        loteService.salvar(loteEnviado);
    }

    private void persisteLote222(LoteForm loteForm) {

        Setor setorDoLote = setorService.obterSetor(loteForm.getCodigoSetor());
        Produto produto = produtoService.obter(loteForm.getCodigoProduto());

        Lote loteEnviado = Lote.builder()
                .setor(setorDoLote)
                .produto(produto)
                .numero(loteForm.getNumero())
                .dataDeValidade(loteForm.getDataDeValidade())
                .dataDeFabricacao(loteForm.getDataDeFabricacao())
                .horaFabricacao(loteForm.getHoraFabricacao())
                .quantidadeAtual(loteForm.getQuantidadeAtual())
                .quantidadeMinina(loteForm.getQuantidadeMinina())
                .temperaturaMinima(loteForm.getTemperaturaMinima())
                .temperaturaAtual(loteForm.getTemperaturaAtual()).build();

        loteService.salvar(loteEnviado);
    }

    private void persisteLote333(LoteForm loteForm) {

        Setor setorDoLote = setorService.obterSetor(loteForm.getCodigoSetor());
        Produto produto = produtoService.obter(loteForm.getCodigoProduto());

        Lote loteEnviado = Lote.builder()
                .setor(setorDoLote)
                .produto(produto)
                .numero(loteForm.getNumero())
                .dataDeValidade(loteForm.getDataDeValidade())
                .dataDeFabricacao(loteForm.getDataDeFabricacao())
                .horaFabricacao(loteForm.getHoraFabricacao())
                .quantidadeAtual(loteForm.getQuantidadeAtual())
                .quantidadeMinina(loteForm.getQuantidadeMinina())
                .temperaturaMinima(loteForm.getTemperaturaMinima())
                .temperaturaAtual(loteForm.getTemperaturaAtual()).build();

        loteService.salvar(loteEnviado);
    }

    private CarrinhoForm payLoadCarrinho111(){

        CompradorForm compradorForm111 = this.payLoadComprador111();
        this.persisteComprador(compradorForm111);

        ProdutoForm produtoForm111 = this.payLoadProduto111();

        ItensForm itensForm111 = this.payLoadItens111(produtoForm111);

        this.persisteItens(itensForm111);

        List<ItensForm> itensFormList = new ArrayList<>();
        itensFormList.add(itensForm111);

        return CarrinhoForm.builder()
                .codigoCarrinho("CAR-987")
                .dataDaOrdem(LocalDate.parse("2021-10-22", DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .statusCompra(StatusCompra.CONCLUIDO)
                .codigoComprador(compradorForm111.getCodigo())
                .itensList(itensFormList)
                .build();
    }
    
   private CarrinhoForm payLoadCarrinho222(){

        CompradorForm compradorForm222 = this.payLoadComprador222();
        this.persisteComprador(compradorForm222);

        ProdutoForm produtoForm222 = this.payLoadProduto222();

        ItensForm itensForm222 = this.payLoadItens222(produtoForm222);

        this.persisteItens(itensForm222);

        List<ItensForm> itensFormList = new ArrayList<>();

        itensFormList.add(itensForm222);

            return CarrinhoForm.builder()
                    .codigoCarrinho("CAR-1996")
                    .dataDaOrdem(LocalDate.parse("2021-10-22", DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .statusCompra(StatusCompra.CANCELADO)
                    .codigoComprador(compradorForm222.getCodigo())
                    .itensList(itensFormList)
                    .build();
    }

   private CarrinhoForm payLoadCarrinho333(){

        CompradorForm compradorForm333 = this.payLoadComprador333();
        this.persisteComprador(compradorForm333);

        ProdutoForm produtoForm333 = this.payLoadProduto333();

        ItensForm itensForm333 = this.payLoadItens333(produtoForm333);

        this.persisteItens(itensForm333);

        List<ItensForm> itensFormList = new ArrayList<>();

        itensFormList.add(itensForm333);

        return CarrinhoForm.builder()
                .codigoCarrinho("CAR-9966")
                .dataDaOrdem(LocalDate.parse("2021-11-22", DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .statusCompra(StatusCompra.CANCELADO)
                .codigoComprador(compradorForm333.getCodigo())
                .itensList(itensFormList)
                .build();
    }

   private Carrinho persisteCarrinho(CarrinhoForm carrinhoForm){

   
        List<ItensForm> itensFormList = carrinhoForm.getItensList();

        List<Itens> itensList = new ArrayList<>();

        for (ItensForm i : itensFormList) {
            Produto produto = this.produtoService.obter(i.getCodigoDoProduto());
            Integer quantidade = i.getQuantidade();
            itensList.add(new Itens(produto, quantidade));
        }

        Carrinho novoCarrinho = Carrinho.builder()
                .codigo(carrinhoForm.getCodigoCarrinho())
                .dataDeOrdem(carrinhoForm.getDataDaOrdem())
                .statusCompra(carrinhoForm.getStatusCompra())
                .itensList(itensList)
                .codigoComprador(carrinhoForm.getCodigoComprador())
                .build();
        return carrinhoService.salvar(novoCarrinho);
    }

   /**
     * Registra um carrinho, criando toda os objetos necessários ou interligados com o carrinho e/ou suas dependencias,
     * executa o registro do token utilizado.
     * @autor alex cruz
     * @throws Exception
   */
  @Test
  void deveRegistrarUmCarrinho() throws Exception{
        RepresentanteForm representanteForm111 = this.payLoadRepresentante111();

        ArmazemForm armazemForm111 = this.payLoadArmazem111(representanteForm111);
        this.persisteArmazem(armazemForm111);

        SetorForm setorDoLote222 =  SetorForm.builder()
                .tipoProduto(TipoProduto.CONGELADOS)
                .nome("Setor de congelados").codigo("Se-400")
                .codigoArmazem(armazemForm111.getCodArmazem()).espacoDisponivel(100)
                .build()
        ;

        this.persisteSetor111(setorDoLote222);

        ProdutoForm produtoForm444 = this.payLoadProduto444();
        this.persisteProduto(produtoForm444);

        LoteForm loteForm = LoteForm.builder()
                .numero(99).codigoSetor(setorDoLote222.getCodigo()).temperaturaAtual(17.0)
                .temperaturaMinima(13.1).quantidadeMinina(2).quantidadeAtual(3)
                .codigoProduto(produtoForm444.getCodigoDoProduto()).horaFabricacao(LocalTime.now())
                .dataDeValidade(LocalDate.of(2021, 12, 20))
                .dataDeFabricacao(LocalDate.now()).build()
        ;
        this.persisteLote111(loteForm);

        CarrinhoForm carrinhoForm111 = this.payLoadCarrinho111();

        Usuario usuario = Usuario.builder().login("representante")
                .senha("$2a$10$BDoxHiGmU8F1ohZ7VEvRoeZujhmT7JP34Nmu/PGkmjPOP4sPX9nd6").build();
        usuarioRepository.save(usuario);

        String login = "representante";
        String senha = "123";
        UsuarioForm payload = UsuarioForm.builder().login(login).senha(senha).build();
        String isso = objectMapper.writeValueAsString(payload);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("http://localhost:8080/auth")
                        .content(isso)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn()
        ;

        String response = result.getResponse().getContentAsString();
        TokenDTO tokenDTO = objectMapper.readValue(response, TokenDTO.class);

        String requestPayload = objectMapper.writeValueAsString(carrinhoForm111);

        this.mockMvc.perform(post("http://localhost:8080/carrinho/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestPayload)
                .header("Authorization", "Bearer " + tokenDTO.getToken()))
                .andExpect(status().isCreated()
        );
  }


  /**
  * Teste deve listar todos os produtos
  * contidos no carrinho.
  * @author Joaquim Borges
  */
  @Test
  void deveMostrarProdutosDoCarrinho() throws Exception {
    RepresentanteForm representanteForm = payLoadRepresentante22();
    ArmazemForm armazemForm = payLoadArmazem22(representanteForm);
    this.persisteArmazem(armazemForm);

    SetorForm setorForm = SetorForm.builder().codigo("S-400")
            .nome("B").codigoArmazem(armazemForm.getCodArmazem()).tipoProduto(TipoProduto.FRESCOS)
            .espacoDisponivel(100).build();
    this.persisteSetor111(setorForm);

    ProdutoForm produtoForm = ProdutoForm.builder()
            .codigoDoProduto(400).tipoProduto(TipoProduto.FRESCOS)
            .nome("picanha").preco(60.0).build();
    this.persisteProduto(produtoForm);

    LoteForm loteForm = LoteForm.builder().numero(400).codigoSetor(setorForm.getCodigo())
            .codigoProduto(produtoForm.getCodigoDoProduto())
            .quantidadeMinina(2).quantidadeAtual(10)
            .dataDeFabricacao(LocalDate.now()).dataDeValidade(LocalDate.of(2021, 12, 30))
            .build();
    this.persisteLote222(loteForm);

    ItensForm itensForm = ItensForm.builder()
            .codigoDoProduto(produtoForm.getCodigoDoProduto())
            .quantidade(5).build();
    this.persisteItens(itensForm);

    CompradorForm compradorForm = CompradorForm.builder()
            .codigo("C-500")
            .nome("Henrique Alfonse")
            .sobrenome("Zang")
            .cpf("12309876534")
            .telefone("51 5555-7777")
            .endereco("Av. João do meio fio.")
            .build()
    ;
    this.persisteComprador(compradorForm);

    CarrinhoForm carrinhoForm = CarrinhoForm.builder()
            .codigoCarrinho("Ca-400").codigoComprador(compradorForm.getCodigo())
            .itensList(List.of(itensForm)).statusCompra(StatusCompra.CONCLUIDO)
            .dataDaOrdem(LocalDate.now())
            .build()
    ;

   Carrinho carrinho = this.persisteCarrinho(carrinhoForm);

   this.mockMvc.perform(get("http://localhost:8080/carrinho/carrinho/" + carrinho.getId()))
           .andExpect(status().isOk()
   );

 }

 /**
  * Teste atualizar o pedido do carrinho,
  * substituindo os produtos.
  * @author Joaquim Borges
  */
 @Test
 void deveAtualizarPedido() throws Exception {
    RepresentanteForm representanteForm = payLoadRepresentante23();
    ArmazemForm armazemForm = payLoadArmazem23(representanteForm);
    this.persisteArmazem(armazemForm);

    SetorForm setorForm = SetorForm.builder().codigo("S-401")
            .nome("C")
            .codigoArmazem(armazemForm.getCodArmazem())
            .tipoProduto(TipoProduto.FRESCOS)
            .espacoDisponivel(100)
            .build()
    ;
    this.persisteSetor111(setorForm);

    ProdutoForm produtoForm = ProdutoForm.builder()
            .codigoDoProduto(401)
            .tipoProduto(TipoProduto.FRESCOS)
            .nome("picanha")
            .preco(60.0)
            .build()
    ;
    this.persisteProduto(produtoForm);

    LoteForm loteForm = LoteForm.builder().numero(401).codigoSetor(setorForm.getCodigo())
            .codigoProduto(produtoForm.getCodigoDoProduto()).quantidadeMinina(2).quantidadeAtual(10)
            .dataDeFabricacao(LocalDate.now()).dataDeValidade(LocalDate.of(2021, 12, 30))
            .build()
    ;
    this.persisteLote222(loteForm);

    ItensForm itensForm = ItensForm.builder()
            .codigoDoProduto(produtoForm.getCodigoDoProduto())
            .quantidade(5)
            .build()
    ;
    this.persisteItens(itensForm);

    CompradorForm compradorForm = payLoadComprador1110();
    this.persisteComprador(compradorForm);

    CarrinhoForm carrinhoForm = CarrinhoForm.builder()
            .codigoCarrinho("Ca-401")
            .codigoComprador(compradorForm.getCodigo())
            .itensList(List.of(itensForm))
            .statusCompra(StatusCompra.CONCLUIDO)
            .dataDaOrdem(LocalDate.now())
            .build()
    ;
    Carrinho carrinho = this.persisteCarrinho(carrinhoForm);

    ProdutoForm produto2 = ProdutoForm.builder()
            .codigoDoProduto(402)
            .tipoProduto(TipoProduto.FRESCOS)
            .nome("Peixe")
            .preco(40.0)
            .build()
    ;
    this.persisteProduto(produto2);

    LoteForm lote2 = LoteForm.builder().numero(401).codigoSetor(setorForm.getCodigo())
            .codigoProduto(produto2.getCodigoDoProduto()).quantidadeMinina(2).quantidadeAtual(10)
            .dataDeFabricacao(LocalDate.now()).dataDeValidade(LocalDate.of(2021, 12, 30))
            .build()
    ;
    this.persisteLote222(lote2);

    ItensForm itensAlterado = ItensForm.builder()
            .codigoDoProduto(produto2.getCodigoDoProduto())
            .quantidade(3)
            .build()
    ;
    this.persisteItens(itensAlterado);

    CarrinhoForm carrinhoAtualizado = CarrinhoForm.builder()
            .codigoCarrinho("Ca-401")
            .codigoComprador(compradorForm.getCodigo())
            .itensList(List.of(itensAlterado))
            .statusCompra(StatusCompra.CONCLUIDO)
            .dataDaOrdem(LocalDate.now())
            .build()
    ;

    String requestPayload = objectMapper.writeValueAsString(carrinhoAtualizado);
    this.mockMvc.perform(put("http://localhost:8080/carrinho/atualizar/" + carrinho.getCodigo())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestPayload))
            .andExpect(status().isOk());
    }

    /**
     * teste deve alterar informações dos carrinhos.
     * @autor Alex Cruz
     */
    @Test
    void alteraPedidosCarrinhos() throws Exception {

        RepresentanteForm representanteForm222 = this.payLoadRepresentante222();

        ArmazemForm armazemForm222 = this.payLoadArmazem222(representanteForm222);
        this.persisteArmazem(armazemForm222);

        ProdutoForm produtoForm777 = this.payLoadProduto777();
        this.persisteProduto(produtoForm777);

        SetorForm setorDoLote222 =  SetorForm.builder()
                .tipoProduto(TipoProduto.REFRIGERADOS)
                .nome("Setor de regrigerados").codigo("SeT-292292")
                .codigoArmazem(armazemForm222.getCodArmazem())
                .espacoDisponivel(10)
                .build();

        this.persisteSetor222(setorDoLote222);

        LoteForm loteForm222 = LoteForm.builder()
                .numero(111).codigoSetor(setorDoLote222.getCodigo()).temperaturaAtual(17.0)
                .temperaturaMinima(13.1).quantidadeMinina(2).quantidadeAtual(3)
                .codigoProduto(produtoForm777.getCodigoDoProduto())
                .horaFabricacao(LocalTime.now())
                .dataDeValidade(LocalDate.of(2021, 12, 20))
                .dataDeFabricacao(LocalDate.now())
                .build();

        this.persisteLote222(loteForm222);

        CarrinhoForm carrinhoForm222 = this.payLoadCarrinho222();
        this.persisteCarrinho(carrinhoForm222);

        ProdutoForm produtoForm888 = this.payLoadProduto888();

        ItensForm itensForm444 = this.payLoadItens444(produtoForm888);
        this.persisteItens(itensForm444);

        CarrinhoForm carrinhoForm222Alterado = CarrinhoForm.builder()
                .codigoCarrinho("CAR-1996")
                .dataDaOrdem(LocalDate.parse("2021-11-25", DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .statusCompra(StatusCompra.CONCLUIDO)
                .codigoComprador(carrinhoForm222.getCodigoComprador())
                .itensList(List.of(itensForm444))
                .build();


        String requestPayload = objectMapper.writeValueAsString(carrinhoForm222Alterado);


        this.mockMvc.perform(put("http://localhost:8080/carrinho/alterar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestPayload))
                .andExpect(status().isOk());

    }

    /**
     * teste deve listar todos os carrinhos.
     * @autor Alex Cruz
     */
    @Test
    void deveListarCarrinhos() throws Exception {

        RepresentanteForm representanteForm333 = this.payLoadRepresentante333();

        ArmazemForm armazemForm333 = this.payLoadArmazem333(representanteForm333);
        this.persisteArmazem(armazemForm333);

        ProdutoForm produtoForm555 = this.payLoadProduto555();
        this.persisteProduto(produtoForm555);

        SetorForm setorDoLote333 =  SetorForm.builder()
                .tipoProduto(TipoProduto.REFRIGERADOS)
                .nome("Setor de regrigerados").codigo("SeT-191191")
                .codigoArmazem(armazemForm333.getCodArmazem())
                .espacoDisponivel(10)
                .build();

        this.persisteSetor333(setorDoLote333);

        LoteForm loteForm333 = LoteForm.builder()
                .numero(111).codigoSetor(setorDoLote333.getCodigo()).temperaturaAtual(17.0)
                .temperaturaMinima(13.1).quantidadeMinina(2).quantidadeAtual(3)
                .codigoProduto(produtoForm555.getCodigoDoProduto())
                .horaFabricacao(LocalTime.now())
                .dataDeValidade(LocalDate.of(2021, 12, 20))
                .dataDeFabricacao(LocalDate.now())
                .build();

        this.persisteLote333(loteForm333);

        CarrinhoForm carrinhoForm333 = this.payLoadCarrinho333();
        this.persisteCarrinho(carrinhoForm333);

        this.mockMvc.perform(get("http://localhost:8080/carrinho/listar"))
                .andExpect(status().isOk());

    }


}
