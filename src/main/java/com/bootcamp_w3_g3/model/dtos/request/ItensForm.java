package com.bootcamp_w3_g3.model.dtos.request;

import com.bootcamp_w3_g3.model.entity.Itens;
import com.bootcamp_w3_g3.model.entity.Produto;
import com.bootcamp_w3_g3.service.ProdutoService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @autor Joaquim Borges
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItensForm {

    private Integer codigoDoProduto;
    private Integer quantidade;




    public Itens converte(ProdutoService produtoService) {
        Produto produto = produtoService.obter(this.codigoDoProduto);

        return Itens.builder()
                .produto(produto)
                .quantidade(this.quantidade).build();
    }
}
