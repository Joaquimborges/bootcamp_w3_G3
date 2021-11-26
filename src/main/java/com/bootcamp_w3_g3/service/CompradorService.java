package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.advisor.EntityNotFoundException;
import com.bootcamp_w3_g3.model.entity.Carteira;
import com.bootcamp_w3_g3.model.entity.Comprador;
import com.bootcamp_w3_g3.repository.CompradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 *
 * @author Alex Cruz
 */
@Service
public class CompradorService {


    private CompradorRepository compradorRepository;
    @Autowired
    private CarteiraService carteiraService;

    @Autowired
    public CompradorService(CompradorRepository compradorRepository, CarteiraService carteiraService){
        this.compradorRepository = compradorRepository;
        this.carteiraService = carteiraService;
    }

    /**
     * Este método valida os campos: nome, sobrenome e cpf
     * @param comprador Passamos o objeto representante.
     * @return O save do objeto.
     * @author Matheus Wllock
     */
    @Transactional
    public Comprador salvar(Comprador comprador) {

        int validateCpf = comprador.getCpf().length();
        String nome = comprador.getNome();
        String sobrenome = comprador.getSobrenome();
        Comprador copCPF = compradorRepository.getCompradorByCpf(comprador.getCpf());
        Comprador copCod = compradorRepository.getByCodigo(comprador.getCodigo());

        if( copCod == null && copCPF == null && validateCpf == 11 && nome != null && !nome.equals("") && !nome.equals(" ")
                && sobrenome != null && !sobrenome.equals("") && !sobrenome.equals(" ")
        ) {
            Carteira carteira = carteiraService.criar(comprador.getCodigo());
            comprador.setCarteira(carteira);
            return compradorRepository.save(comprador);
        } if (sobrenome == null || sobrenome.equals("") || sobrenome.equals(" ")) {
            throw new EntityNotFoundException("sobrenome inválido");
        } if (nome == null || nome.equals("") || nome.equals(" ")) {
            throw new EntityNotFoundException("nome inválido!");
        } if (copCPF != null) {
            throw new EntityNotFoundException("CPF já cadastrado!");
        } if (copCod != null) {
            throw new EntityNotFoundException("Código do comprador já cadastrado!");
        }

        throw new EntityNotFoundException("CPF inválido");

    }
    public Comprador obter(String codigo) {
        try {
            return compradorRepository.findByCodigo(codigo);
        }catch (EntityNotFoundException e){
            throw new EntityNotFoundException("comprador não encontrado");
        }
    }

    public Comprador atualizar(Comprador comprador) {
        Comprador compradorEdited = compradorRepository.getByCodigo(comprador.getCodigo());
        compradorEdited.setNome(comprador.getNome());
        compradorEdited.setSobrenome(comprador.getSobrenome());
        compradorEdited.setTelefone(comprador.getTelefone());
        compradorEdited.setEndereco(comprador.getEndereco());
        compradorEdited.setCarteira(comprador.getCarteira());

        return compradorRepository.save(compradorEdited);
    }

}
