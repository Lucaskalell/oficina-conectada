package io.github.lucaskalell.oficinaconectada.service;

import io.github.lucaskalell.oficinaconectada.dto.MecanicoRequestDTO;
import io.github.lucaskalell.oficinaconectada.dto.MecanicoResponseDTO;
import io.github.lucaskalell.oficinaconectada.dto.MecanicoUpdateDTO;
import io.github.lucaskalell.oficinaconectada.entity.Mecanico;
import io.github.lucaskalell.oficinaconectada.entity.Usuario;
import io.github.lucaskalell.oficinaconectada.repository.MecanicoRepository;
import io.github.lucaskalell.oficinaconectada.repository.UsuarioRepository;
import io.github.lucaskalell.oficinaconectada.status.RoleUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MecanicoService {

    private final MecanicoRepository mecanicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder codificadorSenha;

    public List<MecanicoResponseDTO> listarAtivos() {
        return mecanicoRepository.findByAtivoTrue().stream()
                .map(this::paraResponseDTO)
                .toList();
    }

    public List<MecanicoResponseDTO> listarTodos() {
        return mecanicoRepository.findAll().stream()
                .map(this::paraResponseDTO)
                .toList();
    }

    public MecanicoResponseDTO buscarPorId(Long id) {
        return paraResponseDTO(buscarEntidadePorId(id));
    }

    @Transactional
    public MecanicoResponseDTO criar(MecanicoRequestDTO dados) {
        Usuario usuario = Usuario.builder()
                .nome(dados.getNome())
                .email(dados.getEmail())
                .senha(codificadorSenha.encode(dados.getSenha()))
                .role(RoleUsuario.MECANICO)
                .primeiroAcesso(true)
                .build();
        usuarioRepository.save(usuario);

        Mecanico mecanico = new Mecanico();
        mecanico.setNome(dados.getNome());
        mecanico.setEspecialidade(dados.getEspecialidade());
        mecanico.setTelefone(dados.getTelefone());
        mecanico.setAtivo(true);
        mecanico.setUsuario(usuario);
        mecanicoRepository.save(mecanico);

        return paraResponseDTO(mecanico);
    }

    @Transactional
    public MecanicoResponseDTO atualizar(Long id, MecanicoUpdateDTO dados) {
        Mecanico mecanico = buscarEntidadePorId(id);
        mecanico.setNome(dados.getNome());
        mecanico.setEspecialidade(dados.getEspecialidade());
        mecanico.setTelefone(dados.getTelefone());
        mecanico.setAtivo(dados.isAtivo());
        sincronizarAcessoUsuario(mecanico);
        mecanicoRepository.save(mecanico);
        return paraResponseDTO(mecanico);
    }

    @Transactional
    public void desativar(Long id) {
        Mecanico mecanico = buscarEntidadePorId(id);
        mecanico.setAtivo(false);
        sincronizarAcessoUsuario(mecanico);
        mecanicoRepository.save(mecanico);
    }

    private void sincronizarAcessoUsuario(Mecanico mecanico) {
        Usuario usuario = mecanico.getUsuario();
        usuario.setDeletedAt(mecanico.isAtivo() ? null : LocalDateTime.now());
        usuarioRepository.save(usuario);
    }

    private Mecanico buscarEntidadePorId(Long id) {
        return mecanicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mecânico não encontrado: " + id));
    }

    private MecanicoResponseDTO paraResponseDTO(Mecanico mecanico) {
        return MecanicoResponseDTO.builder()
                .id(mecanico.getId())
                .nome(mecanico.getNome())
                .especialidade(mecanico.getEspecialidade())
                .telefone(mecanico.getTelefone())
                .ativo(mecanico.isAtivo())
                .email(mecanico.getUsuario().getEmail())
                .createdAt(mecanico.getCreatedAt())
                .updatedAt(mecanico.getUpdatedAt())
                .build();
    }
}
