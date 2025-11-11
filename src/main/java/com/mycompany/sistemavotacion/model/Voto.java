package com.mycompany.sistemavotacion.model;

import java.sql.Timestamp;

public class Voto {
    private int id;
    private int usuarioId;
    private int candidatoId;
    private int eleccionId;
    private Timestamp timestamp;
    private String hashVoto;
    private String ipVotacion;
    
    // Constructores
    public Voto() {} // ✅ Constructor sin parámetros
    
    public Voto(int usuarioId, int candidatoId, int eleccionId, String ipVotacion) {
        this.usuarioId = usuarioId;
        this.candidatoId = candidatoId;
        this.eleccionId = eleccionId;
        this.ipVotacion = ipVotacion;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }
    
    // Getters y Setters (igual que antes)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    
    public int getCandidatoId() { return candidatoId; }
    public void setCandidatoId(int candidatoId) { this.candidatoId = candidatoId; }
    
    public int getEleccionId() { return eleccionId; }
    public void setEleccionId(int eleccionId) { this.eleccionId = eleccionId; }
    
    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
    
    public String getHashVoto() { return hashVoto; }
    public void setHashVoto(String hashVoto) { this.hashVoto = hashVoto; }
    
    public String getIpVotacion() { return ipVotacion; }
    public void setIpVotacion(String ipVotacion) { this.ipVotacion = ipVotacion; }
    
    @Override
    public String toString() {
        return "Voto{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", candidatoId=" + candidatoId +
                ", eleccionId=" + eleccionId +
                ", timestamp=" + timestamp +
                '}';
    }
}