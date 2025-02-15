package org.example.back.demos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(
        prefix = "system"
)
public class SystemConfig {
    private int groupId = 1;

    private String hexPrivateKey;

    @NestedConfigurationProperty
    private ContractConfig contract;
}
