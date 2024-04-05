package org.example.back.demos.model.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author ljn
 * @Description: 创建物流订单Bo
 * @date 2024/4/5/17:03
 */

@Data
public class CreateTransOrderBo {
    @JsonProperty(value = "perChaseIndex") //采购订单ID
    private String perChaseIndex;

    @JsonProperty(value = "perChaseCompany") //采购公司
    private String perChaseCompany;

    @JsonProperty(value = "transCompany") //物流公司
    private String transCompany;

    @JsonProperty(value = "clearanceLocation") //出关地点
    private String clearanceLocation;

    @JsonProperty(value = "entryLocation") //入关地点
    private String entryLocation;

    @JsonProperty(value = "clearanceTime") //出关时间
    private String clearanceTime;

    @JsonProperty(value = "entryTime") //入关时间
    private String entryTime;

    @JsonProperty(value = "situation") //物流情况
    private String situation;

    public String toString() {
        return perChaseIndex + "," +
                perChaseCompany + "," +
                transCompany + "," +
                clearanceLocation + "," +
                entryLocation + "," +
                clearanceTime + "," +
                entryTime + "," +
                situation;
    }
}
