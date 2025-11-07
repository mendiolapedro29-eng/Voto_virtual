
package com.mycompany.sistemavotacion.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "Inicio", urlPatterns = {"/Inicio"})
public class Inicio extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String candidatoId = request.getParameter("candidatoId");
        String cargo = request.getParameter("cargo");
        
        // Aquí iría la lógica para guardar el voto en la base de datos
        System.out.println("VOTO REGISTRADO:");
        System.out.println("Cargo: " + cargo);
        System.out.println("ID Candidato: " + candidatoId);
        
        // Redirigimos a la página de confirmación
        response.sendRedirect("votoConfirmado.jsp");
    }
}
