package com.rollerspeed.app.repository;

import com.rollerspeed.app.domain.entity.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    List<Asistencia> findByAlumnoId(Long alumnoId);

    List<Asistencia> findByClaseId(Long claseId);

    List<Asistencia> findByClaseIdAndFecha(Long claseId, LocalDate fecha);
}
