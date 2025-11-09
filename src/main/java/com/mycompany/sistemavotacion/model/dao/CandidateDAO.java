package com.mycompany.sistemavotacion.model.dao;

import com.mycompany.sistemavotacion.model.Candidate;
import com.mycompany.sistemavotacion.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CandidateDAO {
    private static final Logger logger = Logger.getLogger(CandidateDAO.class.getName());
    
    private static final String FIND_BY_ELECCION = 
        "SELECT * FROM candidatos WHERE eleccion_id = ? AND activo = true";
    private static final String FIND_BY_ID = 
        "SELECT * FROM candidatos WHERE id = ?";
    private static final String FIND_BY_CARGO = 
        "SELECT * FROM candidatos WHERE eleccion_id = ? AND cargo = ? AND activo = true";
    private static final String FIND_ALL = 
        "SELECT * FROM candidatos WHERE activo = true ORDER BY cargo, nombre";
    
    public List<Candidate> obtenerPorEleccion(int eleccionId) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Candidate> candidatos = new ArrayList<>();
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(FIND_BY_ELECCION);
            stmt.setInt(1, eleccionId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                candidatos.add(mapResultSetToCandidate(rs));
            }
            return candidatos;
            
        } finally {
            DatabaseUtil.close(rs, stmt, conn);
        }
    }
    
    public List<Candidate> obtenerPorCargo(int eleccionId, String cargo) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Candidate> candidatos = new ArrayList<>();
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(FIND_BY_CARGO);
            stmt.setInt(1, eleccionId);
            stmt.setString(2, cargo);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                candidatos.add(mapResultSetToCandidate(rs));
            }
            return candidatos;
            
        } finally {
            DatabaseUtil.close(rs, stmt, conn);
        }
    }
    
    public Candidate obtenerPorId(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(FIND_BY_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToCandidate(rs);
            }
            return null;
            
        } finally {
            DatabaseUtil.close(rs, stmt, conn);
        }
    }
    
    public List<Candidate> obtenerTodos() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Candidate> candidatos = new ArrayList<>();
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(FIND_ALL);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                candidatos.add(mapResultSetToCandidate(rs));
            }
            return candidatos;
            
        } finally {
            DatabaseUtil.close(rs, stmt, conn);
        }
    }
    
    private Candidate mapResultSetToCandidate(ResultSet rs) throws SQLException {
        Candidate candidate = new Candidate();
        candidate.setId(rs.getInt("id"));
        candidate.setNombre(rs.getString("nombre"));
        candidate.setPartidoPolitico(rs.getString("partido_politico"));
        candidate.setCargo(rs.getString("cargo"));
        candidate.setRegionId(rs.getInt("region_id"));
        candidate.setFoto(rs.getString("foto"));
        candidate.setPropuestas(rs.getString("propuestas"));
        candidate.setActivo(rs.getBoolean("activo"));
        candidate.setEleccionId(rs.getInt("eleccion_id"));
        return candidate;
    }
}