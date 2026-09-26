package io.github.lucaskalell.oficinaconectada.service;

import io.github.lucaskalell.oficinaconectada.entity.TokenRedefinicaoSenha;
import io.github.lucaskalell.oficinaconectada.entity.Usuario;
import io.github.lucaskalell.oficinaconectada.exception.TokenRedefinicaoInvalidoException;
import io.github.lucaskalell.oficinaconectada.repository.TokenRedefinicaoSenhaRepository;
import io.github.lucaskalell.oficinaconectada.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenRedefinicaoSenhaServiceTest {

    @Mock
    private TokenRedefinicaoSenhaRepository tokenRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private EnvioEmailService envioEmailService;
    @InjectMocks
    private TokenRedefinicaoSenhaService service;

    @Test
    void emailNaoCadastradoNaoGeraTokenNemRevelaErro() {
        when(usuarioRepository.findByEmail("naoexiste@oficina.com")).thenReturn(Optional.empty());

        service.solicitarRedefinicao("naoexiste@oficina.com");

        verify(tokenRepository, never()).save(any());
        verify(envioEmailService, never()).enviarTokenRedefinicaoSenha(anyString(), anyString());
    }

    @Test
    void tokenEnviadoPorEmailEDiferenteDoSalvoNoBanco() {
        Usuario usuario = Usuario.builder().email("mecanico@oficina.com").build();
        when(usuarioRepository.findByEmail("mecanico@oficina.com")).thenReturn(Optional.of(usuario));

        service.solicitarRedefinicao("mecanico@oficina.com");

        ArgumentCaptor<String> tokenEnviado = ArgumentCaptor.forClass(String.class);
        verify(envioEmailService).enviarTokenRedefinicaoSenha(eq("mecanico@oficina.com"), tokenEnviado.capture());
        ArgumentCaptor<TokenRedefinicaoSenha> tokenSalvo = ArgumentCaptor.forClass(TokenRedefinicaoSenha.class);
        verify(tokenRepository).save(tokenSalvo.capture());

        assertThat(tokenSalvo.getValue().getToken())
                .hasSize(64)
                .isNotEqualTo(tokenEnviado.getValue());
        assertThat(tokenSalvo.getValue().getExpiresAt()).isAfter(LocalDateTime.now().plusMinutes(59));
    }

    @Test
    void redefineSenhaEMarcaTokenComoUsado() {
        Usuario usuario = Usuario.builder().email("mecanico@oficina.com").build();
        TokenRedefinicaoSenha token = tokenValido(usuario);
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(token));
        when(passwordEncoder.encode("novaSenha123")).thenReturn("hash");

        service.redefinirSenha("codigo-recebido", "novaSenha123");

        assertThat(usuario.getSenha()).isEqualTo("hash");
        assertThat(token.isUsed()).isTrue();
    }

    @Test
    void recusaTokenJaUtilizado() {
        TokenRedefinicaoSenha token = tokenValido(new Usuario());
        token.setUsed(true);
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(token));

        assertThatThrownBy(() -> service.redefinirSenha("codigo", "novaSenha123"))
                .isInstanceOf(TokenRedefinicaoInvalidoException.class)
                .hasMessage("Código já utilizado");
    }

    @Test
    void recusaTokenExpirado() {
        TokenRedefinicaoSenha token = tokenValido(new Usuario());
        token.setExpiresAt(LocalDateTime.now().minusMinutes(1));
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(token));

        assertThatThrownBy(() -> service.redefinirSenha("codigo", "novaSenha123"))
                .isInstanceOf(TokenRedefinicaoInvalidoException.class)
                .hasMessage("Código expirado");
    }

    @Test
    void recusaTokenInexistente() {
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.redefinirSenha("codigo", "novaSenha123"))
                .isInstanceOf(TokenRedefinicaoInvalidoException.class)
                .hasMessage("Código inválido");
    }

    private TokenRedefinicaoSenha tokenValido(Usuario usuario) {
        TokenRedefinicaoSenha token = new TokenRedefinicaoSenha();
        token.setUsuario(usuario);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(30));
        token.setUsed(false);
        return token;
    }
}
