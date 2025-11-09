package com.mycompany.sistemavotacion.model.dao;

import com.mycompany.sistemavotacion.util.DatabaseUtil;
import java.sql.*;

public class AdminDAO {
    
    private static final String VERIFICAR_CREDENCIALES = 
        "SELECT COUNT(*) FROM administradores WHERE username = ? AND password_hash = ? AND activo = true";
    
    public boolean verificarCredenciales(String username, String passwordHash) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(VERIFICAR_CREDENCIALES);
            stmt.setString(1, username);
            stmt.setString(2, passwordHash);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
            
        } finally {
            DatabaseUtil.close(rs, stmt, conn);
        }
    }
}