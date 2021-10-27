package com.bootcamp_w3_g3.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 *
 * @author Alex Cruz
 */

@Getter
@Setter
@Entity
public class Representante extends Pessoa{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;

    public Representante(String nome, String sobrenome, String cpf, String telefone, String endereco) {
        super(nome, sobrenome, cpf, telefone, endereco);
    }

    public Representante() {
    }



}
