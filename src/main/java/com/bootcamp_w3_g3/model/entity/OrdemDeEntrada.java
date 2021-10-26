package com.bootcamp_w3_g3.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @autor Alex Cruz
 */

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdemDeEntrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numeroDaOrdem;
    private LocalDate dataDaOrdem;
    @ManyToOne
    private Setor setor;
    @ManyToOne
    private Representante representante;
    @ManyToOne
    private Lote lote;
    @OneToOne
    private Vendedor vendedor;
    
    


}
