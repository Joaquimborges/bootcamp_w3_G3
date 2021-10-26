package com.bootcamp_w3_g3.controller;

import com.bootcamp_w3_g3.model.dtos.request.ArmazemForm;
import com.bootcamp_w3_g3.model.dtos.response.ArmazemDTO;
import com.bootcamp_w3_g3.model.entity.Armazem;
import com.bootcamp_w3_g3.service.ArmazemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("armazem/")
public class ArmazemController {

    @Autowired
    private ArmazemService armazemService;

    @GetMapping("ping/")
    public String pong() {
        return "pong";
    }

    @PostMapping("/criar")
    public ResponseEntity<ArmazemDTO> criarArmazem(@RequestBody ArmazemForm armazemForm) {
        Armazem armazem = armazemService.criarArmazem(armazemForm.converte());

        return new ResponseEntity<>(ArmazemDTO.converter(armazem), HttpStatus.CREATED);
    }









}
