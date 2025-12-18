package com.rollerspeed.app.repository;

import com.rollerspeed.app.domain.entity.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

	Optional<Alumno> findByUsuarioUsername(String username);
}
