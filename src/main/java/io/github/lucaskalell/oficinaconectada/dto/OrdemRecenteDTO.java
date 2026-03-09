package io.github.lucaskalell.oficinaconectada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrdemRecenteDTO {
    private String codigoOs;
    private String servico;
    private String clienteCarro;
    private String tempo;
    private String status;
}