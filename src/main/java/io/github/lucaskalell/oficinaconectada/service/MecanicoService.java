package io.github.lucaskalell.oficinaconectada.service;

import io.github.lucaskalell.oficinaconectada.entity.Mecanico;
import io.github.lucaskalell.oficinaconectada.repository.MecanicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MecanicoService {

    private final MecanicoRepository mecanicoRepository;

    public List<Mecanico> listarAtivos() {
        return mecanicoRepository.findAll().stream()
                .filter(Mecanico::isAtivo)
                .toList();
    }

    public List<Mecanico> listarTodos() {
        return mecanicoRepository.findAll();
    }

    public Mecanico buscarPorId(Long id) {
        return mecanicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mecânico não encontrado: " + id));
    }

    @Transactional
    public Mecanico criar(Mecanico mecanico) {
        return mecanicoRepository.save(mecanico);
    }

    @Transactional
    public Mecanico atualizar(Long id, Mecanico dados) {
        Mecanico mecanico = buscarPorId(id);
        mecanico.setNome(dados.getNome());
        mecanico.setEspecialidade(dados.getEspecialidade());
        mecanico.setTelefone(dados.getTelefone());
        mecanico.setAtivo(dados.isAtivo());
        return mecanicoRepository.save(mecanico);
    }

    @Transactional
    public void desativar(Long id) {
        Mecanico mecanico = buscarPorId(id);
        mecanico.setAtivo(false);
        mecanicoRepository.save(mecanico);
    }
}
