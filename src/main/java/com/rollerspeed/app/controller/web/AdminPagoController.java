package com.rollerspeed.app.controller.web;

import com.rollerspeed.app.domain.entity.Pago;
import com.rollerspeed.app.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/pagos")
@RequiredArgsConstructor
public class AdminPagoController {

    private final PagoService pagoService;

    @GetMapping
    public String listarPendientes(Model model) {
        List<Pago> pendientes = pagoService.pagosPendientes();
        model.addAttribute("pagos", pendientes);
        return "admin/pagos/lista";
    }
}
