package org.example.back.demos.model.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author ljn
 * @Description: 注册Bean
 * @date 2024/4/4/17:51
 */

@Data
public class RegisterBo {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("role")
    private int role;

    @JsonProperty("location")
    private String location;

    @JsonProperty("business_scope")
    private String businessScope;
}
