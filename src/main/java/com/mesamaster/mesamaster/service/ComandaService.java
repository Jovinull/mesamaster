package com.mesamaster.mesamaster.service;

import com.mesamaster.mesamaster.entidades.Comanda;
import com.mesamaster.mesamaster.entidades.ItemPedido;
import com.mesamaster.mesamaster.entidades.Mesa;
import com.mesamaster.mesamaster.entidades.Produto;
import com.mesamaster.mesamaster.enums.StatusComanda;
import com.mesamaster.mesamaster.enums.StatusMesa;
import com.mesamaster.mesamaster.repositorios.ComandaRepository;
import com.mesamaster.mesamaster.repositorios.ItemPedidoRepository;
import com.mesamaster.mesamaster.repositorios.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ComandaService {

    private final ComandaRepository comandaRepository;
    private final MesaService mesaService;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public ComandaService(ComandaRepository comandaRepository, MesaService mesaService,
                          ProdutoRepository produtoRepository, ItemPedidoRepository itemPedidoRepository) {
        this.comandaRepository = comandaRepository;
        this.mesaService = mesaService;
        this.produtoRepository = produtoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @Transactional
    public Comanda abrirNovaComanda(UUID mesaId) {
        Mesa mesa = mesaService.buscarPorId(mesaId);

        if (mesa.getStatus() == StatusMesa.LIVRE) {
            mesa.setStatus(StatusMesa.OCUPADA);
        }

        Comanda comanda = new Comanda();
        comanda.setMesa(mesa);
        comanda.setStatus(StatusComanda.ABERTA);
        comanda.setData_abertura(LocalDateTime.now());
        comanda.setTotal(BigDecimal.ZERO);

        return comandaRepository.save(comanda);
    }

    public Comanda buscarPorId(UUID id) {
        return comandaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Comanda não encontrada: " + id));
    }

    public List<Comanda> listarComandasAbertasPorMesa(UUID mesaId) {
        Mesa mesa = mesaService.buscarPorId(mesaId);
        return comandaRepository.findByMesaAndStatus(mesa, StatusComanda.ABERTA);
    }

    @Transactional
    public Comanda fecharComanda(UUID comandaId) {
        Comanda comanda = comandaRepository.findById(comandaId)
                .orElseThrow(() -> new NoSuchElementException("Comanda não encontrada: " + comandaId));

        if (comanda.getStatus() == StatusComanda.FECHADA) {
            throw new IllegalStateException("Comanda já está fechada.");
        }

        comanda.setStatus(StatusComanda.FECHADA);
        comanda.setData_fechamento(LocalDateTime.now());
        comandaRepository.save(comanda);

        Mesa mesa = comanda.getMesa();
        List<Comanda> abertas = comandaRepository.findByMesaAndStatus(mesa, StatusComanda.ABERTA);
        if (abertas.isEmpty()) {
            mesa.setStatus(StatusMesa.LIVRE);
        }

        return comanda;
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
