package com.electrotrack.trabajos.controller;

import com.electrotrack.trabajos.model.Trabajo;
import com.electrotrack.trabajos.service.TrabajoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TrabajoControllerTest {

    private TrabajoService service;

    private TrabajoController controller;


    @BeforeEach
    void preparar() {

        service = mock(TrabajoService.class);

        controller = new TrabajoController(service);

    }


    private Trabajo crearTrabajo() {

        return new Trabajo();

    }


    @Test
    void listarTrabajosRetornaRegistros() {

        Trabajo trabajo = crearTrabajo();

        when(service.listarTrabajos())
            .thenReturn(List.of(trabajo));


        List<Trabajo> resultado =
            controller.listarTrabajos();


        assertEquals(1, resultado.size());

        assertSame(trabajo, resultado.get(0));

        verify(service).listarTrabajos();

    }


    @Test
    void listarTrabajosVacios() {

        when(service.listarTrabajos())
            .thenReturn(List.of());


        List<Trabajo> resultado =
            controller.listarTrabajos();


        assertTrue(resultado.isEmpty());

    }


    @Test
    void buscarPorTrabajadorRetornaTrabajos() {

        Trabajo trabajo = crearTrabajo();

        when(service.buscarPorTrabajador("11111111-1"))
            .thenReturn(List.of(trabajo));


        List<Trabajo> resultado =
            controller.buscarPorTrabajador("11111111-1");


        assertEquals(1, resultado.size());

        assertSame(trabajo, resultado.get(0));

        verify(service).buscarPorTrabajador("11111111-1");

    }


    @Test
    void buscarPorTrabajadorSinResultados() {

        when(service.buscarPorTrabajador("99999999-9"))
            .thenReturn(List.of());


        List<Trabajo> resultado =
            controller.buscarPorTrabajador("99999999-9");


        assertTrue(resultado.isEmpty());

    }


    @Test
    void registrarTrabajoRetorna201() {

        Trabajo trabajo = crearTrabajo();

        when(service.registrarTrabajo(trabajo))
            .thenReturn(trabajo);


        ResponseEntity<Trabajo> respuesta =
            controller.registrarTrabajo(trabajo);


        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());

        assertSame(trabajo, respuesta.getBody());

        verify(service).registrarTrabajo(trabajo);

    }


    @Test
    void actualizarTrabajoRetornaDatosActualizados() {

        Trabajo datos = crearTrabajo();

        when(service.actualizarTrabajo(1L, datos))
            .thenReturn(datos);


        Trabajo resultado =
            controller.actualizarTrabajo(1L, datos);


        assertSame(datos, resultado);

        verify(service).actualizarTrabajo(1L, datos);

    }


    @Test
    void actualizarEstadoRetornaTrabajoActualizado() {

        Trabajo trabajo = crearTrabajo();

        when(service.actualizarEstado(1L, "COMPLETADO"))
            .thenReturn(trabajo);


        Trabajo resultado =
            controller.actualizarEstado(1L, "COMPLETADO");


        assertSame(trabajo, resultado);

        verify(service).actualizarEstado(1L, "COMPLETADO");

    }


    @Test
    void eliminarTrabajoRetorna204() {

        ResponseEntity<Void> respuesta =
            controller.eliminarTrabajo(1L);


        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());

        assertNull(respuesta.getBody());

        verify(service).eliminarTrabajo(1L);

    }

}
