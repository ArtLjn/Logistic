package org.example.back.demos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties
@Data
public class BcosConfig {
    private Map<String, List<String>> network;
    private Map<String,Object> cryptoMaterial;
}