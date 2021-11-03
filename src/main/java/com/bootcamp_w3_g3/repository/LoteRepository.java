package com.bootcamp_w3_g3.repository;

import com.bootcamp_w3_g3.model.entity.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Joaquim Borges
 */

@Repository
public interface LoteRepository extends JpaRepository<Lote, Long> {

    Lote findByNumero(Integer numero);

    Lote getByNumero(Integer numero);

    Lote deleteByNumero(Integer numero);

    Lote getLoteByNumero(Integer numeroDoLote);

    Lote findLoteByNumero(Integer numeroDoLote);
}
