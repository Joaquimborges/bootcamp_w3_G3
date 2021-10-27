package com.bootcamp_w3_g3.service;


import com.bootcamp_w3_g3.model.entity.Armazem;
import com.bootcamp_w3_g3.model.entity.OrdemDeEntrada;
import com.bootcamp_w3_g3.model.entity.Representante;
import com.bootcamp_w3_g3.model.entity.Setor;
import com.bootcamp_w3_g3.repository.OrdemDeEntradaRepository;
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
    private OrdemDeEntradaRepository ordemDeEntradaRepository;

    private final RepresentanteService representanteService;
    private final ArmazemService armazemService;
    private final SetorService setorService;


    @Autowired
    public OrdemDeEntradaService(OrdemDeEntradaRepository ordemDeEntradaRepository,
                                 RepresentanteService representanteService, ArmazemService armazemService,
                                 SetorService setorService) {

        this.representanteService = representanteService;
        this.armazemService = armazemService;
        this.setorService = setorService;

    }

    /**
     * @autor Joaquim Borges
     * metodo para validar o armazem
     */
    private boolean armazemExiste(String codigoArmazem) {
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


    /**
     * @autor Matheus Willock
     * metodo para validar a existencia do setor no armazem
     */
    private boolean setorExiste(String codigo) {
        for (Setor setor : armazemService.listarSetores()) {
            if (setor.getCodigo().equals(codigo)){
                return true;
            }
        }
        return false;
    }

    /**
     * @autor Marcelo Santos
     */
    private boolean cabeNoSetor (Integer quantidadeAtual, String codSetor){
        if (setorService.obterSetor(codSetor) == null ) {
            return false;
        }
        return   (armazemService.listarSetores().stream()
                .filter(s-> s.getCodigo().equals(codSetor))
                        .findFirst()).get().getEspacoDisponivel() >= quantidadeAtual;

    }

    /**
     * @author Matheus Willock
     * @author Joaquim Borges
     * @author Alex Cruz
     * @author Marcelo Santos
     * @author Hugo Damm
     */
    private boolean validarOrdem (OrdemDeEntrada ordemDeEntrada) {
        return armazemExiste(ordemDeEntrada.getSetor().getArmazem().getCodArmazem()) &&
                representantePertenceAoArmazem(ordemDeEntrada.getRepresentante().getCodigo()) &&
                setorExiste(ordemDeEntrada.getSetor().getCodigo()) &&
                setorCorrespondeAoTipoDeProduto(ordemDeEntrada.getSetor().getTipoProduto()) &&
                cabeNoSetor(ordemDeEntrada.getSetor().getEspacoDisponivel(), ordemDeEntrada.getSetor().getCodigo());
    }


    public OrdemDeEntrada registra(OrdemDeEntrada ordemDeEntrada) {
        if (validarOrdem(ordemDeEntrada)) {
            return ordemDeEntradaRepository.save(ordemDeEntrada);
        }
        return null;
    }


}
