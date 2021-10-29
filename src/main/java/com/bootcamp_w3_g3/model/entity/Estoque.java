package com.bootcamp_w3_g3.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Setter
@Getter
@Entity
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TipoProduto tipoDeProduto;
    private Double quantidade;
    private Integer codEstoque;

    public Estoque() {
    }

    public Estoque(TipoProduto tipoDeProduto, Double quantidade, Integer codEstoque) {
        this.tipoDeProduto = tipoDeProduto;
        this.quantidade = quantidade;
        this.codEstoque = codEstoque;
    }


    public Estoque(Long id, TipoProduto tipoDeProduto, Double quantidade, Integer codEstoque) {
        this.id = id;
        this.tipoDeProduto = tipoDeProduto;
        this.quantidade = quantidade;
        this.codEstoque = codEstoque;
    }
}
