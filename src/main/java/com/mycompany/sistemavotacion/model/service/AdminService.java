package com.mycompany.sistemavotacion.model.service;

import com.mycompany.sistemavotacion.model.dao.*;
import com.mycompany.sistemavotacion.model.*;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.mycompany.sistemavotacion.util.JSONUtil;

public class AdminService {
    private static final Logger logger = Logger.getLogger(AdminService.class.getName());
    
    private VotoDAO votoDAO;
    private UsuarioDAO usuarioDAO;
    private CandidateDAO candidateDAO;
    private EleccionDAO eleccionDAO;
    private AdminDAO adminDAO;
    
    public AdminService() {
        this.votoDAO = new VotoDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.candidateDAO = new CandidateDAO();
        this.eleccionDAO = new EleccionDAO();
        this.adminDAO = new AdminDAO();
    }
    
    public boolean autenticarAdmin(String username, String password) {
        try {
            // Hash de la contraseña para comparar
            String passwordHash = hashPassword(password);
            return adminDAO.verificarCredenciales(username, passwordHash);
        } catch (Exception e) {
            logger.severe("Error en autenticación de admin: " + e.getMessage());
            return false;
        }
    }
    
    public Map<String, Object> obtenerEstadisticasGenerales(int eleccionId) {
        Map<String, Object> estadisticas = new HashMap<>();
        
        try {
            // Total de votos
            List<Object[]> resultados = votoDAO.obtenerResultadosPorCandidato(eleccionId);
            int totalVotos = resultados.stream().mapToInt(r -> (Integer) r[1]).sum();
            
            // Total de usuarios registrados
            List<Usuario> usuarios = usuarioDAO.obtenerTodos();
            int totalUsuarios = usuarios.size();
            
            // Porcentaje de participación
            double participacion = totalUsuarios > 0 ? (double) totalVotos / totalUsuarios * 100 : 0;
            
            estadisticas.put("totalVotos", totalVotos);
            estadisticas.put("totalUsuarios", totalUsuarios);
            estadisticas.put("participacion", String.format("%.2f%%", participacion));
            estadisticas.put("resultadosPorCandidato", resultados);
            
            return estadisticas;
            
        } catch (SQLException e) {
            logger.severe("Error al obtener estadísticas: " + e.getMessage());
            throw new RuntimeException("Error en el servicio de administración", e);
        }
    }
    
    public List<Usuario> obtenerTodosUsuarios() {
        try {
            return usuarioDAO.obtenerTodos();
        } catch (SQLException e) {
            logger.severe("Error al obtener usuarios: " + e.getMessage());
            throw new RuntimeException("Error al obtener usuarios", e);
        }
    }
    
    public boolean cargarDatasetUsuarios(List<Usuario> usuarios) {
        int exitosos = 0;
        
        for (Usuario usuario : usuarios) {
            try {
                if (usuarioDAO.insertar(usuario)) {
                    exitosos++;
                }
            } catch (SQLException e) {
                logger.warning("Error al insertar usuario " + usuario.getDni() + ": " + e.getMessage());
            }
        }
        
        logger.info("Dataset cargado: " + exitosos + "/" + usuarios.size() + " usuarios insertados");
        return exitosos > 0;
    }
    
    public boolean generarReporte(int eleccionId, String formato, HttpServletResponse response) {
        try {
            // Aquí implementarías la generación de reportes en PDF, Excel, etc.
            // Por ahora, solo simulamos
            Map<String, Object> estadisticas = obtenerEstadisticasGenerales(eleccionId);
            
            switch (formato.toLowerCase()) {
                case "pdf":
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "attachment; filename=reporte_eleccion.pdf");
                    // Lógica para generar PDF
                    break;
                case "excel":
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "attachment; filename=reporte_eleccion.xlsx");
                    // Lógica para generar Excel
                    break;
                case "json":
                    response.setContentType("application/json");
                    response.getWriter().write(JSONUtil.toJson(estadisticas));
                    return true;
                default:
                    return false;
            }
            
            return true;
            
        } catch (Exception e) {
            logger.severe("Error al generar reporte: " + e.getMessage());
            return false;
        }
    }
    
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear contraseña", e);
        }
    }
}