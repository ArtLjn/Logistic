package org.example.back.demos.controller;

import org.example.back.demos.model.Role;
import org.example.back.demos.model.bo.LoginBo;
import org.example.back.demos.model.bo.LogisticsControllerCreatePerChaseCompanyInputBO;
import org.example.back.demos.model.bo.LogisticsControllerCreateTransCompanyInputBO;
import org.example.back.demos.model.bo.RegisterBo;
import org.example.back.demos.model.entity.UserEntity;
import org.example.back.demos.service.LogisticsControllerService;
import org.example.back.demos.service.UserService;
import org.example.back.demos.util.AjaxResult;
import org.example.back.demos.util.ReflectionUtils;
import org.example.back.demos.util.TokenUtil;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.model.CryptoType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author ljn
 * @Description: 用户相关接口
 * @date 2024/4/2/17:14
 */

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final LogisticsControllerService logisticsControllerService;
    @Autowired
    public UserController(UserService userService, LogisticsControllerService logisticsControllerService) {
        this.userService = userService;
        this.logisticsControllerService = logisticsControllerService;
    }

    @PostMapping("/login")
    public AjaxResult<String> login(@RequestBody LoginBo loginBean) {
        try {
            ReflectionUtils.checkNonNullFields(loginBean, LoginBo.class);
        } catch (IllegalAccessException e) {
            return new AjaxResult<>(400,e.getMessage());
        }
        UserEntity userEntity = userService.Login(loginBean);
        if(userEntity != null){
            String token = TokenUtil.sign(loginBean.getUsername(),String.valueOf(System.currentTimeMillis()));
            return getStringAjaxResult(token, userEntity);
        }
        return new AjaxResult<>(400,"登录失败");
    }

    private static AjaxResult<String> getStringAjaxResult(String token, UserEntity userEntity) {
        AjaxResult<String> result = new AjaxResult<>(200,"登录成功");
        Map<String,Object> map = new HashMap<>();
        map.put("token", token);
        map.put("role",userEntity.getRole());
        map.put("username", userEntity.getUsername());
        map.put("company_address", userEntity.getCompanyAddress());
        map.put("company_name", userEntity.getCompanyName());
        map.put("location", userEntity.getLocation());
        map.put("business_scope", userEntity.getBusinessScope());
        result.setData(map);
        return result;
    }

    @PostMapping("/register")
    public AjaxResult<String> register(@RequestBody RegisterBo registerBo){
        try {
            ReflectionUtils.checkFieldsNoEmpty(registerBo,RegisterBo.class);
            UserEntity userEntity = new UserEntity();
            BeanUtils.copyProperties(registerBo,userEntity);
            CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
            userEntity.setCompanyAddress(cryptoSuite.getCryptoKeyPair().getAddress());
            userEntity.setPrivateKey(cryptoSuite.getCryptoKeyPair().getHexPrivateKey());
            String _fields;
            String returnMessage;
            switch (registerBo.getRole()) {
                case Role.permission.TRANS:
                    _fields = String.format("%s,%s,%s,%s",userEntity.getCompanyAddress(),userEntity.getCompanyName(),
                            userEntity.getLocation(),userEntity.getBusinessScope());
                    LogisticsControllerCreateTransCompanyInputBO logisticsControllerCreateTransCompanyInputBO = new LogisticsControllerCreateTransCompanyInputBO();
                    logisticsControllerCreateTransCompanyInputBO.set_fields(_fields);
                    returnMessage = logisticsControllerService.CreateTransCompany(logisticsControllerCreateTransCompanyInputBO).getReturnMessage();
                    break;
                case Role.permission.PER_CHASE:
                default:
                    _fields = String.format("%s,%s",userEntity.getCompanyAddress(),userEntity.getCompanyName());
                    LogisticsControllerCreatePerChaseCompanyInputBO logisticsControllerCreatePerChaseCompanyInputBO = new LogisticsControllerCreatePerChaseCompanyInputBO();
                    logisticsControllerCreatePerChaseCompanyInputBO.set_fields(_fields);
                    returnMessage = logisticsControllerService.CreatePerChaseCompany(logisticsControllerCreatePerChaseCompanyInputBO).getReturnMessage();
                    break;
            }

            if(!Objects.equals(returnMessage,"Success")){
                return new AjaxResult<>(400,"注册失败");
            }
            int result = userService.CreateUser(userEntity);
            if(result == 0){
                return new AjaxResult<>(400,"用户名已经存在");
            }
        } catch (Exception e) {
            return new AjaxResult<>(400,e.getMessage());
        }
        return new AjaxResult<>(200,"注册成功");
    }

//    @BuildClientOptsForPrivateKey
    @GetMapping("/queryCompanyMsg")
    public AjaxResult<Object> queryPerChaseCompany(@RequestParam("company_address") String companyAddress){
        Object list;
        try {
            list = userService.getCompanyMsg(companyAddress);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        AjaxResult<Object> ajaxResult = new AjaxResult<>(200,"success");
        ajaxResult.setData(list);
        return ajaxResult;
    }
}
