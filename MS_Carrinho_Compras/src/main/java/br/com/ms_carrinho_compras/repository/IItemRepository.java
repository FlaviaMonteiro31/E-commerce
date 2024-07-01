package br.com.ms_carrinho_compras.repository;

import br.com.ms_carrinho_compras.model.Carrinho;
import br.com.ms_carrinho_compras.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface IItemRepository extends JpaRepository<Item, UUID> {

    void deleteByCarrinho(Carrinho carrinho);
    void deleteByIditem(UUID id);
}
