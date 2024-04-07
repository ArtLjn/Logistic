package org.example.back.demos.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.example.back.demos.model.Role;
import org.example.back.demos.model.bo.*;
import org.example.back.demos.model.entity.OrderEntity;
import org.example.back.demos.model.entity.UserEntity;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author ljn
 * @Description: 物流公司订单服务
 * @date 2024/4/5/17:01
 */

@Service
public class TransService {
    private final LogisticsControllerService logisticsControllerService;
    private final OrderService orderService;

    @Autowired
    public TransService(LogisticsControllerService logisticsControllerService, OrderService orderService, UserService userService) {
        this.logisticsControllerService = logisticsControllerService;
        this.orderService = orderService;
    }

    public TransactionResponse CreateTransOrder(CreateTransOrderBo createTransOrderBo) throws Exception {
        LogisticsControllerCreateTransOrderInputBO logisticsControllerCreateTransOrderInputBO = new LogisticsControllerCreateTransOrderInputBO();
        logisticsControllerCreateTransOrderInputBO.setFields(createTransOrderBo.toString());
        return logisticsControllerService.CreateTransOrder(logisticsControllerCreateTransOrderInputBO);
    }

    public Object getTransOrder(String orderId) throws Exception {
        LogisticsControllerGetTransOrderInputBO logisticsControllerGetTransOrderInputBO = new LogisticsControllerGetTransOrderInputBO();
        logisticsControllerGetTransOrderInputBO.setIndex(orderId);
        return JSON.parseArray(logisticsControllerService.GetTransOrder(logisticsControllerGetTransOrderInputBO).getReturnObject().get(0).toString()).get(0);
    }

    // 获取所有采购订单数据
    public List<Object> getOwnerAllTransOrderData(UserEntity userEntity) throws Exception {
        List<OrderEntity> transOrderEntities = orderService.getOrderList(userEntity, Role.TransCompany);
        if (transOrderEntities.isEmpty()) return null;
        // 创建一个列表用于存储结果
        List<Object> list = new ArrayList<>();
        for (OrderEntity orderEntity : transOrderEntities) {
            // 调用 getPerChaseData() 方法获取每个订单的数据
            Object data = getTransOrder(String.valueOf(orderEntity.getOrderId()));
            // 如果数据不为空，则将其添加到列表中
            if (Objects.nonNull(data)) {
                list.add(data);
            }
        }
        // 返回列表中的数据
        return list;
    }

    public List<Object> getPerChaseHasTransOrderData(UserEntity userEntity) throws Exception {
        LogisticsControllerGetPerChaseCompanyInputBO logisticsControllerGetPerChaseCompanyInputBO = new LogisticsControllerGetPerChaseCompanyInputBO();
        logisticsControllerGetPerChaseCompanyInputBO.setCompany_addr(userEntity.getCompanyAddress());
        Object object = JSON.parseArray(logisticsControllerService.GetPerChaseCompany(logisticsControllerGetPerChaseCompanyInputBO).getReturnObject().get(0).toString()).get(0);
        JSONObject jsonObject = JSON.parseObject(object.toString());
        JSONObject jsonObject1 = JSON.parseObject(jsonObject.get("fields").toString());
        String transStr = jsonObject1.get("transList").toString();
        String[] tranList = transStr.split("-");
        List<Object> list = new ArrayList<>();
        for (String trans : tranList) {
            Object data = getTransOrder(trans);
            if (Objects.nonNull(data)) {
                list.add(data);
            }
        }
        return list;
    }
}
