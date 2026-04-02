package com.mesamaster.mesamaster.service;

import com.mesamaster.mesamaster.repositorios.ItemPedidoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RelatorioService {

    private final ItemPedidoRepository itemPedidoRepository;

    public RelatorioService(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public List<Map<String, Object>> gerarCurvaABC() {
        List<Object[]> resultados = itemPedidoRepository.findByProdutoVendido();
        List<Map<String, Object>> relatorio = new ArrayList<>();

        int posicao = 1;
        for (Object[] linha : resultados) {
            Map<String, Object> item = new HashMap<>();
            item.put("posicao", posicao++);
            item.put("produto", linha[0]);
            item.put("quantidadeVendida", ((Number) linha[1]).longValue());
            item.put("totalGerado", (BigDecimal) linha[2]);
            relatorio.add(item);
        }

        return relatorio;
    }
}
