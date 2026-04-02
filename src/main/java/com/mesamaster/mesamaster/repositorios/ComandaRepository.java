package com.mesamaster.mesamaster.repositorios;

import com.mesamaster.mesamaster.entidades.Comanda;
import com.mesamaster.mesamaster.entidades.Mesa;
import com.mesamaster.mesamaster.enums.StatusComanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ComandaRepository extends JpaRepository<Comanda, UUID> {

    List<Comanda> findByMesaAndStatus(Mesa mesa, StatusComanda status);

    @Query("SELECT COALESCE(SUM(c.total), 0) FROM Comanda c " +
           "WHERE c.status = :status " +
           "AND c.data_fechamento >= :startDate " +
           "AND c.data_fechamento < :endDate")
    BigDecimal getFaturamentoPorPeriodo(@Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate,
                                        @Param("status") StatusComanda status);
}
