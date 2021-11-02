package com.bootcamp_w3_g3.model.entity;

/**
 * @author Math Willock
 */
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Itens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToOne
    private Produto produto;
    private Integer quantidade;



}
