package io.github.lucaskalell.oficinaconectada.config;

import io.github.lucaskalell.oficinaconectada.entity.Usuario;
import io.github.lucaskalell.oficinaconectada.repository.UsuarioRepository;
import io.github.lucaskalell.oficinaconectada.status.RoleUsuario;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DataInitializerTest {

    private final UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void usaSenhaInicialConfiguradaQuandoInformada() {
        when(usuarioRepository.findByEmail("admin@oficina.com")).thenReturn(Optional.empty());

        new DataInitializer(usuarioRepository, passwordEncoder, "admin@oficina.com", "senhaDoAmbiente").run(null);

        Usuario admin = adminSalvo();
        assertThat(admin.getRole()).isEqualTo(RoleUsuario.ADMIN);
        assertThat(admin.isPrimeiroAcesso()).isTrue();
        assertThat(passwordEncoder.matches("senhaDoAmbiente", admin.getSenha())).isTrue();
    }

    @Test
    void geraSenhaAleatoriaQuandoNaoConfigurada() {
        when(usuarioRepository.findByEmail("admin@oficina.com")).thenReturn(Optional.empty());

        new DataInitializer(usuarioRepository, passwordEncoder, "admin@oficina.com", "").run(null);

        assertThat(adminSalvo().getSenha()).isNotBlank();
    }

    @Test
    void naoRecriaAdminExistente() {
        when(usuarioRepository.findByEmail("admin@oficina.com")).thenReturn(Optional.of(new Usuario()));

        new DataInitializer(usuarioRepository, passwordEncoder, "admin@oficina.com", "").run(null);

        verify(usuarioRepository, never()).save(any());
    }

    private Usuario adminSalvo() {
        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());
        return captor.getValue();
    }
}
