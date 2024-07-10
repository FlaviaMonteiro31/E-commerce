package br.com.ms_pagamentos.model;

import br.com.ms_pagamentos.enums.EstadosEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID enderecoId;
    private String logradouro;
    private String numero;
    private String cep;
    private String complemento;
    private String cidade;
    private EstadosEnum estado;

}
