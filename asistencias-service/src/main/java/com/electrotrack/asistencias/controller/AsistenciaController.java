package com.electrotrack.asistencias.controller;

import com.electrotrack.asistencias.model.Asistencia;
import com.electrotrack.asistencias.service.AsistenciaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/asistencias")
public class AsistenciaController {

    private final AsistenciaService asistenciaService;


    public AsistenciaController(AsistenciaService asistenciaService) {
        this.asistenciaService = asistenciaService;

    }


    @GetMapping
    public List<Asistencia> listarAsistencias() {
        return asistenciaService.listarAsistencias();

    }


    @GetMapping("/{id}")
    public Asistencia obtenerAsistencia(@PathVariable Long id) {
        return asistenciaService.obtenerAsistencia(id);

    }


    @PostMapping
    public ResponseEntity<Asistencia> registrarAsistencia(
        @RequestBody Asistencia asistencia

    ) {
        Asistencia guardada = asistenciaService.registrarAsistencia(asistencia);

        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);

    }


    @PutMapping("/{id}")
    public Asistencia actualizarAsistencia(
        @PathVariable Long id,
        @RequestBody Asistencia datos

    ) {
        return asistenciaService.actualizarAsistencia(id, datos);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsistencia(@PathVariable Long id) {

        asistenciaService.eliminarAsistencia(id);

        return ResponseEntity.noContent().build();

    }
}
