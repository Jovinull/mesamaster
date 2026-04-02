package com.mesamaster.mesamaster.repositorios;

import com.mesamaster.mesamaster.entidades.Comanda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ComandaRepository extends JpaRepository<Comanda, UUID> {
}
