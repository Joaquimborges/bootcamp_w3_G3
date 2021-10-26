package com.bootcamp_w3_g3.service;


import com.bootcamp_w3_g3.model.entity.Armazem;
import com.bootcamp_w3_g3.model.entity.Setor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Matheus Willock
 * @author Joaquim Borges
 * @author Alex Cruz
 * @author Marcelo Oliveira
 * @author Hugo Damm
 */
@Service
public class OrdemDeEntradaService {

    private final RepresentanteService representanteService;
    private final VendedorService vendedorService;
    private final SetorService setorService;


    @Autowired
    public OrdemDeEntradaService(RepresentanteService representanteService, VendedorService vendedorService,
                                 SetorService setorService) {

        this.representanteService = representanteService;
        this.vendedorService = vendedorService;
        this.setorService = setorService;

    }

    /**
     * @autor Joaquim Borges
     * metodo para validar o armazem
     */
    private boolean validarArmazem(String codigoArmazem) {
        for (Setor setor : setorService.listarSetores()) {
            if (setor.getArmazem().getCodArmazem().equals(codigoArmazem)){
                Armazem armazem = setor.getArmazem();
                return armazem != null;
            }
        }
        return false;
    }









}
