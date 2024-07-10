package br.com.ms_pagamentos.repository;

import br.com.ms_pagamentos.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IPagamentoRepository extends JpaRepository<Pagamento, UUID> {
}
