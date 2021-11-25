package com.bootcamp_w3_g3.repository;

import com.bootcamp_w3_g3.model.entity.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteiraRepository extends JpaRepository<Carteira, Long> {

    Carteira findCarteiraByNumero(String numero);
}
