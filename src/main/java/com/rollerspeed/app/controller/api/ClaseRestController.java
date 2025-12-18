package com.rollerspeed.app.controller.api;

import com.rollerspeed.app.domain.entity.Clase;
import com.rollerspeed.app.service.ClaseService;
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
@RequestMapping("/api/clases")
@RequiredArgsConstructor
@Tag(name = "Clases", description = "Gestión de clases de Roller Speed")
public class ClaseRestController {

    private final ClaseService claseService;

    @Operation(summary = "Crear clase")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Clase creada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Clase.class)))
    })
    @PostMapping
    public ResponseEntity<Clase> crear(@Valid @RequestBody Clase clase) {
        Clase creada = claseService.crear(clase);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @Operation(summary = "Listar clases")
    @GetMapping
    public ResponseEntity<List<Clase>> listarTodas() {
        return ResponseEntity.ok(claseService.listarTodas());
    }

    @Operation(summary = "Obtener clase por id")
    @GetMapping("/{id}")
    public ResponseEntity<Clase> obtenerPorId(@PathVariable Long id) {
        return claseService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar clase")
    @PutMapping("/{id}")
    public ResponseEntity<Clase> actualizar(@PathVariable Long id, @Valid @RequestBody Clase clase) {
        Clase actualizada = claseService.actualizar(id, clase);
        return ResponseEntity.ok(actualizada);
    }

    @Operation(summary = "Eliminar clase")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        claseService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
