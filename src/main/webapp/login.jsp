<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login de Usuario</title>
        <link rel="stylesheet" href="../css/style.css">
    </head>
    <body>
        <header class="navbar">
            <div class="container">
                <div class="logo">VotoSeguro</div>
                <nav>
                    <a href="../index.jsp">Inicio</a>
                    <a href="../admin/login.jsp">Administrador</a>
                </nav>
            </div>
        </header>

        <div class="form-container">
            <h2>Iniciar Sesión de Usuario</h2>
            
            <%-- Mostrar mensaje de error si existe --%>
            <% if (request.getParameter("error") != null) { %>
                <p style="color: var(--error-color); text-align: center;">DNI o contraseña incorrectos.</p>
            <% } %>
            
            <form action="login" method="post">
                <div class="form-group">
                    <label for="dni">DNI</label>
                    <input type="text" id="dni" name="dni" placeholder="Ej: 12345678" required>
                </div>
                <div class="form-group">
                    <label for="password">Contraseña</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit" class="btn" style="width: 100%;">Iniciar Sesión</button>
            </form>
        </div>
    </body>
</html>