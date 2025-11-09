package com.mycompany.sistemavotacion.model.dao;

import com.mycompany.sistemavotacion.model.Voto;
import com.mycompany.sistemavotacion.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class VotoDAO {
    private static final Logger logger = Logger.getLogger(VotoDAO.class.getName());
    
    private static final String INSERT_VOTO = 
        "INSERT INTO votos (usuario_id, candidato_id, eleccion_id, hash_voto, ip_votacion) VALUES (?, ?, ?, ?, ?)";
    private static final String COUNT_VOTOS_POR_CANDIDATO = 
        "SELECT candidato_id, COUNT(*) as total FROM votos WHERE eleccion_id = ? GROUP BY candidato_id";
    private static final String COUNT_VOTOS_POR_REGION = 
        "SELECT c.region_id, COUNT(*) as total FROM votos v JOIN candidatos c ON v.candidato_id = c.id WHERE v.eleccion_id = ? GROUP BY c.region_id";
    private static final String VERIFICAR_USUARIO_YA_VOTO = 
        "SELECT COUNT(*) FROM votos WHERE usuario_id = ? AND eleccion_id = ?";
    private static final String OBTENER_TODOS_VOTOS = 
        "SELECT * FROM votos ORDER BY timestamp DESC";
    
    public boolean insertar(Voto voto) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(INSERT_VOTO, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setInt(1, voto.getUsuarioId());
            stmt.setInt(2, voto.getCandidatoId());
            stmt.setInt(3, voto.getEleccionId());
            stmt.setString(4, voto.getHashVoto());
            stmt.setString(5, voto.getIpVotacion());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        voto.setId(generatedKeys.getInt(1));
                    }
                }
            }
            
            return affectedRows > 0;
            
        } finally {
            DatabaseUtil.close(null, stmt, conn);
        }
    }
    
    public boolean usuarioYaVoto(int usuarioId, int eleccionId) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(VERIFICAR_USUARIO_YA_VOTO);
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, eleccionId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
            
        } finally {
            DatabaseUtil.close(rs, stmt, conn);
        }
    }
    
    public List<Object[]> obtenerResultadosPorCandidato(int eleccionId) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Object[]> resultados = new ArrayList<>();
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(COUNT_VOTOS_POR_CANDIDATO);
            stmt.setInt(1, eleccionId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Object[] resultado = new Object[2];
                resultado[0] = rs.getInt("candidato_id");
                resultado[1] = rs.getInt("total");
                resultados.add(resultado);
            }
            return resultados;
            
        } finally {
            DatabaseUtil.close(rs, stmt, conn);
        }
    }
    
    public List<Voto> obtenerTodosVotos() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Voto> votos = new ArrayList<>();
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(OBTENER_TODOS_VOTOS);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                votos.add(mapResultSetToVoto(rs));
            }
            return votos;
            
        } finally {
            DatabaseUtil.close(rs, stmt, conn);
        }
    }
    
    private Voto mapResultSetToVoto(ResultSet rs) throws SQLException {
        Voto voto = new Voto();
        voto.setId(rs.getInt("id"));
        voto.setUsuarioId(rs.getInt("usuario_id"));
        voto.setCandidatoId(rs.getInt("candidato_id"));
        voto.setEleccionId(rs.getInt("eleccion_id"));
        voto.setTimestamp(rs.getTimestamp("timestamp"));
        voto.setHashVoto(rs.getString("hash_voto"));
        voto.setIpVotacion(rs.getString("ip_votacion"));
        return voto;
    }
}