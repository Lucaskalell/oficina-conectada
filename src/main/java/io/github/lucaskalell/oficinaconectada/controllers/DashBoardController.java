package io.github.lucaskalell.oficinaconectada.controllers;

import io.github.lucaskalell.oficinaconectada.dto.DashBoardDTO;
import io.github.lucaskalell.oficinaconectada.service.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashBoardController {
    @Autowired
    private final DashBoardService dashBoardService;

    @GetMapping("/resumo")
    public ResponseEntity<DashBoardDTO> getResumo() {
        return ResponseEntity.ok(dashBoardService.obterResumo());
    }
}