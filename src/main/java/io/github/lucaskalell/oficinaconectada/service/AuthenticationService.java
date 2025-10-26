package io.github.lucaskalell.oficinaconectada.service;


import io.github.lucaskalell.oficinaconectada.dto.RegisterRequestDTO;
import io.github.lucaskalell.oficinaconectada.entity.Usuario;
import io.github.lucaskalell.oficinaconectada.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public Usuario register(RegisterRequestDTO request){
        Usuario usuario = Usuario.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(passwordEncoder.encode(request.getSenha()))
                .build();
        return usuarioRepository.save(usuario);
    }
}
