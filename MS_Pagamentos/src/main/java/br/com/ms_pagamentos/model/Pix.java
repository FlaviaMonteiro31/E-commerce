package br.com.ms_pagamentos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pix {
    private String chavePix;
    private String nomeRecebedor;
    private String cidadeRecebedor;
    private String identificadorTransacao;
    private double valor;
}
