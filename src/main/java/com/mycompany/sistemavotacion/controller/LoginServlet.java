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

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"}) // ✅ CORREGIDO: sin /* para evitar conflictos
public class LoginServlet extends HttpServlet {

    private AuthService authService;
    private SecurityUtil securityUtil;
    
    @Override
    public void init() throws ServletException {
        this.authService = new AuthService();
        this.securityUtil = new SecurityUtil();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // ✅ CORREGIDO: Ruta más simple para login de usuario
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String dni = request.getParameter("dni");
        String tipo = request.getParameter("tipo"); // "usuario" o "admin"
        
        try {
            // ✅ CORREGIDO: Lógica mejorada para determinar tipo de login
            if (tipo != null && "admin".equals(tipo)) {
                loginAdmin(request, response);
            } else {
                loginUsuario(request, response, dni);
            }
            
        } catch (Exception e) {
            request.setAttribute("error", "Error en el sistema: " + e.getMessage());
            // ✅ CORREGIDO: Redirigir a la página correcta según el tipo
            if (tipo != null && "admin".equals(tipo)) {
                request.getRequestDispatcher("/adminLogin.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        }
    }
    
    private void loginUsuario(HttpServletRequest request, HttpServletResponse response, String dni) 
            throws ServletException, IOException {
        
        // Validar que se ingresó DNI
        if (dni == null || dni.trim().isEmpty()) {
            request.setAttribute("error", "Por favor ingrese su DNI");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        
        // Validar formato de DNI
        if (!securityUtil.validarDNI(dni)) {
            request.setAttribute("error", "DNI inválido. Debe contener 8 dígitos numéricos.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
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
            session.setAttribute("usuarioId", usuario.getId());
            session.setAttribute("token", securityUtil.generarTokenSesion(usuario.getId()));
            session.setMaxInactiveInterval(30 * 60); // 30 minutos
            
            // ✅ CORREGIDO: Redirigir a selección de región
            response.sendRedirect(request.getContextPath() + "/seleccionRegion.jsp");
            
        } else if (usuario != null && usuario.isHaVotado()) {
            request.setAttribute("error", "Ya ha ejercido su derecho al voto");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "DNI no encontrado en el padrón electoral");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
    
    private void loginAdmin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Validar que se ingresaron credenciales
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Por favor ingrese usuario y contraseña");
            request.getRequestDispatcher("/adminLogin.jsp").forward(request, response);
            return;
        }
        
        // Validar credenciales de admin
        if ("admin".equals(username) && "admin123".equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("adminLogueado", true);
            session.setAttribute("adminUsername", username);
            session.setAttribute("tipoUsuario", "admin");
            session.setMaxInactiveInterval(30 * 60); // 30 minutos
            
            // ✅ CORREGIDO: Redirigir al dashboard del admin
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } else {
            request.setAttribute("error", "Credenciales de administrador inválidas");
            request.getRequestDispatcher("/adminLogin.jsp").forward(request, response);
        }
    }
    
    // ✅ CORREGIDO: Método doGet para logout (en lugar de doDelete)
    @WebServlet("/logout")
    public static class LogoutServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) 
                throws ServletException, IOException {
            
            HttpSession session = request.getSession(false);
            if (session != null) {
                String tipoUsuario = (String) session.getAttribute("tipoUsuario");
                session.invalidate();
                
                // ✅ Redirigir según el tipo de usuario
                if ("admin".equals(tipoUsuario)) {
                    response.sendRedirect(request.getContextPath() + "/adminLogin.jsp");
                } else {
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        }
    }
}