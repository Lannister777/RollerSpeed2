package com.rollerspeed.app.controller.api;

import com.rollerspeed.app.domain.entity.Aspirante;
import com.rollerspeed.app.service.AspiranteService;
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
@RequestMapping("/api/aspirantes")
@RequiredArgsConstructor
@Tag(name = "Aspirantes", description = "Gestión de aspirantes a alumnos de Roller Speed")
public class AspiranteRestController {

    private final AspiranteService aspiranteService;

    @Operation(summary = "Registrar aspirante", description = "Permite el registro autónomo de aspirantes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Aspirante registrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Aspirante.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<Aspirante> registrar(@Valid @RequestBody Aspirante aspirante) {
        Aspirante creado = aspiranteService.registrar(aspirante);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Listar aspirantes", description = "Obtiene el listado completo de aspirantes (solo para administración)")
    @GetMapping
    public ResponseEntity<List<Aspirante>> listarTodos() {
        return ResponseEntity.ok(aspiranteService.listarTodos());
    }

    @Operation(summary = "Obtener aspirante por id")
    @GetMapping("/{id}")
    public ResponseEntity<Aspirante> obtenerPorId(@PathVariable Long id) {
        return aspiranteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar aspirante")
    @PutMapping("/{id}")
    public ResponseEntity<Aspirante> actualizar(@PathVariable Long id, @Valid @RequestBody Aspirante aspirante) {
        Aspirante actualizado = aspiranteService.actualizar(id, aspirante);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar aspirante")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        aspiranteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
