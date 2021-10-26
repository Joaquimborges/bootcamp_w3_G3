package com.bootcamp_w3_g3.model.dtos.response;

import com.bootcamp_w3_g3.model.entity.Vendedor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hugo damm
 */

@Setter
@Getter
public class VendedorDTO {

    private String codigo;
    private String nome;
    private String sobrenome;

    public VendedorDTO(String codigo, String nome, String sobrenome) {
        this.codigo = codigo;
        this.nome = nome;
        this.sobrenome = sobrenome;
    }

    public static VendedorDTO converter (Vendedor vendedor){
        return new VendedorDTO(vendedor.getCodigo(), vendedor.getNome(), vendedor.getSobrenome());
    }

    public static List<VendedorDTO> converteLista(List<Vendedor> vendedores){
        List<VendedorDTO> vendedorDTOList = new ArrayList<>();
        for (Vendedor vendedor : vendedores){
            vendedorDTOList.add(new VendedorDTO(
                    vendedor.getCodigo(), vendedor.getNome(), vendedor.getSobrenome()
            ));
        }
        return vendedorDTOList;
    }
}
