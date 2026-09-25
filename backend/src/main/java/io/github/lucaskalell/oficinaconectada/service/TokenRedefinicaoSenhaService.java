package io.github.lucaskalell.oficinaconectada.service;

import io.github.lucaskalell.oficinaconectada.entity.TokenRedefinicaoSenha;
import io.github.lucaskalell.oficinaconectada.entity.Usuario;
import io.github.lucaskalell.oficinaconectada.repository.TokenRedefinicaoSenhaRepository;
import io.github.lucaskalell.oficinaconectada.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenRedefinicaoSenhaService {

    private final TokenRedefinicaoSenhaRepository tokenRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String gerarToken(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token = UUID.randomUUID().toString();

        TokenRedefinicaoSenha entidade = new TokenRedefinicaoSenha();
        entidade.setUsuario(usuario);
        entidade.setToken(token);
        entidade.setExpiresAt(LocalDateTime.now().plusHours(1));
        entidade.setUsed(false);

        tokenRepository.save(entidade);
        return token;
    }

    @Transactional
    public void redefinirSenha(String token, String novaSenha) {
        TokenRedefinicaoSenha entidade = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (entidade.isUsed()) {
            throw new RuntimeException("Token já utilizado");
        }

        if (entidade.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado");
        }

        Usuario usuario = entidade.getUsuario();
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);

        entidade.setUsed(true);
        tokenRepository.save(entidade);
    }
}
