package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.advisor.EntityNotFoundException;
import com.bootcamp_w3_g3.model.entity.Produto;
import com.bootcamp_w3_g3.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Classe ProdutoService criada para implmentação do CRUD através das chamadas em métodos da produtoRepository.
 * Bem como aplicar camada dde regra de negócios neccessária.
 *
 * @author Alex Cruz
 */

@Service
public class ProdutoService {

    private ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public Produto salvar(Produto produto) { return produtoRepository.save(produto); }

    public Produto obter(Integer codigo) {
        Produto produto = produtoRepository.findByCodigoDoProduto(codigo);
        if (produto != null){
            return produto;
        }
        throw  new EntityNotFoundException("produto não encontrado");
    }


    public Produto obterPorCategoria(String tipoDeProduto){
        return produtoRepository.findAllByTipoProduto(tipoDeProduto);
    }

    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    public Produto atualizar(Produto produto) {
        Produto produtoEdited = produtoRepository.findByCodigoDoProduto(produto.getCodigoDoProduto());
        produtoEdited.setPreco(produto.getPreco());
        produtoEdited.setNome(produto.getNome());
        produtoEdited.setTemperaturaIndicada(produto.getTemperaturaIndicada());

        return produtoRepository.save(produtoEdited);
    }

    public Produto apagar(Long id) {
       produtoRepository.deleteById(id);
       return null;

    }

}
