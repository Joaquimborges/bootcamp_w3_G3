package com.bootcamp_w3_g3.model.dtos.request;

import com.bootcamp_w3_g3.model.entity.*;
import com.bootcamp_w3_g3.service.ArmazemService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * @author hugo damm
 * @autor Joaquim Borges
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class SetorForm {

    private String codigo;
    private String nome;
    private TipoProduto tipoProduto;
    private ArmazemForm armazem;
    private Integer espacoDisponivel = 100;




    public Setor converte(ArmazemService armazemService){
        Armazem armazem = armazemService.obterArmazem(this.armazem.getCodArmazem());
        return Setor.builder()
                .codigo(codigo)
                .nome(nome)
                .tipoProduto(tipoProduto)
                .armazem(armazem)
                .build();
    }



}

