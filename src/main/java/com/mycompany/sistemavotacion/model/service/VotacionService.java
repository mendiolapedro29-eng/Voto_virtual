package com.mycompany.sistemavotacion.model.service;

import com.mycompany.sistemavotacion.model.Voto;
import com.mycompany.sistemavotacion.model.dao.VotoDAO;
import com.mycompany.sistemavotacion.util.SecurityUtil;
import java.util.logging.Logger;

public class VotacionService {
    private static final Logger logger = Logger.getLogger(VotacionService.class.getName());
    
    private VotoDAO votoDAO;
    private SecurityUtil securityUtil;
    
    public VotacionService() {
        this.votoDAO = new VotoDAO();
        this.securityUtil = new SecurityUtil();
    }
    
    /**
     * Registrar voto - CORREGIDO
     */
    public boolean registrarVoto(int usuarioId, int candidatoId, int eleccionId, String ipVotacion) {
        try {
            // Verificar si el usuario ya votó en esta elección
            if (votoDAO.usuarioYaVoto(usuarioId, eleccionId)) {
                logger.warning("Usuario " + usuarioId + " ya votó en la elección " + eleccionId);
                return false;
            }
            
            // Generar hash del voto
            String hashVoto = securityUtil.generarHashVoto(
                String.valueOf(usuarioId), 
                candidatoId, 
                String.valueOf(System.currentTimeMillis())
            );
            
            // Crear el voto - CORREGIDO: usar constructor correcto
            Voto voto = new Voto();
            voto.setUsuarioId(usuarioId);
            voto.setCandidatoId(candidatoId);
            voto.setEleccionId(eleccionId);
            voto.setIpVotacion(ipVotacion);
            voto.setHashVoto(hashVoto);
            
            boolean exito = votoDAO.insertar(voto);
            if (exito) {
                logger.info("✅ Voto registrado exitosamente para usuario: " + usuarioId + 
                           " - Candidato: " + candidatoId + " - IP: " + ipVotacion);
            } else {
                logger.severe("❌ Error al registrar voto para usuario: " + usuarioId);
            }
            
            return exito;
            
        } catch (Exception e) { // ✅ CORREGIDO: Cambiado SQLException por Exception
            logger.severe("❌ Error al registrar voto: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Verificar si usuario ya votó
     */
    public boolean usuarioYaVoto(int usuarioId, int eleccionId) {
        try {
            return votoDAO.usuarioYaVoto(usuarioId, eleccionId);
        } catch (Exception e) { // ✅ CORREGIDO: Cambiado SQLException por Exception
            logger.severe("❌ Error al verificar voto: " + e.getMessage());
            return true; // Por seguridad, asumir que ya votó si hay error
        }
    }
    
    /**
     * Obtener resultados por candidato
     */
    public java.util.List<Object[]> obtenerResultadosPorCandidato(int eleccionId) {
        try {
            return votoDAO.obtenerResultadosPorCandidato(eleccionId);
        } catch (Exception e) {
            logger.severe("❌ Error al obtener resultados: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }
    
    /**
     * Obtener todos los votos (para admin)
     */
    public java.util.List<Voto> obtenerTodosVotos() {
        try {
            return votoDAO.obtenerTodosVotos();
        } catch (Exception e) {
            logger.severe("❌ Error al obtener votos: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }
    
    /**
     * Obtener estadísticas de votación
     */
    public java.util.Map<String, Object> obtenerEstadisticasVotacion(int eleccionId) {
        try {
            return votoDAO.obtenerEstadisticasVotacion(eleccionId);
        } catch (Exception e) {
            logger.severe("❌ Error al obtener estadísticas: " + e.getMessage());
            java.util.Map<String, Object> statsError = new java.util.HashMap<>();
            statsError.put("error", e.getMessage());
            return statsError;
        }
    }
    
    /**
     * Simular votos masivos para pruebas
     */
    public void simularVotosMasivos(int cantidad, int eleccionId) {
        try {
            votoDAO.simularVotosMasivos(cantidad, eleccionId);
            logger.info("✅ Simulados " + cantidad + " votos para elección " + eleccionId);
        } catch (Exception e) {
            logger.severe("❌ Error en simulación masiva: " + e.getMessage());
        }
    }
    
    /**
     * Obtener total de votos registrados
     */
    public int obtenerTotalVotos() {
        try {
            return votoDAO.obtenerTotalVotos();
        } catch (Exception e) {
            logger.severe("❌ Error al obtener total de votos: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Validar datos antes de votar
     */
    public boolean validarDatosVoto(int usuarioId, int candidatoId, int eleccionId, String ipVotacion) {
        if (usuarioId <= 0) {
            logger.warning("⚠️ ID de usuario inválido: " + usuarioId);
            return false;
        }
        
        if (candidatoId <= 0) {
            logger.warning("⚠️ ID de candidato inválido: " + candidatoId);
            return false;
        }
        
        if (eleccionId <= 0) {
            logger.warning("⚠️ ID de elección inválido: " + eleccionId);
            return false;
        }
        
        if (ipVotacion == null || ipVotacion.trim().isEmpty()) {
            logger.warning("⚠️ IP de votación inválida");
            return false;
        }
        
        return true;
    }
}