package com.electrotrack.asistencias.controller;

import com.electrotrack.asistencias.model.Asistencia;
import com.electrotrack.asistencias.service.AsistenciaService;
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


    @PostMapping
    public Asistencia registrarAsistencia(@RequestBody Asistencia asistencia) {
        return asistenciaService.registrarAsistencia(asistencia);
        
    }

}
