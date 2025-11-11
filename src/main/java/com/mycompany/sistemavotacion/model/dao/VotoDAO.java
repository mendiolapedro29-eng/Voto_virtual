package com.mycompany.sistemavotacion.model.dao;

import com.mycompany.sistemavotacion.model.Voto;
import com.mycompany.sistemavotacion.util.DatabaseUtil;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class VotoDAO {
    private static final Logger logger = Logger.getLogger(VotoDAO.class.getName());
    
    // Almacenamiento en memoria para simulación
    private static final List<Voto> votosSimulados = new ArrayList<>();
    private static int nextVotoId = 1;
    
    // Inicializar algunos votos de ejemplo
    static {
        inicializarVotosSimulados();
    }
    
    private static void inicializarVotosSimulados() {
        // Agregar algunos votos de ejemplo para tener datos iniciales
        Voto voto1 = new Voto();
        voto1.setId(nextVotoId++);
        voto1.setUsuarioId(101);
        voto1.setCandidatoId(1); // Pedro Castillo
        voto1.setEleccionId(1);
        voto1.setTimestamp(new Timestamp(System.currentTimeMillis() - 3600000)); // 1 hora atrás
        voto1.setHashVoto("hash_simulado_001");
        voto1.setIpVotacion("192.168.1.100");
        votosSimulados.add(voto1);
        
        Voto voto2 = new Voto();
        voto2.setId(nextVotoId++);
        voto2.setUsuarioId(102);
        voto2.setCandidatoId(2); // Keiko Fujimori
        voto2.setEleccionId(1);
        voto2.setTimestamp(new Timestamp(System.currentTimeMillis() - 1800000)); // 30 min atrás
        voto2.setHashVoto("hash_simulado_002");
        voto2.setIpVotacion("192.168.1.101");
        votosSimulados.add(voto2);
        
        Voto voto3 = new Voto();
        voto3.setId(nextVotoId++);
        voto3.setUsuarioId(103);
        voto3.setCandidatoId(3); // Yonhy Lescano
        voto3.setEleccionId(1);
        voto3.setTimestamp(new Timestamp(System.currentTimeMillis() - 900000)); // 15 min atrás
        voto3.setHashVoto("hash_simulado_003");
        voto3.setIpVotacion("192.168.1.102");
        votosSimulados.add(voto3);
        
        logger.info("✅ " + votosSimulados.size() + " votos simulados inicializados");
    }
    
    public boolean insertar(Voto voto) {
        logger.info("📥 [SIMULACIÓN] Insertando voto para usuario: " + voto.getUsuarioId() + 
                   ", candidato: " + voto.getCandidatoId());
        
        try {
            // Verificar si el usuario ya votó
            if (usuarioYaVoto(voto.getUsuarioId(), voto.getEleccionId())) {
                logger.warning("⚠️ Usuario " + voto.getUsuarioId() + " ya votó en esta elección");
                return false;
            }
            
            // Asignar ID y timestamp
            voto.setId(nextVotoId++);
            voto.setTimestamp(new Timestamp(System.currentTimeMillis()));
            
            // Generar hash único si no viene
            if (voto.getHashVoto() == null || voto.getHashVoto().isEmpty()) {
                voto.setHashVoto("hash_" + System.currentTimeMillis() + "_" + voto.getUsuarioId());
            }
            
            // Agregar a la lista simulada
            synchronized (votosSimulados) {
                votosSimulados.add(voto);
            }
            
            logger.info("✅ Voto registrado exitosamente - ID: " + voto.getId());
            return true;
            
        } catch (Exception e) {
            logger.severe("❌ Error insertando voto simulado: " + e.getMessage());
            return false;
        }
    }
    
    public boolean usuarioYaVoto(int usuarioId, int eleccionId) {
        logger.info("🔍 [SIMULACIÓN] Verificando si usuario " + usuarioId + " ya votó en elección " + eleccionId);
        
        synchronized (votosSimulados) {
            for (Voto voto : votosSimulados) {
                if (voto.getUsuarioId() == usuarioId && voto.getEleccionId() == eleccionId) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public List<Object[]> obtenerResultadosPorCandidato(int eleccionId) {
        logger.info("📊 [SIMULACIÓN] Obteniendo resultados por candidato para elección: " + eleccionId);
        
        Map<Integer, Integer> conteoPorCandidato = new HashMap<>();
        
        synchronized (votosSimulados) {
            for (Voto voto : votosSimulados) {
                if (voto.getEleccionId() == eleccionId) {
                    int candidatoId = voto.getCandidatoId();
                    conteoPorCandidato.put(candidatoId, conteoPorCandidato.getOrDefault(candidatoId, 0) + 1);
                }
            }
        }
        
        List<Object[]> resultados = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : conteoPorCandidato.entrySet()) {
            Object[] resultado = new Object[2];
            resultado[0] = entry.getKey(); // candidato_id
            resultado[1] = entry.getValue(); // total votos
            resultados.add(resultado);
        }
        
        logger.info("📈 Resultados obtenidos: " + resultados.size() + " candidatos con votos");
        return resultados;
    }
    
    public List<Voto> obtenerTodosVotos() {
        logger.info("📋 [SIMULACIÓN] Obteniendo todos los votos - Total: " + votosSimulados.size());
        
        // Retornar copia para evitar modificación externa
        synchronized (votosSimulados) {
            return new ArrayList<>(votosSimulados);
        }
    }
    
    // NUEVOS MÉTODOS ÚTILES PARA SIMULACIÓN
    
    /**
     * Obtiene votos por candidato específico
     */
    public List<Voto> obtenerVotosPorCandidato(int candidatoId) {
        logger.info("👤 [SIMULACIÓN] Obteniendo votos para candidato: " + candidatoId);
        
        List<Voto> votosCandidato = new ArrayList<>();
        synchronized (votosSimulados) {
            for (Voto voto : votosSimulados) {
                if (voto.getCandidatoId() == candidatoId) {
                    votosCandidato.add(voto);
                }
            }
        }
        return votosCandidato;
    }
    
    /**
 * Obtiene estadísticas generales de votación
 */
public Map<String, Object> obtenerEstadisticasVotacion(int eleccionId) {
    logger.info("📈 [SIMULACIÓN] Obteniendo estadísticas para elección: " + eleccionId);
    
    Map<String, Object> stats = new HashMap<>();
    int totalVotos = 0;
    Map<Integer, Integer> votosPorCandidato = new HashMap<>();
    
    synchronized (votosSimulados) {
        for (Voto voto : votosSimulados) {
            if (voto.getEleccionId() == eleccionId) {
                totalVotos++;
                int candidatoId = voto.getCandidatoId();
                votosPorCandidato.put(candidatoId, votosPorCandidato.getOrDefault(candidatoId, 0) + 1);
            }
        }
    }
    
    stats.put("totalVotos", totalVotos);
    stats.put("votosPorCandidato", votosPorCandidato);
    stats.put("fechaConsulta", new java.util.Date()); // ✅ CORREGIDO
    
    return stats;
}
    /**
     * Simula la recepción de múltiples votos (útil para pruebas)
     */
    public void simularVotosMasivos(int cantidad, int eleccionId) {
        logger.info("🎯 [SIMULACIÓN] Simulando " + cantidad + " votos masivos para elección " + eleccionId);
        
        Random random = new Random();
        int[] candidatosIds = {1, 2, 3, 4, 5}; // IDs de candidatos presidentes
        
        for (int i = 0; i < cantidad; i++) {
            Voto voto = new Voto();
            voto.setId(nextVotoId++);
            voto.setUsuarioId(1000 + i); // Usuarios únicos
            voto.setCandidatoId(candidatosIds[random.nextInt(candidatosIds.length)]); // Candidato aleatorio
            voto.setEleccionId(eleccionId);
            voto.setTimestamp(new Timestamp(System.currentTimeMillis() - random.nextInt(3600000))); // Última hora
            voto.setHashVoto("simulado_masivo_" + i);
            voto.setIpVotacion("192.168.1." + (100 + random.nextInt(100)));
            
            synchronized (votosSimulados) {
                votosSimulados.add(voto);
            }
        }
        
        logger.info("✅ Simulación completada - Total votos: " + votosSimulados.size());
    }
    
    /**
     * Obtiene el total de votos registrados
     */
    public int obtenerTotalVotos() {
        synchronized (votosSimulados) {
            return votosSimulados.size();
        }
    }
    
    /**
     * Limpia todos los votos (útil para pruebas)
     */
    public void limpiarVotos() {
        logger.warning("🧹 [SIMULACIÓN] LIMPIANDO TODOS LOS VOTOS - Solo para desarrollo");
        synchronized (votosSimulados) {
            votosSimulados.clear();
            nextVotoId = 1;
            inicializarVotosSimulados(); // Volver a inicializar con datos básicos
        }
    }
    
    // Método de mapeo mantenido por compatibilidad
    private Voto mapResultSetToVoto(ResultSet rs) throws SQLException {
        Voto voto = new Voto();
        voto.setId(rs.getInt("id"));
        voto.setUsuarioId(rs.getInt("usuario_id"));
        voto.setCandidatoId(rs.getInt("candidato_id"));
        voto.setEleccionId(rs.getInt("eleccion_id"));
        voto.setTimestamp(rs.getTimestamp("timestamp"));
        voto.setHashVoto(rs.getString("hash_voto"));
        voto.setIpVotacion(rs.getString("ip_votacion"));
        return voto;
    }
}