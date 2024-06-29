package br.com.ms_carrinho_compras;


import br.com.ms_carrinho_compras.exception.CarrinhoException;
import br.com.ms_carrinho_compras.gateway.RemoverEstoqueGateway;
import br.com.ms_carrinho_compras.model.Carrinho;
import br.com.ms_carrinho_compras.model.Item;
import br.com.ms_carrinho_compras.model.records.CarrinhoRequest;
import br.com.ms_carrinho_compras.model.records.CarrinhoResponse;
import br.com.ms_carrinho_compras.repository.IItemRepository;
import br.com.ms_carrinho_compras.repository.ICarrinhoRepository;
import br.com.ms_carrinho_compras.service.CarrinhoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.support.GenericMessage;

import java.util.List;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CarrinhoServiceTest {
    @Mock
    private ICarrinhoRepository pedidoRepository;

    @Mock
    private IItemRepository itemRepository;

    @Mock
    private RemoverEstoqueGateway gateway;

    @InjectMocks
    private CarrinhoService pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void gerarPedidoTest() throws CarrinhoException {
        CarrinhoRequest request = new CarrinhoRequest();
        // Preencha os detalhes do PedidoRequest conforme necessário para o teste

        Carrinho carrinho = new Carrinho();
        when(pedidoRepository.save(any(Carrinho.class))).thenReturn(carrinho);

        List<Item> itens = request.getItens().stream().map(itemRequest -> {
            Item item = new Item();
            item.setIdproduto(itemRequest.getIdproduto());
            item.setQuantidade(itemRequest.getQuantidade());
            //item.setPedido(pedido);
            return item;
        }).collect(Collectors.toList());

        when(itemRepository.saveAll(anyList())).thenReturn(itens);

        CarrinhoResponse response = pedidoService.gerarCarrinho(request);

        assertNotNull(response);
        //verify(enderecoRepository, times(1)).save(any(Endereco.class));
        verify(pedidoRepository, times(1)).save(any(Carrinho.class));
        verify(itemRepository, times(1)).saveAll(anyList());
        verify(gateway, times(request.getItens().size())).removerEstoque(any(GenericMessage.class));
    }
}

