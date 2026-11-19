package com.electrotrack.asistencias.repository;

import com.electrotrack.asistencias.model.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    Optional<Asistencia> findByUuid(String uuid);

}
