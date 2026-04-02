package com.mesamaster.mesamaster.repositorios;

import com.mesamaster.mesamaster.entidades.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MesaRepository extends JpaRepository<Mesa, UUID> {
}
