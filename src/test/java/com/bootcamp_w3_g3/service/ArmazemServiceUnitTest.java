package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.model.entity.Armazem;
import com.bootcamp_w3_g3.model.entity.Dimensao;
import com.bootcamp_w3_g3.model.entity.Representante;
import com.bootcamp_w3_g3.model.entity.Setor;
import com.bootcamp_w3_g3.repository.ArmazemRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArmazemServiceUnitTest {

    private ArmazemService armazemService;
    private ArmazemRepository armazemRepository = Mockito.mock(ArmazemRepository.class);

    Setor setor1 = new Setor(
            "123","Setor123", "Frescos",  new Dimensao(10.0,10.0,10.0), new Representante("João", "Paulo", "123.123.122-92", "21 9 9956-6538", "Rua A"));
    Setor setor2 = new Setor("124","Setor124", "Congelados",  new Dimensao(20.0,20.0,20.0), new Representante("Ernani", "Santos", "123.345.678-92", "11 9 7867-3456", "Rua B"));

    List<Setor> setorList = new ArrayList<>();

    Representante representante = new Representante("Alex","Cruz","2345678910","5555555","Rua Joao neves 18");

    Armazem armazem1 = new Armazem("1234-meli", "cd cajamar",  "rua das cabras", "SP",  representante,  setorList );

    Armazem armazem2= new Armazem("4321-meli", "cd barueri",  "rua das cabras", "SP",  representante,  setorList );


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
