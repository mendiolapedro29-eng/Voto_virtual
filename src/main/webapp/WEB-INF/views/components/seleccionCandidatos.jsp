<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Selección de Candidatos - Voto Electrónico</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            padding: 20px;
        }
        .candidates-container {
            max-width: 1200px;
            margin: 0 auto;
        }
        .page-title {
            color: white;
            text-align: center;
            margin-bottom: 2rem;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.5);
        }
        .region-info {
            background: rgba(255, 255, 255, 0.95);
            border-radius: 10px;
            padding: 1rem;
            margin-bottom: 2rem;
            text-align: center;
        }
        .candidate-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
        }
        .candidate-card {
            background: white;
            border-radius: 15px;
            padding: 1.5rem;
            text-align: center;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            transition: all 0.3s ease;
            border: 3px solid transparent;
        }
        .candidate-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 25px rgba(0,0,0,0.2);
            border-color: #007bff;
        }
        .candidate-photo {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            object-fit: cover;
            margin: 0 auto 1rem;
            border: 4px solid #007bff;
        }
        .candidate-name {
            color: #0d47a1;
            font-weight: 700;
            margin-bottom: 0.5rem;
        }
        .candidate-party {
            color: #666;
            font-weight: 600;
            margin-bottom: 1rem;
        }
        .candidate-proposals {
            color: #555;
            font-size: 0.9em;
            margin-bottom: 1.5rem;
            min-height: 60px;
        }
        .vote-btn {
            background: linear-gradient(135deg, #28a745, #20c997);
            color: white;
            border: none;
            border-radius: 25px;
            padding: 10px 25px;
            font-weight: 600;
            transition: all 0.3s ease;
            width: 100%;
        }
        .vote-btn:hover {
            transform: scale(1.05);
            box-shadow: 0 5px 15px rgba(40, 167, 69, 0.4);
        }
        .back-btn {
            background: #6c757d;
            color: white;
            border: none;
            border-radius: 25px;
            padding: 10px 25px;
            margin-bottom: 2rem;
        }
    </style>
</head>
<body>
    <div class="candidates-container">
        <!-- Botón para volver -->
        <a href="${pageContext.request.contextPath}/votacion/seleccion-region" class="btn back-btn">
            <i class="fas fa-arrow-left me-2"></i>Volver a Regiones
        </a>

        <h1 class="page-title">Selecciona tu Candidato</h1>
        
        <!-- Información de la región -->
        <div class="region-info">
            <h3><i class="fas fa-map-marker-alt me-2"></i>Región: ${regionNombre}</h3>
        </div>

        <!-- Grid de candidatos -->
        <div class="candidate-grid">
            <c:forEach var="candidato" items="${candidatos}">
                <div class="candidate-card">
                    <!-- Foto del candidato -->
                    <img src="${candidato.value}" alt="${candidato.key}" class="candidate-photo">
                    
                    <!-- Información del candidato -->
                    <h3 class="candidate-name">${candidato.key}</h3>
                    <p class="candidate-party">${candidato.value}</p>
                    <p class="candidate-proposals">Propuestas y plan de gobierno del candidato.</p>
                    
                    <!-- Formulario de voto -->
                    <form action="${pageContext.request.contextPath}/votacion/registrar" method="POST">
                        <input type="hidden" name="candidato_id" value="${candidato.key}">
                        <input type="hidden" name="region_id" value="${regionId}">
                        <button type="submit" class="btn vote-btn">
                            <i class="fas fa-vote-yea me-2"></i>Votar por este Candidato
                        </button>
                    </form>
                </div>
            </c:forEach>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>