
package com.mycompany.sistemavotacion.model;


public class Candidato {
    private String id;
    private String nombre;
    private String partido;
    private String propuesta;
    private String imagenUrl;
    private String cargo;

    public Candidato(String id, String nombre, String partido, String propuesta, String imagenUrl, String cargo) {
        this.id = id;
        this.nombre = nombre;
        this.partido = partido;
        this.propuesta = propuesta;
        this.imagenUrl = imagenUrl;
        this.cargo = cargo;
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getPartido() { return partido; }
    public String getPropuesta() { return propuesta; }
    public String getImagenUrl() { return imagenUrl; }
    public String getCargo() { return cargo; }
}
