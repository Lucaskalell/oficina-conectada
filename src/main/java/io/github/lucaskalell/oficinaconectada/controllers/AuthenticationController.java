package io.github.lucaskalell.oficinaconectada.controllers;

import io.github.lucaskalell.oficinaconectada.dto.LoginRequestDTO;
import io.github.lucaskalell.oficinaconectada.dto.LoginResponseDTO;
import io.github.lucaskalell.oficinaconectada.dto.RegisterRequestDTO;
import io.github.lucaskalell.oficinaconectada.dto.UsuarioResponseDTO;
import io.github.lucaskalell.oficinaconectada.entity.Usuario;
import io.github.lucaskalell.oficinaconectada.service.AuthenticationService;
import io.github.lucaskalell.oficinaconectada.service.TokenRedefinicaoSenhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final TokenRedefinicaoSenhaService tokenRedefinicaoSenhaService;

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioResponseDTO> registrar(@RequestBody RegisterRequestDTO requisicao) {
        Usuario usuarioRegistrado = authenticationService.registrar(requisicao);

        UsuarioResponseDTO respostaDto = UsuarioResponseDTO.builder()
                .id(usuarioRegistrado.getId())
                .nome(usuarioRegistrado.getNome())
                .email(usuarioRegistrado.getEmail())
                .role(usuarioRegistrado.getRole())
                .build();

        return ResponseEntity.status(201).body(respostaDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO requisicao) {
        LoginResponseDTO resposta = authenticationService.login(requisicao);
        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/solicitar-redefinicao")
    public ResponseEntity<Void> solicitarRedefinicao(@RequestBody Map<String, String> body) {
        tokenRedefinicaoSenhaService.gerarToken(body.get("email"));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<Void> redefinirSenha(@RequestBody Map<String, String> body) {
        tokenRedefinicaoSenhaService.redefinirSenha(body.get("token"), body.get("novaSenha"));
        return ResponseEntity.noContent().build();
    }
}