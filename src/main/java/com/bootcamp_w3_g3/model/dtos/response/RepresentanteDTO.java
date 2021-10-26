package com.bootcamp_w3_g3.model.dtos.response;

import com.bootcamp_w3_g3.model.entity.Lote;
import com.bootcamp_w3_g3.model.entity.Representante;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Classe RepresentanteDTO com campos pré ddefinidos, com o objetivo dde disponibilizar no response.
 * Efetuado método de conversão de Representante para RepresentanteDTO.
 * Criado método de conversão de uma lista Representante para uma lista de RepresentanteDTO
 * @Autor Alex Cruz
 */
@Getter
public class RepresentanteDTO {

    private String codigo;
    private String nome;
    private String sobreNome;

    public RepresentanteDTO() {
    }

    public RepresentanteDTO(String codigo, String nome, String sobreNome) {
        this.codigo = codigo;
        this.nome = nome;
        this.sobreNome = sobreNome;
    }

    /**
     * Método criado para converte um Representate recbido por parametro, e retornado um novo objeto representanteDTO
     *
     * @param representante
     * @return representanteDTO
     */
    public static RepresentanteDTO converteEmRepresentanteDTO(Representante representante) {
        return new RepresentanteDTO(representante.getCodigo(), representante.getNome(), representante.getSobrenome());
    }

    /**
     * Método criado para converter uma lista de representante reccebida por parametro, e retornando uma lista representateDTO
     *
     * @param representantesList
     * @return RepresentanteDTOList
     */
    public static List<RepresentanteDTO> converteListaParaRepresentanteDTO(List<Representante> representantesList) {
        List<RepresentanteDTO> representanteDTOList = new ArrayList<>();
        for (Representante representante : representantesList) {
            representanteDTOList.add(new RepresentanteDTO(representante.getCodigo(), representante.getNome(), representante.getSobrenome()));
        }
        return representanteDTOList;
    }
}
