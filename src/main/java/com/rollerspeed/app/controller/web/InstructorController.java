package com.rollerspeed.app.controller.web;

import com.rollerspeed.app.domain.entity.Clase;
import com.rollerspeed.app.domain.entity.Instructor;
import com.rollerspeed.app.domain.entity.Asistencia;
import com.rollerspeed.app.repository.InstructorRepository;
import com.rollerspeed.app.service.AsistenciaService;
import com.rollerspeed.app.service.ClaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorRepository instructorRepository;
    private final ClaseService claseService;
    private final AsistenciaService asistenciaService;

    @GetMapping
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : null;

        if (username == null) {
            return "instructor/dashboard";
        }

        Instructor instructor = instructorRepository.findByUsuarioUsername(username)
                .orElse(null);
        if (instructor == null) {
            return "instructor/dashboard";
        }

        // Filtrar las clases asignadas a este instructor
        List<Clase> clasesInstructor = claseService.listarTodas().stream()
                .filter(c -> c.getInstructor() != null && instructor.getId().equals(c.getInstructor().getId()))
                .collect(Collectors.toList());

        // Obtener asistencias recientes de las clases del instructor (máx 10)
        List<Asistencia> asistenciasRecientes = new ArrayList<>();
        for (Clase clase : clasesInstructor) {
            asistenciasRecientes.addAll(asistenciaService.asistenciasPorClase(clase.getId()));
        }
        asistenciasRecientes = asistenciasRecientes.stream()
                .sorted(Comparator.comparing(Asistencia::getFecha).reversed())
                .limit(10)
                .collect(Collectors.toList());

        model.addAttribute("instructor", instructor);
        model.addAttribute("clases", clasesInstructor);
        model.addAttribute("asistenciasRecientes", asistenciasRecientes);

        return "instructor/dashboard";
    }
}
