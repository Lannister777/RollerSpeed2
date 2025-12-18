package com.rollerspeed.app.service.impl;

import com.rollerspeed.app.domain.entity.Pago;
import com.rollerspeed.app.repository.PagoRepository;
import com.rollerspeed.app.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;

    @Override
    public Pago registrar(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pago> listarTodos() {
        return pagoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pago> buscarPorId(Long id) {
        return pagoRepository.findById(id);
    }

    @Override
    public Pago actualizar(Long id, Pago pago) {
        pago.setId(id);
        return pagoRepository.save(pago);
    }

    @Override
    public void eliminar(Long id) {
        pagoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pago> pagosPorAlumno(Long alumnoId) {
        return pagoRepository.findByAlumnoId(alumnoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pago> pagosPendientes() {
        return pagoRepository.findByEstado("PENDIENTE");
    }
}
