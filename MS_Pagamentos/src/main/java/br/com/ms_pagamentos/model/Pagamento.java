package br.com.ms_pagamentos.model;

import br.com.ms_pagamentos.enums.FormaPagamentoEnum;
import br.com.ms_pagamentos.enums.MeioPagamentoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "idpagamento", updatable = false, nullable = false)
    private UUID idpagamento;
    private UUID idcarrinho;
    private FormaPagamentoEnum formapagamento;
    private MeioPagamentoEnum meiopagamento;
    private BigDecimal valorCarrinho;
    private String pixCopiaCola;
    private String numeroCartao;
    private Date dataValidadeCartao;
    private Integer codigoSeguranca;
    private String titularCartao;
    private BigDecimal valorParcelaCartao;
    private String statusPagamento;
    private String numeroPedidoGerado;
}
