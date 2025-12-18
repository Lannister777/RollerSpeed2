package com.rollerspeed.app.service.impl;

import com.rollerspeed.app.domain.entity.Aspirante;
import com.rollerspeed.app.repository.AspiranteRepository;
import com.rollerspeed.app.service.AspiranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AspiranteServiceImpl implements AspiranteService {

    private final AspiranteRepository aspiranteRepository;

    @Override
    public Aspirante registrar(Aspirante aspirante) {
        return aspiranteRepository.save(aspirante);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Aspirante> listarTodos() {
        return aspiranteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Aspirante> buscarPorId(Long id) {
        return aspiranteRepository.findById(id);
    }

    @Override
    public Aspirante actualizar(Long id, Aspirante aspirante) {
        aspirante.setId(id);
        return aspiranteRepository.save(aspirante);
    }

    @Override
    public void eliminar(Long id) {
        aspiranteRepository.deleteById(id);
    }
}
