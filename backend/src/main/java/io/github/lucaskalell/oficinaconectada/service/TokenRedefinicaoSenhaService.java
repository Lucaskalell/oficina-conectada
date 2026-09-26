package io.github.lucaskalell.oficinaconectada.service;

import io.github.lucaskalell.oficinaconectada.entity.TokenRedefinicaoSenha;
import io.github.lucaskalell.oficinaconectada.entity.Usuario;
import io.github.lucaskalell.oficinaconectada.exception.TokenRedefinicaoInvalidoException;
import io.github.lucaskalell.oficinaconectada.repository.TokenRedefinicaoSenhaRepository;
import io.github.lucaskalell.oficinaconectada.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenRedefinicaoSenhaService {

    private static final long HORAS_VALIDADE_TOKEN = 1;

    private final TokenRedefinicaoSenhaRepository tokenRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EnvioEmailService envioEmailService;

    @Transactional
    public void solicitarRedefinicao(String email) {
        usuarioRepository.findByEmail(email).ifPresent(usuario -> {
            String token = UUID.randomUUID().toString();

            TokenRedefinicaoSenha entidade = new TokenRedefinicaoSenha();
            entidade.setUsuario(usuario);
            entidade.setToken(gerarHash(token));
            entidade.setExpiresAt(LocalDateTime.now().plusHours(HORAS_VALIDADE_TOKEN));
            entidade.setUsed(false);
            tokenRepository.save(entidade);

            envioEmailService.enviarTokenRedefinicaoSenha(usuario.getEmail(), token);
        });
    }

    @Transactional
    public void redefinirSenha(String token, String novaSenha) {
        TokenRedefinicaoSenha entidade = tokenRepository.findByToken(gerarHash(token))
                .orElseThrow(() -> new TokenRedefinicaoInvalidoException("Código inválido"));

        if (entidade.isUsed()) {
            throw new TokenRedefinicaoInvalidoException("Código já utilizado");
        }

        if (entidade.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new TokenRedefinicaoInvalidoException("Código expirado");
        }

        Usuario usuario = entidade.getUsuario();
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);

        entidade.setUsed(true);
        tokenRepository.save(entidade);
    }

    private String gerarHash(String token) {
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256").digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
