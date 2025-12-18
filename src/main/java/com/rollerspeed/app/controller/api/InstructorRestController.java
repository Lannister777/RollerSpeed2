package com.rollerspeed.app.controller.api;

import com.rollerspeed.app.domain.entity.Instructor;
import com.rollerspeed.app.service.InstructorService;
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
@RequestMapping("/api/instructores")
@RequiredArgsConstructor
@Tag(name = "Instructores", description = "Gestión de instructores de Roller Speed")
public class InstructorRestController {

    private final InstructorService instructorService;

    @Operation(summary = "Crear instructor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Instructor creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Instructor.class)))
    })
    @PostMapping
    public ResponseEntity<Instructor> crear(@Valid @RequestBody Instructor instructor) {
        Instructor creado = instructorService.crear(instructor);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Listar instructores")
    @GetMapping
    public ResponseEntity<List<Instructor>> listarTodos() {
        return ResponseEntity.ok(instructorService.listarTodos());
    }

    @Operation(summary = "Obtener instructor por id")
    @GetMapping("/{id}")
    public ResponseEntity<Instructor> obtenerPorId(@PathVariable Long id) {
        return instructorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar instructor")
    @PutMapping("/{id}")
    public ResponseEntity<Instructor> actualizar(@PathVariable Long id, @Valid @RequestBody Instructor instructor) {
        Instructor actualizado = instructorService.actualizar(id, instructor);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar instructor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        instructorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
