package com.electrotrack.asistencias.service;

import com.electrotrack.asistencias.model.Asistencia;
import com.electrotrack.asistencias.repository.AsistenciaRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class AsistenciaService {
    private final AsistenciaRepository asistenciaRepository;


    public AsistenciaService(AsistenciaRepository asistenciaRepository) {
        this.asistenciaRepository = asistenciaRepository;

    }


    public List<Asistencia> listarAsistencias() {
        return asistenciaRepository.findAll();

    }

    
    public Asistencia registrarAsistencia(Asistencia asistencia) {
        return asistenciaRepository.save(asistencia);

    }

}