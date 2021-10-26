package com.bootcamp_w3_g3.controller;


import com.bootcamp_w3_g3.service.OrdemDeEntradaService;
import com.bootcamp_w3_g3.service.RepresentanteService;
import com.bootcamp_w3_g3.service.SetorService;
import com.bootcamp_w3_g3.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author Joaquim Borges
 * @author Matheus Willock
 * @author Alex Cruz
 * @author Hugo Damm
 * @author Marcelo Oliveira
 */
@RestController
@RequestMapping("api/ordem-entrada/")
public class OrdemEntradaController {

    @Autowired
    private OrdemDeEntradaService ordemDeEntradaService;

    @Autowired
    private VendedorService vendedorService;

    @Autowired
    private RepresentanteService representanteService;

    @Autowired
    private SetorService setorService;


}
