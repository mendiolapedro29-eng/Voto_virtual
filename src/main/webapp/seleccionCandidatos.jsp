<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String dni = request.getParameter("dni");
    String region = request.getParameter("region");
    if (dni == null || region == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Selección de Candidatos - Región <%=region%></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="#">
                <i class="fas fa-vote-yea me-2"></i>Voto Electrónico ONPE
            </a>
            <span class="navbar-text text-white">
                <i class="fas fa-user me-2"></i>DNI: <%=dni%> | <i class="fas fa-map-marker-alt me-2"></i>Región: <%=region%>
            </span>
        </div>
    </nav>

    <main class="container mt-4">
        <h2 class="text-center mb-4">Seleccione a sus candidatos</h2>
        
        <ul class="nav nav-tabs" id="candidatoTabs" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="presidencia-tab" data-bs-toggle="tab" data-bs-target="#presidencia" type="button" role="tab">Presidencia de la República</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="alcaldia-tab" data-bs-toggle="tab" data-bs-target="#alcaldia" type="button" role="tab">Alcaldía Provincial</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="regional-tab" data-bs-toggle="tab" data-bs-target="#regional" type="button" role="tab">Gobierno Regional</button>
            </li>
        </ul>

        <div class="tab-content" id="candidatoTabsContent">
            <div class="tab-pane fade show active" id="presidencia" role="tabpanel">
                <div class="row mt-4">
                    <div class="col-md-4 mb-4">
                        <div class="card card-candidate">
                            <img src="https://via.placeholder.com/400x220" class="card-img-top" alt="Candidato 1">
                            <span class="party-badge">PARTIDO AZUL</span>
                            <div class="card-body">
                                <h5 class="card-title">Candidato A</h5>
                                <p class="card-text">Propuesta: Innovación y futuro para el Perú.</p>
                                <button class="btn btn-success w-100" data-bs-toggle="modal" data-bs-target="#confirmModal" data-candidato="Candidato A" data-cargo="Presidencia">
                                    <i class="fas fa-check-circle"></i> Votar
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4 mb-4">
                        <div class="card card-candidate">
                            <img src="https://via.placeholder.com/400x220" class="card-img-top" alt="Candidato 2">
                            <span class="party-badge">PARTIDO ROJO</span>
                            <div class="card-body">
                                <h5 class="card-title">Candidato B</h5>
                                <p class="card-text">Propuesta: Educación y salud de calidad para todos.</p>
                                <button class="btn btn-success w-100" data-bs-toggle="modal" data-bs-target="#confirmModal" data-candidato="Candidato B" data-cargo="Presidencia">
                                    <i class="fas fa-check-circle"></i> Votar
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Añade más pestañas (alcaldia, regional) aquí como en el ejemplo anterior -->
        </div>
    </main>

    <!-- Modal de Confirmación -->
    <div class="modal fade" id="confirmModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-warning text-dark">
                    <h5 class="modal-title"><i class="fas fa-exclamation-triangle me-2"></i>Confirmar Voto</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <p>¿Está seguro de votar por <strong id="nombreCandidato"></strong> para el cargo de <strong id="nombreCargo"></strong>?</p>
                    <p class="text-muted">Esta acción no se puede deshacer.</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <form action="votoConfirmado.jsp" method="POST">
                        <input type="hidden" name="dni" value="<%=dni%>">
                        <input type="hidden" name="region" value="<%=region%>">
                        <input type="hidden" name="candidato" id="inputCandidato">
                        <input type="hidden" name="cargo" id="inputCargo">
                        <button type="submit" class="btn btn-success">Confirmar Voto</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        const confirmModal = document.getElementById('confirmModal');
        if (confirmModal) {
            confirmModal.addEventListener('show.bs.modal', function (event) {
                const button = event.relatedTarget;
                document.getElementById('nombreCandidato').textContent = button.getAttribute('data-candidato');
                document.getElementById('nombreCargo').textContent = button.getAttribute('data-cargo');
                document.getElementById('inputCandidato').value = button.getAttribute('data-candidato');
                document.getElementById('inputCargo').value = button.getAttribute('data-cargo');
            });
        }
    </script>
</body>
</html>