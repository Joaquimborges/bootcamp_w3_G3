package com.bootcamp_w3_g3.model.dtos.request;

import com.bootcamp_w3_g3.model.entity.Vendedor;
import lombok.*;

/**
 *
 * @Autor hugo damm
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendedorForm {

    private String codigo;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String telefone;
    private String endereco;

    public Vendedor converte(){
        return Vendedor.builder()
                .codigo(this.codigo)
                .nome(this.nome)
                .sobrenome(this.sobrenome)
                .cpf(this.cpf)
                .telefone(this.telefone)
                .endereco(this.endereco)
                .build();

    }

}
