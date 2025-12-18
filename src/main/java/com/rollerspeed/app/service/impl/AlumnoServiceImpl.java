package com.rollerspeed.app.service.impl;

import com.rollerspeed.app.domain.entity.Alumno;
import com.rollerspeed.app.repository.AlumnoRepository;
import com.rollerspeed.app.service.AlumnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;

    @Override
    public Alumno crear(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alumno> listarTodos() {
        return alumnoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Alumno> buscarPorId(Long id) {
        return alumnoRepository.findById(id);
    }

    @Override
    public Alumno actualizar(Long id, Alumno alumno) {
        alumno.setId(id);
        return alumnoRepository.save(alumno);
    }

    @Override
    public void eliminar(Long id) {
        alumnoRepository.deleteById(id);
    }
}
