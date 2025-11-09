<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="https://jakarta.ee/xml/ns/jakartaee" %> <%-- URI moderna para Jakarta EE --%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Boleta de Votación</title>
        <link rel="stylesheet" href="../css/style.css">
        <style>
            /* Estilos específicos para la boleta de votación */
            .vote-section { margin-bottom: var(--spacing-2xl); }
            .vote-section h2 { text-align: center; border-bottom: 2px solid var(--primary-color); padding-bottom: var(--spacing-sm); }
            .candidate-option { display: flex; align-items: center; padding: var(--spacing-md); border: 1px solid #e0e0e0; border-radius: var(--border-radius-md); margin-bottom: var(--spacing-sm); cursor: pointer; transition: var(--transition); }
            .candidate-option:hover { background-color: #f8f9fa; border-color: var(--primary-color); }
            .candidate-option input[type="radio"] { margin-right: var(--spacing-md); }
            .candidate-option img { width: 50px; height: 50px; border-radius: 50%; margin-right: var(--spacing-md); object-fit: cover; }
            .submit-vote-btn { display: block; width: 300px; margin: var(--spacing-2xl) auto; padding: 15px; font-size: var(--font-size-lg); }
        </style>
    </head>
    <body>
        <header class="navbar">
            <div class="container">
                <div class="logo">VotoSeguro</div>
                <nav>
                    <span style="color: var(--text-secondary);">Usuario: ${sessionScope.loggedUser.dni}</span>
                </nav>
            </div>
        </header>

        <main class="container">
            <h1 style="text-align: center;">Boleta de Votación Electrónica</h1>
            <p style="text-align: center; color: var(--text-secondary);">Por favor, seleccione un candidato para cada cargo.</p>

            <form action="submit-vote" method="post">
                <%-- Iteramos sobre el Map que envió el Servlet. La clave es el Position y el valor es la lista de Candidates --%>
                <c:forEach var="entry" items="${candidatesMap}">
                    <div class="vote-section">
                        <%-- Mostramos el nombre del cargo (la clave del Map) --%>
                        <h2>${entry.key.name}</h2>
                        
                        <%-- Iteramos sobre la lista de candidatos (el valor del Map) --%>
                        <c:forEach var="candidate" items="${entry.value}">
                            <label class="candidate-option">
                                <%-- El nombre del input radio debe ser único por cargo. Usamos el ID del position --%>
                                <input type="radio" name="position_${entry.key.id}" value="${candidate.id}" required>
                                <img src="${candidate.photo}" alt="${candidate.name}">
                                <div>
                                    <strong>${candidate.name}</strong> - ${candidate.party}
                                </div>
                            </label>
                        </c:forEach>
                    </div>
                </c:forEach>

                <button type="submit" class="btn btn-accent submit-vote-btn">Enviar Mi Voto</button>
            </form>
        </main>
    </body>
</html>