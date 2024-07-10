package br.com.ms_pagamentos.repository;

import br.com.ms_pagamentos.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IItemRepository extends JpaRepository<Item, UUID> {
}
