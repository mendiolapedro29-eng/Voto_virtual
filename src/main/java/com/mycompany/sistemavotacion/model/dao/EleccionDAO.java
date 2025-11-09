package com.mycompany.sistemavotacion.model.dao;

import com.mycompany.sistemavotacion.model.Eleccion;
import com.mycompany.sistemavotacion.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class EleccionDAO {
    private static final Logger logger = Logger.getLogger(EleccionDAO.class.getName());
    
    private static final String FIND_ACTIVA = 
        "SELECT * FROM elecciones WHERE activa = true ORDER BY fecha_eleccion DESC LIMIT 1";
    private static final String FIND_BY_ID = 
        "SELECT * FROM elecciones WHERE id = ?";
    private static final String FIND_ALL = 
        "SELECT * FROM elecciones ORDER BY fecha_eleccion DESC";
    
    public Eleccion obtenerEleccionActiva() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(FIND_ACTIVA);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToEleccion(rs);
            }
            return null;
            
        } finally {
            DatabaseUtil.close(rs, stmt, conn);
        }
    }
    
    public Eleccion obtenerPorId(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(FIND_BY_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToEleccion(rs);
            }
            return null;
            
        } finally {
            DatabaseUtil.close(rs, stmt, conn);
        }
    }
    
    public List<Eleccion> obtenerTodas() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Eleccion> elecciones = new ArrayList<>();
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(FIND_ALL);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                elecciones.add(mapResultSetToEleccion(rs));
            }
            return elecciones;
            
        } finally {
            DatabaseUtil.close(rs, stmt, conn);
        }
    }
    
    private Eleccion mapResultSetToEleccion(ResultSet rs) throws SQLException {
        Eleccion eleccion = new Eleccion();
        eleccion.setId(rs.getInt("id"));
        eleccion.setNombre(rs.getString("nombre"));
        eleccion.setFechaEleccion(rs.getDate("fecha_eleccion"));
        eleccion.setActiva(rs.getBoolean("activa"));
        eleccion.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        return eleccion;
    }
}