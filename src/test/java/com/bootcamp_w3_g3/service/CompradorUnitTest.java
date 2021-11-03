package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.model.entity.Comprador;
import com.bootcamp_w3_g3.repository.CompradorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

    /**
    * Criado teste unitário referente a classe compradorService.
    * Desenvolvido testes para o CRUD.
    * @autor Alex Cruz
    */
public class CompradorUnitTest{

    CompradorService compradorService;

    CompradorRepository compradorRepository = Mockito.mock(CompradorRepository.class);

    Comprador comprador  = Comprador.builder()
            .usuario("Alex")
            .senha("123456789")
            .build();

    Comprador comprador2  = Comprador.builder()
            .usuario("Alex2")
            .senha("123456789")
            .build();

    List<Comprador> compradorList = new ArrayList<>();

    @Test
    void salvarCompradorTest(){
        Mockito.when(compradorRepository.save(Mockito.any(Comprador.class))).thenReturn(comprador);

        compradorService = new CompradorService(compradorRepository);
        Comprador salvo = compradorService.salvar(comprador);

        Mockito.verify(compradorRepository, Mockito.times(1)).save(comprador);

        assertNotNull(salvo);
    }

    @Test
    void atualizarCompradorTest(){
        comprador.setId(22222L);
        comprador.setUsuario("Alex");
        comprador.setSenha("ba12ba13ba14");
        Mockito.when(compradorRepository.findByUsuario(Mockito.any(String.class))).thenReturn(comprador);
        Mockito.when(compradorRepository.save(Mockito.any(Comprador.class))).thenReturn(comprador);

        compradorService = new CompradorService(compradorRepository);
        Comprador compradorAtualizado = compradorService.atualizar(comprador);

        Mockito.verify(compradorRepository, Mockito.times(1)).findByUsuario(comprador.getUsuario());
        Mockito.verify(compradorRepository, Mockito.times(1)).save(comprador);

        assertNotNull(compradorAtualizado);
        assertEquals(compradorAtualizado.getUsuario(), comprador.getUsuario());

    }
}
