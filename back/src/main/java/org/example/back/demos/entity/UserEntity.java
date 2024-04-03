package org.example.back.demos.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ljn
 * @Description: 用户表
 * @date 2024/4/2/16:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class UserEntity {
    @JsonProperty("id")
    private int id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("company_address")
    private String companyAddress;

    @JsonProperty("role")
    private int role;

    @JsonProperty("location")
    private String location;

    @JsonProperty("business_scope")
    private String businessScope;

}
