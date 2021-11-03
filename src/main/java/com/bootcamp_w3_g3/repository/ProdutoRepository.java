package com.bootcamp_w3_g3.repository;

import com.bootcamp_w3_g3.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

/**
 *
 * @author Alex Cruz
 *
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

    Produto findByCodigoDoProduto(Integer codigo);

    Produto deleteProdutosByCodigoDoProduto(Integer codigo);

    Produto findAllByTipoProduto(String tipoDeProduto);


}


