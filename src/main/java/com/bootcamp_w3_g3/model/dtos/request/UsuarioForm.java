package com.bootcamp_w3_g3.model.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioForm {

    private String login;
    private String senha;


    public UsernamePasswordAuthenticationToken converte() {
        return new UsernamePasswordAuthenticationToken(login, senha);
    }
}
