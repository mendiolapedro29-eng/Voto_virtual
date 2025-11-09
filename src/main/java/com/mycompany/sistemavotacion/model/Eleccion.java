package com.mycompany.sistemavotacion.model;

import java.util.Date;

public class Eleccion {
    private int id;
    private String nombre;
    private Date fechaEleccion;
    private boolean activa;
    private Date fechaCreacion;
    
    // Constructores
    public Eleccion() {
        this.fechaCreacion = new Date();
        this.activa = true;
    }
    
    public Eleccion(String nombre, Date fechaEleccion) {
        this();
        this.nombre = nombre;
        this.fechaEleccion = fechaEleccion;
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public Date getFechaEleccion() { return fechaEleccion; }
    public void setFechaEleccion(Date fechaEleccion) { this.fechaEleccion = fechaEleccion; }
    
    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
    
    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    
    @Override
    public String toString() {
        return "Eleccion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fechaEleccion=" + fechaEleccion +
                ", activa=" + activa +
                '}';
    }
}