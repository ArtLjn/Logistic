package org.example.back.demos.service;

import com.alibaba.fastjson.JSON;
import org.example.back.demos.model.bo.CreatePerChaseOrderBo;
import org.example.back.demos.model.bo.LogisticsControllerCreatePerChaseOrderInputBO;
import org.example.back.demos.model.bo.LogisticsControllerGetPerChaseOrderInputBO;
import org.example.back.demos.model.entity.OrderEntity;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author ljn
 * @Description: 采购者服务类
 * @date 2024/4/5/10:20
 */

@Service
public class PerChaseService {
    private final LogisticsControllerService logisticsControllerService;
    private final OrderService orderService;
    @Autowired
    public PerChaseService(LogisticsControllerService logisticsControllerService, OrderService orderService) {
        this.logisticsControllerService = logisticsControllerService;
        this.orderService = orderService;
    }

    public TransactionResponse CreatePerChase(CreatePerChaseOrderBo createPerChaseOrderBo) throws Exception {
        LogisticsControllerCreatePerChaseOrderInputBO logisticsControllerCreatePerChaseOrderInputBO = new LogisticsControllerCreatePerChaseOrderInputBO();
        logisticsControllerCreatePerChaseOrderInputBO.setFields(createPerChaseOrderBo.toString());
        return logisticsControllerService.CreatePerChaseOrder(logisticsControllerCreatePerChaseOrderInputBO);
    }


    public Object getPerChaseData(String orderId) throws Exception {
        LogisticsControllerGetPerChaseOrderInputBO logisticsControllerGetPerChaseOrderInputBO = new LogisticsControllerGetPerChaseOrderInputBO();
        logisticsControllerGetPerChaseOrderInputBO.setIndex(orderId);
        return JSON.parseArray(logisticsControllerService.GetPerChaseOrder(logisticsControllerGetPerChaseOrderInputBO).getReturnObject().get(0).toString()).get(0);
    }

    // 获取所有采购订单数据
    public List<Object> getOwnerAllPerChaseOrderData(String username) throws Exception {
        List<OrderEntity> perChaseOrderEntities = orderService.getOrderList(username);
        if (perChaseOrderEntities.isEmpty()) return null;
        // 创建一个列表用于存储结果
        List<Object> list = new ArrayList<>();
        for (OrderEntity orderEntity : perChaseOrderEntities) {
            // 调用 getPerChaseData() 方法获取每个订单的数据
            Object data = getPerChaseData(String.valueOf(orderEntity.getOrderId()));
            // 如果数据不为空，则将其添加到列表中
            if (Objects.nonNull(data)) {
                list.add(data);
            }
        }
        // 返回列表中的数据
        return list;
    }

}
