package com.bootcamp_w3_g3.model.dtos.request;

import com.bootcamp_w3_g3.model.entity.Lote;
import com.bootcamp_w3_g3.model.entity.Produto;
import com.bootcamp_w3_g3.model.entity.TipoProduto;
import com.bootcamp_w3_g3.service.LoteService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
public class ProdutoForm {

    private Integer codigoDoProduto;
    private String nome;
    private Double preco;
    private Double temperaturaIndicada;
    private TipoProduto tipoProduto;

    /**
     *
     * Método que auxlia a injeção de um produto diretamente.
     *
     * @return produto
     */
    public Produto convert(LoteService loteService){
        return Produto.builder()
                .codigoDoProduto(codigoDoProduto)
                .nome(nome)
                .preco(preco)
                .temperaturaIndicada(temperaturaIndicada)
                .tipoProduto(tipoProduto)
                .build();
    }
}
