package org.example.back;

import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

@SpringBootTest
class BackApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testCryptoSuiteForECDSA() {
        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
        System.out.println(cryptoSuite.getCryptoKeyPair().getAddress());
        System.out.println(cryptoSuite.getCryptoKeyPair().getHexPrivateKey());
        System.out.println(cryptoSuite.getCryptoKeyPair().getHexPublicKey());
        // generate keyPair
        BigInteger privateKey = new BigInteger(cryptoSuite.getCryptoKeyPair().getHexPrivateKey(),16);
        System.out.println(privateKey);
        CryptoSuite cryptoSuite1 = new CryptoSuite(CryptoType.ECDSA_TYPE);
        CryptoKeyPair keyPair = cryptoSuite.getCryptoKeyPair();
    }

}
