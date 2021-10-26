package com.bootcamp_w3_g3.model.dtos.request;

import com.bootcamp_w3_g3.model.entity.Estoque;
import com.bootcamp_w3_g3.model.entity.TipoProduto;

public class EstoqueForm {

    private TipoProduto tipoDeProduto;
    private Double quantidade;
    private Integer codEstoque;

    public EstoqueForm() {
    }

    public EstoqueForm(TipoProduto tipoDeProduto, Double quantidade, Integer codEstoque) {
        this.tipoDeProduto = tipoDeProduto;
        this.quantidade = quantidade;
        this.codEstoque = codEstoque;
    }

    public Estoque converte() {
        return new Estoque(tipoDeProduto, quantidade, codEstoque);
    }


}
