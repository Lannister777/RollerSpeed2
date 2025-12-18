package com.rollerspeed.app.repository;

import com.rollerspeed.app.domain.entity.ContenidoInstitucional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContenidoInstitucionalRepository extends JpaRepository<ContenidoInstitucional, Long> {

    List<ContenidoInstitucional> findBySeccionOrderByIdAsc(String seccion);
}
