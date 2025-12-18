package com.rollerspeed.app.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicController {

    @GetMapping({"/", "/inicio"})
    public String inicio() {
        return "index";
    }

    @GetMapping("/registro-aspirante")
    public String mostrarFormularioRegistroAspirante() {
        return "registro-aspirante";
    }
}
