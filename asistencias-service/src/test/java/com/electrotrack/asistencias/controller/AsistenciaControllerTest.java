package com.electrotrack.asistencias.controller;

import com.electrotrack.asistencias.model.Asistencia;
import com.electrotrack.asistencias.service.AsistenciaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class AsistenciaControllerTest {

    private AsistenciaService service;

    private AsistenciaController controller;


    @BeforeEach
    void preparar() {

        service = mock(AsistenciaService.class);

        controller = new AsistenciaController(service);

    }


    private Asistencia crearAsistencia() {

        Asistencia asistencia = new Asistencia();

        asistencia.setId(1L);
        asistencia.setUuid("uuid-controlador-001");
        asistencia.setRutTrabajador("11111111-1");
        asistencia.setTipo("ENTRADA");
        asistencia.setFechaHora("20/11/2026 03:45:00");
        asistencia.setLatitud(-34.4);
        asistencia.setLongitud(-70.86);

        return asistencia;

    }


    @Test
    void listarAsistenciasRetornaRegistros() {

        Asistencia asistencia = crearAsistencia();

        when(service.listarAsistencias())
            .thenReturn(List.of(asistencia));


        List<Asistencia> resultado =
            controller.listarAsistencias();


        assertEquals(1, resultado.size());

        assertEquals("ENTRADA", resultado.get(0).getTipo());

        verify(service).listarAsistencias();

    }


    @Test
    void listarAsistenciasVacias() {

        when(service.listarAsistencias())
            .thenReturn(List.of());


        List<Asistencia> resultado =
            controller.listarAsistencias();


        assertTrue(resultado.isEmpty());

    }


    @Test
    void obtenerAsistenciaPorId() {

        Asistencia asistencia = crearAsistencia();

        when(service.obtenerAsistencia(1L))
            .thenReturn(asistencia);


        Asistencia resultado =
            controller.obtenerAsistencia(1L);


        assertEquals(1L, resultado.getId());

        verify(service).obtenerAsistencia(1L);

    }


    @Test
    void registrarAsistenciaRetorna201() {

        Asistencia asistencia = crearAsistencia();

        when(service.registrarAsistencia(asistencia))
            .thenReturn(asistencia);


        ResponseEntity<Asistencia> respuesta =
            controller.registrarAsistencia(asistencia);


        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());

        assertSame(asistencia, respuesta.getBody());

        verify(service).registrarAsistencia(asistencia);

    }


    @Test
    void actualizarAsistenciaRetornaDatosActualizados() {

        Asistencia datos = crearAsistencia();

        datos.setTipo("SALIDA");


        when(service.actualizarAsistencia(1L, datos))
            .thenReturn(datos);


        Asistencia resultado =
            controller.actualizarAsistencia(1L, datos);


        assertEquals("SALIDA", resultado.getTipo());

        verify(service).actualizarAsistencia(1L, datos);

    }


    @Test
    void eliminarAsistenciaRetorna204() {

        ResponseEntity<Void> respuesta =
            controller.eliminarAsistencia(1L);


        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());

        assertNull(respuesta.getBody());

        verify(service).eliminarAsistencia(1L);

    }

}
