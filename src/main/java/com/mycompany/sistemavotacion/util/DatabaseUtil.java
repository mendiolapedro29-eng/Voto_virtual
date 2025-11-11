package com.mycompany.sistemavotacion.util;

import java.sql.*;
import java.util.logging.Logger;

public class DatabaseUtil {
    private static final Logger logger = Logger.getLogger(DatabaseUtil.class.getName());
    
    // Modo simulación activado
    private static final boolean MODO_SIMULACION = true;
    
    static {
        if (MODO_SIMULACION) {
            logger.info("✅ MODO SIMULACIÓN ACTIVADO - No se usarán conexiones reales a BD");
        } else {
            logger.warning("⚠️  MODO PRODUCCIÓN - Se intentarán conexiones reales a BD");
        }
    }
    
    /**
     * Obtiene una conexión SIMULADA a la base de datos
     */
    public static Connection getConnection() throws SQLException {
        if (MODO_SIMULACION) {
            logger.info("📡 [SIMULACIÓN] Solicitando conexión a BD - Retornando conexión simulada");
            // Retornamos una conexión nula pero manejable
            return null;
        } else {
            // Código original para cuando actives la BD real
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String URL = "jdbc:mysql://localhost:3306/sistema_votacion";
                String USER = "root";
                String PASSWORD = "";
                return DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver MySQL no encontrado", e);
            }
        }
    }
    
    /**
     * Cierra recursos SIMULADOS
     */
    public static void close(ResultSet rs, Statement stmt, Connection conn) {
        if (MODO_SIMULACION) {
            logger.info("🔒 [SIMULACIÓN] Cerrando recursos: " + 
                       (rs != null ? "ResultSet " : "") +
                       (stmt != null ? "Statement " : "") +
                       (conn != null ? "Connection" : ""));
            // En modo simulación, no hay nada que cerrar realmente
            return;
        }
        
        // Código original para BD real
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            logger.warning("Error al cerrar ResultSet: " + e.getMessage());
        }
        
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            logger.warning("Error al cerrar Statement: " + e.getMessage());
        }
        
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            logger.warning("Error al cerrar Connection: " + e.getMessage());
        }
    }
    
    /**
     * Cierra Statement y Connection SIMULADOS
     */
    public static void close(Statement stmt, Connection conn) {
        close(null, stmt, conn);
    }
    
    /**
     * Cierra solo Connection SIMULADO
     */
    public static void close(Connection conn) {
        close(null, null, conn);
    }
    
    /**
     * Verifica si la conexión está activa (SIMULADO)
     */
    public static boolean isConnectionValid(Connection conn) {
        if (MODO_SIMULACION) {
            logger.info("🔍 [SIMULACIÓN] Verificando conexión - Siempre válida en simulación");
            return true; // En simulación, siempre es válida
        }
        
        if (conn == null) {
            return false;
        }
        
        try {
            return conn.isValid(2);
        } catch (SQLException e) {
            logger.warning("Error al verificar conexión: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * NUEVO: Método para verificar el modo de operación
     */
    public static boolean isModoSimulacion() {
        return MODO_SIMULACION;
    }
    
    /**
     * NUEVO: Método para obtener información del estado del sistema
     */
    public static String getEstadoSistema() {
        if (MODO_SIMULACION) {
            return "🟢 SISTEMA EN MODO SIMULACIÓN - Usando datos de prueba";
        } else {
            return "🔴 SISTEMA EN MODO PRODUCCIÓN - Conectado a base de datos real";
        }
    }
}