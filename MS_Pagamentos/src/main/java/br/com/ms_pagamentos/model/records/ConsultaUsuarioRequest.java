package br.com.ms_pagamentos.model.records;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaUsuarioRequest {
    private UUID clientId;
}
