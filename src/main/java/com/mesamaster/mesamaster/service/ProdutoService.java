package com.mesamaster.mesamaster.service;

import com.mesamaster.mesamaster.entidades.Produto;
import com.mesamaster.mesamaster.repositorios.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public List<Produto> listarAtivos() {
        return produtoRepository.findByAtivoTrue();
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }
}
