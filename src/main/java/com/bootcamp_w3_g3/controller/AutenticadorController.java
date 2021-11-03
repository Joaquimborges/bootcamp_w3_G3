package com.bootcamp_w3_g3.controller;

import com.bootcamp_w3_g3.model.dtos.request.UsuarioForm;
import com.bootcamp_w3_g3.model.dtos.response.TokenDTO;
import com.bootcamp_w3_g3.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AutenticadorController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDTO> autentica(@RequestBody UsuarioForm usuario) {
        UsernamePasswordAuthenticationToken dadosLogin = usuario.converte();
        Authentication authentication = manager.authenticate(dadosLogin);
        String token = tokenService.geraToken(authentication);
        return ResponseEntity.ok(new TokenDTO("Bearer", token));
    }
}
