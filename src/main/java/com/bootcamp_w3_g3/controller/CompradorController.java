package com.bootcamp_w3_g3.controller;

import com.bootcamp_w3_g3.model.dtos.request.CompradorForm;
import com.bootcamp_w3_g3.model.dtos.response.CompradorDTO;
import com.bootcamp_w3_g3.model.entity.Carteira;
import com.bootcamp_w3_g3.model.entity.Comprador;
import com.bootcamp_w3_g3.service.CarteiraService;
import com.bootcamp_w3_g3.service.CompradorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(description = "Conjunto de endpoints para criação, recuperação, atualização e exclusão de Compradores.")
public class CompradorController {

    @Autowired
    private CompradorService compradorService;
    @Autowired
    private CarteiraService carteiraService;

    @PostMapping("/salvar")
    @ApiOperation("Criar um novo comprador.")
    public ResponseEntity<CompradorDTO> cadastrar(
            @ApiParam("Informações do comprador para um novo comprador a ser criado.")
            @RequestBody CompradorForm compradorForm) {
        Comprador comprador = compradorService.salvar(compradorForm.converte());
        return new ResponseEntity<>(CompradorDTO.converter(comprador), HttpStatus.CREATED);
    }

    @PutMapping("/alterar")
    @ApiOperation("Atualiza as informações do comprador cadastrado.")
    public ResponseEntity<CompradorDTO> alterar(@RequestBody CompradorForm compradorForm) {
        Comprador comprador = compradorService.atualizar(compradorForm.converte());
        return new ResponseEntity<>(CompradorDTO.converter(comprador), HttpStatus.OK);
    }

    @GetMapping("/carteira/depositar/{codigo}/{valor}")
    public ResponseEntity<Carteira> depositar(@PathVariable String codigo, @PathVariable Double valor){
        return new ResponseEntity<>(carteiraService.deposita(codigo, valor), HttpStatus.OK);
    }

    @GetMapping("/carteira/saldo/{codigo}")
    public ResponseEntity<Double> consultarSaldo(@PathVariable String codigo) {
        return new ResponseEntity<>(carteiraService.consultarSaldo(codigo), HttpStatus.OK);
    }
}



