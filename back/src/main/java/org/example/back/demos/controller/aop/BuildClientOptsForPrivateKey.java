package org.example.back.demos.controller.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ljn
 * @Description: 重构Client 发送者交易人
 * @date 2024/4/5/14:43
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BuildClientOptsForPrivateKey {
}
