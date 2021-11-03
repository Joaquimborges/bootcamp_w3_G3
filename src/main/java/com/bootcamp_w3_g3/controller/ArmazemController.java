package com.bootcamp_w3_g3.controller;

import com.bootcamp_w3_g3.model.dtos.request.ArmazemForm;
import com.bootcamp_w3_g3.model.dtos.response.ArmazemDTO;
import com.bootcamp_w3_g3.model.entity.Armazem;
import com.bootcamp_w3_g3.service.ArmazemService;
import com.bootcamp_w3_g3.service.RepresentanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("armazem")
public class ArmazemController {

    @Autowired
    private ArmazemService armazemService;

    @Autowired
    private RepresentanteService representanteService;

//    OK
    @PostMapping("/criar")
    public ResponseEntity<ArmazemDTO> criarArmazem(@RequestBody ArmazemForm armazemForm) {
        Armazem armazem = armazemService.criarArmazem(armazemForm.converte(representanteService));

        return new ResponseEntity<>(ArmazemDTO.converter(armazem),
                HttpStatus.CREATED
        );
    }

    //    OK
    @GetMapping("/listar")
    public ResponseEntity<List<ArmazemDTO>> listar() {
        List<Armazem> armazemList = armazemService.listarArmazens();

        return new ResponseEntity<>(
                ArmazemDTO.armazemDTOListConverte(armazemList),
                HttpStatus.OK
        );

    }


    @GetMapping("/obter/{codigo}")
    public ResponseEntity<ArmazemDTO> obterArmazem(@PathVariable String codigo) {
        Armazem armazem = armazemService.obterArmazem(codigo);

        return new ResponseEntity<>(ArmazemDTO.converter(armazem), HttpStatus.OK);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<ArmazemDTO> atualizarArmazem(@RequestBody ArmazemForm armazemForm) {
        Armazem armazem = armazemService.atualizarArmazem(armazemForm.converte(representanteService));


        return new ResponseEntity<>(ArmazemDTO.converter(armazem), HttpStatus.OK);
    }






}
