package com.bootcamp_w3_g3.model.dtos.response;

import com.bootcamp_w3_g3.model.entity.Armazem;
import com.bootcamp_w3_g3.model.entity.Representante;
import com.bootcamp_w3_g3.model.entity.Setor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor

@NoArgsConstructor
@Getter
public class ArmazemDTO {

    private String codArmazem;
    private String nome;
    private String endereco;
    private String uf;
    private Representante representante;

    public static ArmazemDTO converter(Armazem armazem) {

        return new ArmazemDTO(
                armazem.getCodArmazem(),
                armazem.getNome(),
                armazem.getEndereco(),
                armazem.getUf(),
                armazem.getRepresentante()
        );

    }


    public static List<ArmazemDTO> armazemDTOListConverte(List<Armazem> armazemList) {
        List<ArmazemDTO> armazemDTOList = new ArrayList<>();

        for (Armazem armazem : armazemList) {

            armazemDTOList.add(
                    new ArmazemDTO(
                            armazem.getCodArmazem(),
                            armazem.getNome(),
                            armazem.getEndereco(),
                            armazem.getUf(),
                            armazem.getRepresentante()
                    )
            );
        }

        return armazemDTOList;


    }




}
