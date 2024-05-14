package com;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Event;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
import org.fisco.bcos.sdk.abi.datatypes.generated.Int8;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.eventsub.EventCallback;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class TableTools extends Contract {
    public static final String[] BINARY_ARRAY = {"6080604052348015600f57600080fd5b50604380601d6000396000f3006080604052600080fd00a265627a7a723058202d2dcaabf2ae271e104210a7bdb9269b5d8c0ff40cf255e6bac5f91934e951486c6578706572696d656e74616cf50037"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"6080604052348015600f57600080fd5b50604380601d6000396000f3006080604052600080fd00a265627a7a72305820b482838147dad7e078c01624041c8e69059e47383042da73c31c9e990813b7fb6c6578706572696d656e74616cf50037"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"\",\"type\":\"int8\"},{\"indexed\":false,\"name\":\"\",\"type\":\"string\"}],\"name\":\"INSERT_EVENT\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"\",\"type\":\"int8\"},{\"indexed\":false,\"name\":\"\",\"type\":\"string\"}],\"name\":\"UPDATE_EVENT\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"\",\"type\":\"int8\"}],\"name\":\"QUERY_EVENT\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"\",\"type\":\"int8\"}],\"name\":\"REMOVE_EVENT\",\"type\":\"event\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final Event INSERT_EVENT_EVENT = new Event("INSERT_EVENT", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Int8>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event UPDATE_EVENT_EVENT = new Event("UPDATE_EVENT", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Int8>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event QUERY_EVENT_EVENT = new Event("QUERY_EVENT", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Int8>() {}));
    ;

    public static final Event REMOVE_EVENT_EVENT = new Event("REMOVE_EVENT", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Int8>() {}));
    ;

    protected TableTools(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public List<INSERT_EVENTEventResponse> getINSERT_EVENTEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(INSERT_EVENT_EVENT, transactionReceipt);
        ArrayList<INSERT_EVENTEventResponse> responses = new ArrayList<INSERT_EVENTEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            INSERT_EVENTEventResponse typedResponse = new INSERT_EVENTEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.INSERT_EVENTParam0 = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.INSERT_EVENTParam1 = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeINSERT_EVENTEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(INSERT_EVENT_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeINSERT_EVENTEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(INSERT_EVENT_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public List<UPDATE_EVENTEventResponse> getUPDATE_EVENTEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(UPDATE_EVENT_EVENT, transactionReceipt);
        ArrayList<UPDATE_EVENTEventResponse> responses = new ArrayList<UPDATE_EVENTEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UPDATE_EVENTEventResponse typedResponse = new UPDATE_EVENTEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.UPDATE_EVENTParam0 = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.UPDATE_EVENTParam1 = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeUPDATE_EVENTEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(UPDATE_EVENT_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeUPDATE_EVENTEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(UPDATE_EVENT_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public List<QUERY_EVENTEventResponse> getQUERY_EVENTEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(QUERY_EVENT_EVENT, transactionReceipt);
        ArrayList<QUERY_EVENTEventResponse> responses = new ArrayList<QUERY_EVENTEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            QUERY_EVENTEventResponse typedResponse = new QUERY_EVENTEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.QUERY_EVENTParam0 = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeQUERY_EVENTEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(QUERY_EVENT_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeQUERY_EVENTEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(QUERY_EVENT_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public List<REMOVE_EVENTEventResponse> getREMOVE_EVENTEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REMOVE_EVENT_EVENT, transactionReceipt);
        ArrayList<REMOVE_EVENTEventResponse> responses = new ArrayList<REMOVE_EVENTEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            REMOVE_EVENTEventResponse typedResponse = new REMOVE_EVENTEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.REMOVE_EVENTParam0 = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeREMOVE_EVENTEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(REMOVE_EVENT_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeREMOVE_EVENTEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(REMOVE_EVENT_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public static TableTools load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new TableTools(contractAddress, client, credential);
    }

    public static TableTools deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(TableTools.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }

    public static class INSERT_EVENTEventResponse {
        public TransactionReceipt.Logs log;

        public BigInteger INSERT_EVENTParam0;

        public String INSERT_EVENTParam1;
    }

    public static class UPDATE_EVENTEventResponse {
        public TransactionReceipt.Logs log;

        public BigInteger UPDATE_EVENTParam0;

        public String UPDATE_EVENTParam1;
    }

    public static class QUERY_EVENTEventResponse {
        public TransactionReceipt.Logs log;

        public BigInteger QUERY_EVENTParam0;
    }

    public static class REMOVE_EVENTEventResponse {
        public TransactionReceipt.Logs log;

        public BigInteger REMOVE_EVENTParam0;
    }
}
