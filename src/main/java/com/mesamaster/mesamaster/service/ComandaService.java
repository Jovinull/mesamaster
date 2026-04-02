package com.mesamaster.mesamaster.service;

import com.mesamaster.mesamaster.entidades.Comanda;
import com.mesamaster.mesamaster.entidades.Mesa;
import com.mesamaster.mesamaster.enums.StatusComanda;
import com.mesamaster.mesamaster.enums.StatusMesa;
import com.mesamaster.mesamaster.repositorios.ComandaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ComandaService {

    private final ComandaRepository comandaRepository;
    private final MesaService mesaService;

    public ComandaService(ComandaRepository comandaRepository, MesaService mesaService) {
        this.comandaRepository = comandaRepository;
        this.mesaService = mesaService;
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

    public List<Comanda> listarComandasAbertasPorMesa(UUID mesaId) {
        Mesa mesa = mesaService.buscarPorId(mesaId);
        return comandaRepository.findByMesaAndStatus(mesa, StatusComanda.ABERTA);
    }
}
