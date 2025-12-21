package io.github.lucaskalell.oficinaconectada.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.lucaskalell.oficinaconectada.status.StatusOrdemDeServico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ordens_de_servico")
public class OrdemDeServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOrdemDeServico status;

    @Column(nullable = false)
    private String defeito;

    @Column
    private String descricaoServico;

    @Column(nullable = false)
    private LocalDateTime dataEntrada;

    @Column
    private LocalDateTime dataSaida;

    @Column
    private BigDecimal valorTotal;


    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnore
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "carro_id", nullable = false)
    @JsonIgnore
    private Carro carro;


}
