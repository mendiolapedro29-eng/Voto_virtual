package com.mycompany.sistemavotacion.model;

public class Candidate {
    private int id;
    private String nombre;
    private String partidoPolitico;
    private String cargo; // PRESIDENTE, CONGRESISTA, etc.
    private int regionId;
    private String foto;
    private String propuestas;
    private boolean activo;
    private int eleccionId;
    
    // Constructores
    public Candidate() {
        this.activo = true;
    }
    
    public Candidate(String nombre, String partidoPolitico, String cargo, int eleccionId) {
        this();
        this.nombre = nombre;
        this.partidoPolitico = partidoPolitico;
        this.cargo = cargo;
        this.eleccionId = eleccionId;
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getPartidoPolitico() { return partidoPolitico; }
    public void setPartidoPolitico(String partidoPolitico) { this.partidoPolitico = partidoPolitico; }
    
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    
    public int getRegionId() { return regionId; }
    public void setRegionId(int regionId) { this.regionId = regionId; }
    
    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }
    
    public String getPropuestas() { return propuestas; }
    public void setPropuestas(String propuestas) { this.propuestas = propuestas; }
    
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    
    public int getEleccionId() { return eleccionId; }
    public void setEleccionId(int eleccionId) { this.eleccionId = eleccionId; }
    
    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", partidoPolitico='" + partidoPolitico + '\'' +
                ", cargo='" + cargo + '\'' +
                ", regionId=" + regionId +
                ", activo=" + activo +
                ", eleccionId=" + eleccionId +
                '}';
    }
}