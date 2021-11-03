package com.bootcamp_w3_g3.controller;

import com.bootcamp_w3_g3.model.dtos.request.ItensForm;
import com.bootcamp_w3_g3.model.entity.Itens;
import com.bootcamp_w3_g3.service.ItensService;
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
@RequestMapping("itens/")
public class ItensController {

    @Autowired
    private ItensService itensService;

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Itens> cadastrar(@RequestBody ItensForm itensForm) {
        Itens itens = itensService.salvar(itensForm.converte(produtoService));
        return new ResponseEntity<>(itens, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Itens>> listar(){
        List<Itens> itensList = itensService.listar();
        return new ResponseEntity<>(itensList, HttpStatus.OK);
    }

    @PutMapping("/alterar")
    public ResponseEntity<Itens> alterar(@RequestBody ItensForm itensForm) {
        Itens itens = itensService.atualizar(itensForm.converte(produtoService));
        return new ResponseEntity<>(itens, HttpStatus.OK);
    }
}
