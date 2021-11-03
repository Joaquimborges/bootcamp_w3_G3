package com.bootcamp_w3_g3.controller;

import com.bootcamp_w3_g3.model.dtos.request.CompradorForm;
import com.bootcamp_w3_g3.model.dtos.response.CompradorDTO;
import com.bootcamp_w3_g3.model.entity.Comprador;
import com.bootcamp_w3_g3.service.CompradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Alex Ccruz
 */

@RestController
@RequestMapping("comprador/")
public class CompradorController {

    @Autowired
    private CompradorService compradorService;

    @PostMapping("/salvar")
    public ResponseEntity<CompradorDTO> cadastrar(@RequestBody CompradorForm compradorForm) {
        Comprador comprador = compradorService.salvar(compradorForm.converte());
        return new ResponseEntity<>(CompradorDTO.converter(comprador), HttpStatus.CREATED);
    }

    @PutMapping("/alterar")
    public ResponseEntity<CompradorDTO> alterar(@RequestBody CompradorForm compradorForm) {
        Comprador comprador = compradorService.atualizar(compradorForm.converte());
        return new ResponseEntity<>(CompradorDTO.converter(comprador), HttpStatus.OK);
    }
}



