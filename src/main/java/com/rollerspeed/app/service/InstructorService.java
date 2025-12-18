package com.rollerspeed.app.service;

import com.rollerspeed.app.domain.entity.Instructor;

import java.util.List;
import java.util.Optional;

public interface InstructorService {

    Instructor crear(Instructor instructor);

    List<Instructor> listarTodos();

    Optional<Instructor> buscarPorId(Long id);

    Instructor actualizar(Long id, Instructor instructor);

    void eliminar(Long id);
}
