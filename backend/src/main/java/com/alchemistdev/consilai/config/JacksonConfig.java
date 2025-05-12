package com.alchemistdev.consilai.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

@Configuration
public class JacksonConfig {

    private static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm";

    @Bean
    public Module dateTimeModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDateTime.class,
            new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)));
        return module;
    }
}
