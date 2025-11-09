package com.mycompany.sistemavotacion.util;

import java.sql.*;
import java.util.logging.Logger;

public class DatabaseUtil {
    private static final Logger logger = Logger.getLogger(DatabaseUtil.class.getName());
    
    // Configuración de la base de datos - AJUSTA ESTOS VALORES SEGÚN TU BD
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_votacion";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    static {
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            logger.info("Driver MySQL cargado correctamente");
        } catch (ClassNotFoundException e) {
            logger.severe("Error al cargar el driver MySQL: " + e.getMessage());
            throw new RuntimeException("No se pudo cargar el driver de la base de datos", e);
        }
    }
    
    /**
     * Obtiene una conexión a la base de datos
     * MÉTODO ESTÁTICO - Esto es importante
     */
    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.info("Conexión a BD establecida");
            return conn;
        } catch (SQLException e) {
            logger.severe("Error al conectar a la BD: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Cierra ResultSet, Statement y Connection
     */
    public static void close(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            logger.warning("Error al cerrar ResultSet: " + e.getMessage());
        }
        
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            logger.warning("Error al cerrar Statement: " + e.getMessage());
        }
        
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.warning("Error al cerrar Connection: " + e.getMessage());
        }
    }
    
    /**
     * Cierra Statement y Connection
     */
    public static void close(Statement stmt, Connection conn) {
        close(null, stmt, conn);
    }
    
    /**
     * Cierra solo Connection
     */
    public static void close(Connection conn) {
        close(null, null, conn);
    }
    
    /**
     * Verifica si la conexión está activa
     */
    public static boolean isConnectionValid(Connection conn) {
        if (conn == null) {
            return false;
        }
        
        try {
            return conn.isValid(2); // 2 segundos de timeout
        } catch (SQLException e) {
            logger.warning("Error al verificar conexión: " + e.getMessage());
            return false;
        }
    }
}