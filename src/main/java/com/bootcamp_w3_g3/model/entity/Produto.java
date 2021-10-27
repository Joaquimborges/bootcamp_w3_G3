package com.bootcamp_w3_g3.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @autor Alex Cruz
 */

@Getter
@Setter
@Entity
public class Produto {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    private Integer codigoDoProduto;
    private String nome;
    private BigDecimal preco;
    private LocalDate dataDeValidadae;
    private Double temperaturaIndicada;
    @ManyToOne
    private Lote lote;


    public Produto(){}

    public Produto(Integer codigoDoProduto, String nome, BigDecimal preco, LocalDate dataDeValidadae, Double temperaturaIndicada) {
        this.codigoDoProduto = codigoDoProduto;
        this.nome = nome;
        this.preco = preco;
        this.dataDeValidadae = dataDeValidadae;
        this.temperaturaIndicada = temperaturaIndicada;
    }


}
