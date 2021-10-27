package com.bootcamp_w3_g3.model.dtos.request;

import com.bootcamp_w3_g3.model.entity.*;
import com.bootcamp_w3_g3.service.ArmazemService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author hugo damm
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SetorForm {

    private String codigo;
    private String nome;
    private String tipoProduto;
    private Armazem armazem;
    //private List<Lote> lote;

    public Setor converte(ArmazemService armazemService){
        Armazem armazem = armazemService.obterArmazem(this.armazem.getCodArmazem());
        return Setor.builder()
                .codigo(codigo)
                .nome(nome)
                .tipoProduto(tipoProduto)
                .armazem(armazem).build();
    }

}

