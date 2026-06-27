package io.github.lucaskalell.oficinaconectada.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fotos_ordem_de_servico")
public class FotoOrdemDeServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String caminhoFoto;

    @Column(length = 255)
    private String legenda;

    @ManyToOne
    @JoinColumn(name = "ordem_de_servico_id", nullable = false)
    @JsonIgnore
    private OrdemDeServico ordemDeServico;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
