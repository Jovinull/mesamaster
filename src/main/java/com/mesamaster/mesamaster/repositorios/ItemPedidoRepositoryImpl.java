package com.mesamaster.mesamaster.repositorios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemPedidoRepositoryImpl implements ItemPedidoRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> findByProdutoVendido() {
        return entityManager.createQuery(
                "SELECT ip.produto.nome, SUM(ip.quantidade), SUM(ip.quantidade * ip.preco_unitario_na_hora) " +
                "FROM ItemPedido ip " +
                "GROUP BY ip.produto.nome " +
                "ORDER BY SUM(ip.quantidade) DESC",
                Object[].class)
                .getResultList();
    }
}
