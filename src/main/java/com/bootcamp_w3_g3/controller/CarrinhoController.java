package com.bootcamp_w3_g3.controller;

import com.bootcamp_w3_g3.model.dtos.request.CarrinhoForm;
import com.bootcamp_w3_g3.model.entity.Carrinho;
import com.bootcamp_w3_g3.service.CarrinhoService;
import com.bootcamp_w3_g3.service.CompradorService;
import com.bootcamp_w3_g3.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author Alex Ccruz
 */

@RestController
@RequestMapping("carrinho/")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private CompradorService compradorService;



    @PostMapping("/salvar")
    public ResponseEntity<Carrinho> cadastrar(@RequestBody CarrinhoForm carrinhoForm) {
        Carrinho carrinho = carrinhoService.salvar(carrinhoForm.converte(produtoService, compradorService));
        return new ResponseEntity<>(carrinho, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Carrinho>> listar(){
        List<Carrinho> carrinhoList = carrinhoService.listar();
        return new ResponseEntity<>(carrinhoList, HttpStatus.OK);
    }

    @PutMapping("/alterar")
    public ResponseEntity<Carrinho> alterar(@RequestBody CarrinhoForm carrinhoForm) {
        Carrinho carrinho = carrinhoService.atualizar(carrinhoForm.converte(produtoService, compradorService));
        return new ResponseEntity<>(carrinho, HttpStatus.OK);
    }
}

