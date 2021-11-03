package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.model.entity.Armazem;
import com.bootcamp_w3_g3.model.entity.Representante;
import com.bootcamp_w3_g3.repository.ArmazemRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ArmazemServiceUnitTest {

    private ArmazemService armazemService;
    private final ArmazemRepository armazemRepository = Mockito.mock(ArmazemRepository.class);

    List<Armazem> armazemList = new ArrayList<>();

    Representante representante = Representante.builder()
            .nome("Alex").sobrenome("Cruz").cpf("2345678910").telefone("5555555").endereco("Rua Joao neves 18").build()
    ;

    Armazem armazem1 = Armazem.builder()
            .codArmazem("Ar-123")
            .representante(representante)
            .nome("AR1")
            .endereco("rua 10")
            .uf("SP")
            .build()
    ;

    Armazem armazem2 = Armazem.builder()
            .codArmazem("Ar-321")
            .representante(representante)
            .nome("AR2")
            .endereco("rua 10")
            .uf("RJ")
            .build()
    ;

    @Test
    void criarArmazemTest(){

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
                Mockito.times(1)).findByCodArmazem(armazem1.getCodArmazem()
        );

        assertEquals(obtido.getCodArmazem(), armazem1.getCodArmazem());
    }


    @Test
    void atualizarArmazemTest() {

        armazem1.setNome("Ar2");
        armazem1.setCodArmazem("123");

        Mockito.when(armazemRepository.findByCodArmazem(Mockito.any(String.class))).thenReturn(armazem1);
        Mockito.when(armazemRepository.save(Mockito.any(Armazem.class))).thenReturn(armazem1);

        armazemService = new ArmazemService(armazemRepository);
        Armazem armazemUpdate = armazemService.atualizarArmazem(armazem1);

        assertNotNull(armazemUpdate);

        assertEquals(armazemUpdate.getNome(), armazem1.getNome());
        assertEquals(armazemUpdate.getCodArmazem(), armazem1.getCodArmazem());

    }


    @Test
    void listarArmazensTest() {

        armazemList.add(armazem1);
        armazemList.add(armazem2);

        Mockito.when(armazemRepository.findAll()).thenReturn(armazemList);

        armazemService = new ArmazemService(armazemRepository);
        List<Armazem> lista = armazemService.listarArmazens();

        assertNotNull(lista);
        assertEquals(lista.size(), armazemList.size());

    }

    @Test
    void obterRepresentanteTest() {

        Mockito.when(armazemRepository.findByCodArmazem(Mockito.any(String.class))).thenReturn(armazem1);

        armazemService = new ArmazemService(armazemRepository);

        Representante representanteObtido = armazemService.retornaRepresentanteDoArmazem(
                armazem1.getRepresentante().getCodigo()
        );

        assertNull(representanteObtido);
    }


}
