package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.model.entity.Carrinho;
import com.bootcamp_w3_g3.model.entity.Itens;
import com.bootcamp_w3_g3.repository.CarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Alex Cruz
 */

@Service
public class CarrinhoService {

    private CarrinhoRepository carrinhoRepository;

    @Autowired
    public CarrinhoService(CarrinhoRepository carrinhoRepository){
        this.carrinhoRepository = carrinhoRepository;
    }

    @Transactional
    public Carrinho salvar(Carrinho carrinho) { return carrinhoRepository.save(carrinho); }

    public List<Carrinho> listar() {
        return carrinhoRepository.findAll();
    }

    public Carrinho atualizar(Carrinho carrinho) {
        Carrinho carrinhoEdited = carrinhoRepository.getById(carrinho.getId());
        carrinhoEdited.setDataDeOrdem(carrinho.getDataDeOrdem());
        carrinhoEdited.setItensList(carrinho.getItensList());
        carrinhoEdited.setStatusCompra(carrinho.getStatusCompra());

        return carrinhoRepository.save(carrinhoEdited);
    }

    public BigDecimal retornaPrecoDosItens(Carrinho carrinho) {
        double valorTotal = 0.0;
        for (Itens item : carrinho.getItensList()) {
            valorTotal += item.getProduto().getPreco() * item.getQuantidade();
        }
        return new BigDecimal(valorTotal);
    }




}
