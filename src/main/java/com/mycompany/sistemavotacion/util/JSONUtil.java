package com.mycompany.sistemavotacion.util;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class JSONUtil {
    
    public static String toJson(Map<String, Object> data) {
        if (data == null) {
            return "null";
        }
        
        StringBuilder json = new StringBuilder();
        json.append("{");
        
        boolean first = true;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (!first) {
                json.append(",");
            }
            first = false;
            
            json.append("\"").append(escapeJson(entry.getKey())).append("\":");
            json.append(toJsonValue(entry.getValue()));
        }
        
        json.append("}");
        return json.toString();
    }
    
    private static String toJsonValue(Object value) {
        if (value == null) {
            return "null";
        } else if (value instanceof String) {
            return "\"" + escapeJson((String) value) + "\"";
        } else if (value instanceof Number) {
            return value.toString();
        } else if (value instanceof Boolean) {
            return value.toString();
        } else if (value instanceof List) {
            return listToJson((List<?>) value);
        } else if (value instanceof Object[]) {
            return arrayToJson((Object[]) value);
        } else {
            return "\"" + escapeJson(value.toString()) + "\"";
        }
    }
    
    private static String listToJson(List<?> list) {
        if (list == null) {
            return "null";
        }
        
        StringBuilder json = new StringBuilder();
        json.append("[");
        
        boolean first = true;
        for (Object item : list) {
            if (!first) {
                json.append(",");
            }
            first = false;
            
            json.append(toJsonValue(item));
        }
        
        json.append("]");
        return json.toString();
    }
    
    private static String arrayToJson(Object[] array) {
        if (array == null) {
            return "null";
        }
        
        StringBuilder json = new StringBuilder();
        json.append("[");
        
        boolean first = true;
        for (Object item : array) {
            if (!first) {
                json.append(",");
            }
            first = false;
            
            json.append(toJsonValue(item));
        }
        
        json.append("]");
        return json.toString();
    }
    
    private static String escapeJson(String text) {
        if (text == null) {
            return "";
        }
        
        return text.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\b", "\\b")
                  .replace("\f", "\\f")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
    
    // Método adicional para convertir listas de arrays de objetos
    public static String toJson(List<Object[]> dataList) {
        if (dataList == null) {
            return "null";
        }
        
        StringBuilder json = new StringBuilder();
        json.append("[");
        
        boolean first = true;
        for (Object[] item : dataList) {
            if (!first) {
                json.append(",");
            }
            first = false;
            
            json.append(arrayToJson(item));
        }
        
        json.append("]");
        return json.toString();
    }
}