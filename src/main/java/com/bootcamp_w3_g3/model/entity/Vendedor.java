package com.bootcamp_w3_g3.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author hugo damm
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vendedor extends Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;

    public Vendedor(String nome, String sobrenome, String cpf, String telefone, String endereco) {
        super(nome, sobrenome, cpf, telefone, endereco);
    }

}
