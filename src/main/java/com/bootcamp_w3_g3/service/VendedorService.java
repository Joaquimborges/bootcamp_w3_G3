package com.bootcamp_w3_g3.service;

import com.bootcamp_w3_g3.advisor.EntityNotFoundException;
import com.bootcamp_w3_g3.model.entity.Vendedor;
import com.bootcamp_w3_g3.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Marcelo de Oliveira Santos
 */

@Service
public class VendedorService {


    @Autowired
    VendedorRepository vendedorRepository;

    @Autowired
    public VendedorService(VendedorRepository vendedorRepository) {
        this.vendedorRepository = vendedorRepository;
    }

    @Transactional
    public Vendedor salvar(Vendedor Vendedor) {
        return vendedorRepository.save(Vendedor);
    }

    public Vendedor obter(String codigo) {
        Vendedor vendedor = vendedorRepository.getByCodigo(codigo);
        if (vendedor != null){
            return vendedor;
        }
        throw new EntityNotFoundException("vendedor não encontrado");
    }

    public List<Vendedor> listar() {
        return vendedorRepository.findAll();
    }

    public Vendedor atualizar(Vendedor vendedor) {
        Vendedor editedVendedor = vendedorRepository.getByCodigo(vendedor.getCodigo());
        if (editedVendedor == null) return vendedor;
        editedVendedor.setEndereco(vendedor.getEndereco());
        editedVendedor.setTelefone(vendedor.getTelefone());

        return vendedorRepository.save(editedVendedor);
    }






}
