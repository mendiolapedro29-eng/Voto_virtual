
package com.mycompany.sistemavotacion.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public class SecurityUtil {
    
    public boolean validarDNI(String dni) {
        return dni != null && dni.matches("\\d{8}");
    }
    
    public String generarHashVoto(String dni, int candidatoId, String timestamp) {
        try {
            String data = dni + candidatoId + timestamp;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            // Fallback a UUID si SHA-256 no está disponible
            return UUID.randomUUID().toString();
        }
    }
    
    public String generarToken(int usuarioId) {
        String tokenData = usuarioId + ":" + System.currentTimeMillis();
        return Base64.getEncoder().encodeToString(tokenData.getBytes());
    }
    
    public boolean verificarToken(String token) {
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            String[] parts = decoded.split(":");
            if (parts.length == 2) {
                long timestamp = Long.parseLong(parts[1]);
                // Token válido por 4 horas
                return (System.currentTimeMillis() - timestamp) < (4 * 60 * 60 * 1000);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}