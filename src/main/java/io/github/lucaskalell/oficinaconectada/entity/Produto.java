package io.github.lucaskalell.oficinaconectada.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    private int quantidadeEmEstoque;

    @Column(nullable = false)
    private int quantidadeMinima;

    @Column(nullable = false)
    private BigDecimal precoCusto;

    @Column(nullable = false)
    private BigDecimal precoVenda;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategoria_id", nullable = false)
    @JsonIgnore
    private SubCategoria subCategoria;
}
