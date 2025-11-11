<%-- admin/dashboard.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Panel de Administración</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        .stats-card {
            background: #f8f9fa;
            border-radius: 10px;
            padding: 15px;
            margin-bottom: 15px;
        }
        .progress-bar-custom {
            background-color: #0d6efd;
        }
        .candidato-bar {
            height: 20px;
            background: linear-gradient(90deg, #0d6efd, #6f42c1);
            border-radius: 10px;
            margin: 5px 0;
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-2 bg-dark text-white vh-100">
                <div class="p-3">
                    <h4>Panel Admin</h4>
                    <ul class="nav nav-pills flex-column">
                        <li class="nav-item">
                            <a class="nav-link active" href="?tipo=presidente">Presidente</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-white" href="?tipo=congresista">Congresistas</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-white" href="?tipo=alcalde">Alcaldes</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-white" href="historial.jsp">Historial de Votos</a>
                        </li>
                    </ul>
                </div>
            </div>

            <!-- Main Content -->
            <div class="col-md-10">
                <!-- Header -->
                <div class="row mt-3">
                    <div class="col-md-6">
                        <h2>Presidente/a</h2>
                        <h4 class="text-primary">ECUADOR</h4>
                    </div>
                    <div class="col-md-6 text-end">
                        <select class="form-select w-50 d-inline-block">
                            <option>Seleccione una provincia</option>
                        </select>
                        <button class="btn btn-outline-primary">Buscar</button>
                    </div>
                </div>

                <!-- Estadísticas principales -->
                <div class="row mt-4">
                    <div class="col-md-4">
                        <div class="stats-card">
                            <h6>Sufragantes</h6>
                            <h3>${estadisticas.sufragantes}</h3>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="stats-card">
                            <h6>Ausentismo</h6>
                            <h3>${estadisticas.ausentismo}</h3>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="stats-card">
                            <h6>Electores</h6>
                            <h3>${estadisticas.electores}</h3>
                        </div>
                    </div>
                </div>

                <!-- Gráfico de resultados -->
                <div class="row mt-4">
                    <div class="col-md-8">
                        <div class="card">
                            <div class="card-header">
                                <h5>Elecciones Generales - Presidenta/e</h5>
                                <small>Fecha Corte: <c:out value="${fechaCorte}" /></small>
                            </div>
                            <div class="card-body">
                                <canvas id="resultadosChart" height="200"></canvas>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-4">
                        <div class="card">
                            <div class="card-header">
                                <h6>Resultados por Candidato</h6>
                            </div>
                            <div class="card-body">
                                <c:forEach var="candidato" items="${candidatos}">
                                    <div class="mb-2">
                                        <small>${candidato.nombre}</small>
                                        <div class="d-flex align-items-center">
                                            <div class="candidato-bar" 
                                                 style="width: ${candidato.porcentaje}%"></div>
                                            <span class="ms-2">${candidato.porcentaje}%</span>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Estadísticas de votos -->
                <div class="row mt-4">
                    <div class="col-md-4">
                        <div class="stats-card text-center">
                            <h6>Votos Válidos</h6>
                            <h3 class="text-success">${estadisticas.votosValidos}%</h3>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="stats-card text-center">
                            <h6>Votos Blancos</h6>
                            <h3 class="text-warning">${estadisticas.votosBlancos}%</h3>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="stats-card text-center">
                            <h6>Votos Nulos</h6>
                            <h3 class="text-danger">${estadisticas.votosNulos}%</h3>
                        </div>
                    </div>
                </div>

                <!-- Actas -->
                <div class="row mt-4">
                    <div class="col-md-4">
                        <div class="stats-card">
                            <h6>Actas Pendientes</h6>
                            <h5>${estadisticas.actasPendientes}</h5>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="stats-card">
                            <h6>Actas Novedad</h6>
                            <h5>${estadisticas.actasNovedad}</h5>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="stats-card">
                            <h6>Actas Válidas</h6>
                            <h5>${estadisticas.actasValidas}</h5>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        // Configuración del gráfico
        const ctx = document.getElementById('resultadosChart').getContext('2d');
        const resultadosChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: [
                    <c:forEach var="candidato" items="${candidatos}">
                        '${candidato.nombre}',
                    </c:forEach>
                ],
                datasets: [{
                    label: 'Porcentaje de Votos',
                    data: [
                        <c:forEach var="candidato" items="${candidatos}">
                            ${candidato.porcentaje},
                        </c:forEach>
                    ],
                    backgroundColor: [
                        '#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0',
                        '#9966FF', '#FF9F40', '#FF6384', '#C9CBCF'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true,
                        max: 40,
                        title: {
                            display: true,
                            text: 'Porcentaje (%)'
                        }
                    }
                }
            }
        });
    </script>
</body>
</html>