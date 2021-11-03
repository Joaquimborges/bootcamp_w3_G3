package com.bootcamp_w3_g3.repository;

import com.bootcamp_w3_g3.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Usuario findByLogin(String login);
}
