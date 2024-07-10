package br.com.ms_pagamentos.model.records;

import br.com.ms_pagamentos.enums.FormaPagamentoEnum;
import br.com.ms_pagamentos.enums.MeioPagamentoEnum;
import br.com.ms_pagamentos.model.Pagamento;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PagamentoResponse {

    private UUID idpagamento;
    private FormaPagamentoEnum formapagamento;
    private MeioPagamentoEnum meiopagamento;
    private String pixCopiaCola;
    private String numeroCartao;
    private Date dataValidadeCartao;
    private int codigoSegunca;
    private String titularCartao;
    private String statusPagamento;
    private String numeroPedidoGerado;

    public PagamentoResponse(Pagamento pagamento){
        this.idpagamento = pagamento.getIdpagamento();
        this.formapagamento = pagamento.getFormapagamento();
        this.meiopagamento = pagamento.getMeiopagamento();
        this.pixCopiaCola = pagamento.getPixCopiaCola();
        this.numeroCartao = pagamento.getNumeroCartao();
        this.dataValidadeCartao = pagamento.getDataValidadeCartao();
        this.codigoSegunca = pagamento.getCodigoSeguranca();
        this.titularCartao = pagamento.getTitularCartao();
        this.statusPagamento = pagamento.getStatusPagamento();
        this.numeroPedidoGerado = pagamento.getNumeroPedidoGerado();

    }

}
