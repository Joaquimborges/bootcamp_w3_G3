package com.bootcamp_w3_g3.model.dtos.response;

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
public class CompradorDTO {

    private String usuario;


    public CompradorDTO converter(Comprador comprador) {
        return CompradorDTO.builder()
                .usuario(comprador.getUsuario())
                .build();
    }
}
