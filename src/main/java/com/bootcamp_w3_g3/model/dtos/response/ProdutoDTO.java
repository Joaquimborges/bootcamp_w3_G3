package com.bootcamp_w3_g3.model.dtos.response;

import com.bootcamp_w3_g3.model.entity.Produto;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Classe criada para que atritibutos escolhidos possam ser retornado ao response.
 *
 * @author Alex Cruz
 */
@Getter
public class ProdutoDTO {

    private Integer codigoDoProduto;
    private String nome;
    private BigDecimal preco;
    private LocalDate dataDeValidadae;
    private Double temperaturaIndicada;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Integer codigoDoProduto, String nome, BigDecimal preco, LocalDate dataDeValidadae, Double temperaturaIndicada) {
        this.codigoDoProduto = codigoDoProduto;
        this.nome = nome;
        this.preco = preco;
        this.dataDeValidadae = dataDeValidadae;
        this.temperaturaIndicada = temperaturaIndicada;

    }

    /**
     * Método que converte Produto em ProdutoDTO
     * 
     * @param produto
     * @return produtoDTO9
     */
    public ProdutoDTO convertEmProdutoDTO(Produto produto){

        return new ProdutoDTO(produto.getCodigoDoProduto(),
                                produto.getNome(),
                                produto.getPreco(),
                                produto.getDataDeValidadae(),
                                produto.getTemperaturaIndicada());
    }

    /**
     *
     * Método que converte List de Produto em uma List de ProdutoDTO
     *
     * @param produtoList
     * @return produtoDTOList
     */
    public List<ProdutoDTO> convert(List<Produto> produtoList){
        List<ProdutoDTO> produtoDTOList = new ArrayList<>();
        
        for (Produto produto : produtoList){
            produtoDTOList.add(
                    new ProdutoDTO(produto.getCodigoDoProduto(),
                                    produto.getNome(),
                                    produto.getPreco(),
                                    produto.getDataDeValidadae(), 
                                    produto.getTemperaturaIndicada()));
        }
        return produtoDTOList;
    }
            
}
