package com.bootcamp_w3_g3.repository;

import com.bootcamp_w3_g3.model.entity.Setor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SetorRepository extends JpaRepository<Setor, Long> {


    List<Setor> findSetorByArmazem_Id(Long id);

    Setor getSetorByCodigo(String codigo);

    Object deleteSetorByCodigo(String Codigo);
}
