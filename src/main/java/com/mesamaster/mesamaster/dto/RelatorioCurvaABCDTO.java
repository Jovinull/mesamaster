package com.mesamaster.mesamaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioCurvaABCDTO {
    private String produtoNome;
    private Long quantidadeVendida;
    private BigDecimal totalGerado;
}
