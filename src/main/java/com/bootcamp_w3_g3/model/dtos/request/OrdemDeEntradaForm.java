package com.bootcamp_w3_g3.model.dtos.request;


import com.bootcamp_w3_g3.model.entity.*;
import com.bootcamp_w3_g3.service.ArmazemService;
import com.bootcamp_w3_g3.service.ProdutoService;
import com.bootcamp_w3_g3.service.SetorService;
import com.bootcamp_w3_g3.service.VendedorService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


/**
 * @author Joaquim Borges
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdemDeEntradaForm {

    private Integer numeroOrdem;
    private String codigoSetor;
    private String codigoRepresentante;
    private String codigoVendedor;
    private Integer quantidade;
    private ProdutoForm produtoForm;
    private LocalDate dataFabricacao;
    private LocalDate dataVencimento;
    private LocalDate dataOrdem;
    private LoteForm loteForm;


    /**
     * construindo o payload da ordem de entrada
     */
    public OrdemDeEntrada converte(VendedorService vendedorService, ArmazemService armazemService, ProdutoService produtoService, SetorService setorService) {
       Vendedor vendedor = vendedorService.obter(codigoVendedor);
       Representante representante = armazemService.retornaRepresentanteDoArmazem(codigoRepresentante);
       Setor setor = setorService.obterSetor(codigoSetor);

        Produto produto = produtoService.obter(loteForm.getProdutoForm().getCodigoDoProduto());
        Lote lote = Lote.builder()
                .numero(loteForm.getNumero())
                .temperaturaMinima(loteForm.getTemperaturaMinima())
                .quantidadeAtual(quantidade)
                .produto(produto)
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
                .lote(lote)
                .build();
    }


}
