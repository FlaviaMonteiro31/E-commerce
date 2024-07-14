package br.com.ms_carrinho_compras.repository;

import br.com.ms_carrinho_compras.model.Carrinho;
import br.com.ms_carrinho_compras.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public interface ICarrinhoRepository extends JpaRepository<Carrinho, UUID> {

    Carrinho findByUsuario(UUID usuario);

    @Query("SELECT i.valorCarrinho FROM Carrinho i WHERE i.idcarrinho = :id")
    BigDecimal findValorCarrinhoById(@Param("id") UUID id);

    @Query("SELECT i FROM Carrinho i WHERE i.idcarrinho = :id")
    Carrinho findCarrinhoById(@Param("id") UUID id);

}
