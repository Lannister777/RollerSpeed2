package com.rollerspeed.app.service;

import com.rollerspeed.app.domain.entity.Clase;

import java.util.List;
import java.util.Optional;

public interface ClaseService {

    Clase crear(Clase clase);

    List<Clase> listarTodas();

    Optional<Clase> buscarPorId(Long id);

    Clase actualizar(Long id, Clase clase);

    void eliminar(Long id);
}
