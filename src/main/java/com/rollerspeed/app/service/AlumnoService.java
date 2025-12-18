package com.rollerspeed.app.service;

import com.rollerspeed.app.domain.entity.Alumno;

import java.util.List;
import java.util.Optional;

public interface AlumnoService {

    Alumno crear(Alumno alumno);

    List<Alumno> listarTodos();

    Optional<Alumno> buscarPorId(Long id);

    Alumno actualizar(Long id, Alumno alumno);

    void eliminar(Long id);
}
