package com.rui.basic.app.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {
    
    @Bean
    public SchemaStatementInspector schemaStatementInspector() {
        return new SchemaStatementInspector();
    }
}
