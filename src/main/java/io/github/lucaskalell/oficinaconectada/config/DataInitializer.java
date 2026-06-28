package io.github.lucaskalell.oficinaconectada.config;

import io.github.lucaskalell.oficinaconectada.entity.Usuario;
import io.github.lucaskalell.oficinaconectada.repository.UsuarioRepository;
import io.github.lucaskalell.oficinaconectada.status.RoleUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (usuarioRepository.findByEmail("admin@oficina.com").isEmpty()) {
            Usuario admin = Usuario.builder()
                    .nome("Administrador")
                    .email("admin@oficina.com")
                    .senha(passwordEncoder.encode("pass"))
                    .role(RoleUsuario.ADMIN)
                    .primeiroAcesso(true)
                    .build();
            usuarioRepository.save(admin);
        }
    }
}
