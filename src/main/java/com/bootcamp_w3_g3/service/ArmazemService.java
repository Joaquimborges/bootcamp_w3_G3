package com.bootcamp_w3_g3.service;


/**
 * @author Matheus Willock
 */
import com.bootcamp_w3_g3.model.entity.Armazem;
import com.bootcamp_w3_g3.model.entity.Representante;
import com.bootcamp_w3_g3.model.entity.Setor;
import com.bootcamp_w3_g3.repository.ArmazemRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Service
public class ArmazemService {

    @Autowired
    private SetorService setorService;

    @Autowired
    private ArmazemRepository armazemRepository;

    @Autowired
    public ArmazemService(ArmazemRepository armazemRepository){
        this.armazemRepository = armazemRepository;
    }

    @Transactional
    public Armazem criarArmazem(Armazem armazem){
        return armazemRepository.save(armazem);
    }

    public Armazem obterArmazem(String cod) {
        return armazemRepository.findByCodArmazem(cod);
    }

    public Armazem deletarArmazem(String cod){
        return armazemRepository.deleteByCodArmazem(cod);
    }

    public Armazem atualizarArmazem(Armazem armazem) {
        Armazem editedArmazem = armazemRepository.findByCodArmazem(armazem.getCodArmazem());

        editedArmazem.setSetoresDoArmazem(armazem.getSetoresDoArmazem());
        editedArmazem.setRepresentante(armazem.getRepresentante());
        editedArmazem.setEndereco(armazem.getEndereco());
        editedArmazem.setUf(armazem.getUf());
        editedArmazem.setNome(armazem.getNome());

        return armazemRepository.save(editedArmazem);

    }

    public List<Setor> listarSetores() {
        return setorService.listarSetores();
    }

    public List<Armazem> listarArmazens() {
       return armazemRepository.findAll();
    }


    public Representante retornaRepresentanteDoArmazem(String codigoRepresentante) {
        for (Armazem armazem : listarArmazens()){
            if (armazem.getRepresentante().getCodigo().equals(codigoRepresentante)){
                return armazem.getRepresentante();
            }
        }
        return null;
    }

   public Setor retornaSetorDoArmazem(String codigoDoSetor) {
       List<Setor> setorList = new ArrayList<>();
       for (Armazem armazem : listarArmazens()) {
           setorList = armazem.getSetoresDoArmazem();
       }
       for (Setor setor : setorList) {
           if (setor.getCodigo().equals(codigoDoSetor)){
               return setor;
           }
       }
       return null;
   }

}
