package com.electrotrack.trabajos.service;

import com.electrotrack.trabajos.model.Trabajo;
import com.electrotrack.trabajos.repository.TrabajoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TrabajoServiceTest {

    private TrabajoRepository repository;

    private TrabajoService service;


    @BeforeEach
    void preparar() {

        repository = mock(TrabajoRepository.class);

        service = new TrabajoService(repository);

    }


    private Trabajo crearTrabajo() {

        Trabajo trabajo = new Trabajo();

        trabajo.setId(1L);
        trabajo.setTitulo("Revisar tablero");
        trabajo.setDescripcion("Inspección eléctrica");
        trabajo.setTrabajador("Técnico 1");
        trabajo.setEstado("Pendiente");

        return trabajo;

    }


    @Test
    void listarTrabajosRetornaRegistros() {

        Trabajo trabajo = crearTrabajo();

        when(repository.findAll())
            .thenReturn(List.of(trabajo));


        List<Trabajo> resultado = service.listarTrabajos();


        assertEquals(1, resultado.size());

        assertEquals("Revisar tablero", resultado.get(0).getTitulo());

        verify(repository).findAll();

    }


    @Test
    void buscarPorTrabajadorRetornaSusTrabajos() {

        Trabajo trabajo = crearTrabajo();

        when(repository.findByTrabajador("Técnico 1"))
            .thenReturn(List.of(trabajo));


        List<Trabajo> resultado =
            service.buscarPorTrabajador("Técnico 1");


        assertEquals(1, resultado.size());

        verify(repository).findByTrabajador("Técnico 1");

    }


    @Test
    void registrarTrabajoAsignaEstadoPendiente() {

        Trabajo trabajo = crearTrabajo();

        trabajo.setEstado(null);


        when(repository.save(trabajo))
            .thenReturn(trabajo);


        Trabajo resultado = service.registrarTrabajo(trabajo);


        assertEquals("Pendiente", resultado.getEstado());

        verify(repository).save(trabajo);

    }


    @Test
    void registrarTrabajoConservaEstadoExistente() {

        Trabajo trabajo = crearTrabajo();

        trabajo.setEstado("En progreso");


        when(repository.save(trabajo))
            .thenReturn(trabajo);


        Trabajo resultado = service.registrarTrabajo(trabajo);


        assertEquals("En progreso", resultado.getEstado());

    }


    @Test
    void registrarTrabajoConEstadoVacioAsignaPendiente() {

        Trabajo trabajo = crearTrabajo();

        trabajo.setEstado(" ");


        when(repository.save(trabajo))
            .thenReturn(trabajo);


        Trabajo resultado = service.registrarTrabajo(trabajo);


        assertEquals("Pendiente", resultado.getEstado());

    }


    @Test
    void actualizarTrabajoModificaDatos() {

        Trabajo existente = crearTrabajo();

        Trabajo nuevosDatos = new Trabajo();

        nuevosDatos.setTitulo("Reparar tablero");
        nuevosDatos.setDescripcion("Cambio de componentes");
        nuevosDatos.setTrabajador("Técnico 2");
        nuevosDatos.setEstado("En progreso");


        when(repository.findById(1L))
            .thenReturn(Optional.of(existente));

        when(repository.save(existente))
            .thenReturn(existente);


        Trabajo resultado =
            service.actualizarTrabajo(1L, nuevosDatos);


        assertEquals("Reparar tablero", resultado.getTitulo());

        assertEquals("Cambio de componentes", resultado.getDescripcion());

        assertEquals("Técnico 2", resultado.getTrabajador());

        assertEquals("En progreso", resultado.getEstado());

    }


    @Test
    void actualizarTrabajoConservaEstadoSiNoSeEnvia() {

        Trabajo existente = crearTrabajo();

        Trabajo nuevosDatos = new Trabajo();

        nuevosDatos.setTitulo("Nuevo título");
        nuevosDatos.setEstado(null);


        when(repository.findById(1L))
            .thenReturn(Optional.of(existente));

        when(repository.save(existente))
            .thenReturn(existente);


        Trabajo resultado =
            service.actualizarTrabajo(1L, nuevosDatos);


        assertEquals("Pendiente", resultado.getEstado());

    }


    @Test
    void actualizarTrabajoSinTituloRetorna400() {

        Trabajo existente = crearTrabajo();

        Trabajo nuevosDatos = new Trabajo();

        nuevosDatos.setTitulo(" ");


        when(repository.findById(1L))
            .thenReturn(Optional.of(existente));


        ResponseStatusException error = assertThrows(
            ResponseStatusException.class,
            () -> service.actualizarTrabajo(1L, nuevosDatos)

        );


        assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());

        verify(repository, never()).save(any(Trabajo.class));

    }


    @Test
    void actualizarTrabajoInexistenteRetorna404() {

        when(repository.findById(99L))
            .thenReturn(Optional.empty());


        ResponseStatusException error = assertThrows(
            ResponseStatusException.class,
            () -> service.actualizarTrabajo(99L, crearTrabajo())

        );


        assertEquals(HttpStatus.NOT_FOUND, error.getStatusCode());

    }


    @Test
    void actualizarEstadoFunciona() {

        Trabajo existente = crearTrabajo();


        when(repository.findById(1L))
            .thenReturn(Optional.of(existente));

        when(repository.save(existente))
            .thenReturn(existente);


        Trabajo resultado =
            service.actualizarEstado(1L, "Completado");


        assertEquals("Completado", resultado.getEstado());

        verify(repository).save(existente);

    }


    @Test
    void actualizarEstadoVacioRetorna400() {

        when(repository.findById(1L))
            .thenReturn(Optional.of(crearTrabajo()));


        ResponseStatusException error = assertThrows(
            ResponseStatusException.class,
            () -> service.actualizarEstado(1L, "")

        );


        assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());

    }


    @Test
    void actualizarEstadoInexistenteRetorna404() {

        when(repository.findById(99L))
            .thenReturn(Optional.empty());


        ResponseStatusException error = assertThrows(
            ResponseStatusException.class,
            () -> service.actualizarEstado(99L, "Completado")

        );


        assertEquals(HttpStatus.NOT_FOUND, error.getStatusCode());

    }


    @Test
    void eliminarTrabajoFunciona() {

        Trabajo existente = crearTrabajo();


        when(repository.findById(1L))
            .thenReturn(Optional.of(existente));


        service.eliminarTrabajo(1L);


        verify(repository).delete(existente);

    }


    @Test
    void eliminarTrabajoInexistenteRetorna404() {

        when(repository.findById(99L))
            .thenReturn(Optional.empty());


        ResponseStatusException error = assertThrows(
            ResponseStatusException.class,
            () -> service.eliminarTrabajo(99L)

        );


        assertEquals(HttpStatus.NOT_FOUND, error.getStatusCode());

    }

}
