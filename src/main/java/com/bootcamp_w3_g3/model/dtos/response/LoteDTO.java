package com.bootcamp_w3_g3.model.dtos.response;

import com.bootcamp_w3_g3.model.entity.Lote;
import com.bootcamp_w3_g3.model.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joaquim Borges
 * @autor Alex Cruz
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoteDTO {

    private Integer numero;
    private Integer quantidadeDeIntens;

    private Integer quantidadeAtual;
    private  Integer quantidadeMinina;

    private Produto produto;

    private Double temperaturaAtual;
    private Double temperaturaMinima;

    private LocalTime horaFabricacao;
    private LocalDate dataDeFabricacao;
    private LocalDate dataDeValidade;


    public static LoteDTO converter(Lote lote) {
        return LoteDTO.builder()
                .numero(lote.getNumero())
                .produto(lote.getProduto())
                .temperaturaAtual(lote.getTemperaturaAtual())
                .temperaturaMinima(lote.getTemperaturaMinima())
                .quantidadeAtual(lote.getQuantidadeAtual())
                .quantidadeMinina(lote.getQuantidadeMinina())
                .dataDeFabricacao(lote.getDataDeFabricacao())
                .horaFabricacao(lote.getHoraFabricacao())
                .dataDeValidade(lote.getDataDeValidade())
                .build();
    }

    public static List<LoteDTO> converterLista(List<Lote> loteList) {
        List<LoteDTO> loteDTOList = new ArrayList<>();
        for (Lote lote : loteList) {
            loteDTOList.add(LoteDTO.builder()
                    .numero(lote.getNumero())
                    .produto(lote.getProduto())
                    .temperaturaAtual(lote.getTemperaturaAtual())
                    .temperaturaMinima(lote.getTemperaturaMinima())
                    .quantidadeAtual(lote.getQuantidadeAtual())
                    .quantidadeMinina(lote.getQuantidadeMinina())
                    .dataDeFabricacao(lote.getDataDeFabricacao())
                    .horaFabricacao(lote.getHoraFabricacao())
                    .dataDeValidade(lote.getDataDeValidade())
                    .build());
        }
        return loteDTOList;
    }
}
