package com.rollerspeed.app.service;

import com.rollerspeed.app.domain.entity.Aspirante;

import java.util.List;
import java.util.Optional;

public interface AspiranteService {

    Aspirante registrar(Aspirante aspirante);

    List<Aspirante> listarTodos();

    Optional<Aspirante> buscarPorId(Long id);

    Aspirante actualizar(Long id, Aspirante aspirante);

    void eliminar(Long id);
}
