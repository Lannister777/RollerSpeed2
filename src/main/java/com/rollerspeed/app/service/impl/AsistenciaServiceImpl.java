package com.rollerspeed.app.service.impl;

import com.rollerspeed.app.domain.entity.Asistencia;
import com.rollerspeed.app.repository.AsistenciaRepository;
import com.rollerspeed.app.service.AsistenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AsistenciaServiceImpl implements AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;

    @Override
    public Asistencia registrar(Asistencia asistencia) {
        return asistenciaRepository.save(asistencia);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asistencia> listarTodas() {
        return asistenciaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Asistencia> buscarPorId(Long id) {
        return asistenciaRepository.findById(id);
    }

    @Override
    public Asistencia actualizar(Long id, Asistencia asistencia) {
        asistencia.setId(id);
        return asistenciaRepository.save(asistencia);
    }

    @Override
    public void eliminar(Long id) {
        asistenciaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asistencia> asistenciasPorAlumno(Long alumnoId) {
        return asistenciaRepository.findByAlumnoId(alumnoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asistencia> asistenciasPorClase(Long claseId) {
        return asistenciaRepository.findByClaseId(claseId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asistencia> asistenciasPorClaseYFecha(Long claseId, LocalDate fecha) {
        return asistenciaRepository.findByClaseIdAndFecha(claseId, fecha);
    }
}
