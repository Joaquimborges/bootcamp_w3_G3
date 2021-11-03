package com.bootcamp_w3_g3.model.dtos.request;

import com.bootcamp_w3_g3.model.entity.Comprador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @autor Joaquim Borges
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompradorForm {

    private String usuario;
    private String senha;


    public Comprador converte(){
        return Comprador.builder()
                .senha(usuario)
                .senha(senha).build();
    }
}
