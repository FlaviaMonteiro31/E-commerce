package br.com.ms_pagamentos.model.records;

import br.com.ms_pagamentos.enums.FormaPagamentoEnum;
import br.com.ms_pagamentos.enums.MeioPagamentoEnum;
import br.com.ms_pagamentos.model.Endereco;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class PagamentoRequest {
    private UUID idcarrinho;
    private FormaPagamentoEnum formapagamento;
    private MeioPagamentoEnum meiopagamento;
    private String pixCopiaCola;
    private String numeroCartao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
    private Date dataValidadeCartao;
    private Integer codigoSegunca;
    private String titularCartao;

}
