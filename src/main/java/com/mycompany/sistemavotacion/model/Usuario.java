package com.mycompany.sistemavotacion.model;

import java.util.Date;

public class Usuario {
    private int id;
    private String dni;
    private String nombres;
    private String apellidos;
    private Date fechaNacimiento;
    private boolean haVotado;
    private Date fechaCreacion;
    private String distrito;
    private String provincia;
    private String departamento;
    
    // Constructores
    public Usuario() {
        this.fechaCreacion = new Date();
        this.haVotado = false;
    }
    
    public Usuario(int id, String dni, String nombres, String apellidos, boolean haVotado) {
        this();
        this.id = id;
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.haVotado = haVotado;
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    
    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    
    public boolean isHaVotado() { return haVotado; }
    public void setHaVotado(boolean haVotado) { this.haVotado = haVotado; }
    
    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    
    public String getDistrito() { return distrito; }
    public void setDistrito(String distrito) { this.distrito = distrito; }
    
    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }
    
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", haVotado=" + haVotado +
                ", departamento='" + departamento + '\'' +
                '}';
    }
}