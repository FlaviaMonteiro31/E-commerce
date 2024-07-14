package br.com.ms_pagamentos.repository;

import br.com.ms_pagamentos.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.UUID;

public interface IPagamentoRepository extends JpaRepository<Pagamento, UUID> {

    @Query("SELECT i FROM Pagamento i WHERE i.idpagamento = :id")
    Pagamento findPagamentoById(@Param("id") UUID id);
}
