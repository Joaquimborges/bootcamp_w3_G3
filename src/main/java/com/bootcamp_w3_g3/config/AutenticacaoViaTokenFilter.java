package com.bootcamp_w3_g3.config;

import com.bootcamp_w3_g3.model.entity.Usuario;
import com.bootcamp_w3_g3.repository.UsuarioRepository;
import com.bootcamp_w3_g3.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository repository;

    public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository repository){
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //obtem token do cabecalho da requisicao
        String token = extraiToken(request);
        //validar token
        if (token != null) {
            boolean tokenValido = tokenService.tokenValido(token);
            if(tokenValido) {
                //autenticar o token
                realizaAutenticacaoDoTokenNoSpring(token);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void realizaAutenticacaoDoTokenNoSpring(String token) {
        String userName = tokenService.getUsername(token);
        Usuario usuario = this.repository.findByLogin(userName);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication); //forçando autenticacao pelo spring
    }

    private String extraiToken(HttpServletRequest request) {
        String token = "";
        String authorization = request.getHeader("Authorization");
        if(authorization==null || authorization.isEmpty() || !authorization.startsWith("Bearer ")) {
            return null;
        }else {
            token = authorization.substring(7, authorization.length());
        }
        return token;
    }
}
