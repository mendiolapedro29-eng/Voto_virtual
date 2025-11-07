<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String dni = request.getParameter("dni");
    if (dni == null || dni.isEmpty()) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Seleccionar Región - Voto Electrónico</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="region-selection-container">
        <div class="container">
            <h1 class="region-title">voto electrónico presencial</h1>
            <p class="region-subtitle">APRENDE EL VOTO ELECTRÓNICO DE ACUERDO A TU REGIÓN</p>
            
            <div class="region-buttons">
                <a href="seleccionCandidatos.jsp?region=Lima&dni=<%=dni%>" class="btn-region highlighted">
                    <i class="fas fa-city me-2"></i> Lima Metropolitana Español
                </a>
                <a href="seleccionCandidatos.jsp?region=Apurimac&dni=<%=dni%>" class="btn-region">
                    <i class="fas fa-mountain me-2"></i> Apurímac - Huancavelica - Ayacucho (Español - Quechua)
                </a>
                <a href="seleccionCandidatos.jsp?region=Callao&dni=<%=dni%>" class="btn-region">
                    <i class="fas fa-ship me-2"></i> Callao - Pacarán (Español)
                </a>
                <a href="seleccionCandidatos.jsp?region=Ancash&dni=<%=dni%>" class="btn-region">
                    <i class="fas fa-snowflake me-2"></i> Áncash (Español)
                </a>
                <a href="seleccionCandidatos.jsp?region=Cusco&dni=<%=dni%>" class="btn-region">
                    <i class="fas fa-sun me-2"></i> Cusco - Madre de Dios (Español - Quechua)
                </a>
            </div>
        </div>
        
        <footer class="region-footer">
            <p>CABINA DE VOTACIÓN ELECTRÓNICA</p>
            <img src="img/onpe-logo.png" alt="Logo ONPE">
        </footer>
    </div>
</body>
</html>