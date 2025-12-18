package com.rollerspeed.app.controller.web;

import com.rollerspeed.app.domain.entity.Alumno;
import com.rollerspeed.app.domain.entity.Asistencia;
import com.rollerspeed.app.domain.entity.Clase;
import com.rollerspeed.app.domain.entity.Pago;
import com.rollerspeed.app.repository.AlumnoRepository;
import com.rollerspeed.app.service.AsistenciaService;
import com.rollerspeed.app.service.ClaseService;
import com.rollerspeed.app.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/alumno")
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoRepository alumnoRepository;
    private final ClaseService claseService;
    private final PagoService pagoService;
    private final AsistenciaService asistenciaService;

    @GetMapping
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : null;

        if (username == null) {
            return "alumno/dashboard";
        }

        Alumno alumno = alumnoRepository.findByUsuarioUsername(username)
                .orElse(null);
        if (alumno == null) {
            return "alumno/dashboard";
        }

        // Clases en las que participa este alumno
        List<Clase> clasesAlumno = claseService.listarTodas().stream()
                .filter(c -> c.getAlumnos() != null && c.getAlumnos().stream()
                        .anyMatch(a -> a.getId().equals(alumno.getId())))
                .collect(Collectors.toList());

        // Pagos del alumno (ordenados por id desc como aproximación a más recientes primero)
        List<Pago> pagosAlumno = pagoService.pagosPorAlumno(alumno.getId()).stream()
                .sorted(Comparator.comparing(Pago::getId).reversed())
                .collect(Collectors.toList());

        // Asistencias del alumno (ordenadas por fecha desc)
        List<Asistencia> asistenciasAlumno = asistenciaService.asistenciasPorAlumno(alumno.getId()).stream()
                .sorted(Comparator.comparing(Asistencia::getFecha).reversed())
                .collect(Collectors.toList());

        model.addAttribute("alumno", alumno);
        model.addAttribute("clases", clasesAlumno);
        model.addAttribute("pagos", pagosAlumno);
        model.addAttribute("asistencias", asistenciasAlumno);

        return "alumno/dashboard";
    }
}
