package com.bootcamp_w3_g3.repository;

import com.bootcamp_w3_g3.model.entity.Setor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetorRepository extends JpaRepository<Setor, Long> {

    Setor findByCodigo(String codigo);

    Setor deleteByCodigo(String codigo);

}
