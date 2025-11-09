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

@WebServlet(name = "AdmiServlet", urlPatterns = {"/admin/*"}) // ✅ CORREGIDO: agregué "/*"
public class AdmiServlet extends HttpServlet { // ✅ CORREGIDO: el nombre de la clase
    private AdminService adminService;
    
    @Override
    public void init() throws ServletException {
        this.adminService = new AdminService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo(); // ✅ CORREGIDO: usar pathInfo en lugar de parameter
        String action = "dashboard"; // Valor por defecto
        
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
            case "login":
                mostrarLoginAdmin(request, response); // ✅ NUEVO método para mostrar login
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
    
    // ✅ NUEVO método para mostrar la página de login
    private void mostrarLoginAdmin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/admin/login.jsp").forward(request, response); // ✅ CORREGIDA la ruta
    }
    
    private void mostrarDashboard(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Verificar si el administrador está logueado
        if (!verificarAdminLogueado(request)) {
            response.sendRedirect(request.getContextPath() + "/admin/login"); // ✅ CORREGIDA la redirección
            return;
        }
        
        try {
            // Obtener estadísticas para el dashboard
            Map<String, Object> estadisticas = adminService.obtenerEstadisticasGenerales(1); // Elección por defecto
            
            request.setAttribute("estadisticas", estadisticas);
            request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response); // ✅ CORREGIDA la ruta
            
        } catch (Exception e) {
            request.setAttribute("error", "Error al cargar dashboard: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error/500.jsp").forward(request, response); // ✅ CORREGIDA la ruta
        }
    }
    
    private void mostrarEstadisticas(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if (!verificarAdminLogueado(request)) {
            response.sendRedirect(request.getContextPath() + "/admin/login"); // ✅ CORREGIDA la redirección
            return;
        }
        
        try {
            Map<String, Object> estadisticas = adminService.obtenerEstadisticasGenerales(1);
            
            // Si es una petición AJAX, devolver JSON
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(JSONUtil.toJson(estadisticas));
                return;
            }
            
            request.setAttribute("estadisticas", estadisticas);
            request.getRequestDispatcher("/WEB-INF/views/admin/estadisticas.jsp").forward(request, response); // ✅ CORREGIDA la ruta
            
        } catch (Exception e) {
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            } else {
                request.setAttribute("error", "Error al cargar estadísticas: " + e.getMessage());
                request.getRequestDispatcher("/WEB-INF/views/error/500.jsp").forward(request, response); // ✅ CORREGIDA la ruta
            }
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
                session.setMaxInactiveInterval(30 * 60); // 30 minutos
                
                response.sendRedirect(request.getContextPath() + "/admin/dashboard"); // ✅ CORREGIDA la redirección
            } else {
                request.setAttribute("error", "Credenciales inválidas");
                request.getRequestDispatcher("/WEB-INF/views/admin/login.jsp").forward(request, response); // ✅ CORREGIDA la ruta
            }
            
        } catch (Exception e) {
            request.setAttribute("error", "Error en autenticación: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/admin/login.jsp").forward(request, response); // ✅ CORREGIDA la ruta
        }
    }
    
    private void logoutAdmin(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/admin/login"); // ✅ CORREGIDA la redirección
    }
    
    private void cargarDataset(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if (!verificarAdminLogueado(request)) {
            response.sendRedirect(request.getContextPath() + "/admin/login"); // ✅ CORREGIDA la redirección
            return;
        }
        
        try {
            // Aquí procesarías el archivo subido
            // Por ahora, simulamos una carga
            List<Usuario> usuarios = new ArrayList<>();
            
            // Ejemplo de usuarios de prueba
            usuarios.add(new Usuario("12345678", "Juan", "Pérez", new java.util.Date()));
            usuarios.add(new Usuario("87654321", "María", "Gómez", new java.util.Date()));
            
            boolean exito = adminService.cargarDatasetUsuarios(usuarios);
            
            if (exito) {
                request.setAttribute("mensaje", "Dataset cargado exitosamente");
            } else {
                request.setAttribute("error", "Error al cargar el dataset");
            }
            
            request.getRequestDispatcher("/WEB-INF/views/admin/carga-datos.jsp").forward(request, response); // ✅ CORREGIDO el nombre del archivo
            
        } catch (Exception e) {
            request.setAttribute("error", "Error al procesar dataset: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/admin/carga-datos.jsp").forward(request, response); // ✅ CORREGIDO el nombre del archivo
        }
    }
    
    private void generarReporte(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if (!verificarAdminLogueado(request)) {
            response.sendRedirect(request.getContextPath() + "/admin/login"); // ✅ CORREGIDA la redirección
            return;
        }
        
        try {
            // Generar reporte en diferentes formatos
            String formato = request.getParameter("formato");
            if (formato == null) formato = "pdf";
            
            boolean exito = adminService.generarReporte(1, formato, response);
            
            if (!exito) {
                request.setAttribute("error", "Error al generar reporte");
                request.getRequestDispatcher("/WEB-INF/views/admin/estadisticas.jsp").forward(request, response); // ✅ CORREGIDA la ruta
            }
            
        } catch (Exception e) {
            request.setAttribute("error", "Error al generar reporte: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/admin/estadisticas.jsp").forward(request, response); // ✅ CORREGIDA la ruta
        }
    }
    
    private void mostrarUsuarios(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if (!verificarAdminLogueado(request)) {
            response.sendRedirect(request.getContextPath() + "/admin/login"); // ✅ CORREGIDA la redirección
            return;
        }
        
        try {
            List<Usuario> usuarios = adminService.obtenerTodosUsuarios();
            request.setAttribute("usuarios", usuarios);
            request.getRequestDispatcher("/WEB-INF/views/admin/usuarios.jsp").forward(request, response); // ✅ CORREGIDA la ruta
            
        } catch (Exception e) {
            request.setAttribute("error", "Error al cargar usuarios: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error/500.jsp").forward(request, response); // ✅ CORREGIDA la ruta
        }
    }
    
    private void mostrarCargaDatos(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if (!verificarAdminLogueado(request)) {
            response.sendRedirect(request.getContextPath() + "/admin/login"); // ✅ CORREGIDA la redirección
            return;
        }
        
        request.getRequestDispatcher("/WEB-INF/views/admin/carga-datos.jsp").forward(request, response); // ✅ CORREGIDO el nombre del archivo
    }
    
    private boolean verificarAdminLogueado(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("adminLogueado") != null;
    }
}