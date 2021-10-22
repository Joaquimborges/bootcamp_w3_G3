package com.bootcamp_w3_g3.controller;

import com.bootcamp_w3_g3.model.dtos.request.SetorForm;
import com.bootcamp_w3_g3.model.dtos.response.SetorDTO;
import com.bootcamp_w3_g3.model.entity.Setor;
import com.bootcamp_w3_g3.service.SetorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hugo damm
 */

@RestController
@RequestMapping("setor/")
public class SetorController {

    @Autowired
    private SetorService setorService;

    @PostMapping("/salvar")
    public ResponseEntity<SetorDTO> salvar(@RequestBody SetorForm setorForm){
        Setor setor = setorService.salvar(setorForm.converte());
        return new ResponseEntity<>(SetorDTO.converter(setor), HttpStatus.CREATED);
    }

    @GetMapping("/obter/{id}")
    public ResponseEntity<SetorDTO> obter(@PathVariable Integer codigo){
        Setor setor = setorService.obter(codigo);
        return new ResponseEntity<>(SetorDTO.converter(setor), HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<SetorDTO>> listar(){
        List<Setor> setores = setorService.listar();
        return new ResponseEntity<>(SetorDTO.converterLista(setores), HttpStatus.OK);
    }

    @PutMapping("/alterar")
    public ResponseEntity<SetorDTO> alterar(@RequestBody SetorForm setorForm){
        Setor setor = setorService.atualizar(setorForm.converte());
        return new ResponseEntity<>(SetorDTO.converter(setor), HttpStatus.OK);
    }

}