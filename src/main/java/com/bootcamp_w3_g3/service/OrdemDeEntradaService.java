package com.bootcamp_w3_g3.service;


import com.bootcamp_w3_g3.model.entity.Armazem;
import com.bootcamp_w3_g3.model.entity.Representante;
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

    /**
     * @autor Alex Cruz
     * metodo para verificar se o representante pertence ao armazem
     */
    private boolean representantePertenceAoArmazem(String codigo){
        for (Setor setor : setorService.listarSetores()){
            if (setor.getArmazem().getRepresentante().getCodigo().equals(codigo)){
                Representante representante = setor.getArmazem().getRepresentante();
                return representante != null;
            }
        }
        return false;
    }

    /**
     * @autor JoaquimBorges
     * metodo para verificar se o setor corresponde
     * a categoria de produto que esta sendo enviado na ordem de entrada
     */

    private boolean setorCorrespondeAoTipoDeProduto(String tipoDeProduto) {
        for (Setor setor : setorService.listarSetores()) {
            if (setor.getTipoProduto().equals(tipoDeProduto)) {
                return true;
            }
        }
        return false;
    }









}
