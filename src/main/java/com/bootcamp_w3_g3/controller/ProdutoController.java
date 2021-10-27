package com.bootcamp_w3_g3.controller;
import com.bootcamp_w3_g3.model.dtos.request.ProdutoForm;
import com.bootcamp_w3_g3.model.dtos.response.ProdutoDTO;
import com.bootcamp_w3_g3.model.entity.Produto;
import com.bootcamp_w3_g3.repository.ProdutoRepository;
import com.bootcamp_w3_g3.service.ProdutoService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Marcelo de Oliveira Santos
 *
 * @implNote Nao serao criados somente para retorno.
 */

@RestController
@RequestMapping(value="/produtos")
@Getter
public class ProdutoController {


    private ProdutoService produtoService;
    private ProdutoForm produtoForm;
    private ProdutoDTO produtoDTO;


    @Autowired
    private ProdutoController(){}

    private ProdutoController(ProdutoService produtoService, ProdutoForm produtoForm, ProdutoDTO produtoDTO){
        this.produtoService = produtoService;
        this.produtoForm = produtoForm;
        this.produtoDTO = produtoDTO;
    }

    /**
     * Create do CRUD
     * @param produtoForm
     * @return ProdutoDTO
     */
    @PostMapping(value = "/cadastra")
    public ResponseEntity<ProdutoDTO> cadastrar(@RequestBody ProdutoForm produtoForm)
    {
        return new ResponseEntity<>
                (
                        produtoDTO.convertEmProdutoDTO
                                (
                                        produtoService.salvar
                                                (
                                                        produtoForm.convert()
                                                )
                                ),      HttpStatus.CREATED
                );
    }

    /**
     * Read do CRUD
     * @param cod_prod
     * @return produtoDTO
     */
    @GetMapping("/obter")
    public ResponseEntity<ProdutoDTO> obter(@RequestParam Integer cod_prod)
    {
        return new ResponseEntity<>
                (
                        produtoDTO.convertEmProdutoDTO
                                (
                                        produtoService.obter(cod_prod)
                                ),      HttpStatus.OK
                );
    }

    /**
     * Put do CRUD
      * @param cod_prod
     * @param produto
     * @return Produtodto
     */
    @PutMapping("/alterar")
    public ResponseEntity<ProdutoDTO> alterar(@RequestParam Integer cod_prod, ProdutoDTO produto)
    {
               Produto editedProduto = (produtoService.obter(cod_prod));

               editedProduto.setPreco(produto.getPreco());
               editedProduto.setNome(produto.getNome());
               editedProduto.setTemperaturaIndicada(produto.getTemperaturaIndicada());
               editedProduto.setCodigoDoProduto(produto.getCodigoDoProduto());

               return new ResponseEntity<>
                       (
                               produtoDTO.convertEmProdutoDTO(editedProduto), HttpStatus.OK
                       );
    }

    /**
     * Delete do CRUD
     * @param cod_prod
     * @return produtoDTO
     */
    @DeleteMapping(value="/deletar")
    public void cadastro(@RequestParam Integer cod_prod)
    {
        produtoService.apagar(cod_prod);
    }

    /**
     * GET de todos os Produtos
     * @return List<ProdutoDTO>
     */
    @GetMapping("/listar")
    public List<ProdutoDTO> listar()
    {
        return produtoDTO.convert(produtoService.listar());
    }
}