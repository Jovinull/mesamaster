package com.mesamaster.mesamaster.service;

import com.mesamaster.mesamaster.enums.StatusComanda;
import com.mesamaster.mesamaster.repositorios.ComandaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DashboardService {

    private final ComandaRepository comandaRepository;

    public DashboardService(ComandaRepository comandaRepository) {
        this.comandaRepository = comandaRepository;
    }

    public BigDecimal getFaturamentoDoDia() {
        LocalDateTime inicio = LocalDate.now().atStartOfDay();
        LocalDateTime fim = inicio.plusDays(1);
        return comandaRepository.getFaturamentoPorPeriodo(inicio, fim, StatusComanda.FECHADA);
    }

    public BigDecimal getFaturamentoUltimos7Dias() {
        LocalDateTime fim = LocalDate.now().plusDays(1).atStartOfDay();
        LocalDateTime inicio = fim.minusDays(7);
        return comandaRepository.getFaturamentoPorPeriodo(inicio, fim, StatusComanda.FECHADA);
    }

    public BigDecimal getFaturamentoMesVigente() {
        LocalDateTime inicio = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime fim = inicio.plusMonths(1);
        return comandaRepository.getFaturamentoPorPeriodo(inicio, fim, StatusComanda.FECHADA);
    }
}
