package br.com.ms_pagamentos.model.records;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaCarrinhoResponse {
    private UUID id;
    private UUID usuario;
    private List<ItemResponse> itens;
    private BigDecimal valorCarrinho;
}
