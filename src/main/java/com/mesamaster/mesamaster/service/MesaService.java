package com.mesamaster.mesamaster.service;

import com.mesamaster.mesamaster.entidades.Mesa;
import com.mesamaster.mesamaster.repositorios.MesaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class MesaService {

    private final MesaRepository mesaRepository;

    public MesaService(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    public List<Mesa> listarTodas() {
        return mesaRepository.findAll();
    }

    public Mesa buscarPorId(UUID id) {
        return mesaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Mesa não encontrada com id: " + id));
    }
}
