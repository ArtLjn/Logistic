package org.example.back.demos.config;

import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.config.ConfigOption;
import org.fisco.bcos.sdk.config.exceptions.ConfigException;
import org.fisco.bcos.sdk.config.model.ConfigProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Slf4j
//@Configuration
public class SdkConfig {
    private final BcosConfig bcosConfig;
    private final SystemConfig systemConfig;

    public SdkConfig(BcosConfig bcosConfig, SystemConfig systemConfig) {
        this.bcosConfig = bcosConfig;
        this.systemConfig = systemConfig;
    }

    @Bean
    public Client client() throws ConfigException {
        ConfigProperty configProperty = new ConfigProperty();
        Map peers = bcosConfig.getNetwork();
        Map<String, Object> conf = bcosConfig.getCryptoMaterial();
        configProperty.setNetwork(peers);
        configProperty.setCryptoMaterial(conf);
        ConfigOption configOption = new ConfigOption(configProperty);
        Client client = new BcosSDK(configOption).getClient(systemConfig.getGroupId());
        configCryptoKeyPair(client);
        return client;
    }

    public void configCryptoKeyPair(Client client){
        if(systemConfig.getHexPrivateKey() == null || systemConfig.getHexPrivateKey().isEmpty()){
            return;
        }
        if(systemConfig.getHexPrivateKey().startsWith("0x") || systemConfig.getHexPrivateKey().startsWith("0X")){
            System.out.println(systemConfig.getHexPrivateKey().substring(2));
            systemConfig.setHexPrivateKey(systemConfig.getHexPrivateKey().substring(2));
        }
        client.getCryptoSuite().setCryptoKeyPair(client.getCryptoSuite().createKeyPair(systemConfig.getHexPrivateKey()));
    }
}
