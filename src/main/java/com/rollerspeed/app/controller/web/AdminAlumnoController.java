package com.rollerspeed.app.controller.web;

import com.rollerspeed.app.domain.entity.Alumno;
import com.rollerspeed.app.service.AlumnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/alumnos")
@RequiredArgsConstructor
public class AdminAlumnoController {

    private final AlumnoService alumnoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("alumnos", alumnoService.listarTodos());
        return "admin/alumnos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("alumno", new Alumno());
        return "admin/alumnos/form";
    }

    @PostMapping
    public String crear(@ModelAttribute("alumno") Alumno alumno,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/alumnos/form";
        }
        alumnoService.crear(alumno);
        return "redirect:/admin/alumnos";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Optional<Alumno> alumnoOpt = alumnoService.buscarPorId(id);
        if (alumnoOpt.isEmpty()) {
            return "redirect:/admin/alumnos";
        }
        model.addAttribute("alumno", alumnoOpt.get());
        return "admin/alumnos/form";
    }

    @PostMapping("/{id}/editar")
    public String actualizar(@PathVariable Long id,
                             @ModelAttribute("alumno") Alumno alumno,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/alumnos/form";
        }
        alumnoService.actualizar(id, alumno);
        return "redirect:/admin/alumnos";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        alumnoService.eliminar(id);
        return "redirect:/admin/alumnos";
    }
}
