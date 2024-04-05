package org.example.back.demos.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.back.demos.config.SystemConfig;
import org.example.back.demos.model.bo.*;
import org.example.back.demos.util.IOUtil;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.dto.CallResponse;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@NoArgsConstructor
@Data
public class LogisticsControllerService {
  public static final String ABI = IOUtil.readResourceAsString("abi/LogisticsController.abi");

  public static final String BINARY = IOUtil.readResourceAsString("bin/LogisticsController.bin");

  @Value("${system.contract.logisticsControllerAddress}")
  private String address;

  @Autowired
  SystemConfig config;

  @Autowired
  private Client client;

  AssembleTransactionProcessor txProcessor;

  @PostConstruct
  public void init() throws Exception {
    System.out.printf("fisco-bcos-blockNumber:%s\n", this.client.getBlockNumber().getBlockNumber().toString());
    this.txProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(this.client, this.client.getCryptoSuite().getCryptoKeyPair());
  }

  public CallResponse GetPerChaseCompany(LogisticsControllerGetPerChaseCompanyInputBO input) throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "GetPerChaseCompany", input.toArgs());
  }

  public CallResponse GetTransOrder(LogisticsControllerGetTransOrderInputBO input) throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "GetTransOrder", input.toArgs());
  }

  public TransactionResponse CreateTransOrder(LogisticsControllerCreateTransOrderInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "CreateTransOrder", input.toArgs());
  }

  public TransactionResponse CreateTransCompany(LogisticsControllerCreateTransCompanyInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "CreateTransCompany", input.toArgs());
  }

  public CallResponse GetPerChaseOrder(LogisticsControllerGetPerChaseOrderInputBO input) throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "GetPerChaseOrder", input.toArgs());
  }

  public TransactionResponse CreatePerChaseOrder(LogisticsControllerCreatePerChaseOrderInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "CreatePerChaseOrder", input.toArgs());
  }

  public TransactionResponse CreatePerChaseCompany(LogisticsControllerCreatePerChaseCompanyInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "CreatePerChaseCompany", input.toArgs());
  }

  public CallResponse GetTransCompany(LogisticsControllerGetTransCompanyInputBO input) throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "GetTransCompany", input.toArgs());
  }
}
