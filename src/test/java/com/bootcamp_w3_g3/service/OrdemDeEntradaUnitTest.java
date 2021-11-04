package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.model.entity.*;
import com.bootcamp_w3_g3.repository.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author hugo damm
 * @author matheus willock
 */
public class OrdemDeEntradaUnitTest {


    VendedorRepository vendedorRepository = Mockito.mock(VendedorRepository.class);
    VendedorService vendedorService = new VendedorService(vendedorRepository);


    LoteRepository loteRepository = Mockito.mock(LoteRepository.class);
    LoteService loteService = new LoteService(loteRepository);


    RepresentanteRepository representanteRepository = Mockito.mock(RepresentanteRepository.class);
    RepresentanteService representanteService = new RepresentanteService(representanteRepository);

    ArmazemRepository armazemRepository = Mockito.mock(ArmazemRepository.class);
    ArmazemService armazemService = new ArmazemService(armazemRepository);

    SetorRepository setorRepository = Mockito.mock(SetorRepository.class);
    SetorService setorService = new SetorService(setorRepository);

    OrdemDeEntradaRepository ordemDeEntradaRepository = Mockito.mock(OrdemDeEntradaRepository.class);

    OrdemDeEntradaService ordemDeEntradaService = new OrdemDeEntradaService(ordemDeEntradaRepository, representanteService, armazemService, setorService );

    private static final Long SETOR_ID = Long.valueOf(1);

    List<Setor> setorList = new ArrayList<>();

    Representante representante1 = Representante.builder()
            .nome("Ernani")
            .sobrenome("Santos")
            .cpf("123.345.678-92")
            .telefone("11 9 7867-3456")
            .endereco("Rua B")
            .build();

    Representante representante2 = Representante.builder()
            .nome("João")
            .sobrenome("Paulo")
            .cpf("678.123.645-92")
            .telefone("19 9 7867-3456")
            .endereco("Rua ABCDEF")
            .build();

    Armazem armazem1 = Armazem.builder()
            .codArmazem("Ar-123")
            .representante(representante1)
            .nome("AR1")
            .endereco("rua 10")
            .uf("SP").build();

    Setor setor1 = Setor.builder()
            .id(SETOR_ID)
            .codigo("123")
            .nome("Setor123")
            .tipoProduto(TipoProduto.CONGELADOS)
            .armazem(armazem1).build();

    Setor setor2 = Setor.builder()
            .id(SETOR_ID)
            .codigo("2")
            .nome("Setor2")
            .tipoProduto(TipoProduto.FRESCOS)
            .armazem(armazem1).build();

    Produto produto = Produto.builder()
            .codigoDoProduto(123)
            .nome("carne")
            .preco(60.0)
            .temperaturaIndicada(8.0)
            .tipoProduto(TipoProduto.FRESCOS)
            .build();

    Lote lote = Lote.builder()
            .numero(10)
            .quantidadeAtual(10)
            .quantidadeMinina(5)
            .temperaturaAtual(10.0)
            .temperaturaMinima(5.0)
            .horaFabricacao(LocalTime.now())
            .dataDeFabricacao(LocalDate.now())
            .dataDeValidade(LocalDate.now())
            .dataDeValidade(LocalDate.now())
            .produto(produto)
            .build();

    Vendedor vendedor = Vendedor.builder()
            .nome("Alex")
            .sobrenome("Cruz")
            .cpf("2345678910")
            .telefone("5555555")
            .endereco("Rua Joao neves 18")
            .build();

    Vendedor vendedor1 = Vendedor.builder()
            .nome("Mestre")
            .sobrenome("Dos Mestres")
            .cpf("2345678910")
            .telefone("21 9 9934-3454")
            .endereco("Av. Copacabana")
            .build();

    OrdemDeEntrada ordemDeEntrada = OrdemDeEntrada.builder()
            .numeroDaOrdem(100)
            .dataDaOrdem(LocalDate.now())
            .setor(setor1)
            .representante(representante1)
            .lote(lote)
            .vendedor(vendedor)
            .build();

    @Test
    void registrarOrdemDeEntradaTest()
    {
        ordemDeEntrada.setSetor(setor1);
        Mockito.when(setorRepository.save(Mockito.any(Setor.class))).thenReturn(setor1);
        setorService.salvarSetor(setor1);
        Mockito.when(ordemDeEntradaRepository.save(Mockito.any(OrdemDeEntrada.class))).thenReturn(ordemDeEntrada);

        ordemDeEntradaService.registra(ordemDeEntrada);
        assertNotNull(ordemDeEntrada);
    }

    @Test
    void alterarOrdemDeEntradaTest() {

        //alteração do setor - preparação
        ordemDeEntrada.setLote(lote);
        ordemDeEntrada.setRepresentante(representante1);
        ordemDeEntrada.setSetor(setor1);
        ordemDeEntrada.setVendedor(vendedor1);

        ordemDeEntradaService = Mockito.mock(OrdemDeEntradaService.class);

        Mockito.when(loteRepository.save(Mockito.any(Lote.class))).thenReturn(lote);
        Mockito.when(representanteRepository.save(Mockito.any(Representante.class))).thenReturn(representante1);
        Mockito.when(setorRepository.save(Mockito.any(Setor.class))).thenReturn(setor1);
        Mockito.when(vendedorRepository.save(Mockito.any(Vendedor.class))).thenReturn(vendedor);
        Mockito.when(ordemDeEntradaRepository.findByNumeroDaOrdem(Mockito.any(Integer.class))).thenReturn(ordemDeEntrada);
        Mockito.when(ordemDeEntradaRepository.save(Mockito.any(OrdemDeEntrada.class))).thenReturn(ordemDeEntrada);
        Mockito.when(ordemDeEntradaService.atualizaOrdem(ordemDeEntrada)).thenReturn(ordemDeEntrada);

        ordemDeEntradaService.atualizaOrdem(ordemDeEntrada);
        OrdemDeEntrada atualizado = ordemDeEntradaService.obter(ordemDeEntrada.getNumeroDaOrdem());
        if (atualizado != null) {
            Mockito.verify(ordemDeEntradaService, Mockito.times(1)).atualizaOrdem(ordemDeEntrada);
            assertNotNull(atualizado);
            assertNotEquals(atualizado.getVendedor(), ordemDeEntrada.getVendedor());

        }

    }

}
