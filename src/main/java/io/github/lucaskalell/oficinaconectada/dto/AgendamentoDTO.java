package io.github.lucaskalell.oficinaconectada.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgendamentoDTO {
    private String horario;
    private String servico;
    private String clienteCarro;
    private String mecanico;
    private String status;
}
