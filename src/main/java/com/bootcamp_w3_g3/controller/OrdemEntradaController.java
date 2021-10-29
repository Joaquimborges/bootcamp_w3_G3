package com.bootcamp_w3_g3.controller;


import com.bootcamp_w3_g3.model.dtos.request.OrdemDeEntradaForm;
import com.bootcamp_w3_g3.model.entity.OrdemDeEntrada;
import com.bootcamp_w3_g3.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


/**
 * @author Joaquim Borges
 * @author Matheus Willock
 * @author Alex Cruz
 * @author Hugo Damm
 * @author Marcelo Santos
 */
@RestController
@RequestMapping("/api/ordem-entrada/")
public class OrdemEntradaController {

    @Autowired
    private OrdemDeEntradaService ordemService;

    @Autowired
    private VendedorService vendedorService;

    @Autowired
    private ArmazemService armazemService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private SetorService setorService;


    @PostMapping("/registrar")
    public ResponseEntity<OrdemDeEntradaForm> registrarOrdem(@RequestBody OrdemDeEntradaForm dto, UriComponentsBuilder uriComponentsBuilder) {
        OrdemDeEntrada ordemDeEntrada = dto.converte(vendedorService, armazemService, produtoService, setorService);
        ordemService.registra(ordemDeEntrada);
        URI uri = uriComponentsBuilder.path("api/ordem-entrada/{id}").buildAndExpand(ordemDeEntrada.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/alterar")
    public ResponseEntity<OrdemDeEntradaForm> alteraOrdem(@RequestBody OrdemDeEntradaForm dto, UriComponentsBuilder uriComponentsBuilder){
        OrdemDeEntrada ordemDeEntrada = dto.converte(vendedorService,armazemService, produtoService, setorService);
        ordemService.atualizaOrdem(ordemDeEntrada);
        URI uri = uriComponentsBuilder.path("api/ordem-entrada/{id}").buildAndExpand(ordemDeEntrada.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


}
