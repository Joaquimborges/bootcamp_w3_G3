package com.bootcamp_w3_g3.service;


/**
 * @author Matheus Willock
 */
import com.bootcamp_w3_g3.advisor.EntityNotFoundException;
import com.bootcamp_w3_g3.model.entity.Armazem;
import com.bootcamp_w3_g3.model.entity.Representante;
import com.bootcamp_w3_g3.repository.ArmazemRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
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
       Armazem armazem = armazemRepository.findByCodArmazem(cod);
       if (armazem != null){
           return armazem;
       } else{
           throw new EntityNotFoundException("armazem não encontrado");
       }

    }

    public Armazem deletarArmazem(Long id){
        armazemRepository.deleteById(id);
        return null;
    }

    public Armazem atualizarArmazem(Armazem armazem) {
        Armazem editedArmazem = armazemRepository.findByCodArmazem(armazem.getCodArmazem());

        editedArmazem.setRepresentante(armazem.getRepresentante());
        editedArmazem.setEndereco(armazem.getEndereco());
        editedArmazem.setUf(armazem.getUf());
        editedArmazem.setNome(armazem.getNome());

        return armazemRepository.save(editedArmazem);

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


}
