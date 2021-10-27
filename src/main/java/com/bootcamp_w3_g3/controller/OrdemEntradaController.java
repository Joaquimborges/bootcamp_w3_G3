package com.bootcamp_w3_g3.controller;


import com.bootcamp_w3_g3.model.dtos.request.OrdemDeEntradaDTO;
import com.bootcamp_w3_g3.model.entity.OrdemDeEntrada;
import com.bootcamp_w3_g3.service.OrdemDeEntradaService;
import com.bootcamp_w3_g3.service.RepresentanteService;
import com.bootcamp_w3_g3.service.SetorService;
import com.bootcamp_w3_g3.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author Joaquim Borges
 * @author Matheus Willock
 * @author Alex Cruz
 * @author Hugo Damm
 * @author Marcelo Santos
 */
@RestController
@RequestMapping("api/ordem-entrada/")
public class OrdemEntradaController {

    @Autowired
    private OrdemDeEntradaService ordemService;

    @Autowired
    private VendedorService vendedorService;

    @Autowired
    private RepresentanteService representanteService;

    @Autowired
    private SetorService setorService;


    @PostMapping("/registrar")
    public ResponseEntity<OrdemDeEntradaDTO> registrarOrdem(@RequestBody OrdemDeEntradaDTO dto) {
        OrdemDeEntrada ordemDeEntrada = dto.converte(setorService, representanteService, vendedorService);
        ordemService.registra(ordemDeEntrada);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
