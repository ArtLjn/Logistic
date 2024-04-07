package org.example.back.demos.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.back.demos.dao.OrderMapper;
import org.example.back.demos.model.Role;
import org.example.back.demos.model.entity.OrderEntity;
import org.example.back.demos.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author ljn
 * @Description: 订单服务
 * @date 2024/4/7/10:11
 */

@Service
public class OrderService {
    private final UserService userService;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderService(UserService userService, OrderMapper orderMapper) {
        this.userService = userService;
        this.orderMapper = orderMapper;
    }

    public boolean setSaveData(UserEntity userEntity, String orderId) {
        // 创建一个 OrderEntity 对象
        OrderEntity pco = new OrderEntity();
        // 将订单 ID 和公司地址设置为 OrderEntity 对象
        pco.setOrderId(Integer.parseInt(orderId));
        pco.setCompanyAddress(userEntity.getCompanyAddress());
        // 使用 OrderMapper 对象将 OrderEntity 对象插入数据库
        return orderMapper.insert(pco) > 0;
    }

    public List<OrderEntity>  getOrderList(UserEntity userEntity, Role role) {
       if (Role.getRole(userEntity.getRole()) != role) {
            return null;
        }
        // 创建一个 QueryWrapper 对象，用于查询指定公司地址的采购订单
        QueryWrapper<OrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_address", userEntity.getCompanyAddress());
        // 查询指定公司地址的采购订单，并将结果添加到列表中
        return orderMapper.selectList(queryWrapper);
    }
}
