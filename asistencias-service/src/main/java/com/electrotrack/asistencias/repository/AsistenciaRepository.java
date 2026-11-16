package com.electrotrack.asistencias.repository;

import com.electrotrack.asistencias.model.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

}