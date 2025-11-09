<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.mycompany.sistemavotacion.model.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Voto Confirmado - Sistema de Votación</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card shadow">
                    <div class="card-header bg-success text-white text-center">
                        <h4 class="mb-0"><i class="fas fa-check-circle"></i> Voto Confirmado</h4>
                    </div>
                    <div class="card-body text-center">
                        <div class="mb-4">
                            <i class="fas fa-check-circle fa-5x text-success"></i>
                        </div>
                        
                        <h5 class="text-success">¡Su voto ha sido registrado exitosamente!</h5>
                        
                        <div class="alert alert-info mt-3">
                            <strong>Elector:</strong> <%= usuario.getNombres() %> <%= usuario.getApellidos() %><br>
                            <strong>DNI:</strong> <%= usuario.getDni() %><br>
                            <strong>Fecha:</strong> <%= new java.util.Date() %>
                        </div>
                        
                        <p class="text-muted">
                            Gracias por participar en el proceso electoral. 
                            Su voto ha sido procesado de manera segura y anónima.
                        </p>
                        
                        <div class="d-grid gap-2">
                            <a href="${pageContext.request.contextPath}/logout" class="btn btn-primary">Cerrar Sesión</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>