package com.rollerspeed.app.controller.web;

import com.rollerspeed.app.repository.ContenidoInstitucionalRepository;
import com.rollerspeed.app.repository.EventoRepository;
import com.rollerspeed.app.repository.NoticiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class InstitutionalController {

    private final ContenidoInstitucionalRepository contenidoRepo;
    private final NoticiaRepository noticiaRepository;
    private final EventoRepository eventoRepository;

    @GetMapping("/institucional")
    public String institucional(Model model) {
        model.addAttribute("mision",
                contenidoRepo.findBySeccionOrderByIdAsc("MISION"));
        model.addAttribute("vision",
                contenidoRepo.findBySeccionOrderByIdAsc("VISION"));
        model.addAttribute("servicios",
                contenidoRepo.findBySeccionOrderByIdAsc("SERVICIOS"));
        return "institucional";
    }

    @GetMapping("/noticias")
    public String noticias(Model model) {
        model.addAttribute("noticias",
                noticiaRepository.findAllByOrderByFechaPublicacionDesc());
        return "noticias";
    }

    @GetMapping("/eventos")
    public String eventos(Model model) {
        model.addAttribute("eventos",
                eventoRepository.findAllByOrderByFechaEventoAsc());
        return "eventos";
    }
}
