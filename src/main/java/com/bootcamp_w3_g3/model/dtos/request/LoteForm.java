package com.bootcamp_w3_g3.model.dtos.request;

import com.bootcamp_w3_g3.model.entity.Lote;
import com.bootcamp_w3_g3.model.entity.Produto;

import com.bootcamp_w3_g3.model.entity.Setor;
import com.bootcamp_w3_g3.service.ProdutoService;
import com.bootcamp_w3_g3.service.SetorService;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Joaquim Borges
 * @autor Alex Cruz
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoteForm {

    private Integer numero;
    private Integer quantidadeAtual;
    private  Integer quantidadeMinina;

    private SetorForm setorForm;
    private ProdutoForm produtoForm;

    private Double temperaturaAtual;
    private Double temperaturaMinima;

    private LocalTime horaFabricacao;
    private LocalDate dataDeFabricacao;
    private LocalDate dataDeValidade;

    public Lote converte(ProdutoService produtoService, SetorService setorService) {
        Produto produto = produtoService.obter(produtoForm.getCodigoDoProduto());
        Setor setor = setorService.obterSetor(this.setorForm.getCodigo());
        return Lote.builder()
                .numero(numero)
                .produto(produto)
                .temperaturaAtual(temperaturaAtual)
                .temperaturaMinima(temperaturaMinima)
                .quantidadeAtual(quantidadeAtual)
                .quantidadeMinina(quantidadeMinina)
                .dataDeFabricacao(dataDeFabricacao)
                .horaFabricacao(horaFabricacao)
                .dataDeValidade(dataDeValidade)
                .setor(setor)
                .build();

    }
}
