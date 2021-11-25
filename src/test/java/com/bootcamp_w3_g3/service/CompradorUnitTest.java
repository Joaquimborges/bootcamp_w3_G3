package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.model.entity.Carteira;
import com.bootcamp_w3_g3.model.entity.Comprador;
import com.bootcamp_w3_g3.repository.CarteiraRepository;
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
    CarteiraService carteiraService = Mockito.mock(CarteiraService.class);

    CompradorRepository compradorRepository = Mockito.mock(CompradorRepository.class);
    CarteiraRepository carteiraRepository = Mockito.mock(CarteiraRepository.class);

    Comprador comprador  = Comprador.builder()
            .nome("Alex")
            .sobrenome("Cruz")
            .cpf("12312345567")
            .telefone("5555555")
            .endereco("Rua Joao neves 18")
            .build();

    Comprador comprador2  = Comprador.builder()
            .nome("Hugo")
            .sobrenome("Damm")
            .cpf("12312345590")
            .telefone("21 3333-1122")
            .endereco("Rua Copacabana")
            .build();

        Carteira carteira = Carteira.builder()
                .numero("MFC-C-23").saldo(100.0).build();

    List<Comprador> compradorList = new ArrayList<>();

    @Test
    void salvarCompradorTest(){
        Mockito.when(compradorRepository.save(Mockito.any(Comprador.class))).thenReturn(comprador);
        Mockito.when(carteiraRepository.save(Mockito.any(Carteira.class))).thenReturn(carteira);

        compradorService = new CompradorService(compradorRepository, carteiraService);
        Comprador salvo = compradorService.salvar(comprador);

        Mockito.verify(compradorRepository, Mockito.times(1)).save(comprador);

        assertNotNull(salvo);
    }

    @Test
    void atualizarCompradorTest(){
        comprador.setId(22222L);
        comprador.setCodigo("25");
        comprador.setTelefone("777777777");
        comprador.setEndereco("Rua ABC");
        Mockito.when(compradorRepository.getByCodigo(Mockito.any(String.class))).thenReturn(comprador);
        Mockito.when(compradorRepository.save(Mockito.any(Comprador.class))).thenReturn(comprador);

        compradorService = new CompradorService(compradorRepository, carteiraService);
        Comprador compradorAtualizado = compradorService.atualizar(comprador);

        Mockito.verify(compradorRepository, Mockito.times(1)).getByCodigo(comprador.getCodigo());
        Mockito.verify(compradorRepository, Mockito.times(1)).save(comprador);

        assertNotNull(compradorAtualizado);
        assertEquals(compradorAtualizado.getTelefone(), comprador.getTelefone());

    }

    @Test
    void obterCompradorTest(){
        Mockito.when(compradorRepository.findByCodigo(Mockito.any(String.class))).thenReturn(comprador2);

        compradorService = new CompradorService(compradorRepository, carteiraService);
        Comprador compradorObtido = compradorService.obter(comprador2.getNome());

        Mockito.verify(compradorRepository, Mockito.times(1)).findByCodigo((comprador2.getNome()));

        assertEquals(compradorObtido.getNome(), comprador2.getNome());

    }
}
