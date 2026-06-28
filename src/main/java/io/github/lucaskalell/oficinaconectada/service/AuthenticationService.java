package io.github.lucaskalell.oficinaconectada.service;

import io.github.lucaskalell.oficinaconectada.dto.LoginRequestDTO;
import io.github.lucaskalell.oficinaconectada.dto.LoginResponseDTO;
import io.github.lucaskalell.oficinaconectada.dto.RegisterRequestDTO;
import io.github.lucaskalell.oficinaconectada.entity.Usuario;
import io.github.lucaskalell.oficinaconectada.repository.UsuarioRepository;
import io.github.lucaskalell.oficinaconectada.security.JwtService;
import io.github.lucaskalell.oficinaconectada.status.RoleUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder codificadorSenha;
    private final JwtService jwtService;
    private final AuthenticationManager gerenciadorAutenticacao;

    public Usuario registrar(RegisterRequestDTO request) {
        Usuario usuario = Usuario.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(codificadorSenha.encode(request.getSenha()))
                .role(request.getRole() != null ? request.getRole() : RoleUsuario.ATENDENTE)
                .primeiroAcesso(true)
                .build();
        return usuarioRepository.save(usuario);
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        gerenciadorAutenticacao.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getSenha()
                )
        );

        var usuario = usuarioRepository.findByEmail(request.getEmail()).orElseThrow();
        var token = jwtService.generateToken(usuario);

        return LoginResponseDTO.builder()
                .token(token)
                .primeiroAcesso(usuario.isPrimeiroAcesso())
                .role(usuario.getRole().name())
                .build();
    }

    public void alterarSenha(String email, String novaSenha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setSenha(codificadorSenha.encode(novaSenha));
        usuario.setPrimeiroAcesso(false);
        usuarioRepository.save(usuario);
    }
}
