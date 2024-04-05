package org.example.back.demos.service;

import org.example.back.demos.model.bo.CreatePerChaseOrderBo;
import org.example.back.demos.model.bo.LogisticsControllerCreatePerChaseOrderInputBO;
import org.fisco.bcos.sdk.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author ljn
 * @Description: 采购者服务类
 * @date 2024/4/5/10:20
 */

@Service
public class PerChaseService {
    private final LogisticsControllerService logisticsControllerService;

    @Autowired
    public PerChaseService(LogisticsControllerService logisticsControllerService) {
        this.logisticsControllerService = logisticsControllerService;
    }

    public boolean CreatePerChase(CreatePerChaseOrderBo createPerChaseOrderBo) throws Exception {
        LogisticsControllerCreatePerChaseOrderInputBO logisticsControllerCreatePerChaseOrderInputBO = new LogisticsControllerCreatePerChaseOrderInputBO();
        logisticsControllerCreatePerChaseOrderInputBO.setFields(createPerChaseOrderBo.toString());
        return Objects.equals(logisticsControllerService.CreatePerChaseOrder(logisticsControllerCreatePerChaseOrderInputBO).getReturnMessage(), "Success");
    }
}
