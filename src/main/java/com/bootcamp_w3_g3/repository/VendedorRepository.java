package com.bootcamp_w3_g3.repository;

import com.bootcamp_w3_g3.model.entity.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Marcelo de Oliveira Santos
 */

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {

    Vendedor findByCodigo(String codigo);

    Vendedor deleteByCodigo(String codigo);

}