package io.github.lucaskalell.oficinaconectada.dto;

import io.github.lucaskalell.oficinaconectada.entity.Agendamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoResponseDTO {
    private Long id;
    private LocalDateTime dataHora;
    private String descricaoServico;
    private String status;
    private ClienteResumoDTO cliente;
    private CarroResumoDTO carro;
    private MecanicoResumoDTO mecanico;

    public static AgendamentoResponseDTO fromEntity(Agendamento agendamento) {
        return AgendamentoResponseDTO.builder()
                .id(agendamento.getId())
                .dataHora(agendamento.getDataHora())
                .descricaoServico(agendamento.getDescricaoServico())
                .status(agendamento.getStatus().name())
                .cliente(new ClienteResumoDTO(agendamento.getCliente().getId(), agendamento.getCliente().getNome()))
                .carro(new CarroResumoDTO(
                        agendamento.getCarro().getId(),
                        agendamento.getCarro().getModelo(),
                        agendamento.getCarro().getPlaca()
                ))
                .mecanico(agendamento.getMecanico() != null
                        ? new MecanicoResumoDTO(agendamento.getMecanico().getId(), agendamento.getMecanico().getNome())
                        : null)
                .build();
    }

    @Data
    @AllArgsConstructor
    public static class ClienteResumoDTO {
        private Long id;
        private String nome;
    }

    @Data
    @AllArgsConstructor
    public static class CarroResumoDTO {
        private Long id;
        private String modelo;
        private String placa;
    }

    @Data
    @AllArgsConstructor
    public static class MecanicoResumoDTO {
        private Long id;
        private String nome;
    }
}
