package com.electrotrack.asistencias.service;

import com.electrotrack.asistencias.model.Asistencia;
import com.electrotrack.asistencias.repository.AsistenciaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class AsistenciaServiceTest {

    private AsistenciaRepository repository;

    private AsistenciaService service;


    @BeforeEach
    void preparar() {

        repository = mock(AsistenciaRepository.class);

        service = new AsistenciaService(repository);

    }


    private Asistencia crearAsistencia() {

        Asistencia asistencia = new Asistencia();

        asistencia.setId(1L);
        asistencia.setUuid("uuid-prueba-001");
        asistencia.setRutTrabajador("11111111-1");
        asistencia.setTipo("ENTRADA");
        asistencia.setFechaHora("20/11/2026 03:30:00");
        asistencia.setLatitud(-34.4);
        asistencia.setLongitud(-70.86);

        return asistencia;

    }


    @Test
    void listarAsistenciasRetornaRegistros() {

        Asistencia asistencia = crearAsistencia();


        when(repository.findAll())
            .thenReturn(List.of(asistencia));


        List<Asistencia> resultado =
            service.listarAsistencias();


        assertEquals(1, resultado.size());

        assertEquals("ENTRADA", resultado.get(0).getTipo());

        verify(repository).findAll();

    }


    @Test
    void listarAsistenciasVaciasRetornaListaVacia() {

        when(repository.findAll())
            .thenReturn(List.of());


        List<Asistencia> resultado =
            service.listarAsistencias();


        assertTrue(resultado.isEmpty());

    }


    @Test
    void registrarAsistenciaNuevaFunciona() {

        Asistencia asistencia = crearAsistencia();


        when(repository.findByUuid("uuid-prueba-001"))
            .thenReturn(Optional.empty());

        when(repository.save(asistencia))
            .thenReturn(asistencia);


        Asistencia resultado =
            service.registrarAsistencia(asistencia);


        assertEquals("uuid-prueba-001", resultado.getUuid());

        verify(repository).save(asistencia);

    }


    @Test
    void registrarAsistenciaDuplicadaNoGuardaOtraVez() {

        Asistencia existente = crearAsistencia();

        Asistencia duplicada = crearAsistencia();


        when(repository.findByUuid("uuid-prueba-001"))
            .thenReturn(Optional.of(existente));


        Asistencia resultado =
            service.registrarAsistencia(duplicada);


        assertSame(existente, resultado);

        verify(repository, never()).save(any(Asistencia.class));

    }


    @Test
    void registrarAsistenciaSinUuidRetorna400() {

        Asistencia asistencia = crearAsistencia();

        asistencia.setUuid(null);


        ResponseStatusException error = assertThrows(
            ResponseStatusException.class,
            () -> service.registrarAsistencia(asistencia)

        );


        assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());

        verify(repository, never()).save(any(Asistencia.class));

    }


    @Test
    void registrarAsistenciaConUuidVacioRetorna400() {

        Asistencia asistencia = crearAsistencia();

        asistencia.setUuid(" ");


        ResponseStatusException error = assertThrows(
            ResponseStatusException.class,
            () -> service.registrarAsistencia(asistencia)

        );


        assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());

    }


    @Test
    void obtenerAsistenciaExistenteFunciona() {

        Asistencia asistencia = crearAsistencia();


        when(repository.findById(1L))
            .thenReturn(Optional.of(asistencia));


        Asistencia resultado =
            service.obtenerAsistencia(1L);


        assertEquals(1L, resultado.getId());

        assertEquals("ENTRADA", resultado.getTipo());

    }


    @Test
    void obtenerAsistenciaInexistenteRetorna404() {

        when(repository.findById(99L))
            .thenReturn(Optional.empty());


        ResponseStatusException error = assertThrows(
            ResponseStatusException.class,
            () -> service.obtenerAsistencia(99L)

        );


        assertEquals(HttpStatus.NOT_FOUND, error.getStatusCode());

    }


    @Test
    void actualizarAsistenciaModificaDatos() {

        Asistencia existente = crearAsistencia();

        Asistencia nuevosDatos = crearAsistencia();

        nuevosDatos.setRutTrabajador("22222222-2");
        nuevosDatos.setTipo("SALIDA");
        nuevosDatos.setFechaHora("20/11/2026 18:00:00");
        nuevosDatos.setLatitud(-33.5);
        nuevosDatos.setLongitud(-70.7);


        when(repository.findById(1L))
            .thenReturn(Optional.of(existente));

        when(repository.save(existente))
            .thenReturn(existente);


        Asistencia resultado =
            service.actualizarAsistencia(1L, nuevosDatos);


        assertEquals("22222222-2", resultado.getRutTrabajador());

        assertEquals("SALIDA", resultado.getTipo());

        assertEquals("20/11/2026 18:00:00", resultado.getFechaHora());

        assertEquals(-33.5, resultado.getLatitud());

        assertEquals(-70.7, resultado.getLongitud());

        assertEquals("uuid-prueba-001", resultado.getUuid());

        verify(repository).save(existente);

    }


    @Test
    void actualizarAsistenciaSinRutRetorna400() {

        Asistencia existente = crearAsistencia();

        Asistencia nuevosDatos = crearAsistencia();

        nuevosDatos.setRutTrabajador(null);


        when(repository.findById(1L))
            .thenReturn(Optional.of(existente));


        ResponseStatusException error = assertThrows(
            ResponseStatusException.class,
            () -> service.actualizarAsistencia(1L, nuevosDatos)

        );


        assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());

        verify(repository, never()).save(any(Asistencia.class));

    }


    @Test
    void actualizarAsistenciaConRutVacioRetorna400() {

        Asistencia existente = crearAsistencia();

        Asistencia nuevosDatos = crearAsistencia();

        nuevosDatos.setRutTrabajador(" ");


        when(repository.findById(1L))
            .thenReturn(Optional.of(existente));


        ResponseStatusException error = assertThrows(
            ResponseStatusException.class,
            () -> service.actualizarAsistencia(1L, nuevosDatos)

        );


        assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());

    }


    @Test
    void actualizarAsistenciaSinTipoRetorna400() {

        Asistencia existente = crearAsistencia();

        Asistencia nuevosDatos = crearAsistencia();

        nuevosDatos.setTipo(null);


        when(repository.findById(1L))
            .thenReturn(Optional.of(existente));


        ResponseStatusException error = assertThrows(
            ResponseStatusException.class,
            () -> service.actualizarAsistencia(1L, nuevosDatos)

        );


        assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());

    }


    @Test
    void actualizarAsistenciaConTipoVacioRetorna400() {

        Asistencia existente = crearAsistencia();

        Asistencia nuevosDatos = crearAsistencia();

        nuevosDatos.setTipo(" ");


        when(repository.findById(1L))
            .thenReturn(Optional.of(existente));


        ResponseStatusException error = assertThrows(
            ResponseStatusException.class,
            () -> service.actualizarAsistencia(1L, nuevosDatos)

        );


        assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());

    }


    @Test
    void actualizarAsistenciaInexistenteRetorna404() {

        when(repository.findById(99L))
            .thenReturn(Optional.empty());


        ResponseStatusException error = assertThrows(
            ResponseStatusException.class,
            () -> service.actualizarAsistencia(99L, crearAsistencia())

        );


        assertEquals(HttpStatus.NOT_FOUND, error.getStatusCode());

    }


    @Test
    void eliminarAsistenciaExistenteFunciona() {

        Asistencia existente = crearAsistencia();


        when(repository.findById(1L))
            .thenReturn(Optional.of(existente));


        service.eliminarAsistencia(1L);


        verify(repository).delete(existente);

    }


    @Test
    void eliminarAsistenciaInexistenteRetorna404() {

        when(repository.findById(99L))
            .thenReturn(Optional.empty());


        ResponseStatusException error = assertThrows(
            ResponseStatusException.class,
            () -> service.eliminarAsistencia(99L)

        );


        assertEquals(HttpStatus.NOT_FOUND, error.getStatusCode());

    }

}
