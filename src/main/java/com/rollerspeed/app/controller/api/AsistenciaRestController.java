package com.rollerspeed.app.controller.api;

import com.rollerspeed.app.domain.entity.Asistencia;
import com.rollerspeed.app.service.AsistenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
@RequiredArgsConstructor
@Tag(name = "Asistencias", description = "Registro y consulta de asistencias por clase y alumno")
public class AsistenciaRestController {

    private final AsistenciaService asistenciaService;

    @Operation(summary = "Registrar asistencia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Asistencia registrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Asistencia.class)))
    })
    @PostMapping
    public ResponseEntity<Asistencia> registrar(@Valid @RequestBody Asistencia asistencia) {
        Asistencia creada = asistenciaService.registrar(asistencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @Operation(summary = "Listar asistencias")
    @GetMapping
    public ResponseEntity<List<Asistencia>> listarTodas() {
        return ResponseEntity.ok(asistenciaService.listarTodas());
    }

    @Operation(summary = "Asistencias por alumno")
    @GetMapping("/alumno/{alumnoId}")
    public ResponseEntity<List<Asistencia>> asistenciasPorAlumno(@PathVariable Long alumnoId) {
        return ResponseEntity.ok(asistenciaService.asistenciasPorAlumno(alumnoId));
    }

    @Operation(summary = "Asistencias por clase")
    @GetMapping("/clase/{claseId}")
    public ResponseEntity<List<Asistencia>> asistenciasPorClase(@PathVariable Long claseId) {
        return ResponseEntity.ok(asistenciaService.asistenciasPorClase(claseId));
    }

    @Operation(summary = "Asistencias por clase y fecha")
    @GetMapping("/clase/{claseId}/fecha/{fecha}")
    public ResponseEntity<List<Asistencia>> asistenciasPorClaseYFecha(
            @PathVariable Long claseId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(asistenciaService.asistenciasPorClaseYFecha(claseId, fecha));
    }

    @Operation(summary = "Obtener asistencia por id")
    @GetMapping("/{id}")
    public ResponseEntity<Asistencia> obtenerPorId(@PathVariable Long id) {
        return asistenciaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar asistencia")
    @PutMapping("/{id}")
    public ResponseEntity<Asistencia> actualizar(@PathVariable Long id, @Valid @RequestBody Asistencia asistencia) {
        Asistencia actualizada = asistenciaService.actualizar(id, asistencia);
        return ResponseEntity.ok(actualizada);
    }

    @Operation(summary = "Eliminar asistencia")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        asistenciaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
