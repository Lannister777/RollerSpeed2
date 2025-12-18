package com.rollerspeed.app.controller.web;

import com.rollerspeed.app.domain.entity.Aspirante;
import com.rollerspeed.app.service.AspiranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/aspirantes")
@RequiredArgsConstructor
public class AdminAspiranteController {

    private final AspiranteService aspiranteService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("aspirantes", aspiranteService.listarTodos());
        return "admin/aspirantes/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("aspirante", new Aspirante());
        return "admin/aspirantes/form";
    }

    @PostMapping
    public String crear(@ModelAttribute("aspirante") Aspirante aspirante,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/aspirantes/form";
        }
        aspiranteService.registrar(aspirante);
        return "redirect:/admin/aspirantes";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Optional<Aspirante> aspiranteOpt = aspiranteService.buscarPorId(id);
        if (aspiranteOpt.isEmpty()) {
            return "redirect:/admin/aspirantes";
        }
        model.addAttribute("aspirante", aspiranteOpt.get());
        return "admin/aspirantes/form";
    }

    @PostMapping("/{id}/editar")
    public String actualizar(@PathVariable Long id,
                             @ModelAttribute("aspirante") Aspirante aspirante,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/aspirantes/form";
        }
        aspiranteService.actualizar(id, aspirante);
        return "redirect:/admin/aspirantes";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        aspiranteService.eliminar(id);
        return "redirect:/admin/aspirantes";
    }
}
