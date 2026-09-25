package io.github.lucaskalell.oficinaconectada.dto;

import io.github.lucaskalell.oficinaconectada.status.StatusOrdemDeServico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarroStatusDTO {
    private CarroDTO carro;
    private StatusOrdemDeServico status;
}
