<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String dni = request.getParameter("dni");
    String candidato = request.getParameter("candidato");
    String cargo = request.getParameter("cargo");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Voto Registrado</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { background-color: #f8f9fa; }
        .confirmation-container { text-align: center; padding: 5rem 0; }
        .confirmation-icon { font-size: 5rem; color: #28a745; margin-bottom: 1rem; }
        .card { border: none; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }
    </style>
</head>
<body>
    <div class="container confirmation-container">
        <div class="card mx-auto" style="max-width: 600px;">
            <div class="card-body p-5">
                <div class="confirmation-icon">
                    <i class="fas fa-check-circle"></i>
                </div>
                <h1 class="card-title mb-3">¡Gracias por votar!</h1>
                <p class="card-text lead">Su voto ha sido registrado de forma segura y anónima.</p>
                
                <hr class="my-4">

                <div class="row text-start">
                    <div class="col-sm-4"><strong>DNI:</strong></div>
                    <div class="col-sm-8"><%=dni != null ? "********" : "N/A" %></div>
                    <div class="col-sm-4 mt-2"><strong>Cargo:</strong></div>
                    <div class="col-sm-8 mt-2"><%=cargo != null ? cargo : "N/A" %></div>
                    <div class="col-sm-4 mt-2"><strong>Candidato:</strong></div>
                    <div class="col-sm-8 mt-2"><%=candidato != null ? candidato : "N/A" %></div>
                </div>

                <hr class="my-4">

                <a href="index.jsp" class="btn btn-primary mt-3">
                    <i class="fas fa-home me-2"></i>Ir al Inicio
                </a>
            </div>
        </div>
        <p class="mt-4 text-muted">&copy; 2023 ONPE - Oficina Nacional de Procesos Electorales</p>
    </div>
</body>
</html>