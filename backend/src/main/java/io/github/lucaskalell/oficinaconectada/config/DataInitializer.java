package io.github.lucaskalell.oficinaconectada.config;

import io.github.lucaskalell.oficinaconectada.entity.Usuario;
import io.github.lucaskalell.oficinaconectada.repository.UsuarioRepository;
import io.github.lucaskalell.oficinaconectada.status.RoleUsuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
@Component
public class DataInitializer implements ApplicationRunner {

    private static final int BYTES_SENHA_GERADA = 12;

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final String emailAdmin;
    private final String senhaInicialAdmin;

    public DataInitializer(UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder,
                           @Value("${app.admin.email}") String emailAdmin,
                           @Value("${app.admin.senha-inicial}") String senhaInicialAdmin) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailAdmin = emailAdmin;
        this.senhaInicialAdmin = senhaInicialAdmin;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (usuarioRepository.findByEmail(emailAdmin).isPresent()) {
            return;
        }

        String senha = senhaInicialAdmin.isBlank() ? gerarSenhaAleatoria() : senhaInicialAdmin;

        usuarioRepository.save(Usuario.builder()
                .nome("Administrador")
                .email(emailAdmin)
                .senha(passwordEncoder.encode(senha))
                .role(RoleUsuario.ADMIN)
                .primeiroAcesso(true)
                .build());

        if (senhaInicialAdmin.isBlank()) {
            log.warn("Administrador {} criado com senha gerada: {} (troca obrigatória no primeiro acesso)", emailAdmin, senha);
        }
    }

    private String gerarSenhaAleatoria() {
        byte[] bytes = new byte[BYTES_SENHA_GERADA];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
