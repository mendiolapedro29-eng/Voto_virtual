
package com.mycompany.sistemavotacion.model;


public class Region {
        private String nombre;
    private String url;
    private String icono;

    public Region(String nombre, String url, String icono) {
        this.nombre = nombre;
        this.url = url;
        this.icono = icono;
    }
    
    // Getters
    public String getNombre() { return nombre; }
    public String getUrl() { return url; }
    public String getIcono() { return icono; }
}
