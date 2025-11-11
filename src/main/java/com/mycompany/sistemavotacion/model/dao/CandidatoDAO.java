package com.mycompany.sistemavotacion.model.dao;

import com.mycompany.sistemavotacion.model.Candidato;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class CandidatoDAO {
    private static final Logger logger = Logger.getLogger(CandidatoDAO.class.getName());
    private Random random = new Random();
    
    // Datos simulados de candidatos
    private List<Candidato> generarCandidatosSimulados() {
        List<Candidato> candidatos = new ArrayList<>();
        
        // Presidentes simulados
        String[] presidentes = {
            "Pedro Castillo", "Keiko Fujimori", "Yonhy Lescano", "Rafael López Aliaga", 
            "Verónika Mendoza", "Daniel Urresti", "George Forsyth", "Hernando de Soto", 
            "César Acuña", "Julio Guzmán", "Alfredo Barnechea", "Rosa Bartra", 
            "Jorge Montoya", "Rómulo Mucho", "José Vega"
        };
        
        String[] partidosPresidentes = {
            "Perú Libre", "Fuerza Popular", "Acción Popular", "Renovación Popular",
            "Juntos por el Perú", "Podemos Perú", "Victoria Nacional", "Avanza País",
            "Alianza para el Progreso", "Partido Morado", "Acción Popular", "Fuerza Popular",
            "Renovación Popular", "Perú Libre", "Unión por el Perú"
        };
        
        double[] porcentajesPresidentes = {
            18.5, 15.2, 12.8, 9.7, 8.3, 7.1, 6.5, 5.9, 
            4.8, 4.2, 3.1, 2.7, 1.9, 1.5, 1.8
        };
        
        for (int i = 0; i < presidentes.length; i++) {
            Candidato cand = new Candidato();
            cand.setId(i + 1);
            cand.setNombre(presidentes[i]);
            cand.setPartido(partidosPresidentes[i]);
            cand.setCargo("PRESIDENTE");
            cand.setVotos((int)(porcentajesPresidentes[i] * 10000));
            cand.setPorcentaje(porcentajesPresidentes[i]);
            cand.setActivo(true);
            cand.setEleccionId(1);
            candidatos.add(cand);
        }
        
        // Congresistas simulados
        String[] congresistas = {"Juan Pérez", "Ana Gómez", "Luis Ramírez", "María Flores", "Carlos Rojas"};
        double[] porcentajesCongresistas = {28.5, 24.3, 19.7, 15.2, 12.3};
        
        for (int i = 0; i < congresistas.length; i++) {
            Candidato cand = new Candidato();
            cand.setId(presidentes.length + i + 1);
            cand.setNombre(congresistas[i]);
            cand.setPartido("Partido " + (char)('A' + i));
            cand.setCargo("CONGRESISTA");
            cand.setVotos((int)(porcentajesCongresistas[i] * 5000));
            cand.setPorcentaje(porcentajesCongresistas[i]);
            cand.setActivo(true);
            cand.setEleccionId(1);
            candidatos.add(cand);
        }
        
        // Alcaldes simulados
        String[] alcaldes = {"Miguel Torres", "Lucía Sánchez", "Raúl Díaz", "Carla Paredes", "Eduardo Molina"};
        double[] porcentajesAlcaldes = {32.1, 25.4, 18.9, 12.7, 10.9};
        
        for (int i = 0; i < alcaldes.length; i++) {
            Candidato cand = new Candidato();
            cand.setId(presidentes.length + congresistas.length + i + 1);
            cand.setNombre(alcaldes[i]);
            cand.setPartido("Partido " + (char)('X' + i));
            cand.setCargo("ALCALDE");
            cand.setVotos((int)(porcentajesAlcaldes[i] * 3000));
            cand.setPorcentaje(porcentajesAlcaldes[i]);
            cand.setActivo(true);
            cand.setEleccionId(1);
            candidatos.add(cand);
        }
        
        return candidatos;
    }
    
    public List<Candidato> obtenerPorEleccion(int eleccionId) {
        logger.info("Obteniendo candidatos para elección: " + eleccionId + " (SIMULADO)");
        return generarCandidatosSimulados();
    }
    
    public List<Candidato> obtenerPorCargo(int eleccionId, String cargo) {
        logger.info("Obteniendo candidatos para cargo: " + cargo + " (SIMULADO)");
        List<Candidato> todosCandidatos = generarCandidatosSimulados();
        List<Candidato> filtrados = new ArrayList<>();
        
        for (Candidato cand : todosCandidatos) {
            if (cand.getCargo().equalsIgnoreCase(cargo)) {
                // Simular actualización en tiempo real de votos
                Candidato candidatoActualizado = simularVotoEnTiempoReal(cand);
                filtrados.add(candidatoActualizado);
            }
        }
        return filtrados;
    }
    
    public Candidato obtenerPorId(int id) {
        logger.info("Obteniendo candidato por ID: " + id + " (SIMULADO)");
        List<Candidato> todosCandidatos = generarCandidatosSimulados();
        
        for (Candidato cand : todosCandidatos) {
            if (cand.getId() == id) {
                return simularVotoEnTiempoReal(cand);
            }
        }
        return null;
    }
    
    public List<Candidato> obtenerTodos() {
        logger.info("Obteniendo todos los candidatos (SIMULADO)");
        List<Candidato> todosCandidatos = generarCandidatosSimulados();
        List<Candidato> actualizados = new ArrayList<>();
        
        for (Candidato cand : todosCandidatos) {
            actualizados.add(simularVotoEnTiempoReal(cand));
        }
        return actualizados;
    }
    
    public List<Candidato> obtenerCandidatosConVotos(int eleccionId) {
        logger.info("Obteniendo candidatos con votos (SIMULADO)");
        return obtenerPorEleccion(eleccionId);
    }
    
    public List<Candidato> obtenerCandidatosConPorcentajes(int eleccionId) {
        logger.info("Obteniendo candidatos con porcentajes (SIMULADO)");
        List<Candidato> candidatos = obtenerPorEleccion(eleccionId);
        
        // Recalcular porcentajes para que sumen 100%
        recalcularPorcentajes(candidatos);
        return candidatos;
    }
    
    // Método para simular votos en tiempo real
    private Candidato simularVotoEnTiempoReal(Candidato candidato) {
        Candidato copia = clonarCandidato(candidato);
        
        // Simular incremento aleatorio de votos (entre 0 y 50 votos)
        int votosExtra = random.nextInt(51);
        copia.setVotos(copia.getVotos() + votosExtra);
        
        return copia;
    }
    
    // Método para recalcular porcentajes
    private void recalcularPorcentajes(List<Candidato> candidatos) {
        int totalVotos = 0;
        
        // Calcular total de votos
        for (Candidato cand : candidatos) {
            totalVotos += cand.getVotos();
        }
        
        // Recalcular porcentajes
        for (Candidato cand : candidatos) {
            if (totalVotos > 0) {
                double porcentaje = (cand.getVotos() * 100.0) / totalVotos;
                cand.setPorcentaje(Math.round(porcentaje * 10.0) / 10.0); // Redondear a 1 decimal
            } else {
                cand.setPorcentaje(0.0);
            }
        }
    }
    
    // Método para clonar candidato (evitar modificar el original)
    private Candidato clonarCandidato(Candidato original) {
        Candidato copia = new Candidato();
        copia.setId(original.getId());
        copia.setNombre(original.getNombre());
        copia.setPartido(original.getPartido());
        copia.setCargo(original.getCargo());
        copia.setRegionId(original.getRegionId());
        copia.setFoto(original.getFoto());
        copia.setPropuestas(original.getPropuestas());
        copia.setActivo(original.isActivo());
        copia.setEleccionId(original.getEleccionId());
        copia.setVotos(original.getVotos());
        copia.setPorcentaje(original.getPorcentaje());
        return copia;
    }
    
    // Métodos CRUD simulados (para compatibilidad)
    public boolean insertarCandidato(Candidato candidato) {
        logger.info("Insertando candidato simulado: " + candidato.getNombre());
        // En una implementación real, aquí se guardaría en base de datos
        return true;
    }
    
    public boolean actualizarCandidato(Candidato candidato) {
        logger.info("Actualizando candidato simulado: " + candidato.getNombre());
        // En una implementación real, aquí se actualizaría en base de datos
        return true;
    }
    
    public boolean eliminarCandidato(int id) {
        logger.info("Eliminando candidato simulado ID: " + id);
        // En una implementación real, aquí se eliminaría de la base de datos
        return true;
    }
    
    // Método adicional para obtener estadísticas en tiempo real
    public List<Candidato> obtenerTopCandidatos(int eleccionId, String cargo, int limite) {
        logger.info("Obteniendo top " + limite + " candidatos para " + cargo + " (SIMULADO)");
        List<Candidato> candidatos = obtenerPorCargo(eleccionId, cargo);
        
        // Ordenar por votos (descendente)
        candidatos.sort((c1, c2) -> Integer.compare(c2.getVotos(), c1.getVotos()));
        
        // Limitar resultados
        if (candidatos.size() > limite) {
            return candidatos.subList(0, limite);
        }
        return candidatos;
    }
    
    // Método para simular un voto específico
    public void simularVotoParaCandidato(int candidatoId) {
        logger.info("Simulando voto para candidato ID: " + candidatoId);
        // En una implementación real, esto incrementaría el contador en la base de datos
    }
    
    // Método para obtener candidatos por región (simulado)
    public List<Candidato> obtenerPorRegion(int eleccionId, String cargo, int regionId) {
        logger.info("Obteniendo candidatos para región " + regionId + " y cargo " + cargo + " (SIMULADO)");
        List<Candidato> candidatos = obtenerPorCargo(eleccionId, cargo);
        
        // Simular que algunos candidatos son de diferentes regiones
        List<Candidato> candidatosRegion = new ArrayList<>();
        for (Candidato cand : candidatos) {
            // Asignar región aleatoria para simulación
            cand.setRegionId(random.nextInt(5) + 1);
            if (cand.getRegionId() == regionId) {
                candidatosRegion.add(cand);
            }
        }
        
        return candidatosRegion;
    }
}