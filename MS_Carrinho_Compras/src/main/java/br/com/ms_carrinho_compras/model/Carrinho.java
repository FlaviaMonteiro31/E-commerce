package br.com.ms_carrinho_compras.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_carrinho")
public class Carrinho {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "idcarrinho", updatable = false, nullable = false)
    private UUID idcarrinho;
    private UUID usuario;
    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL)
    private List<Item> itens;
    private BigDecimal valorCarrinho;
}
