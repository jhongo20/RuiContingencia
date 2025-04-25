package com.rui.basic.app.basic.config;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
public class SchemaStatementInspector implements StatementInspector {
    
    // Patrón para identificar nombres de tablas sin esquema
    private static final Pattern TABLE_PATTERN = Pattern.compile(
            "\\s(RUI_[A-Z_]+)\\s", // Con espacios alrededor
            Pattern.CASE_INSENSITIVE
    );
    
    // Patrón alternativo para capturas al inicio de consulta o después de FROM, JOIN, etc.
    private static final Pattern TABLE_PATTERN_WITH_CLAUSE = Pattern.compile(
            "(FROM|JOIN|UPDATE|INTO|,)\\s+(RUI_[A-Z_]+)",
            Pattern.CASE_INSENSITIVE
    );
    
    @Override
    public String inspect(String sql) {
        if (sql == null) {
            return null;
        }
        
        // Reemplaza ocurrencias con espacios alrededor
        String modifiedSql = TABLE_PATTERN.matcher(sql).replaceAll(" RUI.$1 ");
        
        // Reemplaza ocurrencias después de cláusulas
        modifiedSql = TABLE_PATTERN_WITH_CLAUSE.matcher(modifiedSql)
                .replaceAll("$1 RUI.$2");
        
        // Registra la consulta modificada si cambió
        if (!modifiedSql.equals(sql)) {
            System.out.println("SQL modificado: " + modifiedSql);
        }
        
        return modifiedSql;
    }

    // Dentro de la clase SchemaStatementInspector

    // Para depuración
    private void logSqlChange(String original, String modified) {
        System.out.println("SQL original: " + original);
        System.out.println("SQL modificado: " + modified);
    }

    // Método adicional para manejar consultas con CTE (WITH clause)
    private String handleCTEQueries(String sql) {
        if (sql.toUpperCase().startsWith("WITH ")) {
            // Identificar la sección WITH y dejarla sin cambios
            int mainQueryStart = sql.indexOf(") SELECT ");
            if (mainQueryStart > 0) {
                String cteSection = sql.substring(0, mainQueryStart + 1);
                String mainQuery = sql.substring(mainQueryStart + 1);
                // Aplicar los reemplazos solo a la consulta principal
                String modifiedMainQuery = TABLE_PATTERN.matcher(mainQuery).replaceAll(" RUI.$1 ");
                modifiedMainQuery = TABLE_PATTERN_WITH_CLAUSE.matcher(modifiedMainQuery).replaceAll("$1 RUI.$2");
                return cteSection + modifiedMainQuery;
            }
        }
        return sql;
    }
}
