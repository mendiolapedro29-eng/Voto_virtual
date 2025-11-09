<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.mycompany.sistemavotacion.model.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    if (usuario.isHaVotado()) {
        response.sendRedirect("confirmacionVoto.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Votación - Sistema de Votación</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-success text-white">
                        <h4 class="mb-0">Boleta Electoral</h4>
                    </div>
                    <div class="card-body">
                        <div class="alert alert-info">
                            <strong>Elector:</strong> <%= usuario.getNombres() %> <%= usuario.getApellidos() %><br>
                            <strong>DNI:</strong> <%= usuario.getDni() %>
                        </div>
                        
                        <% if (request.getAttribute("error") != null) { %>
                            <div class="alert alert-danger">
                                <%= request.getAttribute("error") %>
                            </div>
                        <% } %>
                        
                        <form action="${pageContext.request.contextPath}/votacion" method="post">
                            <!-- Elección Presidencial -->
                            <div class="mb-4">
                                <h5 class="border-bottom pb-2">Presidente de la República</h5>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="candidato_id" value="1" id="cand1" required>
                                    <label class="form-check-label" for="cand1">
                                        <strong>Juan Pérez García</strong> - Partido Democrático
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="candidato_id" value="2" id="cand2">
                                    <label class="form-check-label" for="cand2">
                                        <strong>María López Martínez</strong> - Partido Republicano
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="candidato_id" value="3" id="cand3">
                                    <label class="form-check-label" for="cand3">
                                        <strong>Carlos Rodríguez Santos</strong> - Partido Verde
                                    </label>
                                </div>
                            </div>
                            
                            <input type="hidden" name="eleccion_id" value="1">
                            
                            <div class="alert alert-warning">
                                <strong>⚠️ Importante:</strong> Una vez confirmado el voto, no podrá modificarse.
                            </div>
                            
                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-success btn-lg">Confirmar Voto</button>
                                <a href="${pageContext.request.contextPath}/logout" class="btn btn-secondary">Cancelar</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>