package com.electrotrack.trabajos.controller;

import com.electrotrack.trabajos.model.Trabajo;
import com.electrotrack.trabajos.service.TrabajoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trabajos")
public class TrabajoController {

    private final TrabajoService trabajoService;

    public TrabajoController(TrabajoService trabajoService) {
        this.trabajoService = trabajoService;
    }


    @GetMapping
    public List<Trabajo> listarTrabajos() {
        return trabajoService.listarTrabajos();
    }


    @GetMapping("/trabajador/{trabajador}")
    public List<Trabajo> buscarPorTrabajador(
        @PathVariable String trabajador

    ) {
        return trabajoService.buscarPorTrabajador(trabajador);

    }


    @PostMapping
    public ResponseEntity<Trabajo> registrarTrabajo(
        @RequestBody Trabajo trabajo

    ) {
        Trabajo guardado = trabajoService.registrarTrabajo(trabajo);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);

    }


    @PutMapping("/{id}")
    public Trabajo actualizarTrabajo(
        @PathVariable Long id,
        @RequestBody Trabajo datos

    ) {
        return trabajoService.actualizarTrabajo(id, datos);

    }


    @PatchMapping("/{id}/estado")
    public Trabajo actualizarEstado(
        @PathVariable Long id,
        @RequestParam String estado

    ) {
        return trabajoService.actualizarEstado(id, estado);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTrabajo(@PathVariable Long id) {
        trabajoService.eliminarTrabajo(id);
        return ResponseEntity.noContent().build();
        
    }
}
