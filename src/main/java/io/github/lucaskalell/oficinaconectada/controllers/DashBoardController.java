package io.github.lucaskalell.oficinaconectada.controllers;

import io.github.lucaskalell.oficinaconectada.dto.DashBoardDTO;
import io.github.lucaskalell.oficinaconectada.dto.VendaSemanaDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashBoardController {


    @GetMapping("/resumo")
    public ResponseEntity<DashBoardDTO>getResumo(){
        DashBoardDTO dashBoardDTO = new DashBoardDTO();
        dashBoardDTO.setFaturamentoMensal(22000.000);
        dashBoardDTO.setTotalOrdensAbertas(10);
        dashBoardDTO.setProdutosBaixosEstoque(5);

        dashBoardDTO.setVendasSemana(Arrays.asList(
                new VendaSemanaDTO("Seg",1200.00),
                new VendaSemanaDTO("Ter",4000.00),
                new VendaSemanaDTO("Qua",1800.00),
                new VendaSemanaDTO("Qui",2000.00),
                new VendaSemanaDTO("Sex",2200.00),
                new VendaSemanaDTO("Sab",6000.00)

        ));
        Map<String, Integer> status = new HashMap<>();
        status.put("concluido",40);
        status.put("Em Andamento",15);
        status.put("Pendente",5);

        dashBoardDTO.setStatusOrdens(status);

        return ResponseEntity.ok(dashBoardDTO);
    }
}
