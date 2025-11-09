package com.mycompany.sistemavotacion.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.mycompany.sistemavotacion.model.service.AuthService;
import com.mycompany.sistemavotacion.util.SecurityUtil;
import com.mycompany.sistemavotacion.model.Usuario;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login/*"}) // ✅ CORREGIDO: patrón más limpio
public class LoginServlet extends HttpServlet {

    private AuthService authService;
    private SecurityUtil securityUtil;
    
    @Override
    public void init() throws ServletException {
        this.authService = new AuthService();
        this.securityUtil = new SecurityUtil();
    }
    
    // ✅ AÑADIDO: Método doGet para mostrar la página de login
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/components/login.jsp").forward(request, response); // ✅ CORREGIDA ruta
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String dni = request.getParameter("dni");
        String tipo = request.getParameter("tipo"); // "usuario" o "admin"
        
        try {
            // ✅ AÑADIDO: Lógica para login de administrador
            if ("admin".equals(tipo)) {
                loginAdmin(request, response);
                return;
            }
            
            // Lógica para login de usuario normal
            loginUsuario(request, response, dni);
            
        } catch (Exception e) {
            request.setAttribute("error", "Error en el sistema: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/components/login.jsp").forward(request, response); // ✅ CORREGIDA ruta
        }
    }
    
    private void loginUsuario(HttpServletRequest request, HttpServletResponse response, String dni) 
            throws ServletException, IOException {
        
        // Validar DNI
        if (!securityUtil.validarDNI(dni)) {
            request.setAttribute("error", "DNI inválido. Debe contener 8 dígitos numéricos.");
            request.getRequestDispatcher("/WEB-INF/views/components/login.jsp").forward(request, response);
            return;
        }
        
        // Autenticar usuario
        Usuario usuario = authService.autenticarPorDNI(dni);
        
        if (usuario != null && !usuario.isHaVotado()) {
            // Crear sesión de usuario
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
            session.setAttribute("usuarioLogueado", true);
            session.setAttribute("tipoUsuario", "ciudadano");
            session.setAttribute("token", securityUtil.generarToken(usuario.getId()));
            session.setMaxInactiveInterval(30 * 60); // 30 minutos
            
            // Redirigir a selección de región
            response.sendRedirect(request.getContextPath() + "/votacion/seleccion-region"); // ✅ CORREGIDA redirección
            
        } else if (usuario != null && usuario.isHaVotado()) {
            request.setAttribute("error", "Ya ha ejercido su derecho al voto");
            request.getRequestDispatcher("/WEB-INF/views/components/login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "DNI no encontrado en el padrón electoral");
            request.getRequestDispatcher("/WEB-INF/views/components/login.jsp").forward(request, response);
        }
    }
    
    // ✅ AÑADIDO: Método para login de administrador
    private void loginAdmin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Validar credenciales de admin (simplificado por ahora)
        if ("admin".equals(username) && "admin123".equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("adminLogueado", true);
            session.setAttribute("adminUsername", username);
            session.setAttribute("tipoUsuario", "admin");
            session.setMaxInactiveInterval(30 * 60); // 30 minutos
            
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } else {
            request.setAttribute("error", "Credenciales de administrador inválidas");
            request.getRequestDispatcher("/WEB-INF/views/admin/login.jsp").forward(request, response); // ✅ CORREGIDA ruta
        }
    }
    
    // ✅ AÑADIDO: Método para logout
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        response.sendRedirect(request.getContextPath() + "/");
    }
}