package com.bootcamp_w3_g3.repository;

import com.bootcamp_w3_g3.model.entity.Comprador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author hugo damm
 */

@Repository
public interface CompradorRepository extends JpaRepository<Comprador, Long> {

    Comprador findByUsuario(String usuario));

}
