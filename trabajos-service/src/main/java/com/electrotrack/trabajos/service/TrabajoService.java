package com.electrotrack.trabajos.service;

import com.electrotrack.trabajos.model.Trabajo;
import com.electrotrack.trabajos.repository.TrabajoRepository;
import org.springframework.stereotype.Service;

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


    public Trabajo actualizarEstado(Long id, String estado) {

        Trabajo trabajo = trabajoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trabajo no encontrado"));

        if (estado == null || estado.isBlank()) {
            throw new IllegalArgumentException("El estado es obligatorio");

        }

        trabajo.setEstado(estado);

        return trabajoRepository.save(trabajo);

    }

    public void eliminarTrabajo(Long id) {

    if (!trabajoRepository.existsById(id)) {
        throw new IllegalArgumentException("Trabajo no encontrado");

    }

    trabajoRepository.deleteById(id);

    }

}
