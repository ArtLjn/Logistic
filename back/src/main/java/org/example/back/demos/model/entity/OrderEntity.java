package org.example.back.demos.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ljn
 * @Description: 订单模块
 * @date 2024/4/2/17:21
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("order_map")
public class OrderEntity {
    @TableField("company_address")
    private String companyAddress;
    @TableField("order_id")
    private int orderId;
}
