package io.github.lucaskalell.oficinaconectada.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fotos_ordem_de_servico")
public class FotoOrdemDeServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String caminhoFoto;

    private String legenda;

    @ManyToOne
    @JoinColumn(name = "ordem_de_servico_id", nullable = false)
    @JsonIgnore
    private OrdemDeServico ordemDeServico;
}