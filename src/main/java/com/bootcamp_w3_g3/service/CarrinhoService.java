package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.advisor.EntityNotFoundException;
import com.bootcamp_w3_g3.model.entity.Carrinho;
import com.bootcamp_w3_g3.model.entity.Itens;
import com.bootcamp_w3_g3.model.entity.Lote;
import com.bootcamp_w3_g3.model.entity.Produto;
import com.bootcamp_w3_g3.repository.CarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex Cruz
 */

@Service
public class CarrinhoService {

    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private LoteService loteService;

    @Autowired
    public CarrinhoService(CarrinhoRepository carrinhoRepository){
        this.carrinhoRepository = carrinhoRepository;
    }

    @Transactional
    public BigDecimal registrarPedido(Carrinho carrinho) {
        for (Itens itens : carrinho.getItensList()){
            if (produtoVencido(itens.getProduto())){
                return null;
            }
        }
        Carrinho carrinhoSalvo = carrinhoRepository.save(carrinho);
         return retornaPrecoDosItens(carrinhoSalvo);
    }

    public Carrinho salvar(Carrinho carrinho){
        return carrinhoRepository.save(carrinho);
    }

    public List<Carrinho> listar() {
        return carrinhoRepository.findAll();
    }

    public Carrinho atualizar(Carrinho carrinho) {
        Carrinho carrinhoEdited = carrinhoRepository.getByCodigo(carrinho.getCodigo());
        carrinhoEdited.setDataDeOrdem(carrinho.getDataDeOrdem());
        carrinhoEdited.setItensList(carrinho.getItensList());
        carrinhoEdited.setStatusCompra(carrinho.getStatusCompra());

        return carrinhoRepository.save(carrinhoEdited);
    }

    /**
     *metodo calcula os itens contidos
     * no carrinho e retorna o valor total.
     * @autor Joaquim Borges
     */
    public BigDecimal retornaPrecoDosItens(Carrinho carrinho) {
        double valorTotal = 0.0;
        for (Itens item : carrinho.getItensList()) {
            valorTotal += item.getProduto().getPreco() * item.getQuantidade();
        }
        return new BigDecimal(valorTotal);
    }

    /**
     * metodo para exibir todos os produtos
     * existentes em um determinado pedido.
     * @autor Joaquim Borges
     */
    public List<Produto> mostrarProdutosDoPedido(Long idCarrinho){
        List<Produto> produtosDoPedido = new ArrayList<>();
        Carrinho carrinho = carrinhoRepository.getById(idCarrinho);
        for (Itens itens : carrinho.getItensList()){
            produtosDoPedido.add(itens.getProduto());
        }
        return produtosDoPedido;
    }

    /**
     * metodo permite alterar os dados de
     * um pedido específico.
     * @autor Joaquim Borges
     */
    public Carrinho alterarPedido(Carrinho carrinho, Long id) {
        try {
            Carrinho carrinhoEncontrado = carrinhoRepository.getById(id);
            if (carrinhoEncontrado != null){
                carrinhoEncontrado.setItensList(carrinho.getItensList());
                carrinhoEncontrado.setDataDeOrdem(carrinho.getDataDeOrdem());
                carrinhoEncontrado.setStatusCompra(carrinho.getStatusCompra());
                carrinhoRepository.save(carrinhoEncontrado);
            }
            return carrinhoEncontrado;

        }catch (Exception e){
            throw new EntityNotFoundException("pedido não encontrado");
        }
    }

    /**
     *metodo auxiliar para verificar a validade do produto
     * @autor Joaquim Borges
     */
    public boolean produtoVencido(Produto produto){
        Lote loteDoProduto = loteService.obter(produto.getLote().getNumero());
        long dias = ChronoUnit.DAYS
                .between(loteDoProduto.getDataDeFabricacao(), loteDoProduto.getDataDeValidade());
        return dias < 23;
    }










}
