package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.model.entity.Armazem;
import com.bootcamp_w3_g3.model.entity.Representante;
import com.bootcamp_w3_g3.model.entity.Setor;
import com.bootcamp_w3_g3.repository.ArmazemRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArmazemServiceUnitTest {

    private ArmazemService armazemService;
    private ArmazemRepository armazemRepository = Mockito.mock(ArmazemRepository.class);


    List<Setor> setorList = new ArrayList<>();

    Representante representante = Representante.builder()
            .nome("Alex").sobrenome("Cruz").cpf("2345678910").telefone("5555555").endereco("Rua Joao neves 18").build();


    Armazem armazem1 = Armazem.builder()
            .setoresDoArmazem(setorList)
            .codArmazem("Ar-123")
            .representante(representante)
            .nome("AR1")
            .endereco("rua 10")
            .uf("SP").build();

    Armazem armazem2= Armazem.builder()
            .setoresDoArmazem(setorList)
            .codArmazem("Ar-123")
            .representante(representante)
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
    void salvarArmazemTest(){

        Mockito.when(armazemRepository.save(Mockito.any(Armazem.class))).thenReturn(armazem1);

        armazemService = new ArmazemService(armazemRepository);
        armazemService.criarArmazem(armazem1);

        assertNotNull(armazem1);
    }


    @Test
    void obterArmazemTest(){
        Mockito.when(armazemRepository.findByCodArmazem(Mockito.any(String.class))).thenReturn(armazem1);

        armazemService = new ArmazemService(armazemRepository);
        Armazem obtido = armazemService.obterArmazem(armazem1.getCodArmazem());

        Mockito.verify(armazemRepository,
                Mockito.times(1)).findByCodArmazem(armazem1.getCodArmazem());

        assertEquals(obtido.getCodArmazem(), armazem1.getCodArmazem());
    }


    @Test
    void deletarArmazemTest() {
        Mockito.when(armazemRepository.deleteByCodArmazem(Mockito.any(String.class))).thenReturn(null);

        armazemService = new ArmazemService(armazemRepository);
        Armazem armazemDeletado = armazemService.deletarArmazem(armazem1.getCodArmazem());

        assertNull(armazemDeletado);

    }

    @Test
    void atualizarSetorTest(){

        Mockito.when(armazemRepository.findByCodArmazem(Mockito.any(String.class))).thenReturn(armazem1);

        Mockito.when(armazemRepository.save(Mockito.any(Armazem.class))).thenReturn(armazem1);


        armazemService = new ArmazemService(armazemRepository);
        Armazem uptaded = armazemService.atualizarArmazem(armazem1);

        Mockito.verify(armazemRepository, Mockito.times(1)).findByCodArmazem(armazem1.getCodArmazem());
        Mockito.verify(armazemRepository, Mockito.times(1)).save(armazem1);

        assertNotNull(uptaded);
        assertEquals(uptaded.getCodArmazem(), armazem1.getCodArmazem());

    }

}
