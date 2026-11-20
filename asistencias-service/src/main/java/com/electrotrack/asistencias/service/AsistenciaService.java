package com.electrotrack.asistencias.service;

import com.electrotrack.asistencias.model.Asistencia;
import com.electrotrack.asistencias.repository.AsistenciaRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

        if (asistencia.getUuid() == null || asistencia.getUuid().isBlank()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El UUID es obligatorio"

            );
        }


        return asistenciaRepository.findByUuid(asistencia.getUuid())
            .orElseGet(() -> asistenciaRepository.save(asistencia));
    }


    public Asistencia obtenerAsistencia(Long id) {
        return asistenciaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Asistencia no encontrada"

            ));
    }


    public Asistencia actualizarAsistencia(Long id, Asistencia datos) {

        Asistencia asistencia = obtenerAsistencia(id);


        if (datos.getRutTrabajador() == null ||
            datos.getRutTrabajador().isBlank()) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El RUT del trabajador es obligatorio"

            );
        }


        if (datos.getTipo() == null || datos.getTipo().isBlank()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El tipo de asistencia es obligatorio"

            );
        }


        asistencia.setRutTrabajador(datos.getRutTrabajador());
        asistencia.setTipo(datos.getTipo());
        asistencia.setFechaHora(datos.getFechaHora());
        asistencia.setLatitud(datos.getLatitud());
        asistencia.setLongitud(datos.getLongitud());


        return asistenciaRepository.save(asistencia);
    }


    public void eliminarAsistencia(Long id) {

        Asistencia asistencia = obtenerAsistencia(id);

        asistenciaRepository.delete(asistencia);
    }
}
