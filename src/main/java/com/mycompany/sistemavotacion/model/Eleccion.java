package com.mycompany.sistemavotacion.model;

import java.util.Date;

public class Eleccion {
    //Atributos privados
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
    
    public Eleccion(int id, String nombre, Date fechaEleccion, 
            boolean activa, Date fechaCreacion) {
        
        this.id = id;
        this.nombre = nombre;
        this.fechaEleccion = fechaEleccion;
        this.activa = activa;
        this.fechaCreacion = fechaCreacion;
    }
    
    // Getters y Setters
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre=nombre;
    } 
    
    public Date getFechaEleccion(){
        return fechaEleccion;
    } 
    public void setFechaEleccion(Date fechaEleccion){
        this.fechaEleccion=fechaEleccion;
    } 
            
    public boolean isActiva(){
        return activa;
    } 
    public void setActiva(boolean activa){
        this.activa=activa;
    } 
    
    public Date getFechaCreacion(){
        return fechaCreacion;
    } 
    public void setFechaCreacion(Date fechaCreacion){
        this.fechaCreacion=fechaCreacion;
    } 
    
    }
