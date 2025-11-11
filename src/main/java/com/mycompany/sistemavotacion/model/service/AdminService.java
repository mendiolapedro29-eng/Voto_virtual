package com.mycompany.sistemavotacion.model.service;

import com.mycompany.sistemavotacion.model.dao.*;
import com.mycompany.sistemavotacion.model.*;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;
import com.mycompany.sistemavotacion.util.JSONUtil;
import com.mycompany.sistemavotacion.util.SecurityUtil;

public class AdminService {
    private static final Logger logger = Logger.getLogger(AdminService.class.getName());
    
    private VotoDAO votoDAO;
    private UsuarioDAO usuarioDAO;
    private CandidatoDAO candidatoDAO;
    private SecurityUtil securityUtil;
    
    public AdminService() {
        this.votoDAO = new VotoDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.candidatoDAO = new CandidatoDAO();
        this.securityUtil = new SecurityUtil();
    }
    
    public boolean autenticarAdmin(String username, String password) {
        logger.info("🔐 [SIMULACIÓN] Autenticando admin: " + username);
        
        // Credenciales hardcodeadas para simulación
        boolean credencialesValidas = "admin".equals(username) && "admin123".equals(password);
        
        if (credencialesValidas) {
            logger.info("✅ Admin autenticado exitosamente: " + username);
        } else {
            logger.warning("❌ Credenciales inválidas para admin: " + username);
        }
        
        return credencialesValidas;
    }
    
    public Map<String, Object> obtenerEstadisticasGenerales(int eleccionId) {
        logger.info("📊 [SIMULACIÓN] Obteniendo estadísticas para elección: " + eleccionId);
        
        Map<String, Object> estadisticas = new HashMap<>();
        
        try {
            // Obtener estadísticas de usuarios
            Map<String, Object> statsUsuarios = usuarioDAO.obtenerEstadisticas();
            
            // Obtener resultados por candidato
            List<Object[]> resultados = votoDAO.obtenerResultadosPorCandidato(eleccionId);
            int totalVotos = votoDAO.obtenerTotalVotos();
            
            // Obtener candidatos con porcentajes
            List<Candidato> candidatosConStats = candidatoDAO.obtenerCandidatosConPorcentajes(eleccionId);
            
            // Estadísticas generales simuladas
            estadisticas.put("totalVotos", totalVotos);
            estadisticas.put("totalUsuarios", statsUsuarios.get("totalUsuarios"));
            estadisticas.put("usuariosQueVotaron", statsUsuarios.get("yaVotaron"));
            estadisticas.put("usuariosPorVotar", statsUsuarios.get("noHanVotado"));
            estadisticas.put("participacion", statsUsuarios.get("porcentajeParticipacion"));
            estadisticas.put("resultadosPorCandidato", resultados);
            estadisticas.put("candidatosConPorcentajes", candidatosConStats);
            
            // Estadísticas adicionales simuladas
            estadisticas.put("votosPorHora", obtenerVotosPorHora());
            estadisticas.put("participacionPorRegion", obtenerParticipacionPorRegion());
            estadisticas.put("tendencias", obtenerTendenciasTiempoReal());
            
            logger.info("✅ Estadísticas generadas - Total votos: " + totalVotos);
            
        } catch (Exception e) {
            logger.severe("❌ Error al obtener estadísticas: " + e.getMessage());
            // Datos de fallback para evitar errores
            estadisticas.put("totalVotos", 0);
            estadisticas.put("totalUsuarios", 0);
            estadisticas.put("participacion", "0%");
            estadisticas.put("error", e.getMessage());
        }
        
        return estadisticas;
    }
    
    public List<Usuario> obtenerTodosUsuarios() {
        logger.info("👥 [SIMULACIÓN] Obteniendo todos los usuarios");
        
        try {
            List<Usuario> usuarios = usuarioDAO.obtenerTodos();
            logger.info("✅ Usuarios obtenidos: " + usuarios.size());
            return usuarios;
        } catch (Exception e) {
            logger.severe("❌ Error al obtener usuarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public List<Map<String, Object>> obtenerHistorialVotos() {
        logger.info("📋 [SIMULACIÓN] Obteniendo historial de votos");
        
        List<Map<String, Object>> historial = new ArrayList<>();
        
        try {
            // Obtener votos reales del DAO
            List<Voto> votos = votoDAO.obtenerTodosVotos();
            
            for (Voto voto : votos) {
                Map<String, Object> registro = new HashMap<>();
                registro.put("id", "V" + String.format("%06d", voto.getId()));
                registro.put("fecha", voto.getTimestamp().toString());
                registro.put("usuarioId", voto.getUsuarioId());
                registro.put("candidatoId", voto.getCandidatoId());
                registro.put("eleccionId", voto.getEleccionId());
                registro.put("ip", voto.getIpVotacion());
                registro.put("hash", voto.getHashVoto());
                
                // Enriquecer con datos del candidato
                Candidato candidato = candidatoDAO.obtenerPorId(voto.getCandidatoId());
                if (candidato != null) {
                    registro.put("candidato", candidato.getNombre());
                    registro.put("cargo", candidato.getCargo());
                    registro.put("partido", candidato.getPartido());
                }
                
                historial.add(registro);
            }
            
            // Si no hay votos reales, usar datos simulados
            if (historial.isEmpty()) {
                historial = generarHistorialSimulado();
            }
            
            logger.info("✅ Historial obtenido: " + historial.size() + " registros");
            
        } catch (Exception e) {
            logger.severe("❌ Error al obtener historial: " + e.getMessage());
            historial = generarHistorialSimulado(); // Fallback a datos simulados
        }
        
        return historial;
    }
    
    private List<Map<String, Object>> generarHistorialSimulado() {
        List<Map<String, Object>> historial = new ArrayList<>();
        
        // Datos simulados del historial
        String[][] datosVotos = {
            {"V001234", "20/08/2023 10:30:25", "Lima", "Pedro Castillo", "PRESIDENTE", "Perú Libre", "192.168.1.100"},
            {"V001235", "20/08/2023 10:31:15", "Arequipa", "Keiko Fujimori", "PRESIDENTE", "Fuerza Popular", "192.168.1.101"},
            {"V001236", "20/08/2023 10:32:45", "Cuzco", "Juan Pérez", "CONGRESISTA", "Partido A", "192.168.1.102"},
            {"V001237", "20/08/2023 10:33:20", "Lima", "Miguel Torres", "ALCALDE", "Partido X", "192.168.1.103"},
            {"V001238", "20/08/2023 10:34:10", "Trujillo", "Verónika Mendoza", "PRESIDENTE", "Juntos por el Perú", "192.168.1.104"},
            {"V001239", "20/08/2023 10:35:05", "Lima", "Ana Gómez", "CONGRESISTA", "Partido B", "192.168.1.105"}
        };
        
        for (String[] dato : datosVotos) {
            Map<String, Object> voto = new HashMap<>();
            voto.put("id", dato[0]);
            voto.put("fecha", dato[1]);
            voto.put("provincia", dato[2]);
            voto.put("candidato", dato[3]);
            voto.put("cargo", dato[4]);
            voto.put("partido", dato[5]);
            voto.put("ip", dato[6]);
            historial.add(voto);
        }
        
        return historial;
    }
    
    public boolean cargarDatasetUsuarios(List<Usuario> usuarios) {
        logger.info("📥 [SIMULACIÓN] Cargando dataset de " + usuarios.size() + " usuarios");
        
        int exitosos = 0;
        
        for (Usuario usuario : usuarios) {
            try {
                if (usuarioDAO.insertar(usuario)) {
                    exitosos++;
                }
            } catch (Exception e) {
                logger.warning("⚠️ Error al insertar usuario " + usuario.getDni() + ": " + e.getMessage());
            }
        }
        
        boolean exito = exitosos > 0;
        logger.info("✅ Dataset cargado: " + exitosos + "/" + usuarios.size() + " usuarios insertados - Éxito: " + exito);
        
        return exito;
    }
    
    public boolean generarReporte(int eleccionId, String formato, HttpServletResponse response) {
        logger.info("📄 [SIMULACIÓN] Generando reporte en formato: " + formato + " para elección: " + eleccionId);
        
        try {
            Map<String, Object> estadisticas = obtenerEstadisticasGenerales(eleccionId);
            
            switch (formato.toLowerCase()) {
                case "pdf":
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "attachment; filename=reporte_eleccion_" + eleccionId + ".pdf");
                    // Simular generación de PDF
                    response.getOutputStream().write("PDF SIMULADO - Reporte de Elección".getBytes());
                    break;
                    
                case "excel":
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "attachment; filename=reporte_eleccion_" + eleccionId + ".xlsx");
                    // Simular generación de Excel
                    response.getOutputStream().write("EXCEL SIMULADO - Reporte de Elección".getBytes());
                    break;
                    
                case "json":
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(JSONUtil.toJson(estadisticas));
                    break;
                    
                case "csv":
                    response.setContentType("text/csv");
                    response.setHeader("Content-Disposition", "attachment; filename=reporte_eleccion_" + eleccionId + ".csv");
                    generarCSV(estadisticas, response);
                    break;
                    
                default:
                    logger.warning("⚠️ Formato no soportado: " + formato);
                    return false;
            }
            
            logger.info("✅ Reporte generado exitosamente en formato: " + formato);
            return true;
            
        } catch (Exception e) {
            logger.severe("❌ Error al generar reporte: " + e.getMessage());
            return false;
        }
    }
    
    // Métodos auxiliares para estadísticas simuladas
    
    private List<Map<String, Object>> obtenerVotosPorHora() {
        List<Map<String, Object>> votosPorHora = new ArrayList<>();
        
        String[] horas = {"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00"};
        int[] votos = {1250, 3450, 5670, 7890, 8450, 9230, 8670, 7540};
        
        for (int i = 0; i < horas.length; i++) {
            Map<String, Object> registro = new HashMap<>();
            registro.put("hora", horas[i]);
            registro.put("votos", votos[i]);
            registro.put("tendencia", i > 0 ? ((votos[i] - votos[i-1]) * 100 / votos[i-1]) : 0);
            votosPorHora.add(registro);
        }
        
        return votosPorHora;
    }
    
    private Map<String, Object> obtenerParticipacionPorRegion() {
        Map<String, Object> participacion = new HashMap<>();
        
        participacion.put("Lima", 78.5);
        participacion.put("Arequipa", 82.3);
        participacion.put("Cuzco", 75.8);
        participacion.put("La Libertad", 79.1);
        participacion.put("Piura", 71.4);
        participacion.put("Lambayeque", 76.9);
        
        return participacion;
    }
    
    private List<Map<String, Object>> obtenerTendenciasTiempoReal() {
        List<Map<String, Object>> tendencias = new ArrayList<>();
        
        String[] candidatos = {"Pedro Castillo", "Keiko Fujimori", "Yonhy Lescano", "Rafael López Aliaga"};
        double[] porcentajes = {18.5, 15.2, 12.8, 9.7};
        String[] tendencia = {"↑", "↓", "→", "↑"};
        
        for (int i = 0; i < candidatos.length; i++) {
            Map<String, Object> registro = new HashMap<>();
            registro.put("candidato", candidatos[i]);
            registro.put("porcentaje", porcentajes[i]);
            registro.put("tendencia", tendencia[i]);
            registro.put("cambio", (i % 2 == 0) ? "+0.5" : "-0.3");
            tendencias.add(registro);
        }
        
        return tendencias;
    }
    
    private void generarCSV(Map<String, Object> estadisticas, HttpServletResponse response) {
        try {
            StringBuilder csv = new StringBuilder();
            csv.append("Estadística,Valor\n");
            csv.append("Total Votos,").append(estadisticas.get("totalVotos")).append("\n");
            csv.append("Total Usuarios,").append(estadisticas.get("totalUsuarios")).append("\n");
            csv.append("Participación,").append(estadisticas.get("participacion")).append("\n");
            
            response.getWriter().write(csv.toString());
        } catch (Exception e) {
            logger.severe("❌ Error generando CSV: " + e.getMessage());
        }
    }
    
    /**
     * Método adicional para simular datos de prueba
     */
    public void simularDatosPrueba() {
        logger.info("🎯 [SIMULACIÓN] Generando datos de prueba");
        
        // Simular votos masivos
        votoDAO.simularVotosMasivos(100, 1);
        
        // Simular carga de usuarios
        usuarioDAO.simularCargaMasiva(50);
        
        logger.info("✅ Datos de prueba generados exitosamente");
    }
    
    /**
     * Método para limpiar datos de prueba
     */
    public void limpiarDatosPrueba() {
        logger.warning("🧹 [SIMULACIÓN] Limpiando datos de prueba");
        
        votoDAO.limpiarVotos();
        usuarioDAO.limpiarUsuarios();
        
        logger.info("✅ Datos de prueba limpiados");
    }
}