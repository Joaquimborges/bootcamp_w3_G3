package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.model.entity.Vendedor;
import com.bootcamp_w3_g3.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Marcelo de Oliveira Santos
 */

@Service
public class VendedorService {


    private final VendedorRepository vendedorRepository;

    @Autowired
    public VendedorService(VendedorRepository vendedorRepository) {
        this.vendedorRepository = vendedorRepository;
    }

    public Vendedor salvar(Vendedor Vendedor) {
        return vendedorRepository.save(Vendedor);
    }

    public Vendedor obter(String codigo) {
        return vendedorRepository.findByCodigo(codigo);
    }

    public List<Vendedor> listar() {
        return vendedorRepository.findAll();
    }

    public Vendedor atualizar(Vendedor vendedor) {
        Vendedor editedVendedor = vendedorRepository.findByCodigo(vendedor.getCodigo());
        editedVendedor.setEndereco(vendedor.getEndereco());
        editedVendedor.setTelefone(vendedor.getTelefone());

        return vendedorRepository.save(editedVendedor);
    }

    public Vendedor apagar(String codigo) {
        return vendedorRepository.deleteByCodigo(codigo);
    }



}
