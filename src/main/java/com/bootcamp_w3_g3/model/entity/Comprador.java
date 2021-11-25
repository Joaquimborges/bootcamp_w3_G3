package com.bootcamp_w3_g3.model.entity;


import lombok.*;

import javax.persistence.*;

/**
 * @author Math Willock
 * @author Hugo Damm (Refaturação)
 */

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Comprador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String telefone;
    private String endereco;
    @OneToOne
    private Carteira carteira;

}