package com.rollerspeed.app.repository;

import com.rollerspeed.app.domain.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

	Optional<Instructor> findByUsuarioUsername(String username);
}
