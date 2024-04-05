package org.example.back.demos.model.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author ljn
 * @Description: 创建采购订单BO
 * @date 2024/4/5/14:30
 */

@Data
public class CreatePerChaseOrderBo {
    @JsonProperty("materials")
    private String materials;
    @JsonProperty("perChaseTime")
    private String perChaseTime;
    @JsonProperty("perChasementCycle")
    private String perChasementCycle;

    public String toString() {
        return materials + "," +
                perChaseTime + "," +
                perChasementCycle;
    }
}
