package com.mesamaster.mesamaster.repositorios;

import com.mesamaster.mesamaster.entidades.Comanda;
import com.mesamaster.mesamaster.entidades.Mesa;
import com.mesamaster.mesamaster.enums.StatusComanda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ComandaRepository extends JpaRepository<Comanda, UUID> {

    List<Comanda> findByMesaAndStatus(Mesa mesa, StatusComanda status);
}
