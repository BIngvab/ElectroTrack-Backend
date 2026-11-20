# ElectroTrack Backend

Backend del proyecto académico ElectroTrack, desarrollado con Java 21, Spring Boot y PostgreSQL.

## Arquitectura

El backend está compuesto por dos microservicios REST independientes:

| Servicio | Puerto | Base de datos |
|---|---|---|
| asistencias-service | 8081 | electrotrack_asistencias |
| trabajos-service | 8082 | electrotrack_trabajos |

## Tecnologías

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- JUnit 5
- Mockito
- JaCoCo

## Microservicio de asistencias

Permite registrar, consultar, modificar y eliminar asistencias.

### Endpoints

| Método | Ruta |
|---|---|
| GET | /api/asistencias |
| GET | /api/asistencias/{id} |
| POST | /api/asistencias |
| PUT | /api/asistencias/{id} |
| DELETE | /api/asistencias/{id} |

Los registros incluyen datos como identificador, UUID, RUT, tipo de asistencia, fecha, hora y coordenadas geográficas.

## Microservicio de trabajos

Permite gestionar trabajos electromecánicos y consultar asignaciones.

### Endpoints

| Método | Ruta |
|---|---|
| GET | /api/trabajos |
| GET | /api/trabajos/trabajador/{trabajador} |
| POST | /api/trabajos |
| PUT | /api/trabajos/{id} |
| PATCH | /api/trabajos/{id}/estado |
| DELETE | /api/trabajos/{id} |

## Configuración

Se requiere Java 21, PostgreSQL y las bases de datos correspondientes.

La contraseña de PostgreSQL se proporciona mediante la variable de entorno `DB_PASSWORD`, evitando almacenarla directamente en el código fuente.

En PowerShell:

```powershell
$env:DB_PASSWORD = Read-Host "Contraseña de PostgreSQL"
```

## Ejecución

Desde la carpeta de cada microservicio:

```powershell
.\mvnw.cmd spring-boot:run
```

Ejecutar cada microservicio en una terminal independiente.

## Pruebas automatizadas

Se desarrollaron pruebas unitarias para la lógica de servicios y controladores, utilizando JUnit 5 y Mockito.

Para ejecutar las pruebas y generar el reporte de JaCoCo:

```powershell
.\mvnw.cmd clean verify
```

### Resultados

| Microservicio | Cobertura de instrucciones |
|---|---:|
| asistencias-service | 96 % |
| trabajos-service | 94 % |

Los reportes HTML se generan en:

- `asistencias-service/target/site/jacoco/index.html`
- `trabajos-service/target/site/jacoco/index.html`

### Evidencias

- `evidencias/cobertura-asistencias-96.png`
- `evidencias/cobertura-trabajos-94.png`

## Seguridad

- Las contraseñas de base de datos no deben incluirse en GitHub.
- Las bases de datos PostgreSQL se ejecutan localmente durante las pruebas.
- Los registros de asistencia pueden contener datos personales y coordenadas GPS, por lo que no deben publicarse como evidencias sin anonimización.
- El backend corresponde a un prototipo académico y no está preparado para exposición pública sin controles adicionales de seguridad.

## Aplicación Android

https://github.com/BIngvab/dsy1105-electromechanics

## Proyecto académico

**ElectroTrack — Electromechanics Ingeniería SpA**  
**Asignatura:** DSY1105 — Desarrollo de Aplicaciones Móviles  
**Institución:** Duoc UC