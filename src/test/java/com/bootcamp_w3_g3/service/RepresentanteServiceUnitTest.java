package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.model.entity.Representante;
import com.bootcamp_w3_g3.repository.RepresentanteRepository;
import com.bootcamp_w3_g3.service.RepresentanteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Criado teste unitário referente a classe representanteService.
 * Desenvolvido testes para o CRUD.
 * @autor Alex Cruz
 */
public class RepresentanteServiceUnitTest {

    RepresentanteService representanteService;

    RepresentanteRepository representanteRepository = Mockito.mock(RepresentanteRepository.class);

    Representante representante  = Representante.builder()
            .nome("Alex").sobrenome("Cruz").cpf("2345678910").telefone("5555555").endereco("Rua Joao neves 18").build();

    Representante representante2  = Representante.builder()
            .nome("Alex").sobrenome("Cruz").cpf("2345678910").telefone("5555555").endereco("Rua Joao neves 18").build();

    List<Representante> representantesList = new ArrayList<>();

    @Test
    void salvarRepresentanteTest(){
        Mockito.when(representanteRepository.save(Mockito.any(Representante.class))).thenReturn(representante);

        representanteService = new RepresentanteService(representanteRepository);
        Representante salvo = representanteService.salvar(representante);

        Mockito.verify(representanteRepository, Mockito.times(1)).save(representante);

        assertNotNull(salvo);
    }

    @Test

    void obterRepresentanteTest(){

        representante.setCodigo("25");
        Mockito.when(representanteRepository.findByCodigo(Mockito.any(String.class))).thenReturn(representante);

        representanteService = new RepresentanteService(representanteRepository);
        Representante obtido = representanteService.obter(representante.getCodigo());

        Mockito.verify(representanteRepository,
                Mockito.times(1))
                .findByCodigo(representante.getCodigo());

        assertEquals(obtido.getCpf(), representante.getCpf());
    }

    @Test
    void listarRepresentanteTest(){
        representantesList.add(representante);
        representantesList.add(representante2);
        Mockito.when(representanteRepository.findAll()).thenReturn(representantesList);

        representanteService = new RepresentanteService(representanteRepository);
        List<Representante> lista = representanteService.listar();

        Mockito.verify(representanteRepository, Mockito.times(1)).findAll();

        assertEquals(lista.size(),representantesList.size());

    }

    @Test
    void atualizarRepresentanteTest(){
        representante.setId(22222L);
        representante.setCodigo("25");
        representante.setTelefone("777777777");
        representante.setEndereco("Rua A");
        Mockito.when(representanteRepository.getByCodigo(Mockito.any(String.class))).thenReturn(representante);
        Mockito.when(representanteRepository.save(Mockito.any(Representante.class))).thenReturn(representante);

        representanteService = new RepresentanteService(representanteRepository);
        Representante atualizado = representanteService.atualizar(representante);

        Mockito.verify(representanteRepository, Mockito.times(1)).getByCodigo(representante.getCodigo());
        Mockito.verify(representanteRepository, Mockito.times(1)).save(representante);

        assertNotNull(atualizado);
        assertEquals(atualizado.getTelefone(), representante.getTelefone());

    }

   @Test

   void apagarRepresentanteTest(){

       representante.setCodigo("25");
       Mockito.when(representanteRepository.deleteByCodigo(Mockito.any(String.class))).thenReturn(null);

       representanteService = new RepresentanteService(representanteRepository);
       Representante deletado = representanteService.apagar(representante.getCodigo());

       Mockito.verify(representanteRepository, Mockito.times(1)).deleteByCodigo(representante.getCodigo());

       assertNotEquals(deletado,representante);
   }
}
