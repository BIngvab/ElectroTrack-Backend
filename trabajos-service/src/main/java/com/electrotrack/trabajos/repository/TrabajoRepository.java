package com.electrotrack.trabajos.repository;

import com.electrotrack.trabajos.model.Trabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TrabajoRepository extends JpaRepository<Trabajo, Long> {

    List<Trabajo> findByTrabajador(String trabajador);

}
