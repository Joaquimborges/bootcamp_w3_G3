package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.advisor.EntityNotFoundException;
import com.bootcamp_w3_g3.model.entity.Lote;
import com.bootcamp_w3_g3.model.entity.Produto;
import com.bootcamp_w3_g3.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    private LoteService loteService;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public Produto salvar(Produto produto) { return produtoRepository.save(produto); }

    public Produto obter(Integer codigo) { return produtoRepository.findByCodigoDoProduto(codigo); }

    public Lote obterLote(Integer codLote) {
        return loteService.obter(codLote);
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
