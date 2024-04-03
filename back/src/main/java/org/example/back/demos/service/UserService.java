package org.example.back.demos.service;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.back.demos.bean.LoginBean;
import org.example.back.demos.dao.UserMapper;
import org.example.back.demos.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ljn
 * @Description: 用户服务类实现
 * @date 2024/4/3/09:38
 */

@Service
public class UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public boolean Login(LoginBean loginBean) {
        loginBean.setPassword(SecureUtil.md5(loginBean.getPassword()));
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", loginBean.getUsername())
                    .eq("password", loginBean.getPassword());
        UserEntity userEntity = userMapper.selectOne(queryWrapper);
        return userEntity == null;
    }
}
