package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.advisor.EntityNotFoundException;
import com.bootcamp_w3_g3.model.entity.Lote;
import com.bootcamp_w3_g3.model.entity.Produto;
import com.bootcamp_w3_g3.model.entity.TipoProduto;
import com.bootcamp_w3_g3.repository.LoteRepository;
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
 * @autor Joaquim Borges
 */

@Service
public class ProdutoService {

    private ProdutoRepository produtoRepository;
    private LoteRepository loteRepository;


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

    public Lote obterLote(Integer codLote){
        Lote lote = loteRepository.findByNumero(codLote);
        return lote;
    }


    public List<Produto> listarPorCategoria(TipoProduto categoria){
            switch (categoria) {
                case CONGELADOS:
                 return produtoRepository.findAllByTipoProduto(TipoProduto.CONGELADOS);

                case FRESCOS:
                    return produtoRepository.findAllByTipoProduto(TipoProduto.FRESCOS);

                case REFRIGERADOS:
                    return produtoRepository.findAllByTipoProduto(TipoProduto.REFRIGERADOS);
            }
            return null;
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
