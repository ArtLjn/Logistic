package org.example.back.demos.model.bo;

import lombok.Data;

/**
 * @author ljn
 * @Description: 创建采购订单BO
 * @date 2024/4/5/14:30
 */

@Data
public class CreatePerChaseOrderBo {
    private String materials;
    private String perChaseTime;
    private String perChasementCycle;

    public String toString() {
        return materials + "," +
                perChaseTime + "," +
                perChasementCycle;
    }
}
