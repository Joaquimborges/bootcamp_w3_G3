package com.bootcamp_w3_g3.model.dtos.request;

import com.bootcamp_w3_g3.model.entity.*;
import com.bootcamp_w3_g3.service.CompradorService;
import com.bootcamp_w3_g3.service.ProdutoService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @autor Joaquim Borges
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarrinhoForm {

    private String codigoCarrinho;
    private LocalDate dataDaOrdem;
    private StatusCompra statusCompra;
    private List<ItensForm> itensList = new ArrayList<>();
    private String usuario;


    public Carrinho converte(ProdutoService produtoService, CompradorService compradorService) {
        Comprador comprador = compradorService.obter(this.usuario);

        List<Itens> itens = new ArrayList<>();
        for (ItensForm i : itensList){
            Produto produto = produtoService.obter(i.getCodigoDoProduto());
            Integer quantidade = i.getQuantidade();
            itens.add(new Itens(produto, quantidade));
        }

        return Carrinho.builder()
                .codigo(codigoCarrinho)
                .dataDeOrdem(dataDaOrdem)
                .statusCompra(statusCompra)
                .codigoComprador(comprador.getUsuario())
                .itensList(itens).build();
    }


}
