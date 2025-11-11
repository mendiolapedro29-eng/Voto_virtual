<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Voto Electrónico | Inicio</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
        .hero-section {
            background: linear-gradient(rgba(0,0,0,0.6), rgba(0,0,0,0.6)), url('https://images.unsplash.com/photo-1581833971358-2c8b550f87b3?q=80&w=2070') no-repeat center center/cover;
            color: white;
            padding: 10rem 0;
            text-align: center;
        }
        .hero-section h1 { font-weight: 700; font-size: 3.5rem; text-shadow: 2px 2px 4px rgba(0,0,0,0.5); }
        .btn-hero { padding: 12px 30px; font-size: 1.2rem; font-weight: 600; border-radius: 50px; transition: all 0.3s ease; }
        .btn-hero:hover { transform: translateY(-3px); box-shadow: 0 10px 20px rgba(0,0,0,0.2); }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
        <a class="navbar-brand" href="#"><i class="fas fa-vote-yea me-2"></i>Voto Electrónico</a>
        <div class="ms-auto"><span class="navbar-text"><i class="fas fa-lock me-2"></i>Conexión Segura</span></div>
    </div>
</nav>

<header class="hero-section">
    <div class="container">
        <h1>Participa en las Elecciones 2025</h1>
        <p>Ejerce tu derecho al voto de forma rápida, segura y transparente desde cualquier lugar.</p>

        <form action="seleccionRegion.jsp" method="GET" class="row justify-content-center g-3" onsubmit="return validarFormulario()">
            <div class="col-md-5">
                <input type="text" id="dni" name="dni" class="form-control form-control-lg text-center" placeholder="Ingresa tu número de DNI" required maxlength="8" onblur="autocompletarDatos()">
            </div>
            <div class="col-md-5">
                <input type="text" id="nombre" name="nombre" class="form-control mb-2" placeholder="Nombre" readonly>
                <input type="text" id="apellido" name="apellido" class="form-control mb-2" placeholder="Apellido" readonly>
                <input type="date" id="fechaNacimiento" name="fechaNacimiento" class="form-control mb-2" placeholder="Fecha de Nacimiento" readonly>
                <input type="number" id="edad" name="edad" class="form-control mb-2" placeholder="Edad" readonly>
            </div>
            <div class="col-md-auto">
                <button type="submit" class="btn btn-primary btn-hero"><i class="fas fa-sign-in-alt me-2"></i>Comenzar a Votar</button>
            </div>
        </form>
        <div id="error-dni" class="alert alert-danger mt-3 w-50 mx-auto d-none" role="alert">
            <i class="fas fa-exclamation-triangle me-2"></i>El DNI debe contener exactamente 8 dígitos numéricos.
        </div>
    </div>
</header>

<script>
    const usuariosSimulados = {
        "12345678": {nombre:"Pedro", apellido:"Mendiola", fechaNacimiento:"1995-05-10", edad:28},
        "87654321": {nombre:"Ana", apellido:"Gomez", fechaNacimiento:"1990-08-20", edad:33},
        "11223344": {nombre:"Luis", apellido:"Ramirez", fechaNacimiento:"1988-03-15", edad:35}
    };

    function autocompletarDatos() {
        const dni = document.getElementById('dni').value;
        const errorDiv = document.getElementById('error-dni');
        if(!/^\d{8}$/.test(dni)){ errorDiv.classList.remove('d-none'); limpiarCampos(); return; }
        else { errorDiv.classList.add('d-none'); }

        const usuario = usuariosSimulados[dni];
        if(usuario){
            document.getElementById('nombre').value = usuario.nombre;
            document.getElementById('apellido').value = usuario.apellido;
            document.getElementById('fechaNacimiento').value = usuario.fechaNacimiento;
            document.getElementById('edad').value = usuario.edad;
        } else { limpiarCampos(); }
    }

    function limpiarCampos(){
        document.getElementById('nombre').value = "";
        document.getElementById('apellido').value = "";
        document.getElementById('fechaNacimiento').value = "";
        document.getElementById('edad').value = "";
    }

    function validarFormulario(){
        const dni = document.getElementById('dni').value;
        const errorDiv = document.getElementById('error-dni');
        if(!/^\d{8}$/.test(dni)){ errorDiv.classList.remove('d-none'); return false; }
        errorDiv.classList.add('d-none');
        return true;
    }
</script>
</body>
</html>
