package io.github.lucaskalell.oficinaconectada.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueResumoDTO {
        private Long totalPecasCadastradas;
        private String itemMaisVendido;
        private Long itensBaixoEstoque;
        private List<CategoriaResumoDTO> categorias;
}
