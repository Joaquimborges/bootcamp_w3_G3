package com.bootcamp_w3_g3.model.dtos.request;

import com.bootcamp_w3_g3.model.entity.Lote;
import com.bootcamp_w3_g3.model.entity.Produto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Joaquim Borges
 * @autor Alex Cruz
 */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class LoteForm {

    private Integer numero;
    private Integer quantidadeAtual;
    private  Integer quantidadeMinina;


    private Produto produto;

    private Double temperaturaAtual;
    private Double temperaturaMinima;

    private LocalTime horaFabricacao;
    private LocalDate dataDeFabricacao;
    private LocalDate dataDeValidade;

    public Lote converte() {
        return Lote.builder()
                .numero(numero)
                .produtos(produto)
                .temperaturaAtual(temperaturaAtual)
                .temperaturaMinima(temperaturaMinima)
                .quantidadeAtual(quantidadeAtual)
                .quantidadeMinina(quantidadeMinina)
                .dataDeFabricacao(dataDeFabricacao)
                .horaFabricacao(horaFabricacao)
                .dataDeValidade(dataDeValidade)
                .build();

    }
}
