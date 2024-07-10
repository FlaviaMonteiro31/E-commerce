package br.com.ms_carrinho_compras.model;
import br.com.ms_carrinho_compras.model.records.ConsultaProdutoResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_item_carrinho")
public class Item {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "iditem", updatable = false, nullable = false)
    private UUID iditem;
    private UUID idproduto;
    private BigDecimal quantidade;
    @ManyToOne
    @JoinColumn(name = "idcarrinho")
    private Carrinho carrinho;
    private BigDecimal valoritem;

}
