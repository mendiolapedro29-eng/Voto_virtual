package com.mycompany.sistemavotacion.model.service;

import com.mycompany.sistemavotacion.model.Usuario;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class AuthService {
    private static final Logger logger = Logger.getLogger(AuthService.class.getName());
    
    // Base de datos simulada de usuarios
    private static final Map<String, Usuario> usuariosSimulados = new HashMap<>();
    
    static {
        // Inicializar algunos usuarios de prueba
        usuariosSimulados.put("12345678", new Usuario(1, "12345678", "Juan", "Pérez", false));
        usuariosSimulados.put("87654321", new Usuario(2, "87654321", "María", "Gómez", false));
        usuariosSimulados.put("11111111", new Usuario(3, "11111111", "Carlos", "López", true)); // Ya votó
        usuariosSimulados.put("22222222", new Usuario(4, "22222222", "Ana", "Rodríguez", false));
    }
    
    public Usuario autenticarPorDNI(String dni) {
        logger.info("🔐 Autenticando usuario con DNI: " + dni);
        
        Usuario usuario = usuariosSimulados.get(dni);
        if (usuario != null) {
            logger.info("✅ Usuario encontrado: " + usuario.getNombres() + " " + usuario.getApellidos());
        } else {
            logger.warning("❌ Usuario no encontrado para DNI: " + dni);
        }
        
        return usuario;
    }
    
    // Método para simular que un usuario ya votó
    public void marcarComoVotado(String dni) {
        Usuario usuario = usuariosSimulados.get(dni);
        if (usuario != null) {
            usuario.setHaVotado(true);
            logger.info("✅ Usuario " + dni + " marcado como votado");
        }
    }
}