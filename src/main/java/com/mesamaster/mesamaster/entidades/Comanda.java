package com.mesamaster.mesamaster.entidades;

import com.mesamaster.mesamaster.enums.StatusComanda;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "comandas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comanda {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private Mesa mesa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusComanda status;

    private LocalDateTime data_abertura;
    private LocalDateTime data_fechamento;

    @Column(precision = 10, scale = 2)
    private BigDecimal total;

    @OneToMany(mappedBy = "comanda", fetch = FetchType.EAGER)
    @OrderBy("hora_pedido ASC")
    @ToString.Exclude
    private List<ItemPedido> itens = new ArrayList<>();
}
