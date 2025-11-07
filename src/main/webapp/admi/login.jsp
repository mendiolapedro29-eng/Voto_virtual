<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Panel de Administración - Login</title>
        <link rel="stylesheet" href="../css/style.css">
    </head>
    <body>
        <div class="form-container">
            <div style="text-align: center; margin-bottom: var(--spacing-xl);">
                <div class="logo" style="font-size: var(--font-size-2xl); justify-content: center;">VotoSeguro</div>
                <h2 style="margin-top: var(--spacing-sm);">Panel de Administración</h2>
            </div>
            
            <form action="login" method="post">
                <div class="form-group">
                    <label for="username">Usuario</label>
                    <input type="text" id="username" name="username" required>
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