package com.bootcamp_w3_g3.model.dtos.request;

import com.bootcamp_w3_g3.model.entity.*;
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
    private List<Lote> lote;

    public Setor converte(){
        return new Setor(codigo, nome, tipoProduto, armazem,lote);
    }

}

