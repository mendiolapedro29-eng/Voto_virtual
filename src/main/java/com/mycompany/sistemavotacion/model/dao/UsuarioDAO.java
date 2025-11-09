package com.mycompany.sistemavotacion.model.dao;

import com.mycompany.sistemavotacion.model.Usuario;
import com.mycompany.sistemavotacion.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UsuarioDAO {
    private static final Logger logger = Logger.getLogger(UsuarioDAO.class.getName());
    
    private static final String FIND_BY_DNI = 
        "SELECT * FROM usuarios WHERE dni = ?";
    private static final String UPDATE_VOTO_STATUS = 
        "UPDATE usuarios SET ha_votado = true WHERE id = ?";
    private static final String INSERT_USUARIO = 
        "INSERT INTO usuarios (dni, nombres, apellidos, fecha_nacimiento, distrito, provincia, departamento) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL = 
        "SELECT * FROM usuarios ORDER BY apellidos, nombres";
    
    public Usuario buscarPorDNI(String dni) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(FIND_BY_DNI);
            stmt.setString(1, dni);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToUsuario(rs);
            }
            return null;
            
        } finally {
            DatabaseUtil.close(rs, stmt, conn);
        }
    }
    
    public boolean marcarComoVotado(int usuarioId) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(UPDATE_VOTO_STATUS);
            stmt.setInt(1, usuarioId);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } finally {
            DatabaseUtil.close(null, stmt, conn);
        }
    }
    
    public boolean insertar(Usuario usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(INSERT_USUARIO, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, usuario.getDni());
            stmt.setString(2, usuario.getNombres());
            stmt.setString(3, usuario.getApellidos());
            stmt.setDate(4, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
            stmt.setString(5, usuario.getDistrito());
            stmt.setString(6, usuario.getProvincia());
            stmt.setString(7, usuario.getDepartamento());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        usuario.setId(generatedKeys.getInt(1));
                    }
                }
            }
            
            return affectedRows > 0;
            
        } finally {
            DatabaseUtil.close(null, stmt, conn);
        }
    }
    
    public List<Usuario> obtenerTodos() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> usuarios = new ArrayList<>();
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(FIND_ALL);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                usuarios.add(mapResultSetToUsuario(rs));
            }
            return usuarios;
            
        } finally {
            DatabaseUtil.close(rs, stmt, conn);
        }
    }
    
    private Usuario mapResultSetToUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setDni(rs.getString("dni"));
        usuario.setNombres(rs.getString("nombres"));
        usuario.setApellidos(rs.getString("apellidos"));
        usuario.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
        usuario.setHaVotado(rs.getBoolean("ha_votado"));
        usuario.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        usuario.setDistrito(rs.getString("distrito"));
        usuario.setProvincia(rs.getString("provincia"));
        usuario.setDepartamento(rs.getString("departamento"));
        return usuario;
    }
}