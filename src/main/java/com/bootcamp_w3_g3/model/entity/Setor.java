package com.bootcamp_w3_g3.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;

/**
 * @author hugo damm
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Builder
public class Setor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private String nome;
    private String tipoProduto;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "setor")
    private List<Lote> lote;

    private Integer espacoDisponivel = 100;

    @JsonBackReference
    @ManyToOne
    private Armazem armazem;

//    public Setor(String codigo, String nome, String tipoProduto, Armazem armazem, List<Lote> lote) {
//        this.codigo = codigo;
//        this.nome = nome;
//        this.tipoProduto = tipoProduto;
//        this.armazem = armazem;
//        this.lote = lote;
//    }



}