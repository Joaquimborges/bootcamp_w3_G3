package com.bootcamp_w3_g3.controller;

import com.bootcamp_w3_g3.model.dtos.request.RepresentanteForm;
import com.bootcamp_w3_g3.model.dtos.response.RepresentanteDTO;
import com.bootcamp_w3_g3.model.entity.Representante;
import com.bootcamp_w3_g3.service.RepresentanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("representante")
public class RepresentanteController {

        @Autowired
        private RepresentanteService representanteService;

        @PostMapping("/salvar")
        public ResponseEntity<RepresentanteDTO> salvar(@RequestBody RepresentanteForm representanteForm) {
            Representante representante = representanteService.salvar(representanteForm.converte());
            return new ResponseEntity<>(RepresentanteDTO.converteEmRepresentanteDTO(representante), HttpStatus.CREATED);
        }

        @GetMapping("/obter/{codigo}")
        public ResponseEntity<RepresentanteDTO> obter(@PathVariable String codigo) {
            Representante representante = representanteService.obter(codigo);
            return new ResponseEntity<>(RepresentanteDTO.converteEmRepresentanteDTO(representante), HttpStatus.OK);
        }

        @GetMapping("/listar")
        public ResponseEntity<List<RepresentanteDTO>> listar(){
            List<Representante> representanteList = representanteService.listar();
            return new ResponseEntity<>(RepresentanteDTO.converteListaParaRepresentanteDTO(representanteList), HttpStatus.OK);
        }

        @PutMapping("/alterar")
        public ResponseEntity<RepresentanteDTO> alterar(@RequestBody RepresentanteForm representanteForm) {
            Representante representante = representanteService.atualizar(representanteForm.converte());
            return new ResponseEntity<>(RepresentanteDTO.converteEmRepresentanteDTO(representante), HttpStatus.OK);
        }

        @DeleteMapping("/apagar/{codigo}")
        public ResponseEntity<RepresentanteDTO> apagar(@PathVariable String codigo){
            Representante representante = representanteService.apagar(codigo);
            return new ResponseEntity<>(RepresentanteDTO.converteEmRepresentanteDTO(representante), HttpStatus.ACCEPTED);
        }
    }
