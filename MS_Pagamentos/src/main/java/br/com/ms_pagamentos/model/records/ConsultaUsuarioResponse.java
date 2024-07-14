package br.com.ms_pagamentos.model.records;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
public class ConsultaUsuarioResponse {
    private UUID clientId;
    private String cpf;
    private String nome;
    private String telefone;
    private String email;
    private String login;
}
