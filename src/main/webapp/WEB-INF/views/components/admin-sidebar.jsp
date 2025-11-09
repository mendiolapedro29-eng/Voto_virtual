<div class="position-sticky pt-3">
    <h5 class="px-3">Panel de Administración</h5>
    <ul class="nav flex-column">
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/admin?action=dashboard">
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