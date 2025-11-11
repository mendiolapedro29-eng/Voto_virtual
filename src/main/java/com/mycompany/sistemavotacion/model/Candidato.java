package com.mycompany.sistemavotacion.model;

public class Candidato {
    private int id;
    private String nombre;
    private String partido;
    private String cargo;
    private int regionId;
    private String foto;
    private String propuestas;
    private boolean activo;
    private int eleccionId;
    private int votos;
    private double porcentaje;
    
    // Constructores
    public Candidato() {
        this.activo = true;
        this.eleccionId = 1; // Elección por defecto
    }
    
    public Candidato(int id, String nombre, String partido, String cargo, int votos, double porcentaje) {
        this();
        this.id = id;
        this.nombre = nombre;
        this.partido = partido;
        this.cargo = cargo;
        this.votos = votos;
        this.porcentaje = porcentaje;
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getPartido() { return partido; }
    public void setPartido(String partido) { this.partido = partido; }
    
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
    
    public int getVotos() { return votos; }
    public void setVotos(int votos) { this.votos = votos; }
    
    public double getPorcentaje() { return porcentaje; }
    public void setPorcentaje(double porcentaje) { this.porcentaje = porcentaje; }
    
    @Override
    public String toString() {
        return "Candidato{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", partido='" + partido + '\'' +
                ", cargo='" + cargo + '\'' +
                ", votos=" + votos +
                ", porcentaje=" + porcentaje +
                '}';
    }
}