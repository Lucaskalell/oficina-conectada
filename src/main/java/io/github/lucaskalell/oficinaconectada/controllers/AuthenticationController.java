package io.github.lucaskalell.oficinaconectada.controllers;

import io.github.lucaskalell.oficinaconectada.dto.LoginRequestDTO;
import io.github.lucaskalell.oficinaconectada.dto.LoginResponseDTO;
import io.github.lucaskalell.oficinaconectada.dto.RegisterRequestDTO;
import io.github.lucaskalell.oficinaconectada.dto.UsuarioResponseDTO;
import io.github.lucaskalell.oficinaconectada.entity.Usuario;
import io.github.lucaskalell.oficinaconectada.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> register(
            @RequestBody RegisterRequestDTO request
    ) {
        Usuario usuarioRegistrado = authenticationService.register(request);

        UsuarioResponseDTO responseDto = UsuarioResponseDTO.builder()
                .id(usuarioRegistrado.getId())
                .nome(usuarioRegistrado.getNome())
                .email(usuarioRegistrado.getEmail())
                .build();

        return ResponseEntity.status(201).body(responseDto);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO request
    ) {
        LoginResponseDTO response = authenticationService.login(request);

        return ResponseEntity.ok(response);
    }
}
