package com.bootcamp_w3_g3.repository;

/**
 * @author Matheus Willock
 */
import com.bootcamp_w3_g3.model.entity.Armazem;
import com.bootcamp_w3_g3.model.entity.Representante;
import com.bootcamp_w3_g3.model.entity.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArmazemRepository extends JpaRepository<Armazem, Long> {

    Armazem findByCodArmazem(String cod);

    Armazem deleteByCodArmazem(String cod);

    Representante findByRepresentanteCodigo(Integer codigo);


}
