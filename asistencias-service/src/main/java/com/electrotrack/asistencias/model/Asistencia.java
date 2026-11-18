package com.electrotrack.asistencias.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rutTrabajador;
    private String tipo;
    private String fechaHora;
    private Double latitud;
    private Double longitud;

    @Column(unique = true)
    private String uuid;


    public Asistencia() {
    }


    public Long getId() {
        return id;

    }


    public void setId(Long id) {
        this.id = id;

    }


    public String getRutTrabajador() {
        return rutTrabajador;

    }


    public void setRutTrabajador(String rutTrabajador) {
        this.rutTrabajador = rutTrabajador;

    }


    public String getTipo() {
        return tipo;

    }


    public void setTipo(String tipo) {
        this.tipo = tipo;

    }


    public String getFechaHora() {
        return fechaHora;

    }


    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;

    }


    public Double getLatitud() {
        return latitud;

    }


    public void setLatitud(Double latitud) {
        this.latitud = latitud;

    }


    public Double getLongitud() {
        return longitud;

    }


    public void setLongitud(Double longitud) {
        this.longitud = longitud;
        
    }


    public String getUuid() {
        return uuid;

    }


    public void setUuid(String uuid) {
        this.uuid = uuid;

    }


}
