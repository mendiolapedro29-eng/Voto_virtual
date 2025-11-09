package com.mycompany.sistemavotacion.model.service;

import com.mycompany.sistemavotacion.model.Voto;
import com.mycompany.sistemavotacion.model.dao.VotoDAO;
import com.mycompany.sistemavotacion.util.SecurityUtil;
import java.sql.SQLException;
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
     * Registrar voto - AHORA CON 4 PARÁMETROS
     */
    public boolean registrarVoto(int usuarioId, int candidatoId, int eleccionId, String ipVotacion) {
        try {
            // Verificar si el usuario ya votó en esta elección
            if (votoDAO.usuarioYaVoto(usuarioId, eleccionId)) {
                logger.warning("Usuario " + usuarioId + " ya votó en la elección " + eleccionId);
                return false;
            }
            
            // Crear y guardar el voto
            Voto voto = new Voto(usuarioId, candidatoId, eleccionId);
            voto.setIpVotacion(ipVotacion);
            voto.setHashVoto(securityUtil.generarHashVoto(
                String.valueOf(usuarioId), 
                candidatoId, 
                String.valueOf(System.currentTimeMillis())
            ));
            
            boolean exito = votoDAO.insertar(voto);
            if (exito) {
                logger.info("Voto registrado exitosamente para usuario: " + usuarioId + 
                           " - Candidato: " + candidatoId + " - IP: " + ipVotacion);
            } else {
                logger.severe("Error al registrar voto para usuario: " + usuarioId);
            }
            
            return exito;
            
        } catch (SQLException e) {
            logger.severe("Error al registrar voto: " + e.getMessage());
            return false;
        }
    }
    
    public boolean usuarioYaVoto(int usuarioId, int eleccionId) {
        try {
            return votoDAO.usuarioYaVoto(usuarioId, eleccionId);
        } catch (SQLException e) {
            logger.severe("Error al verificar voto: " + e.getMessage());
            return true; // Por seguridad, asumir que ya votó si hay error
        }
    }
}