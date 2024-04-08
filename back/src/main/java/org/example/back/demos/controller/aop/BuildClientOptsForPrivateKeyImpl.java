package org.example.back.demos.controller.aop;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.example.back.demos.model.entity.UserEntity;
import org.example.back.demos.service.LogisticsControllerService;
import org.example.back.demos.service.UserService;
import org.example.back.demos.util.AjaxResult;
import org.example.back.demos.util.TokenUtil;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author ljn
 * @Description: 实现类
 * @date 2024/4/5/14:44
 */
@Component
//标识切面
@Aspect
@Slf4j
public class BuildClientOptsForPrivateKeyImpl {
    private final UserService userService;
    private final LogisticsControllerService logisticsControllerService;
    @Autowired
    public BuildClientOptsForPrivateKeyImpl(UserService userService, LogisticsControllerService logisticsControllerService) {
        this.userService = userService;
        this.logisticsControllerService = logisticsControllerService;
    }

    @Autowired
    public Client client;
    public static final ThreadLocal<UserEntity> currentUser = new ThreadLocal<>();

    @Pointcut("@annotation(org.example.back.demos.controller.aop.BuildClientOptsForPrivateKey)")
    public void tokenRequiredPointcut() {
    }

    @Before("tokenRequiredPointcut()")
    public AjaxResult<String> beforeTokenRequired(JoinPoint joinPoint)  {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        // 非空判断：@RequestHeader("Authorization") String header
        if (StringUtils.isEmpty(token)) {
             throw new RuntimeException("Authorization header is missing or empty");
        }
        String username = TokenUtil.getLoginName(token);
        if (StringUtils.isEmpty(username)) {
            throw new RuntimeException("Authorization verify failed");
        }
        UserEntity userEntity = userService.GetUser(username);
        if (Objects.isNull(userEntity)) {
           throw new RuntimeException("Authorization verify failed");
        }
        // 存储用户名到ThreadLocal
        currentUser.set(userEntity);
        this.client.getCryptoSuite().setCryptoKeyPair(client.getCryptoSuite().createKeyPair(userEntity.getPrivateKey()));
        try {
            logisticsControllerService.txProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(this.client, this.client.getCryptoSuite().getCryptoKeyPair());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }
    @After("tokenRequiredPointcut()")
    public void afterTokenRequired(JoinPoint joinPoint) {
        // 清除存储在ThreadLocal中的用户名
        currentUser.remove();
    }
}
