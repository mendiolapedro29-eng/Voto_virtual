package com.mycompany.sistemavotacion.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.mycompany.sistemavotacion.model.service.VotacionService;
import com.mycompany.sistemavotacion.util.SecurityUtil;
import com.mycompany.sistemavotacion.model.Usuario;
import com.mycompany.sistemavotacion.model.service.AuthService;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "VotacionServlet", urlPatterns = {"/votacion/*"})
public class VotacionServlet extends HttpServlet {
    
    private VotacionService votacionService;
    private AuthService authService;
    private SecurityUtil securityUtil;
    
    @Override
    public void init() throws ServletException {
        this.votacionService = new VotacionService();
        this.authService = new AuthService();
        this.securityUtil = new SecurityUtil();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        System.out.println("PathInfo recibido: " + pathInfo); // Para debug
        
        if (pathInfo == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        // ✅ CORREGIDO: Manejar la ruta específica
        if ("/seleccion-region".equals(pathInfo)) {
            mostrarSeleccionRegion(request, response);
            return;
        }
        
        if ("/candidatos".equals(pathInfo)) {
            mostrarSeleccionCandidatos(request, response);
            return;
        }
        
        if ("/confirmacion".equals(pathInfo)) {
            mostrarConfirmacion(request, response);
            return;
        }
        
        // Si no coincide ninguna ruta conocida
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
    
    // ✅ CORREGIDO: Método simplificado para selección de región
    private void mostrarSeleccionRegion(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Datos de ejemplo para regiones
            Map<String, String> regiones = new HashMap<>();
            regiones.put("1", "Lima Metropolitana Español");
            regiones.put("2", "Apurímac - Huancavelica - Ayacucho (Español - Quechua)");
            regiones.put("3", "Callao - Pacarán (Español)");
            regiones.put("4", "Áncash (Español)");
            regiones.put("5", "Cusco - Madre de Dios (Español - Quechua)");
            
            request.setAttribute("regiones", regiones);
            request.getRequestDispatcher("/WEB-INF/views/components/seleccionRegion.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al cargar regiones: " + e.getMessage());
        }
    }
    
    private void mostrarSeleccionCandidatos(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String regionId = request.getParameter("regionId");
            if (regionId == null || regionId.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/votacion/seleccion-region");
                return;
            }
            
            // Datos de ejemplo para candidatos
            Map<String, String> candidatos = new HashMap<>();
            
            switch(regionId) {
                case "1": // Lima
                    candidatos.put("101", "Juan Pérez - Partido Azul");
                    candidatos.put("102", "María García - Partido Verde");
                    candidatos.put("103", "Carlos López - Partido Rojo");
                    break;
                case "2": // Arequipa
                    candidatos.put("201", "Ana Martínez - Partido Azul");
                    candidatos.put("202", "Luis Rodríguez - Partido Verde");
                    break;
                default:
                    candidatos.put("301", "Candidato Regional - Partido Único");
            }
            
            request.setAttribute("candidatos", candidatos);
            request.setAttribute("regionId", regionId);
            request.setAttribute("regionNombre", obtenerNombreRegion(regionId));
            request.getRequestDispatcher("/WEB-INF/views/components/seleccionCandidatos.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al cargar candidatos: " + e.getMessage());
        }
    }
    
    private void mostrarConfirmacion(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.getRequestDispatcher("/WEB-INF/views/components/confirmacionVoto.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Lógica para POST (registrar votos)
        response.sendRedirect(request.getContextPath() + "/votacion/confirmacion");
    }
    
    // ✅ AÑADIDO: Método helper para nombres de región
    private String obtenerNombreRegion(String regionId) {
        switch(regionId) {
            case "1": return "Lima Metropolitana";
            case "2": return "Apurímac - Huancavelica - Ayacucho";
            case "3": return "Callao - Pacarán";
            case "4": return "Áncash";
            case "5": return "Cusco - Madre de Dios";
            default: return "Región " + regionId;
        }
    }
}