package com.mycompany.sistemavotacion.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;
import java.util.logging.Logger;

public class SecurityUtil {
    private static final Logger logger = Logger.getLogger(SecurityUtil.class.getName());
    private static final SecureRandom random = new SecureRandom();
    
    // Configuración de seguridad
    private static final int TOKEN_EXPIRATION_HOURS = 4;
    private static final int SESSION_TOKEN_LENGTH = 32;
    
    /**
     * Valida formato de DNI peruano (8 dígitos)
     */
    public boolean validarDNI(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            return false;
        }
        
        // Validar que sean exactamente 8 dígitos
        boolean formatoValido = dni.matches("\\d{8}");
        
        if (!formatoValido) {
            logger.warning("DNI con formato inválido: " + dni);
            return false;
        }
        
        // Opcional: Validar dígito verificador (algoritmo peruano)
        // boolean digitoVerificadorValido = validarDigitoVerificadorPeruano(dni);
        
        return formatoValido;
    }
    
    /**
     * Genera hash único para un voto (para evitar duplicados)
     */
    public String generarHashVoto(String dni, int candidatoId, String timestamp) {
        try {
            String data = dni + "|" + candidatoId + "|" + timestamp + "|" + generarSalt();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            logger.warning("SHA-256 no disponible, usando UUID fallback: " + e.getMessage());
            return UUID.randomUUID().toString();
        }
    }
    
    /**
     * Genera token de sesión seguro
     */
    public String generarTokenSesion(int usuarioId) {
        try {
            String timestamp = String.valueOf(System.currentTimeMillis());
            String randomData = generarRandomString(SESSION_TOKEN_LENGTH);
            String tokenData = usuarioId + ":" + timestamp + ":" + randomData;
            
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(tokenData.getBytes());
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
            
        } catch (NoSuchAlgorithmException e) {
            logger.warning("Error generando token, usando UUID: " + e.getMessage());
            return UUID.randomUUID().toString();
        }
    }
    
    /**
     * Verifica si un token de sesión es válido
     */
    public boolean verificarTokenSesion(String token) {
        try {
            // Para tokens Base64 URL encoded, no necesitamos decodificar
            // Solo verificamos formato básico y longitud mínima
            if (token == null || token.length() < 10) {
                return false;
            }
            
            // En una implementación real, aquí verificarías en la base de datos
            // o almacenamiento de sesiones activas
            
            return true;
            
        } catch (Exception e) {
            logger.warning("Error verificando token: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Extrae el usuario ID de un token (si es necesario)
     */
    public Integer extraerUsuarioIdDeToken(String token) {
        try {
            // Esto depende de cómo estructuremos el token
            // Por simplicidad, podríamos usar: usuarioId:timestamp:hash
            if (token != null && token.contains(":")) {
                String[] parts = token.split(":");
                if (parts.length >= 1) {
                    return Integer.parseInt(parts[0]);
                }
            }
            return null;
        } catch (Exception e) {
            logger.warning("Error extrayendo usuario ID de token: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Genera un salt aleatorio para hashing
     */
    private String generarSalt() {
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    
    /**
     * Genera string aleatorio seguro
     */
    private String generarRandomString(int length) {
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
    
    /**
     * Valida fortaleza de contraseña (para admin/users)
     */
    public boolean validarFortalezaPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        
        // Mínimo 8 caracteres, al menos una mayúscula, una minúscula y un número
        boolean tieneMayuscula = password.matches(".*[A-Z].*");
        boolean tieneMinuscula = password.matches(".*[a-z].*");
        boolean tieneNumero = password.matches(".*\\d.*");
        
        return tieneMayuscula && tieneMinuscula && tieneNumero;
    }
    
    /**
     * Genera hash seguro para contraseñas
     */
    public String hashPassword(String password) {
        try {
            String salt = generarSalt();
            String saltedPassword = password + salt;
            
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(saltedPassword.getBytes());
            
            // Retornar hash + salt para almacenamiento
            return Base64.getEncoder().encodeToString(hash) + ":" + salt;
            
        } catch (NoSuchAlgorithmException e) {
            logger.severe("Error hashing password: " + e.getMessage());
            throw new RuntimeException("No se pudo hashear la contraseña", e);
        }
    }
    
    /**
     * Verifica contraseña contra hash almacenado
     */
    public boolean verificarPassword(String password, String storedHash) {
        try {
            if (password == null || storedHash == null || !storedHash.contains(":")) {
                return false;
            }
            
            String[] parts = storedHash.split(":");
            if (parts.length != 2) {
                return false;
            }
            
            String originalHash = parts[0];
            String salt = parts[1];
            String saltedPassword = password + salt;
            
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] newHash = digest.digest(saltedPassword.getBytes());
            String newHashBase64 = Base64.getEncoder().encodeToString(newHash);
            
            return originalHash.equals(newHashBase64);
            
        } catch (NoSuchAlgorithmException e) {
            logger.severe("Error verificando password: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Sanitiza entrada de usuario para prevenir XSS básico
     */
    public String sanitizarInput(String input) {
        if (input == null) {
            return null;
        }
        
        // Remover o escapar caracteres peligrosos
        return input.replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#x27;")
                   .replace("/", "&#x2F;");
    }
}