package io.github.lucaskalell.oficinaconectada.service;

import io.github.lucaskalell.oficinaconectada.dto.VendaRequestDTO;
import io.github.lucaskalell.oficinaconectada.entity.Cliente;
import io.github.lucaskalell.oficinaconectada.entity.ItemVenda;
import io.github.lucaskalell.oficinaconectada.entity.Produto;
import io.github.lucaskalell.oficinaconectada.entity.Usuario;
import io.github.lucaskalell.oficinaconectada.entity.Venda;
import io.github.lucaskalell.oficinaconectada.repository.ClienteRepository;
import io.github.lucaskalell.oficinaconectada.repository.ProdutoRepository;
import io.github.lucaskalell.oficinaconectada.repository.UsuarioRepository;
import io.github.lucaskalell.oficinaconectada.repository.VendaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Venda> listarTodasVendas() {
        return vendaRepository.findAll();
    }

    @Transactional
    public Venda criarVenda(VendaRequestDTO request) {
        Cliente cliente = null;
        if (request.getClienteId() != null) {
            cliente = clienteRepository.findById(request.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado: " + request.getClienteId()));
        }

        Usuario usuario = null;
        if (request.getUsuarioId() != null) {
            usuario = usuarioRepository.findById(request.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + request.getUsuarioId()));
        }

        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setUsuario(usuario);

        List<ItemVenda> itens = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (var itemDTO : request.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemDTO.getProdutoId()));

            if (produto.getQuantidadeEmEstoque() < itemDTO.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() - itemDTO.getQuantidade());
            produtoRepository.save(produto);

            ItemVenda item = new ItemVenda();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnitario(produto.getPrecoVenda());
            item.setVenda(venda);
            itens.add(item);

            total = total.add(produto.getPrecoVenda().multiply(BigDecimal.valueOf(itemDTO.getQuantidade())));
        }

        venda.setItens(itens);
        venda.setValorTotal(total);
        return vendaRepository.save(venda);
    }
}
