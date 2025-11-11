package com.mycompany.sistemavotacion.controller;

import com.mycompany.sistemavotacion.model.service.AdminService;
import com.mycompany.sistemavotacion.model.Usuario;
import com.mycompany.sistemavotacion.util.JSONUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@WebServlet(name = "AdminDashboardServlet", urlPatterns = {"/admin/*"})
public class AdminDashboardServlet extends HttpServlet {
    private AdminService adminService;
    
    @Override
    public void init() throws ServletException {
        this.adminService = new AdminService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        String action = "dashboard";
        
        if (pathInfo != null) {
            switch (pathInfo) {
                case "/dashboard":
                    action = "dashboard";
                    break;
                case "/estadisticas":
                    action = "estadisticas";
                    break;
                case "/usuarios":
                    action = "usuarios";
                    break;
                case "/carga-datos":
                    action = "carga-datos";
                    break;
                case "/login":
                    action = "login";
                    break;
                case "/historial":
                    action = "historial"; // ✅ NUEVO: agregar historial
                    break;
            }
        }
        
        switch (action) {
            case "dashboard":
                mostrarDashboard(request, response);
                break;
            case "estadisticas":
                mostrarEstadisticas(request, response);
                break;
            case "usuarios":
                mostrarUsuarios(request, response);
                break;
            case "carga-datos":
                mostrarCargaDatos(request, response);
                break;
            case "historial": // ✅ NUEVO: caso para historial
                mostrarHistorial(request, response);
                break;
            case "login":
                mostrarLoginAdmin(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }
        
        switch (action) {
            case "login":
                loginAdmin(request, response);
                break;
            case "cargar-dataset":
                cargarDataset(request, response);
                break;
            case "generar-reporte":
                generarReporte(request, response);
                break;
            case "logout":
                logoutAdmin(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    // ✅ CORREGIDO: Rutas según tu estructura
    private void mostrarLoginAdmin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/adminLogin.jsp").forward(request, response);
    }
    
    private void mostrarDashboard(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if (!verificarAdminLogueado(request)) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        
        try {
            Map<String, Object> estadisticas = adminService.obtenerEstadisticasGenerales(1);
            request.setAttribute("estadisticas", estadisticas);
            request.getRequestDispatcher("/adminDashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Error al cargar dashboard: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    private void mostrarEstadisticas(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if (!verificarAdminLogueado(request)) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        
        try {
            Map<String, Object> estadisticas = adminService.obtenerEstadisticasGenerales(1);
            
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(JSONUtil.toJson(estadisticas));
                return;
            }
            
            request.setAttribute("estadisticas", estadisticas);
            request.getRequestDispatcher("/adminEstadisticas.jsp").forward(request, response);
            
        } catch (Exception e) {
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            } else {
                request.setAttribute("error", "Error al cargar estadísticas: " + e.getMessage());
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        }
    }
    
    // ✅ NUEVO: Método para historial
    private void mostrarHistorial(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if (!verificarAdminLogueado(request)) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        
        try {
            // Obtener datos del historial
            List<Map<String, Object>> historial = adminService.obtenerHistorialVotos();
            request.setAttribute("historial", historial);
            request.getRequestDispatcher("/adminHistorial.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Error al cargar historial: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    private void loginAdmin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try {
            boolean autenticado = adminService.autenticarAdmin(username, password);
            
            if (autenticado) {
                HttpSession session = request.getSession();
                session.setAttribute("adminLogueado", true);
                session.setAttribute("adminUsername", username);
                session.setMaxInactiveInterval(30 * 60);
                
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                request.setAttribute("error", "Credenciales inválidas");
                request.getRequestDispatcher("/adminLogin.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            request.setAttribute("error", "Error en autenticación: " + e.getMessage());
            request.getRequestDispatcher("/adminLogin.jsp").forward(request, response);
        }
    }
    
    private void logoutAdmin(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/admin/login");
    }
    
    private void cargarDataset(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if (!verificarAdminLogueado(request)) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        
        try {
            List<Usuario> usuarios = new ArrayList<>();
            boolean exito = adminService.cargarDatasetUsuarios(usuarios);
            
            if (exito) {
                request.setAttribute("mensaje", "Dataset cargado exitosamente");
            } else {
                request.setAttribute("error", "Error al cargar el dataset");
            }
            
            request.getRequestDispatcher("/adminCargaDatos.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Error al procesar dataset: " + e.getMessage());
            request.getRequestDispatcher("/adminCargaDatos.jsp").forward(request, response);
        }
    }
    
    private void generarReporte(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if (!verificarAdminLogueado(request)) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        
        try {
            String formato = request.getParameter("formato");
            if (formato == null) formato = "pdf";
            
            boolean exito = adminService.generarReporte(1, formato, response);
            
            if (!exito) {
                request.setAttribute("error", "Error al generar reporte");
                request.getRequestDispatcher("/adminEstadisticas.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            request.setAttribute("error", "Error al generar reporte: " + e.getMessage());
            request.getRequestDispatcher("/adminEstadisticas.jsp").forward(request, response);
        }
    }
    
    private void mostrarUsuarios(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if (!verificarAdminLogueado(request)) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        
        try {
            List<Usuario> usuarios = adminService.obtenerTodosUsuarios();
            request.setAttribute("usuarios", usuarios);
            request.getRequestDispatcher("/adminUsuarios.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Error al cargar usuarios: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    private void mostrarCargaDatos(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if (!verificarAdminLogueado(request)) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        
        request.getRequestDispatcher("/adminCargaDatos.jsp").forward(request, response);
    }
    
    private boolean verificarAdminLogueado(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("adminLogueado") != null;
    }
}