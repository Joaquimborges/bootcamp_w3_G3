package com.bootcamp_w3_g3.repository;

import com.bootcamp_w3_g3.model.entity.Representante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface RepresentanteRepository extendendo de JpaRepository que já consta vários métodos de CRUD, bem como de localização.
 * Criado três métodos específicos
 *  findByCodigo - para buscar pelo código do representante.
 *  deletebyCodigo - deletando pelo código do representante.
 *  getByCodigo - retornando o código do representando.
 *
 * @autor
 */
@Repository
public interface RepresentanteRepository extends JpaRepository <Representante, Long>{

    Representante findByCodigo(String codigo);

    Representante deleteByCodigo(String codigo);

    Representante getByCodigo(String codigo);
}
