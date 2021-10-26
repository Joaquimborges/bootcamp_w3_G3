package com.bootcamp_w3_g3.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Matheus Willock
 */
import lombok.*;


import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Armazem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String codArmazem;

    private String nome;
    private String endereco;
    private String uf;

    @OneToOne
    private Representante representante;

    @OneToMany
    private List<Setor> SetoresDoArmazem;

    public Armazem(String codArmazem, String nome, String endereco, String uf, Representante representante, List<Setor> setoresDoArmazem) {
        this.codArmazem = codArmazem;
        this.nome = nome;
        this.endereco = endereco;
        this.uf = uf;
        this.representante = representante;
        this.SetoresDoArmazem = setoresDoArmazem;

    }


    public Armazem(String codigoArmazem){
        this.codArmazem = codigoArmazem;
    }
}
