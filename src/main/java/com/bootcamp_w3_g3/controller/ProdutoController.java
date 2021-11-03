package com.bootcamp_w3_g3.controller;
import com.bootcamp_w3_g3.model.dtos.request.ProdutoForm;
import com.bootcamp_w3_g3.model.dtos.response.ProdutoDTO;
import com.bootcamp_w3_g3.model.entity.Produto;
import com.bootcamp_w3_g3.model.entity.TipoProduto;
import com.bootcamp_w3_g3.repository.ProdutoRepository;
import com.bootcamp_w3_g3.service.CarrinhoService;
import com.bootcamp_w3_g3.service.ProdutoService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Marcelo de Oliveira Santos
 *
 * @implNote Nao serao criados somente para retorno.
 */

@RestController
@RequestMapping(value="produtos/")
@Getter
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CarrinhoService carrinhoService;

    /**
     * Create do CRUD
     * @return ProdutoDTO
     */
    @PostMapping(value = "/cadastra")
    public ResponseEntity<ProdutoDTO> cadastrar(@RequestBody ProdutoForm produtoForm) {
       Produto produto = produtoService.salvar(produtoForm.convert());
       return new ResponseEntity<>(ProdutoDTO.convertEmProdutoDTO(produto), HttpStatus.CREATED);
    }

    /**
     * Read do CRUD
     * @return produtoDTO
     */
    @GetMapping("/obter/{cod}")
    public ResponseEntity<ProdutoDTO> obter(@PathVariable Integer cod)
    {
        Produto produto = produtoService.obter(cod);
        return new ResponseEntity<>(ProdutoDTO.convertEmProdutoDTO(produto), HttpStatus.OK);
    }

    /**
     * Put do CRUD
     * @return Produtodto
     */
    @PutMapping("/alterar")
    public ResponseEntity<ProdutoDTO> alterar(@RequestBody ProdutoForm produtoForm)
    {
        Produto produto = produtoService.atualizar(produtoForm.convert());
        return new ResponseEntity<>(ProdutoDTO.convertEmProdutoDTO(produto), HttpStatus.OK);
    }

    /**
     * Delete do CRUD
     * @return produtoDTO
     */
    @DeleteMapping(value="/deletar/{cod}")
    public ResponseEntity<Produto> apagar(@PathVariable Long cod)
    {
        produtoService.apagar(cod);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * GET de todos os Produtos
     * @return List<ProdutoDTO>
     */
    @GetMapping("/listar")
    public ResponseEntity<List<ProdutoDTO>> listar()
    {
        try {
            List<Produto> produtos = produtoService.listar();
            return new ResponseEntity<>(ProdutoDTO.convert(produtos), HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/listar/{categoria}")
    public ResponseEntity<List<Produto>> listarPorCategoria(@PathVariable TipoProduto categoria){
        try {
            return new ResponseEntity<>(produtoService.listarPorCategoria(categoria), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/carrinho/{idCarrinho}")
    public ResponseEntity<List<Produto>> mostrarProdutosDoPedido(@PathVariable Long idCarrinho) {
        return new ResponseEntity<>(carrinhoService.mostrarProdutosDoPedido(idCarrinho), HttpStatus.OK);
    }
}