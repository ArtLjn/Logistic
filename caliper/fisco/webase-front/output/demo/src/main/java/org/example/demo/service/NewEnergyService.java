package org.example.demo.service;

import java.lang.Exception;
import java.lang.String;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo.model.bo.NewEnergyAccountRemoveInputBO;
import org.example.demo.model.bo.NewEnergyAccountUpdateInputBO;
import org.example.demo.model.bo.NewEnergyEnergy_InitInputBO;
import org.example.demo.model.bo.NewEnergyEnergy_Order_insertInputBO;
import org.example.demo.model.bo.NewEnergyEnergy_RemoveInputBO;
import org.example.demo.model.bo.NewEnergyEnergy_UpdateInputBO;
import org.example.demo.model.bo.NewEnergyLoginInputBO;
import org.example.demo.model.bo.NewEnergyRegisterInputBO;
import org.example.demo.model.bo.NewEnergySPU_InsertInputBO;
import org.example.demo.model.bo.NewEnergySPU_Order_InsertInputBO;
import org.example.demo.model.bo.NewEnergySPU_QueryByAddrInputBO;
import org.example.demo.model.bo.NewEnergySPU_QueryByIDInputBO;
import org.example.demo.model.bo.NewEnergySPU_RemoveInputBO;
import org.example.demo.model.bo.NewEnergySPU_SellerInputBO;
import org.example.demo.model.bo.NewEnergySPU_TransferInputBO;
import org.example.demo.model.bo.NewEnergySPU_UpdateInputBO;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.dto.CallResponse;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Data
public class NewEnergyService {
  public static final String ABI = org.example.demo.utils.IOUtil.readResourceAsString("abi/NewEnergy.abi");

  public static final String BINARY = org.example.demo.utils.IOUtil.readResourceAsString("bin/ecc/NewEnergy.bin");

  public static final String SM_BINARY = org.example.demo.utils.IOUtil.readResourceAsString("bin/sm/NewEnergy.bin");

  @Value("${system.contract.newEnergyAddress}")
  private String address;

  @Autowired
  private Client client;

  AssembleTransactionProcessor txProcessor;

  @PostConstruct
  public void init() throws Exception {
    this.txProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(this.client, this.client.getCryptoSuite().getCryptoKeyPair());
  }

  public TransactionResponse SPU_Order_Insert(NewEnergySPU_Order_InsertInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "SPU_Order_Insert", input.toArgs());
  }

  public CallResponse SPU_QueryByID(NewEnergySPU_QueryByIDInputBO input) throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "SPU_QueryByID", input.toArgs());
  }

  public TransactionResponse SPU_Transfer(NewEnergySPU_TransferInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "SPU_Transfer", input.toArgs());
  }

  public TransactionResponse SPU_Seller(NewEnergySPU_SellerInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "SPU_Seller", input.toArgs());
  }

  public CallResponse Energy_Order_get() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "Energy_Order_get", Arrays.asList());
  }

  public TransactionResponse AccountUpdate(NewEnergyAccountUpdateInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "AccountUpdate", input.toArgs());
  }

  public TransactionResponse SPU_Insert(NewEnergySPU_InsertInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "SPU_Insert", input.toArgs());
  }

  public TransactionResponse Register(NewEnergyRegisterInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "Register", input.toArgs());
  }

  public CallResponse SPU_QueryByAddr(NewEnergySPU_QueryByAddrInputBO input) throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "SPU_QueryByAddr", input.toArgs());
  }

  public TransactionResponse Energy_Order_insert(NewEnergyEnergy_Order_insertInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "Energy_Order_insert", input.toArgs());
  }

  public CallResponse SPU_Order_Query() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "SPU_Order_Query", Arrays.asList());
  }

  public TransactionResponse Login(NewEnergyLoginInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "Login", input.toArgs());
  }

  public TransactionResponse Energy_Remove(NewEnergyEnergy_RemoveInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "Energy_Remove", input.toArgs());
  }

  public TransactionResponse SPU_Update(NewEnergySPU_UpdateInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "SPU_Update", input.toArgs());
  }

  public TransactionResponse Energy_Init(NewEnergyEnergy_InitInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "Energy_Init", input.toArgs());
  }

  public CallResponse SPU_Query() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "SPU_Query", Arrays.asList());
  }

  public CallResponse energyInterface() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "energyInterface", Arrays.asList());
  }

  public TransactionResponse AccountRemove(NewEnergyAccountRemoveInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "AccountRemove", input.toArgs());
  }

  public CallResponse Energy_Select() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "Energy_Select", Arrays.asList());
  }

  public TransactionResponse Energy_Update(NewEnergyEnergy_UpdateInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "Energy_Update", input.toArgs());
  }

  public CallResponse equipInterface() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "equipInterface", Arrays.asList());
  }

  public CallResponse roleInterface() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "roleInterface", Arrays.asList());
  }

  public CallResponse QueryAllAccount() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "QueryAllAccount", Arrays.asList());
  }

  public CallResponse energyInterfaceTwo() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "energyInterfaceTwo", Arrays.asList());
  }

  public TransactionResponse SPU_Remove(NewEnergySPU_RemoveInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "SPU_Remove", input.toArgs());
  }

  public CallResponse equipInterfaceTwo() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "equipInterfaceTwo", Arrays.asList());
  }
}
