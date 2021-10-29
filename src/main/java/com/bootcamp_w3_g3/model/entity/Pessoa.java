package com.bootcamp_w3_g3.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author Joaquim Borges
 */

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class Pessoa {

    private String nome;
    private String sobrenome;
    private String cpf;
    private String telefone;
    private String endereco;


}
