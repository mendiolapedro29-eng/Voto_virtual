<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%
    Map<String, Object> estadisticas = (Map<String, Object>) request.getAttribute("estadisticas");
    if (estadisticas == null) {
        response.sendRedirect(request.getContextPath() + "/admin?action=login");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Sistema de Votación</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .sidebar {
            background-color: #f8f9fa;
            min-height: 100vh;
        }
        .stat-card {
            transition: transform 0.3s;
        }
        .stat-card:hover {
            transform: translateY(-5px);
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <nav class="col-md-3 col-lg-2 d-md-block sidebar collapse">
                <div class="position-sticky pt-3">
                    <h5 class="px-3">Panel de Administración</h5>
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/admin?action=dashboard">
                                <i class="fas fa-tachometer-alt"></i> Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin?action=estadisticas">
                                <i class="fas fa-chart-bar"></i> Estadísticas
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin?action=usuarios">
                                <i class="fas fa-users"></i> Usuarios
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin?action=carga-datos">
                                <i class="fas fa-upload"></i> Carga de Datos
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-danger" href="${pageContext.request.contextPath}/admin?action=logout">
                                <i class="fas fa-sign-out-alt"></i> Cerrar Sesión
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>

            <!-- Main content -->
            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">Dashboard</h1>
                    <div class="btn-toolbar mb-2 mb-md-0">
                        <span class="me-2">Bienvenido, Admin</span>
                    </div>
                </div>

                <!-- Estadísticas -->
                <div class="row">
                    <div class="col-md-3 mb-4">
                        <div class="card text-white bg-primary stat-card">
                            <div class="card-body">
                                <div class="d-flex">
                                    <div>
                                        <h4><%= estadisticas.get("totalVotos") %></h4>
                                        <p class="mb-0">Total de Votos</p>
                                    </div>
                                    <div class="ms-auto">
                                        <i class="fas fa-vote-yea fa-2x"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-3 mb-4">
                        <div class="card text-white bg-success stat-card">
                            <div class="card-body">
                                <div class="d-flex">
                                    <div>
                                        <h4><%= estadisticas.get("totalUsuarios") %></h4>
                                        <p class="mb-0">Usuarios Registrados</p>
                                    </div>
                                    <div class="ms-auto">
                                        <i class="fas fa-users fa-2x"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-3 mb-4">
                        <div class="card text-white bg-warning stat-card">
                            <div class="card-body">
                                <div class="d-flex">
                                    <div>
                                        <h4><%= estadisticas.get("participacion") %></h4>
                                        <p class="mb-0">Participación</p>
                                    </div>
                                    <div class="ms-auto">
                                        <i class="fas fa-chart-line fa-2x"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-3 mb-4">
                        <%
                            int totalUsuarios = (Integer) estadisticas.get("totalUsuarios");
                            int totalVotos = (Integer) estadisticas.get("totalVotos");
                            int porVotar = totalUsuarios - totalVotos;
                        %>
                        <div class="card text-white bg-info stat-card">
                            <div class="card-body">
                                <div class="d-flex">
                                    <div>
                                        <h4><%= porVotar %></h4>
                                        <p class="mb-0">Por Votar</p>
                                    </div>
                                    <div class="ms-auto">
                                        <i class="fas fa-clock fa-2x"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Información rápida -->
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <h5 class="card-title mb-0">Resumen del Sistema</h5>
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <h6>Estado del Sistema:</h6>
                                        <span class="badge bg-success">Activo</span>
                                        <p class="mt-2">Sistema de votación funcionando correctamente.</p>
                                    </div>
                                    <div class="col-md-6">
                                        <h6>Acciones Rápidas:</h6>
                                        <div class="d-grid gap-2">
                                            <a href="${pageContext.request.contextPath}/admin?action=estadisticas" 
                                               class="btn btn-outline-primary">Ver Estadísticas Detalladas</a>
                                            <a href="${pageContext.request.contextPath}/admin?action=usuarios" 
                                               class="btn btn-outline-success">Gestionar Usuarios</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>