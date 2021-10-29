package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.model.entity.Armazem;
import com.bootcamp_w3_g3.model.entity.Representante;
import com.bootcamp_w3_g3.model.entity.Setor;
import com.bootcamp_w3_g3.repository.SetorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author hugo damm
 */

public class SetorUnitTest {

    private SetorService setorService;
    private final SetorRepository setorRepository = Mockito.mock(SetorRepository.class);

    Representante representante1 = Representante.builder()
            .nome("Ernani").sobrenome("Santos").cpf("123.345.678-92").telefone("11 9 7867-3456").endereco("Rua B").build();

    List<Setor> setorList = new ArrayList<>();


    Armazem armazem1 = Armazem.builder()
            .codArmazem("Ar-123")
            .representante(representante1)
            .nome("AR1")
            .endereco("rua 10")
            .uf("SP").build();


    Setor setor1 = Setor.builder()
            .codigo("123")
            .nome("Setor123")
            .tipoProduto("Frescos")
            .armazem(armazem1).build();


    Setor setor2 = Setor.builder()
            .codigo("124")
            .nome("Setor124")
            .tipoProduto("Congelados")
            .armazem(armazem1).build();



    @Test
    void salvarSetorTest(){

        Mockito.when(setorRepository.save(Mockito.any(Setor.class))).thenReturn(setor2);

        setorService = new SetorService(setorRepository);
        setorService.salvarSetor(setor2);

        assertNotNull(setor2);
    }

    @Test
    void obterSetorTest(){
        setor1.setCodigo("123");
        Mockito.when(setorRepository.findByCodigo(Mockito.any(String.class))).thenReturn(setor1);

        setorService = new SetorService(setorRepository);
        Setor obtido = setorService.obterSetor(setor1.getCodigo());

        Mockito.verify(setorRepository,
                Mockito.times(1)).findByCodigo(setor1.getCodigo());

        assertEquals(obtido.getCodigo(), setor1.getCodigo());
    }

    @Test
    void listarSetorTest(){
        setorList.add(setor1);
        setorList.add(setor2);

        Mockito.when(setorRepository.findAll()).thenReturn(setorList);

        setorService = new SetorService(setorRepository);
        List<Setor> lista = setorService.listarSetores();

        Mockito.verify(setorRepository, Mockito.times(1)).findAll();

        assertEquals(lista.size(), setorList.size());

    }

    @Test
    void atualizarSetorTest(){
        setor1.setCodigo("1234");
        setor1.setNome("Setor1234");
        setor1.setTipoProduto("Frescos01");

        Mockito.when(setorRepository.findByCodigo(Mockito.any(String.class))).thenReturn(setor1);
        Mockito.when(setorRepository.save(Mockito.any(Setor.class))).thenReturn(setor1);

        setorService = new SetorService(setorRepository);
        Setor uptaded = setorService.atualizarSetor(setor1);

        Mockito.verify(setorRepository, Mockito.times(1)).findByCodigo(setor1.getCodigo());
        Mockito.verify(setorRepository, Mockito.times(1)).save(setor1);

        assertNotNull(uptaded);
        assertEquals(uptaded.getCodigo(), setor1.getCodigo());

    }

    @Test
    void removerSetorTest() {
        Mockito.when(setorRepository.deleteByCodigo(Mockito.any(String.class))).thenReturn(null);

        setorService = new SetorService(setorRepository);
        Setor setorDeletado = setorService.removerSetor(setor1.getCodigo());

        assertNull(setorDeletado);
    }

}
