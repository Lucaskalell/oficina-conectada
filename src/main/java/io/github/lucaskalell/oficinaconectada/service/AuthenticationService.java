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

        var usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var tokenJwt = jwtService.generateToken(usuario);

        return LoginResponseDTO.builder()
                .token(tokenJwt)
                .build();
    }
}