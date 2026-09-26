package io.github.lucaskalell.oficinaconectada.service;

import io.github.lucaskalell.oficinaconectada.dto.MecanicoRequestDTO;
import io.github.lucaskalell.oficinaconectada.dto.MecanicoResponseDTO;
import io.github.lucaskalell.oficinaconectada.entity.Mecanico;
import io.github.lucaskalell.oficinaconectada.entity.Usuario;
import io.github.lucaskalell.oficinaconectada.repository.MecanicoRepository;
import io.github.lucaskalell.oficinaconectada.repository.UsuarioRepository;
import io.github.lucaskalell.oficinaconectada.status.RoleUsuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MecanicoServiceTest {

    @Mock
    private MecanicoRepository mecanicoRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordEncoder codificadorSenha;
    @InjectMocks
    private MecanicoService mecanicoService;

    @Test
    void criarMecanicoCriaUsuarioComPerfilDeMecanico() {
        when(codificadorSenha.encode("senhaInicial")).thenReturn("hash");

        MecanicoResponseDTO resposta = mecanicoService.criar(new MecanicoRequestDTO(
                "Carlos", "Suspensão", "41999990000", "carlos@oficina.com", "senhaInicial"));

        ArgumentCaptor<Usuario> usuario = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(usuario.capture());
        assertThat(usuario.getValue().getRole()).isEqualTo(RoleUsuario.MECANICO);
        assertThat(usuario.getValue().getSenha()).isEqualTo("hash");
        assertThat(usuario.getValue().isPrimeiroAcesso()).isTrue();
        assertThat(resposta.isAtivo()).isTrue();
        assertThat(resposta.getEmail()).isEqualTo("carlos@oficina.com");
    }

    @Test
    void desativarMecanicoBloqueiaLoginDoUsuario() {
        Usuario usuario = Usuario.builder().email("carlos@oficina.com").build();
        Mecanico mecanico = new Mecanico();
        mecanico.setAtivo(true);
        mecanico.setUsuario(usuario);
        when(mecanicoRepository.findById(7L)).thenReturn(Optional.of(mecanico));

        mecanicoService.desativar(7L);

        assertThat(mecanico.isAtivo()).isFalse();
        assertThat(usuario.getDeletedAt()).isNotNull();
    }
}
