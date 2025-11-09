package com.mycompany.sistemavotacion.model.service;

import com.mycompany.sistemavotacion.model.Usuario;
import com.mycompany.sistemavotacion.model.dao.UsuarioDAO;
import java.sql.SQLException;
import java.util.logging.Logger;

public class AuthService {
    private static final Logger logger = Logger.getLogger(AuthService.class.getName());
    
    private UsuarioDAO usuarioDAO;
    
    public AuthService() {
        this.usuarioDAO = new UsuarioDAO();
    }
    
    public Usuario autenticarPorDNI(String dni) {
        try {
            Usuario usuario = usuarioDAO.buscarPorDNI(dni);
            if (usuario != null) {
                logger.info("Usuario autenticado: " + usuario.getDni());
            } else {
                logger.warning("Intento de autenticación fallido con DNI: " + dni);
            }
            return usuario;
        } catch (SQLException e) {
            logger.severe("Error en autenticación: " + e.getMessage());
            return null;
        }
    }
    
    public boolean verificarElegibilidad(Usuario usuario) {
        return usuario != null && !usuario.isHaVotado();
    }
    
    public boolean marcarComoVotado(int usuarioId) {
        try {
            boolean exito = usuarioDAO.marcarComoVotado(usuarioId);
            if (exito) {
                logger.info("Usuario marcado como votado: " + usuarioId);
            }
            return exito;
        } catch (SQLException e) {
            logger.severe("Error al marcar como votado: " + e.getMessage());
            return false;
        }
    }
}