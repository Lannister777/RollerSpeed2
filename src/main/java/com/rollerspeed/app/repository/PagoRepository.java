package com.rollerspeed.app.repository;

import com.rollerspeed.app.domain.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByAlumnoId(Long alumnoId);

    List<Pago> findByEstado(String estado);
}
