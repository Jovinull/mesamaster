package com.mesamaster.mesamaster.repositorios;

import com.mesamaster.mesamaster.dto.RelatorioCurvaABCDTO;
import com.mesamaster.mesamaster.enums.StatusComanda;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ItemPedidoRepositoryImpl implements ItemPedidoRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RelatorioCurvaABCDTO> obterRelatorioCurvaABC() {
        String jpql = "SELECT new com.mesamaster.mesamaster.dto.RelatorioCurvaABCDTO(" +
                      "p.nome, SUM(ip.quantidade), SUM(ip.quantidade * ip.preco_unitario_na_hora)) " +
                      "FROM ItemPedido ip " +
                      "JOIN ip.produto p " +
                      "JOIN ip.comanda c " +
                      "WHERE c.status = :status " +
                      "GROUP BY p.nome " +
                      "ORDER BY SUM(ip.quantidade) DESC";

        TypedQuery<RelatorioCurvaABCDTO> query = entityManager.createQuery(jpql, RelatorioCurvaABCDTO.class);
        query.setParameter("status", StatusComanda.FECHADA);
        return query.getResultList();
    }
}
