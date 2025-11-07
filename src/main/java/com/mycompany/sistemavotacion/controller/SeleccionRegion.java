
package com.mycompany.sistemavotacion.controller;

import com.mycompany.sistemavotacion.model.Region;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "SeleccionRegion", urlPatterns = {"/SeleccionRegion"})
public class SeleccionRegion extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // En una app real, esto vendría de una base de datos
        List<Region> regiones = new ArrayList<>();
        regiones.add(new Region("Lima Metropolitana Español", "Lima", "fas fa-city"));
        regiones.add(new Region("Apurímac - Huancavelica - Ayacucho", "Apurimac", "fas fa-mountain"));
        regiones.add(new Region("Callao - Pacarán", "Callao", "fas fa-ship"));
        
        request.setAttribute("regiones", regiones);
        request.getRequestDispatcher("seleccionRegion.jsp").forward(request, response);
    }
}
