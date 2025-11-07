<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Candidatos - Sistema de Votación</title>
        <style>
            body { font-family: Arial, sans-serif; background-color: #f4f7f6; margin: 0; padding: 20px; }
            .container { max-width: 1200px; margin: 0 auto; }
            h1 { text-align: center; color: #333; }
            .candidate-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 20px; }
            .candidate-card { border: 1px solid #ddd; border-radius: 8px; padding: 20px; background: white; text-align: center; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
            .candidate-card img { width: 100px; height: 100px; border-radius: 50%; object-fit: cover; margin-bottom: 15px; }
            .candidate-card h3 { margin: 0 0 10px 0; color: #0d47a1; }
            .candidate-card p { color: #555; font-size: 0.9em; }
            .vote-btn { background-color: #007bff; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; font-size: 1em; margin-top: 15px; }
            .vote-btn:hover { background-color: #0056b3; }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Lista de Candidatos</h1>
            <div class="candidate-grid">
                <!-- Este es un bucle que recorre la lista de candidatos que nos envió el Servlet -->
                <c:forEach var="candidate" items="${candidates}">
                    <div class="candidate-card">
                        <img src="${candidate.photo}" alt="${candidate.name}">
                        <h3>${candidate.name}</h3>
                        <p><strong>Partido:</strong> ${candidate.party}</p>
                        <p>${candidate.proposals}</p>
                        
                        <!-- Este formulario enviará el voto a nuestro próximo servlet -->
                        <form action="vote" method="post" style="margin: 0;">
                            <input type="hidden" name="candidateId" value="${candidate.id}">
                            <button type="submit" class="vote-btn">Votar por ${candidate.name}</button>
                        </form>
                    </div>
                </c:forEach>
            </div>
        </div>
    </body>
</html>