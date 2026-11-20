package com.electrotrack.trabajos.service;

import com.electrotrack.trabajos.model.Trabajo;
import com.electrotrack.trabajos.repository.TrabajoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class TrabajoService {

    private final TrabajoRepository trabajoRepository;

    public TrabajoService(TrabajoRepository trabajoRepository) {
        this.trabajoRepository = trabajoRepository;

    }


    public List<Trabajo> listarTrabajos() {
        return trabajoRepository.findAll();

    }


    public List<Trabajo> buscarPorTrabajador(String trabajador) {
        return trabajoRepository.findByTrabajador(trabajador);

    }


    public Trabajo registrarTrabajo(Trabajo trabajo) {
        if (trabajo.getEstado() == null || trabajo.getEstado().isBlank()) {
            trabajo.setEstado("Pendiente");

        }
        return trabajoRepository.save(trabajo);
    }


    public Trabajo actualizarTrabajo(Long id, Trabajo datos) {
        Trabajo trabajo = buscarPorId(id);

        if (datos.getTitulo() == null || datos.getTitulo().isBlank()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "El titulo es obligatorio"

            );
        }


        trabajo.setTitulo(datos.getTitulo());
        trabajo.setDescripcion(datos.getDescripcion());
        trabajo.setTrabajador(datos.getTrabajador());


        if (datos.getEstado() != null && !datos.getEstado().isBlank()) {
            trabajo.setEstado(datos.getEstado());

        }

        return trabajoRepository.save(trabajo);
    }

    public Trabajo actualizarEstado(Long id, String estado) {
        Trabajo trabajo = buscarPorId(id);

        if (estado == null || estado.isBlank()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "El estado es obligatorio"

            );
        }

        trabajo.setEstado(estado);
        return trabajoRepository.save(trabajo);

    }

    public void eliminarTrabajo(Long id) {
        Trabajo trabajo = buscarPorId(id);
        trabajoRepository.delete(trabajo);

    }

    private Trabajo buscarPorId(Long id) {
        return trabajoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Trabajo no encontrado"
                
            ));
    }
}
