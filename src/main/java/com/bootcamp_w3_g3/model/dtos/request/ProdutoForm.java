package com.bootcamp_w3_g3.model.dtos.request;

import com.bootcamp_w3_g3.model.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;


/**
 *
 * Classe criada para recceber payload, podendo optar por quais atributos serão recebidos, podendo ser ou não os mesmo da Entity.
 *
 * @author Alex Cruz
 */
@Data
@Builder
@AllArgsConstructor
public class ProdutoForm {

    private Integer codigoDoProduto;
    private String nome;
    private BigDecimal preco;
    private Double temperaturaIndicada;

    public ProdutoForm() {
    }

    public ProdutoForm(Integer codigoDoProduto, String nome, BigDecimal preco, LocalDate dataDeValidadae, Double temperaturaIndicada) {
        this.codigoDoProduto = codigoDoProduto;
        this.nome = nome;
        this.preco = preco;
        this.temperaturaIndicada = temperaturaIndicada;
    }

    /**
     *
     * Método que auxlia a injeção de um produto diretamente.
     *
     * @return produto
     */
    public Produto convert(){
        return Produto.builder()
                .codigoDoProduto(codigoDoProduto)
                .nome(nome)
                .preco(preco)
                .temperaturaIndicada(temperaturaIndicada).build();
    }
}
