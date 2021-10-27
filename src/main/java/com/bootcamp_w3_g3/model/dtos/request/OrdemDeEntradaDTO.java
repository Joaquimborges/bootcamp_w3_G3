package com.bootcamp_w3_g3.model.dtos.request;


import com.bootcamp_w3_g3.model.entity.*;
import com.bootcamp_w3_g3.service.ArmazemService;
import com.bootcamp_w3_g3.service.RepresentanteService;
import com.bootcamp_w3_g3.service.SetorService;
import com.bootcamp_w3_g3.service.VendedorService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;


/**
 * @author Joaquim Borges
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    public OrdemDeEntrada converte(VendedorService vendedorService, ArmazemService armazemService) {

       Vendedor vendedor = vendedorService.obter(codigoVendedor);
       Representante representante = armazemService.retornaRepresentanteDoArmazem(codigoRepresentante);
       Setor setor = armazemService.retornaSetorDoArmazem(codigoSetor);


        Lote lote = Lote.builder()
                .quantidadeAtual(quantidade)
                .produtos(produto)
                .dataDeFabricacao(dataFabricacao)
                .dataDeValidade(dataVencimento)
                .setor(setor)
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
