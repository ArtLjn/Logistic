package org.logistics.back.service;

import java.lang.Exception;
import java.lang.String;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.dto.CallResponse;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.logistics.back.model.bo.LogisticsControllerCreatePerChaseCompanyInputBO;
import org.logistics.back.model.bo.LogisticsControllerCreatePerChaseOrderInputBO;
import org.logistics.back.model.bo.LogisticsControllerCreateTransCompanyInputBO;
import org.logistics.back.model.bo.LogisticsControllerCreateTransOrderInputBO;
import org.logistics.back.model.bo.LogisticsControllerGetPerChaseCompanyInputBO;
import org.logistics.back.model.bo.LogisticsControllerGetPerChaseOrderInputBO;
import org.logistics.back.model.bo.LogisticsControllerGetTransCompanyInputBO;
import org.logistics.back.model.bo.LogisticsControllerGetTransOrderInputBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Data
public class LogisticsControllerService {
  public static final String ABI = org.logistics.back.utils.IOUtil.readResourceAsString("abi/LogisticsController.abi");

  public static final String BINARY = org.logistics.back.utils.IOUtil.readResourceAsString("bin/ecc/LogisticsController.bin");

  public static final String SM_BINARY = org.logistics.back.utils.IOUtil.readResourceAsString("bin/sm/LogisticsController.bin");

  @Value("${system.contract.logisticsControllerAddress}")
  private String address;

  @Autowired
  private Client client;

  AssembleTransactionProcessor txProcessor;

  @PostConstruct
  public void init() throws Exception {
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
