package com.bootcamp_w3_g3.controller;

import com.bootcamp_w3_g3.model.dtos.request.VendedorForm;
import com.bootcamp_w3_g3.model.dtos.response.VendedorDTO;
import com.bootcamp_w3_g3.model.entity.Vendedor;
import com.bootcamp_w3_g3.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hugo damm
 */

@RestController
@RequestMapping("vendedor/")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    @PostMapping("/salvar")
    public ResponseEntity<VendedorDTO> salvar(@RequestBody VendedorForm vendedorForm){
        Vendedor vendedor = vendedorService.salvar(vendedorForm.converte());
        return new ResponseEntity<>(VendedorDTO.converter(vendedor), HttpStatus.CREATED);
    }

    @GetMapping("/obter/{codigo}")
    public ResponseEntity<VendedorDTO> obter(@PathVariable String codigo){
        Vendedor vendedor = vendedorService.obter(
                codigo);
        return new ResponseEntity<>(VendedorDTO.converter(vendedor), HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<VendedorDTO>> listar(){
        List<Vendedor> vendedores = vendedorService.listar();
        return new ResponseEntity<>(VendedorDTO.converteLista(vendedores), HttpStatus.OK);
    }

    @PutMapping("/alterar")
    public ResponseEntity<VendedorDTO> alterar(@RequestBody VendedorForm vendedorForm){
        Vendedor vendedor = vendedorService.atualizar(vendedorForm.converte());
        return new ResponseEntity<>(VendedorDTO.converter(vendedor), HttpStatus.OK);
    }
}
