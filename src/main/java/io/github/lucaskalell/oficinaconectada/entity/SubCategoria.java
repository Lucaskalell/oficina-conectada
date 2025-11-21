package io.github.lucaskalell.oficinaconectada.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subcategorias")
public class SubCategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    @JsonIgnore
    private Categoria categoria;

    @OneToMany(mappedBy = "subCategoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("subCategoria")
    private List<Produto> produtos;
}
