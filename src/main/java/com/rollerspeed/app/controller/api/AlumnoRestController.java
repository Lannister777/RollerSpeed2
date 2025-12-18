package com.rollerspeed.app.controller.api;

import com.rollerspeed.app.domain.entity.Alumno;
import com.rollerspeed.app.service.AlumnoService;
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
@RequestMapping("/api/alumnos")
@RequiredArgsConstructor
@Tag(name = "Alumnos", description = "Gestión de alumnos de Roller Speed")
public class AlumnoRestController {

    private final AlumnoService alumnoService;

    @Operation(summary = "Crear alumno", description = "Crea un nuevo alumno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alumno creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Alumno.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<Alumno> crear(@Valid @RequestBody Alumno alumno) {
        Alumno creado = alumnoService.crear(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Listar alumnos")
    @GetMapping
    public ResponseEntity<List<Alumno>> listarTodos() {
        return ResponseEntity.ok(alumnoService.listarTodos());
    }

    @Operation(summary = "Obtener alumno por id")
    @GetMapping("/{id}")
    public ResponseEntity<Alumno> obtenerPorId(@PathVariable Long id) {
        return alumnoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar alumno")
    @PutMapping("/{id}")
    public ResponseEntity<Alumno> actualizar(@PathVariable Long id, @Valid @RequestBody Alumno alumno) {
        Alumno actualizado = alumnoService.actualizar(id, alumno);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar alumno")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        alumnoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
