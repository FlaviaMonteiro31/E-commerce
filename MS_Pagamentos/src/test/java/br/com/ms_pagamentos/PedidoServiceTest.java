//package br.com.ms_pagamentos;
//
//
//import br.com.ms_pagamentos.exception.PagamentoException;
//import br.com.ms_pagamentos.configuration.gateway.RemoverEstoqueGateway;
//import br.com.ms_pagamentos.model.Endereco;
//import br.com.ms_pagamentos.model.Item;
//import br.com.ms_pagamentos.model.Pedido;
//import br.com.ms_pagamentos.model.records.PedidoRequest;
//import br.com.ms_pagamentos.model.records.PedidoResponse;
//import br.com.ms_pagamentos.repository.IEnderecoRepository;
//import br.com.ms_pagamentos.repository.IItemRepository;
//import br.com.ms_pagamentos.repository.IPedidoRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.messaging.support.GenericMessage;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class PedidoServiceTest {
//    @Mock
//    private IEnderecoRepository enderecoRepository;
//
//    @Mock
//    private IPedidoRepository pedidoRepository;
//
//    @Mock
//    private IItemRepository itemRepository;
//
//    @Mock
//    private RemoverEstoqueGateway gateway;
//
//    @InjectMocks
//    private PedidoService pedidoService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void gerarPedidoTest() throws PagamentoException {
//        PedidoRequest request = new PedidoRequest();
//        // Preencha os detalhes do PedidoRequest conforme necessário para o teste
//
//        Endereco endereco = new Endereco();
//        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);
//
//        Pedido pedido = new Pedido();
//        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
//
//        List<Item> itens = request.getItens().stream().map(itemRequest -> {
//            Item item = new Item();
//            item.setIdproduto(itemRequest.getIdproduto());
//            item.setQuantidade(itemRequest.getQuantidade());
//            item.setPedido(pedido);
//            return item;
//        }).collect(Collectors.toList());
//
//        when(itemRepository.saveAll(anyList())).thenReturn(itens);
//
//        PedidoResponse response = pedidoService.gerarPedido(request);
//
//        assertNotNull(response);
//        verify(enderecoRepository, times(1)).save(any(Endereco.class));
//        verify(pedidoRepository, times(1)).save(any(Pedido.class));
//        verify(itemRepository, times(1)).saveAll(anyList());
//        verify(gateway, times(request.getItens().size())).removerEstoque(any(GenericMessage.class));
//    }
//}
//
