package org.example.back.demos.service;

import org.example.back.demos.model.bo.CreateTransOrderBo;
import org.example.back.demos.model.bo.LogisticsControllerCreateTransCompanyInputBO;
import org.example.back.demos.model.bo.LogisticsControllerCreateTransOrderInputBO;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author ljn
 * @Description: 物流公司订单服务
 * @date 2024/4/5/17:01
 */

@Service
public class TransService {
    private final LogisticsControllerService logisticsControllerService;

    @Autowired
    public TransService(LogisticsControllerService logisticsControllerService) {
        this.logisticsControllerService = logisticsControllerService;
    }

    public boolean CreateTransOrder(CreateTransOrderBo createTransOrderBo) throws Exception {
        LogisticsControllerCreateTransOrderInputBO logisticsControllerCreateTransOrderInputBO = new LogisticsControllerCreateTransOrderInputBO();
        logisticsControllerCreateTransOrderInputBO.setFields(createTransOrderBo.toString());
        TransactionResponse  transactionResponse = logisticsControllerService.CreateTransOrder(logisticsControllerCreateTransOrderInputBO);
        return Objects.equals(transactionResponse.getReturnMessage(), "Success");
    }
}
