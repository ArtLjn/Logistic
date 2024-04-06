package org.example.back.demos.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.back.demos.dao.PerChaseOrderMapper;
import org.example.back.demos.model.bo.CreatePerChaseOrderBo;
import org.example.back.demos.model.bo.LogisticsControllerCreatePerChaseOrderInputBO;
import org.example.back.demos.model.bo.LogisticsControllerGetPerChaseOrderInputBO;
import org.example.back.demos.model.entity.PerChaseOrderEntity;
import org.example.back.demos.model.entity.UserEntity;
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
    private final UserService userService;
    private final PerChaseOrderMapper perChaseOrderMapper;
    @Autowired
    public PerChaseService(LogisticsControllerService logisticsControllerService, UserService userService, PerChaseOrderMapper perChaseOrderMapper) {
        this.logisticsControllerService = logisticsControllerService;
        this.userService = userService;
        this.perChaseOrderMapper = perChaseOrderMapper;
    }

    public TransactionResponse CreatePerChase(CreatePerChaseOrderBo createPerChaseOrderBo) throws Exception {
        LogisticsControllerCreatePerChaseOrderInputBO logisticsControllerCreatePerChaseOrderInputBO = new LogisticsControllerCreatePerChaseOrderInputBO();
        logisticsControllerCreatePerChaseOrderInputBO.setFields(createPerChaseOrderBo.toString());
        return logisticsControllerService.CreatePerChaseOrder(logisticsControllerCreatePerChaseOrderInputBO);
    }

    public boolean setSaveData(String username, String orderId) {
        // 根据客户姓名获取用户信息
        UserEntity userEntity = userService.GetUser(username);
        if (Objects.isNull(userEntity)) {
            return false;
        }
        // 创建一个 PerChaseOrderEntity 对象
        PerChaseOrderEntity pco = new PerChaseOrderEntity();
        // 将订单 ID 和公司地址设置为 PerChaseOrderEntity 对象
        pco.setOrderId(Integer.parseInt(orderId));
        pco.setCompanyAddress(userEntity.getCompanyAddress());
        // 使用 PerChaseOrderMapper 对象将 PerChaseOrderEntity 对象插入数据库
        return perChaseOrderMapper.insert(pco) > 0;
    }

    public Object getPerChaseData(String orderId) throws Exception {
        LogisticsControllerGetPerChaseOrderInputBO logisticsControllerGetPerChaseOrderInputBO = new LogisticsControllerGetPerChaseOrderInputBO();
        logisticsControllerGetPerChaseOrderInputBO.setIndex(orderId);
        return JSON.parseArray(logisticsControllerService.GetPerChaseOrder(logisticsControllerGetPerChaseOrderInputBO).getReturnObject().get(0).toString()).get(0);
    }

    // 获取所有采购订单数据
    public List<Object> getOwnerAllPerChaseOrderData(String username) throws Exception {
        // 根据用户名获取用户实体对象
        UserEntity userEntity = userService.GetUser(username);
        if (Objects.isNull(userEntity)) {
            return null;
        }
        // 创建一个列表用于存储结果
        List<Object> list = new ArrayList<>();
        // 创建一个 QueryWrapper 对象，用于查询指定公司地址的采购订单
        QueryWrapper<PerChaseOrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_address", userEntity.getCompanyAddress());
        // 查询指定公司地址的采购订单，并将结果添加到列表中
        List<PerChaseOrderEntity> perChaseOrderEntities = perChaseOrderMapper.selectList(queryWrapper);
        for (PerChaseOrderEntity perChaseOrderEntity : perChaseOrderEntities) {
            // 调用 getPerChaseData() 方法获取每个订单的数据
            Object data = getPerChaseData(String.valueOf(perChaseOrderEntity.getOrderId()));
            // 如果数据不为空，则将其添加到列表中
            if (Objects.nonNull(data)) {
                list.add(data);
            }
        }
        // 返回列表中的数据
        return list;
    }
}
