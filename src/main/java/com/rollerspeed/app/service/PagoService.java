package com.rollerspeed.app.service;

import com.rollerspeed.app.domain.entity.Pago;

import java.util.List;
import java.util.Optional;

public interface PagoService {

    Pago registrar(Pago pago);

    List<Pago> listarTodos();

    Optional<Pago> buscarPorId(Long id);

    Pago actualizar(Long id, Pago pago);

    void eliminar(Long id);

    List<Pago> pagosPorAlumno(Long alumnoId);

    List<Pago> pagosPendientes();
}
