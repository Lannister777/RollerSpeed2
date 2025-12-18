package com.rollerspeed.app.controller.api;

import com.rollerspeed.app.domain.entity.Pago;
import com.rollerspeed.app.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
@Tag(name = "Pagos", description = "Gestión de pagos de alumnos")
public class PagoRestController {

    private final PagoService pagoService;

    @Operation(summary = "Registrar pago")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago registrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pago.class)))
    })
    @PostMapping
    public ResponseEntity<Pago> registrar(@Valid @RequestBody Pago pago) {
        Pago creado = pagoService.registrar(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Listar pagos")
    @GetMapping
    public ResponseEntity<List<Pago>> listarTodos() {
        return ResponseEntity.ok(pagoService.listarTodos());
    }

    @Operation(summary = "Obtener pago por id")
    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtenerPorId(@PathVariable Long id) {
        return pagoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar pago")
    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizar(@PathVariable Long id, @Valid @RequestBody Pago pago) {
        Pago actualizado = pagoService.actualizar(id, pago);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar pago")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Pagos por alumno")
    @GetMapping("/alumno/{alumnoId}")
    public ResponseEntity<List<Pago>> pagosPorAlumno(@PathVariable Long alumnoId) {
        return ResponseEntity.ok(pagoService.pagosPorAlumno(alumnoId));
    }

    @Operation(summary = "Pagos pendientes")
    @GetMapping("/pendientes")
    public ResponseEntity<List<Pago>> pagosPendientes() {
        return ResponseEntity.ok(pagoService.pagosPendientes());
    }
}
