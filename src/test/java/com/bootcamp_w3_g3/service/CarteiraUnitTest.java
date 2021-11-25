package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.model.entity.Carteira;
import com.bootcamp_w3_g3.model.entity.Comprador;
import com.bootcamp_w3_g3.repository.CarteiraRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarteiraUnitTest {

    private CarteiraService carteiraService;
    private final CarteiraRepository carteiraRepository = Mockito.mock(CarteiraRepository.class);

    Carteira carteira = Carteira.builder()
            .numero("MFC-C-23").saldo(100.0).build();

    Carteira carteira1 = Carteira.builder()
            .numero("MFC-764").saldo(10.0).build();

    Comprador comprador = Comprador.builder().codigo("C-23")
            .nome("Paulo")
            .carteira(carteira).build();

    List<Carteira> carteiras = new ArrayList<>();

    /**
     * Teste deve instanciar uma carteira nova
     * concatenando o nome com o codigo do comprador
     */
    @Test
    void deveCriarUmaCarteira(){
        Mockito.when(carteiraRepository.save(Mockito.any(Carteira.class))).thenReturn(carteira);
        carteiraService = new CarteiraService(carteiraRepository);

        Carteira carteiraSalva = carteiraService.criar(comprador.getCodigo());
        assertNotNull(carteiraSalva);
        assertEquals(carteiraSalva.getNumero(), carteira.getNumero());
    }

    /**
     * Teste deve obter uma carteira especifica
     * buscando pelo seu número.
     */
    @Test
    void deveObterUmaCarteira(){
        Mockito.when(carteiraRepository.findCarteiraByNumero(Mockito.any(String.class))).thenReturn(carteira);
        carteiraService = new CarteiraService(carteiraRepository);

        Carteira carteiraObtida = carteiraService.obter(carteira.getNumero());

        assertEquals(carteiraObtida.getNumero(), carteira.getNumero());

    }

    /**
     * Teste deve listar todas as carteiras
     * que foram criadas
     */
    @Test
    void deveListarCarteiras() {
        carteiras.add(carteira);
        carteiras.add(carteira1);

        Mockito.when(carteiraRepository.findAll()).thenReturn(carteiras);
        carteiraService = new CarteiraService(carteiraRepository);

        List<Carteira> list = carteiraService.listar();

        assertNotNull(list);
        assertEquals(carteiras.size(), list.size());
    }

    /**
     * Teste deve atualizar o saldo da carteira
     */
    @Test
    void deveAtualizarSaldoDaCarteira() {
        carteira1.setSaldo(20.0);

        Mockito.when(carteiraRepository.findCarteiraByNumero(Mockito.any(String.class))).thenReturn(carteira1);
        Mockito.when(carteiraRepository.save(Mockito.any(Carteira.class))).thenReturn(carteira1);

        carteiraService = new CarteiraService(carteiraRepository);
        Carteira carteiraAtualizada = carteiraService.atualizar(carteira1);

        assertNotNull(carteiraAtualizada);
        assertEquals(carteiraAtualizada.getSaldo(), carteira1.getSaldo());
    }


    /**
     * Teste deve consultar o saldo de uma carteira.
     * Será localizada a carteira pelo seu codigo e
     * retornar o saldo.
     */
    @Test
    void deveConsultarSaldoDaCarteira() {
        double valor = 10.0;

        Mockito.when(carteiraRepository.findCarteiraByNumero(Mockito.any(String.class))).thenReturn(carteira1);

        carteiraService = new CarteiraService(carteiraRepository);
        double saldoCarteira = carteiraService.consultarSaldo(carteira1.getNumero());

        assertEquals(saldoCarteira, valor);

    }

    /**
     * Teste deve localizar uma carteira,
     * depositar o valor enviado e atualizar a carteira
     */
    @Test
    void deveRealizarDeposito() {
        double valor = 20.0;

        Mockito.when(carteiraRepository.findCarteiraByNumero(Mockito.any(String.class))).thenReturn(carteira1);
        Mockito.when(carteiraRepository.save(Mockito.any(Carteira.class))).thenReturn(carteira1);

        carteiraService = new CarteiraService(carteiraRepository);

        Carteira carteiraObtida = carteiraService.deposita(carteira1.getNumero(), 10.0);

        assertNotNull(carteiraObtida);
        assertEquals(valor, carteiraObtida.getSaldo());

    }

}
