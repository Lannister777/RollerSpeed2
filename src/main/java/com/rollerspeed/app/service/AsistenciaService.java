package com.rollerspeed.app.service;

import com.rollerspeed.app.domain.entity.Asistencia;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AsistenciaService {

    Asistencia registrar(Asistencia asistencia);

    List<Asistencia> listarTodas();

    Optional<Asistencia> buscarPorId(Long id);

    Asistencia actualizar(Long id, Asistencia asistencia);

    void eliminar(Long id);

    List<Asistencia> asistenciasPorAlumno(Long alumnoId);

    List<Asistencia> asistenciasPorClase(Long claseId);

    List<Asistencia> asistenciasPorClaseYFecha(Long claseId, LocalDate fecha);
}
