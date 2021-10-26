package com.bootcamp_w3_g3.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Joaquim Borges
 * @autor Alex Cruz
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;
    private Integer quantidadeAtual;
    private  Integer quantidadeMinina;

    private Double temperaturaAtual;
    private Double temperaturaMinima;

    private LocalTime horaFabricacao;
    private LocalDate dataDeFabricacao;
    private LocalDate dataDeValidade;

    @Embedded
    private Dimensao dimensao;

    @OneToOne
    private Produto produtos;

}
