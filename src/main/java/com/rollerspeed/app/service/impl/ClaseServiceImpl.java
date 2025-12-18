package com.rollerspeed.app.service.impl;

import com.rollerspeed.app.domain.entity.Clase;
import com.rollerspeed.app.repository.ClaseRepository;
import com.rollerspeed.app.service.ClaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClaseServiceImpl implements ClaseService {

    private final ClaseRepository claseRepository;

    @Override
    public Clase crear(Clase clase) {
        return claseRepository.save(clase);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Clase> listarTodas() {
        return claseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Clase> buscarPorId(Long id) {
        return claseRepository.findById(id);
    }

    @Override
    public Clase actualizar(Long id, Clase clase) {
        clase.setId(id);
        return claseRepository.save(clase);
    }

    @Override
    public void eliminar(Long id) {
        claseRepository.deleteById(id);
    }
}
