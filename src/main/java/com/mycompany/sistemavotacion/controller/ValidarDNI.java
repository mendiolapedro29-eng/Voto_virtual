
package com.mycompany.sistemavotacion.controller;

import com.mycompany.sistemavotacion.model.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet(name = "ValidarDNI", urlPatterns = {"/ValidarDNI"})
public class ValidarDNI extends HttpServlet {

   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String dni = request.getParameter("dni");
        String error = null;
        
        if (dni == null || !dni.matches("\\d{8}")) {
            error = "DNI inválido. Debe contener exactamente 8 dígitos numéricos.";
            request.setAttribute("error", error);
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
        
        // Si el DNI es válido, lo guardamos en la sesión
        HttpSession session = request.getSession();
        session.setAttribute("usuario", new Usuario(dni));
        // Redirigimos al servlet que prepara la selección de región
        response.sendRedirect("seleccionarRegion");
    }
}
