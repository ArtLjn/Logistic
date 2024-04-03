package org.example.back.demos.controller;

import org.example.back.demos.bean.LoginBean;
import org.example.back.demos.service.UserService;
import org.example.back.demos.util.AjaxResult;
import org.example.back.demos.util.ReflectionUtils;
import org.example.back.demos.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ljn
 * @Description: 用户相关接口
 * @date 2024/4/2/17:14
 */

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public AjaxResult<String> login(@RequestBody LoginBean loginBean) {
        try {
            ReflectionUtils.checkFieldsNoEmpty(loginBean,LoginBean.class);
        } catch (IllegalAccessException e) {
            return new AjaxResult<>(400,e.getMessage());
        }
        boolean isLogin = userService.Login(loginBean);
        if(isLogin){
            String token = TokenUtil.sign(loginBean.getUsername(),String.valueOf(System.currentTimeMillis()));
            AjaxResult<String> result = new AjaxResult<>(200,"登录成功");
            result.setData(token);
            return result;
        }
        return new AjaxResult<>(400,"登录失败");
    }

    @PostMapping("/register")
    public String register(){
        return "register";
    }
}
