<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Voto Electrónico ONPE | Prueba</title>
    
    <!-- Bootstrap y Font Awesome desde CDN (siempre funcionan) -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <!-- CSS EMbebido directamente en la página para evitar problemas de ruta -->
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .hero-section {
            /* Usamos una imagen de Unsplash como respaldo */
            background: linear-gradient(rgba(0, 0, 0, 0.6), rgba(0, 0, 0, 0.6)), url('https://images.unsplash.com/photo-1581833971358-2c8b550f87b3?q=80&w=2070') no-repeat center center/cover;
            color: white;
            padding: 10rem 0;
            text-align: center;
        }
        .hero-section h1 {
            font-weight: 700;
            font-size: 3.5rem;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.5);
        }
        .btn-hero {
            padding: 12px 30px;
            font-size: 1.2rem;
            font-weight: 600;
            border-radius: 50px;
            transition: all 0.3s ease;
        }
        .btn-hero:hover {
            transform: translateY(-3px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.2);
        }
        .feature-icon {
            font-size: 3rem;
            color: #0d6efd;
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>
    <!-- Navegación -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
        <div class="container">
            <a class="navbar-brand" href="#">
                <!-- Usamos un icono en lugar del logo para la prueba -->
                <i class="fas fa-vote-yea me-2"></i>
                Voto Electrónico
            </a>
            <div class="ms-auto">
                 <span class="navbar-text">
                    <i class="fas fa-lock me-2"></i>Conexión Segura
                </span>
            </div>
        </div>
    </nav>

    <!-- Sección Principal (Hero) -->
    <header class="hero-section">
        <div class="container">
            <h1 class="display-4">Participa en las Elecciones 2025</h1>
            <p>Ejerce tu derecho al voto de forma rápida, segura y transparente desde cualquier lugar.</p>
            
            <!-- ✅ CORREGIDO: Cambiado action al servlet -->
            <form action="${pageContext.request.contextPath}/votacion/seleccion-region" method="GET" class="row justify-content-center g-3" onsubmit="return validarDNI()">
                <div class="col-md-5">
                    <div class="form-group">
                        <input type="text" name="dni" class="form-control form-control-lg text-center" placeholder="Ingresa tu número de DNI" required maxlength="8" pattern="[0-9]{8}">
                    </div>
                </div>
                <div class="col-md-auto">
                    <button type="submit" class="btn btn-primary btn-hero">
                        <i class="fas fa-sign-in-alt me-2"></i>Comenzar a Votar
                    </button>
                </div>
            </form>
            <div id="error-dni" class="alert alert-danger mt-3 w-50 mx-auto d-none" role="alert">
                <i class="fas fa-exclamation-triangle me-2"></i>El DNI debe contener exactamente 8 dígitos numéricos.
            </div>
        </div>
    </header>

    <main>
        <!-- Sección de Características -->
        <section class="container my-5 py-5">
            <div class="row text-center">
                <div class="col-md-4 mb-4">
                    <div class="feature-icon"><i class="fas fa-shield-alt"></i></div>
                    <h3>100% Seguro</h3>
                    <p>Tu voto es cifrado de extremo a extremo, garantizando el secreto y la integridad de tu elección.</p>
                </div>
                <div class="col-md-4 mb-4">
                    <div class="feature-icon"><i class="fas fa-clock"></i></div>
                    <h3>Rápido y Sencillo</h3>
                    <p>Todo el proceso de votación toma menos de 5 minutos. Interfaz intuitiva y fácil de usar.</p>
                </div>
                <div class="col-md-4 mb-4">
                    <div class="feature-icon"><i class="fas fa-user-check"></i></div>
                    <h3>Verificación Única</h3>
                    <p>El sistema valida tu identidad para asegurar que cada ciudadano emita un solo voto.</p>
                </div>
            </div>
        </section>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function validarDNI() {
            const dni = document.querySelector('input[name="dni"]').value;
            const errorDiv = document.getElementById('error-dni');
            if (!/^\d{8}$/.test(dni)) {
                errorDiv.classList.remove('d-none');
                return false;
            }
            errorDiv.classList.add('d-none');
            return true;
        }
    </script>
</body>
</html>