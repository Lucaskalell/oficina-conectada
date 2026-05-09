package io.github.lucaskalell.oficinaconectada.controllers;

import io.github.lucaskalell.oficinaconectada.dto.DashboardDTO;
import io.github.lucaskalell.oficinaconectada.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/resumo")
    public ResponseEntity<DashboardDTO> obterResumo() {
        return ResponseEntity.ok(dashboardService.obterResumo());
    }
}