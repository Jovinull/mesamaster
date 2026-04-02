package com.mesamaster.mesamaster.service;

import com.mesamaster.mesamaster.entidades.Comanda;
import com.mesamaster.mesamaster.entidades.ItemPedido;
import com.mesamaster.mesamaster.entidades.Produto;
import com.mesamaster.mesamaster.repositorios.ComandaRepository;
import com.mesamaster.mesamaster.repositorios.ItemPedidoRepository;
import com.mesamaster.mesamaster.repositorios.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PedidoService {

    private final ComandaRepository comandaRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public PedidoService(ComandaRepository comandaRepository,
                         ProdutoRepository produtoRepository,
                         ItemPedidoRepository itemPedidoRepository) {
        this.comandaRepository = comandaRepository;
        this.produtoRepository = produtoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @Transactional
    public void adicionarItemNaComanda(UUID comandaId, UUID produtoId, int quantidade) {
        Comanda comanda = comandaRepository.findById(comandaId)
                .orElseThrow(() -> new NoSuchElementException("Comanda não encontrada: " + comandaId));

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado: " + produtoId));

        ItemPedido item = new ItemPedido();
        item.setComanda(comanda);
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setPreco_unitario_na_hora(produto.getPrecoVenda());
        item.setHora_pedido(LocalDateTime.now());

        itemPedidoRepository.save(item);

        BigDecimal subtotal = produto.getPrecoVenda().multiply(BigDecimal.valueOf(quantidade));
        comanda.setTotal(comanda.getTotal().add(subtotal));
        comandaRepository.save(comanda);
    }
}
