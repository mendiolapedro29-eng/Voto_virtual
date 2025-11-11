<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmación de Voto</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        :root {
            --primary-color: #28a745;
            --secondary-color: #20c997;
            --light-color: #d4edda;
        }
        
        body {
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            min-height: 100vh;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        
        .confirmation-container {
            background-color: white;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            padding: 2.5rem;
            margin-top: 2rem;
            max-width: 800px;
        }
        
        .success-icon {
            font-size: 4rem;
            color: var(--primary-color);
            margin-bottom: 1rem;
        }
        
        .voter-info {
            background-color: var(--light-color);
            border-radius: 10px;
            padding: 1.5rem;
            margin: 2rem 0;
            border-left: 5px solid var(--primary-color);
        }
        
        .candidate-selection {
            background-color: #f8f9fa;
            border-radius: 10px;
            padding: 1.5rem;
            margin-bottom: 1.5rem;
        }
        
        .btn-home {
            background-color: var(--primary-color);
            color: white;
            border: none;
            padding: 12px 40px;
            font-size: 1.1rem;
            border-radius: 50px;
            transition: all 0.3s ease;
        }
        
        .btn-home:hover {
            background-color: var(--secondary-color);
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
            color: white;
        }
        
        .candidate-badge {
            background-color: var(--primary-color);
            color: white;
            padding: 8px 15px;
            border-radius: 20px;
            font-weight: 500;
        }
    </style>
</head>
<body>
    <!-- Header -->
    <header class="bg-success text-white py-3">
        <div class="container">
            <div class="d-flex align-items-center">
                <i class="bi bi-check-circle-fill me-3" style="font-size: 2rem;"></i>
                <h1 class="h3 mb-0">Confirmación de Voto</h1>
            </div>
        </div>
    </header>

    <!-- Main Content -->
    <div class="container">
        <div class="confirmation-container mx-auto">
            <!-- Icono de éxito -->
            <div class="text-center">
                <i class="bi bi-check-circle-fill success-icon"></i>
                <h2 class="text-success mb-3">¡Voto Registrado Exitosamente!</h2>
                <p class="lead">Tu voto ha sido procesado correctamente. Gracias por participar en el proceso electoral.</p>
            </div>

            <!-- Información del votante -->
            <div class="voter-info">
                <h4 class="mb-3">
                    <i class="bi bi-person-circle me-2"></i>
                    Información del Votante
                </h4>
                <div class="row">
                    <div class="col-md-6">
                        <p class="mb-2"><strong>DNI:</strong> <%= request.getParameter("dni") != null ? request.getParameter("dni") : "No disponible" %></p>
                        <p class="mb-2"><strong>Nombre:</strong> <%= request.getParameter("nombre") != null ? request.getParameter("nombre") : "No disponible" %></p>
                    </div>
                    <div class="col-md-6">
                        <p class="mb-2"><strong>Apellido:</strong> <%= request.getParameter("apellido") != null ? request.getParameter("apellido") : "No disponible" %></p>
                        <p class="mb-2"><strong>Región:</strong> <span class="badge bg-primary"><%= request.getParameter("region") != null ? request.getParameter("region") : "No disponible" %></span></p>
                    </div>
                </div>
            </div>

            <!-- Candidatos seleccionados -->
            <div class="candidate-selection">
                <h4 class="mb-3">
                    <i class="bi bi-list-check me-2"></i>
                    Tus Selecciones
                </h4>
                
                <!-- Presidente -->
                <div class="mb-3">
                    <h6 class="text-muted">Presidente:</h6>
                    <span class="candidate-badge">
                        <i class="bi bi-person-fill me-1"></i>
                        <%= request.getParameter("presidente") != null ? request.getParameter("presidente") : "No seleccionado" %>
                    </span>
                </div>
                
                <!-- Congresista -->
                <div class="mb-3">
                    <h6 class="text-muted">Congresista:</h6>
                    <span class="candidate-badge">
                        <i class="bi bi-people-fill me-1"></i>
                        <%= request.getParameter("congresista") != null ? request.getParameter("congresista") : "No seleccionado" %>
                    </span>
                </div>
                
                <!-- Alcalde -->
                <div class="mb-3">
                    <h6 class="text-muted">Alcalde:</h6>
                    <span class="candidate-badge">
                        <i class="bi bi-building me-1"></i>
                        <%= request.getParameter("alcalde") != null ? request.getParameter("alcalde") : "No seleccionado" %>
                    </span>
                </div>
            </div>

            <!-- Información adicional -->
            <div class="alert alert-info">
                <h6><i class="bi bi-info-circle me-2"></i>Información importante:</h6>
                <ul class="mb-0">
                    <li>Tu voto ha sido registrado de forma segura y anónima</li>
                    <li>Recibirás un comprobante por correo electrónico si lo solicitaste</li>
                    <li>Los resultados estarán disponibles una vez cerrado el proceso electoral</li>
                </ul>
            </div>

            <!-- Botones de acción -->
            <div class="text-center mt-4">
                <a href="index.jsp" class="btn btn-home me-3">
                    <i class="bi bi-house-fill me-2"></i>
                    Volver al Inicio
                </a>
                <button class="btn btn-outline-secondary" onclick="window.print()">
                    <i class="bi bi-printer me-2"></i>
                    Imprimir Comprobante
                </button>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer class="bg-dark text-white py-4 mt-5">
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <h5>Sistema de Votación Electrónica</h5>
                    <p class="mb-0">Proceso electoral seguro y transparente</p>
                </div>
                <div class="col-md-6 text-md-end">
                    <p class="mb-0">&copy; 2025 - Todos los derechos reservados</p>
                </div>
            </div>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Mostrar mensaje de confirmación
        document.addEventListener('DOMContentLoaded', function() {
            console.log('Voto confirmado exitosamente');
            
            // Opcional: Agregar animación de confeti
            setTimeout(() => {
                if (typeof confetti === 'function') {
                    confetti({
                        particleCount: 100,
                        spread: 70,
                        origin: { y: 0.6 }
                    });
                }
            }, 500);
        });
    </script>
    
    <!-- Opcional: Script de confeti para celebración -->
    <script src="https://cdn.jsdelivr.net/npm/canvas-confetti@1.5.1/dist/confetti.browser.min.js"></script>
</body>
</html>