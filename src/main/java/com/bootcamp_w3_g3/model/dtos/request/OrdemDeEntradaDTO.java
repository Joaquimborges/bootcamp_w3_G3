package com.bootcamp_w3_g3.model.dtos.request;


import com.bootcamp_w3_g3.model.entity.*;
import com.bootcamp_w3_g3.service.RepresentanteService;
import com.bootcamp_w3_g3.service.SetorService;
import com.bootcamp_w3_g3.service.VendedorService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


/**
 * @author Joaquim Borges
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdemDeEntradaDTO {

    private Integer numeroOrdem;
    private String codigoSetor;
    private String codigoRepresentante;
    private String codigoVendedor;
    private Integer quantidade;
    private Produto produto;
    private LocalDate dataFabricacao;
    private LocalDate dataVencimento;
    private LocalDate dataOrdem;


    /**
     * construindo o payload da ordem de entrada
     */
    public OrdemDeEntrada converte(SetorService setorService, RepresentanteService representanteService,
                                   VendedorService vendedorService) {

        Vendedor vendedor = vendedorService.obter(codigoVendedor);
        Representante representante = representanteService.obter(codigoRepresentante);
        Setor setor = setorService.obterSetor(codigoSetor);

        Lote lote = Lote.builder()
                .quantidadeAtual(quantidade)
                .produtos(produto)
                .dataDeFabricacao(dataFabricacao)
                .dataDeValidade(dataVencimento)
                .build();

        return OrdemDeEntrada.builder()
                .dataDaOrdem(dataOrdem)
                .numeroDaOrdem(numeroOrdem)
                .representante(representante)
                .setor(setor)
                .vendedor(vendedor)
                .lote(lote).build();
    }



}
