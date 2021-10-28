package com.bootcamp_w3_g3.controller;


import com.bootcamp_w3_g3.model.dtos.request.OrdemDeEntradaDTO;
import com.bootcamp_w3_g3.model.entity.OrdemDeEntrada;
import com.bootcamp_w3_g3.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    private ArmazemService armazemService;


    @PostMapping("/registrar")
    public ResponseEntity<OrdemDeEntradaDTO> registrarOrdem(@RequestBody OrdemDeEntradaDTO dto) {
        OrdemDeEntrada ordemDeEntrada = dto.converte(vendedorService, armazemService);
        ordemService.registra(ordemDeEntrada);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/alterar")
    public ResponseEntity<OrdemDeEntradaDTO> alteraOrdem(@RequestBody OrdemDeEntradaDTO ordemDeEntradaDTO){
        OrdemDeEntrada ordemDeEntrada = ordemDeEntradaDTO.converte(vendedorService,armazemService);
        ordemService.atualizaOrdem(ordemDeEntrada);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
