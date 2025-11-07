
package com.mycompany.sistemavotacion.controller;

import com.mycompany.sistemavotacion.model.Candidato;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "SeleccionCandidatos", urlPatterns = {"/SeleccionCandidatos"})
public class SeleccionCandidatos extends HttpServlet {

   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String region = request.getParameter("region");
        request.setAttribute("regionSeleccionada", region);
        
        // Datos de ejemplo para candidatos
        List<Candidato> candidatos = new ArrayList<>();
        candidatos.add(new Candidato("1", "Candidato A", "PARTIDO AZUL", "Innovación y futuro para el Perú.", "https://via.placeholder.com/400x220", "Presidencia"));
        candidatos.add(new Candidato("2", "Candidato B", "PARTIDO ROJO", "Educación y salud de calidad para todos.", "https://via.placeholder.com/400x220", "Presidencia"));
        candidatos.add(new Candidato("3", "Alcalde X", "MOV. URBANO", "Modernización y transporte sostenible.", "https://via.placeholder.com/400x220", "Alcaldía"));
        
        request.setAttribute("candidatos", candidatos);
        request.getRequestDispatcher("seleccionCandidatos.jsp").forward(request, response);
    }
}
