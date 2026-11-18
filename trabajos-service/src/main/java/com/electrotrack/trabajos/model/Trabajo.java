package com.electrotrack.trabajos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Trabajo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;
    private String trabajador;
    private String estado;


    public Trabajo() {

    }


    public Long getId() {
        return id;

    }


    public void setId(Long id) {
        this.id = id;

    }


    public String getTitulo() {
        return titulo;

    }


    public void setTitulo(String titulo) {
        this.titulo = titulo;

    }


    public String getDescripcion() {
        return descripcion;

    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;

    }


    public String getTrabajador() {
        return trabajador;

    }


    public void setTrabajador(String trabajador) {
        this.trabajador = trabajador;

    }


    public String getEstado() {
        return estado;

    }


    public void setEstado(String estado) {
        this.estado = estado;

    }

}
