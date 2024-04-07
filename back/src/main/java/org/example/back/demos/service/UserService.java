package org.example.back.demos.service;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.back.demos.model.Role;
import org.example.back.demos.model.bo.LoginBo;
import org.example.back.demos.model.bo.LogisticsControllerGetPerChaseCompanyInputBO;
import org.example.back.demos.model.bo.LogisticsControllerGetTransCompanyInputBO;
import org.example.back.demos.model.bo.RegisterBo;
import org.example.back.demos.dao.UserMapper;
import org.example.back.demos.model.entity.UserEntity;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.model.CryptoType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author ljn
 * @Description: 用户服务类实现
 * @date 2024/4/3/09:38
 */

@Service
public class UserService {
    private final UserMapper userMapper;
    private final LogisticsControllerService logisticsControllerService;

    @Autowired
    public UserService(UserMapper userMapper, LogisticsControllerService logisticsControllerService) {
        this.userMapper = userMapper;
        this.logisticsControllerService = logisticsControllerService;
    }

    public UserEntity Login(LoginBo loginBean) {
        loginBean.setPassword(SecureUtil.md5(loginBean.getPassword()));
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", loginBean.getUsername())
                    .eq("password", loginBean.getPassword());
        return userMapper.selectOne(queryWrapper);
    }
    
    public int CreateUser(UserEntity userEntity) {
        if (hasUser(userEntity.getUsername())) {
            return 0;
        }
        userEntity.setPassword(SecureUtil.md5(userEntity.getPassword()));
        return userMapper.insert(userEntity);
    }

    public UserEntity GetUser(String username) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }

    public boolean hasUser(String username) {
        return GetUser(username) != null;
    }

    public Object getCompanyMsg(String address) throws Exception {
        QueryWrapper<UserEntity>  queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_address", address);
        UserEntity userEntity = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(userEntity)) {
            return null;
        }
        Map<String,Object> object = new HashMap<>();
        object.put("role",userEntity.getRole());
        switch (userEntity.getRole()) {
            case Role.permission.PER_CHASE:
                LogisticsControllerGetPerChaseCompanyInputBO logisticsControllerGetPerChaseCompanyInputBO = new LogisticsControllerGetPerChaseCompanyInputBO();
                logisticsControllerGetPerChaseCompanyInputBO.setCompany_addr(userEntity.getCompanyAddress());
                object.put("chain",JSON.parseArray(logisticsControllerService.GetPerChaseCompany(logisticsControllerGetPerChaseCompanyInputBO).getReturnObject().get(0).toString()).get(0));
                break;
            case Role.permission.TRANS:
                LogisticsControllerGetTransCompanyInputBO logisticsControllerGetTransCompanyInputBO = new LogisticsControllerGetTransCompanyInputBO();
                logisticsControllerGetTransCompanyInputBO.setCompany_addr(userEntity.getCompanyAddress());
                object.put("chain",JSON.parseArray(logisticsControllerService.GetTransCompany(logisticsControllerGetTransCompanyInputBO).getReturnObject().get(0).toString()).get(0));
                break;
        }
        return object;
    }
}
