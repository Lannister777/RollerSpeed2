package com.rollerspeed.app.service.impl;

import com.rollerspeed.app.domain.entity.Instructor;
import com.rollerspeed.app.repository.InstructorRepository;
import com.rollerspeed.app.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;

    @Override
    public Instructor crear(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Instructor> listarTodos() {
        return instructorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Instructor> buscarPorId(Long id) {
        return instructorRepository.findById(id);
    }

    @Override
    public Instructor actualizar(Long id, Instructor instructor) {
        instructor.setId(id);
        return instructorRepository.save(instructor);
    }

    @Override
    public void eliminar(Long id) {
        instructorRepository.deleteById(id);
    }
}
