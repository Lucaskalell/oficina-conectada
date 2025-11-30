package io.github.lucaskalell.oficinaconectada.service;

import io.github.lucaskalell.oficinaconectada.dto.VendaRequestDTO;
import io.github.lucaskalell.oficinaconectada.entity.ItemVenda;
import io.github.lucaskalell.oficinaconectada.entity.Produto;
import io.github.lucaskalell.oficinaconectada.entity.Venda;
import io.github.lucaskalell.oficinaconectada.repository.ProdutoRepository;
import io.github.lucaskalell.oficinaconectada.repository.VendaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;

    public List<Venda> listarTodasVendas() {
        return vendaRepository.findAll();
    }

    @Transactional
    public Venda criarVenda(VendaRequestDTO vendaRequest) {
        Venda novaVenda = new Venda();
        novaVenda.setDataVenda(LocalDateTime.now());

        List<ItemVenda> itensVenda = new ArrayList<>();
        for (var itemRequest : vendaRequest.getItens()) {
            Produto produto = produtoRepository.findById(itemRequest.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemRequest.getProdutoId()));

            if (produto.getQuantidadeEmEstoque() < itemRequest.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            // Diminui o estoque
            produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() - itemRequest.getQuantidade());
            produtoRepository.save(produto);

            ItemVenda itemVenda = new ItemVenda();
            itemVenda.setProduto(produto);
            itemVenda.setQuantidade(itemRequest.getQuantidade());
            itemVenda.setPrecoUnitario(produto.getPrecoVenda());
            itemVenda.setVenda(novaVenda);
            itensVenda.add(itemVenda);
        }

        novaVenda.setItens(itensVenda);
        return vendaRepository.save(novaVenda);
    }
}
