package com.bootcamp_w3_g3.model.dtos.response;

import com.bootcamp_w3_g3.model.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {

    private String login;


    public UsuarioDTO converter(Usuario usuario){
        return UsuarioDTO.builder()
                .login(usuario.getLogin()).build();
    }
}
