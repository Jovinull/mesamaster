package com.mesamaster.mesamaster.service;

import com.mesamaster.mesamaster.dto.RelatorioCurvaABCDTO;
import com.mesamaster.mesamaster.repositorios.ItemPedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final ItemPedidoRepository itemPedidoRepository;

    public List<RelatorioCurvaABCDTO> gerarRelatorioCurvaABC() {
        return itemPedidoRepository.obterRelatorioCurvaABC();
    }
}
