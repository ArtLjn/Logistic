package org.example.back.demos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(
        prefix = "contract"
)
public class ContractConfig {
    public String logisticsControllerAddress;
}
