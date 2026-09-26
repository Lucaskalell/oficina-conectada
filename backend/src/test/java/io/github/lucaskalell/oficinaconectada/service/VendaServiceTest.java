package io.github.lucaskalell.oficinaconectada.service;

import io.github.lucaskalell.oficinaconectada.dto.ItemVendaRequestDTO;
import io.github.lucaskalell.oficinaconectada.dto.VendaRequestDTO;
import io.github.lucaskalell.oficinaconectada.entity.Produto;
import io.github.lucaskalell.oficinaconectada.entity.Venda;
import io.github.lucaskalell.oficinaconectada.repository.ClienteRepository;
import io.github.lucaskalell.oficinaconectada.repository.ProdutoRepository;
import io.github.lucaskalell.oficinaconectada.repository.UsuarioRepository;
import io.github.lucaskalell.oficinaconectada.repository.VendaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VendaServiceTest {

    @Mock
    private VendaRepository vendaRepository;
    @Mock
    private ProdutoRepository produtoRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @InjectMocks
    private VendaService vendaService;

    @Test
    void vendaDaBaixaNoEstoqueECalculaTotal() {
        Produto oleo = produto(1L, "Óleo 5W30", 10, "45.90");
        Produto filtro = produto(2L, "Filtro de óleo", 4, "32.50");
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(oleo));
        when(produtoRepository.findById(2L)).thenReturn(Optional.of(filtro));
        when(vendaRepository.save(any(Venda.class))).thenAnswer(invocacao -> invocacao.getArgument(0));

        Venda venda = vendaService.criarVenda(new VendaRequestDTO(null, null, List.of(
                new ItemVendaRequestDTO(1L, 4),
                new ItemVendaRequestDTO(2L, 1))));

        assertThat(oleo.getQuantidadeEmEstoque()).isEqualTo(6);
        assertThat(filtro.getQuantidadeEmEstoque()).isEqualTo(3);
        assertThat(venda.getValorTotal()).isEqualByComparingTo("216.10");
        assertThat(venda.getItens()).hasSize(2);
    }

    @Test
    void recusaVendaComEstoqueInsuficiente() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto(1L, "Pastilha de freio", 2, "120.00")));

        assertThatThrownBy(() -> vendaService.criarVenda(new VendaRequestDTO(null, null, List.of(
                new ItemVendaRequestDTO(1L, 3)))))
                .hasMessageContaining("Estoque insuficiente");

        verify(vendaRepository, never()).save(any());
    }

    private Produto produto(Long id, String nome, int quantidade, String precoVenda) {
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome(nome);
        produto.setQuantidadeEmEstoque(quantidade);
        produto.setPrecoVenda(new BigDecimal(precoVenda));
        return produto;
    }
}
