package br.com.ms_pagamentos.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_item_pedido")
public class Item {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "iditem", updatable = false, nullable = false)
    private UUID iditem;
    private UUID idproduto;
    private BigDecimal quantidade;
    @ManyToOne
    @JoinColumn(name = "idpedido")
    private Pedido pedido;

}
