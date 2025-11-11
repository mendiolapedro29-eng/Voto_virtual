package com.mycompany.sistemavotacion.model.dao;

import com.mycompany.sistemavotacion.model.Usuario;
import java.util.*;
import java.util.logging.Logger;

public class UsuarioDAO {
    private static final Logger logger = Logger.getLogger(UsuarioDAO.class.getName());
    
    // Almacenamiento en memoria para simulación
    private static final Map<String, Usuario> usuariosSimulados = new HashMap<>();
    private static int nextUsuarioId = 1;
    
    // Inicializar usuarios de ejemplo
    static {
        inicializarUsuariosSimulados();
    }
    
    private static void inicializarUsuariosSimulados() {
        // Crear usuarios de prueba
        agregarUsuario("12345678", "Juan", "Pérez", "Lima", "Lima", "Lima", new GregorianCalendar(1990, 0, 15).getTime());
        agregarUsuario("87654321", "María", "Gómez", "Miraflores", "Lima", "Lima", new GregorianCalendar(1985, 5, 20).getTime());
        agregarUsuario("11111111", "Carlos", "López", "Arequipa", "Arequipa", "Arequipa", new GregorianCalendar(1992, 2, 10).getTime());
        agregarUsuario("22222222", "Ana", "Rodríguez", "Cuzco", "Cuzco", "Cuzco", new GregorianCalendar(1988, 7, 5).getTime());
        agregarUsuario("33333333", "Luis", "Torres", "Trujillo", "La Libertad", "La Libertad", new GregorianCalendar(1995, 11, 25).getTime());
        
        // Marcar a Carlos como que ya votó
        usuariosSimulados.get("11111111").setHaVotado(true);
        
        logger.info("✅ " + usuariosSimulados.size() + " usuarios simulados inicializados");
    }
    
    private static void agregarUsuario(String dni, String nombres, String apellidos, 
                                     String distrito, String provincia, String departamento, Date fechaNacimiento) {
        Usuario usuario = new Usuario();
        usuario.setId(nextUsuarioId++);
        usuario.setDni(dni);
        usuario.setNombres(nombres);
        usuario.setApellidos(apellidos);
        usuario.setDistrito(distrito);
        usuario.setProvincia(provincia);
        usuario.setDepartamento(departamento);
        usuario.setFechaNacimiento(fechaNacimiento);
        usuario.setHaVotado(false);
        usuario.setFechaCreacion(new Date());
        
        usuariosSimulados.put(dni, usuario);
    }
    
    public Usuario buscarPorDNI(String dni) {
        logger.info("🔍 [SIMULACIÓN] Buscando usuario con DNI: " + dni);
        
        Usuario usuario = usuariosSimulados.get(dni);
        if (usuario != null) {
            logger.info("✅ Usuario encontrado: " + usuario.getNombres() + " " + usuario.getApellidos());
        } else {
            logger.warning("❌ Usuario no encontrado para DNI: " + dni);
        }
        
        return usuario;
    }
    
    public boolean marcarComoVotado(int usuarioId) {
        logger.info("✅ [SIMULACIÓN] Marcando como votado al usuario ID: " + usuarioId);
        
        for (Usuario usuario : usuariosSimulados.values()) {
            if (usuario.getId() == usuarioId) {
                usuario.setHaVotado(true);
                logger.info("✅ Usuario " + usuario.getNombres() + " " + usuario.getApellidos() + " marcado como votado");
                return true;
            }
        }
        
        logger.warning("❌ No se encontró usuario con ID: " + usuarioId);
        return false;
    }
    
    public boolean marcarComoVotadoPorDNI(String dni) {
        logger.info("✅ [SIMULACIÓN] Marcando como votado al usuario DNI: " + dni);
        
        Usuario usuario = usuariosSimulados.get(dni);
        if (usuario != null) {
            usuario.setHaVotado(true);
            logger.info("✅ Usuario " + usuario.getNombres() + " " + usuario.getApellidos() + " marcado como votado");
            return true;
        }
        
        logger.warning("❌ No se encontró usuario con DNI: " + dni);
        return false;
    }
    
    public boolean insertar(Usuario usuario) {
        logger.info("📥 [SIMULACIÓN] Insertando usuario: " + usuario.getNombres() + " " + usuario.getApellidos());
        
        try {
            // Verificar si el DNI ya existe
            if (usuariosSimulados.containsKey(usuario.getDni())) {
                logger.warning("⚠️ DNI ya existe: " + usuario.getDni());
                return false;
            }
            
            // Asignar ID si no tiene
            if (usuario.getId() == 0) {
                usuario.setId(nextUsuarioId++);
            }
            
            // Establecer fecha de creación si no tiene
            if (usuario.getFechaCreacion() == null) {
                usuario.setFechaCreacion(new Date());
            }
            
            usuariosSimulados.put(usuario.getDni(), usuario);
            logger.info("✅ Usuario insertado exitosamente - ID: " + usuario.getId());
            return true;
            
        } catch (Exception e) {
            logger.severe("❌ Error insertando usuario: " + e.getMessage());
            return false;
        }
    }
    
    public List<Usuario> obtenerTodos() {
        logger.info("📋 [SIMULACIÓN] Obteniendo todos los usuarios - Total: " + usuariosSimulados.size());
        
        // Retornar copia para evitar modificación externa
        return new ArrayList<>(usuariosSimulados.values());
    }
    
    /**
     * Obtener usuarios por departamento
     */
    public List<Usuario> obtenerPorDepartamento(String departamento) {
        logger.info("🏢 [SIMULACIÓN] Obteniendo usuarios del departamento: " + departamento);
        
        List<Usuario> usuariosDepartamento = new ArrayList<>();
        for (Usuario usuario : usuariosSimulados.values()) {
            if (departamento.equalsIgnoreCase(usuario.getDepartamento())) {
                usuariosDepartamento.add(usuario);
            }
        }
        
        logger.info("📊 Usuarios encontrados: " + usuariosDepartamento.size());
        return usuariosDepartamento;
    }
    
    /**
     * Obtener usuarios que ya votaron
     */
    public List<Usuario> obtenerQueYaVotaron() {
        logger.info("✅ [SIMULACIÓN] Obteniendo usuarios que ya votaron");
        
        List<Usuario> votantes = new ArrayList<>();
        for (Usuario usuario : usuariosSimulados.values()) {
            if (usuario.isHaVotado()) {
                votantes.add(usuario);
            }
        }
        
        logger.info("📊 Usuarios que ya votaron: " + votantes.size());
        return votantes;
    }
    
    /**
     * Obtener usuarios que NO han votado
     */
    public List<Usuario> obtenerQueNoHanVotado() {
        logger.info("⏳ [SIMULACIÓN] Obteniendo usuarios que no han votado");
        
        List<Usuario> noVotantes = new ArrayList<>();
        for (Usuario usuario : usuariosSimulados.values()) {
            if (!usuario.isHaVotado()) {
                noVotantes.add(usuario);
            }
        }
        
        logger.info("📊 Usuarios que no han votado: " + noVotantes.size());
        return noVotantes;
    }
    
    /**
     * Obtener estadísticas de usuarios
     */
    public Map<String, Object> obtenerEstadisticas() {
        logger.info("📈 [SIMULACIÓN] Obteniendo estadísticas de usuarios");
        
        Map<String, Object> stats = new HashMap<>();
        int totalUsuarios = usuariosSimulados.size();
        int yaVotaron = obtenerQueYaVotaron().size();
        int noHanVotado = obtenerQueNoHanVotado().size();
        
        stats.put("totalUsuarios", totalUsuarios);
        stats.put("yaVotaron", yaVotaron);
        stats.put("noHanVotado", noHanVotado);
        stats.put("porcentajeParticipacion", totalUsuarios > 0 ? (yaVotaron * 100.0 / totalUsuarios) : 0);
        stats.put("fechaConsulta", new java.util.Date());
        
        logger.info("📊 Estadísticas - Total: " + totalUsuarios + 
                   ", Votaron: " + yaVotaron + 
                   ", Por votar: " + noHanVotado);
        
        return stats;
    }
    
    /**
     * Simular carga masiva de usuarios (para pruebas)
     */
    public void simularCargaMasiva(int cantidad) {
        logger.info("🎯 [SIMULACIÓN] Simulando carga de " + cantidad + " usuarios");
        
        Random random = new Random();
        String[] nombres = {"Juan", "María", "Carlos", "Ana", "Luis", "Elena", "Pedro", "Laura"};
        String[] apellidos = {"Pérez", "Gómez", "López", "Rodríguez", "Torres", "Fernández", "Ramírez", "Díaz"};
        String[] departamentos = {"Lima", "Arequipa", "Cuzco", "La Libertad", "Piura", "Lambayeque"};
        
        for (int i = 0; i < cantidad; i++) {
            String dni = String.valueOf(10000000 + random.nextInt(90000000));
            String nombre = nombres[random.nextInt(nombres.length)];
            String apellido = apellidos[random.nextInt(apellidos.length)];
            String departamento = departamentos[random.nextInt(departamentos.length)];
            
            agregarUsuario(dni, nombre, apellido, "Centro", departamento, departamento, 
                         new GregorianCalendar(1980 + random.nextInt(30), random.nextInt(12), random.nextInt(28) + 1).getTime());
        }
        
        logger.info("✅ Carga masiva completada - Total usuarios: " + usuariosSimulados.size());
    }
    
    /**
     * Limpiar todos los usuarios (solo para pruebas)
     */
    public void limpiarUsuarios() {
        logger.warning("🧹 [SIMULACIÓN] LIMPIANDO TODOS LOS USUARIOS - Solo para desarrollo");
        usuariosSimulados.clear();
        nextUsuarioId = 1;
        inicializarUsuariosSimulados(); // Volver a inicializar con datos básicos
    }
}