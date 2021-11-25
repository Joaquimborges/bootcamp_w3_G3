package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.advisor.EntityNotFoundException;
import com.bootcamp_w3_g3.model.entity.Carteira;
import com.bootcamp_w3_g3.repository.CarteiraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CarteiraService {


    private final CarteiraRepository repository;

    @Autowired
    public CarteiraService(CarteiraRepository repository) {
        this.repository = repository;
    }

    /**
     *Metodo para criar uma conta do comprador,
     * a carteira será criada no momento em que um
     * comprador for criado.
     */
    @Transactional
    public Carteira criar(String codigoComprador) {
        Carteira carteira = Carteira.builder()
                .numero("MFC-".concat(codigoComprador))
                .saldo(00.0).build();

        return repository.save(carteira);
    }

    /**
     * Metodo usado para depositar valor na carteira
     * recebe o número da carteira e o valor como parameters.
     */
    public Carteira deposita(String numero, double valor) {
        try {
            double valorAtual;
            Carteira carteira = obter(numero);
            valorAtual = carteira.getSaldo() + valor;
            carteira.setSaldo(valorAtual);

            return atualizar(carteira);

        }catch (NoSuchElementException e){
            throw new EntityNotFoundException("carteira não encontrada.");
        }
    }

    /**
     * Metodo criado para ser usado no momento em que
     * uma compra for confirmada no codigo do comprador.
     */
    public void retira(String numero, double valor) {
        try {
            double valorAtual;
            Carteira carteira = obter(numero);

            if (valor <= carteira.getSaldo()) {
                valorAtual = carteira.getSaldo() - valor;
                carteira.setSaldo(valorAtual);
                atualizar(carteira);
                return;
            }
            throw new EntityNotFoundException("Seu saldo está negativo");
        }catch (NoSuchElementException e){
            throw new EntityNotFoundException("carteira não encontrada.");
        }
    }

    public Double consultarSaldo(String codigo) {
        Carteira carteira = obter(codigo);
        return carteira.getSaldo();
    }


    /**
     * Metodos auxiliares!!
     */
    public Carteira atualizar(Carteira carteira){
        Carteira carteiraAtualizada = obter(carteira.getNumero());
        carteiraAtualizada.setSaldo(carteira.getSaldo());

        return repository.save(carteiraAtualizada);
    }

    public Carteira obter(String numero){
        return repository.findCarteiraByNumero(numero);
    }

    public List<Carteira> listar(){
        return repository.findAll();
    }

}
