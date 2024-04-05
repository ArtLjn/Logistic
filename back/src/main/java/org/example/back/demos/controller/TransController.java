package org.example.back.demos.controller;

import org.example.back.demos.controller.apo.BuildClientOptsForPrivateKey;
import org.example.back.demos.model.bo.CreateTransOrderBo;
import org.example.back.demos.service.TransService;
import org.example.back.demos.util.AjaxResult;
import org.example.back.demos.util.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ljn
 * @Description: 物流公司订单接口
 * @date 2024/4/5/17:11
 */

@RestController
@RequestMapping("/api/trans")
public class TransController {

    private final TransService transService;

    @Autowired
    public TransController(TransService transService) {
        this.transService = transService;
    }

    @BuildClientOptsForPrivateKey
    @PostMapping("/createTrans")
    public AjaxResult<String> createTrans(@RequestBody CreateTransOrderBo createTransOrderBo) {
        try {
            ReflectionUtils.checkNonNullFields(createTransOrderBo, CreateTransOrderBo.class);
            if (!transService.CreateTransOrder(createTransOrderBo)) {
                return new AjaxResult<>(400,"创建失败");
            }
        } catch (Exception e) {
            return new AjaxResult<>(400,e.getMessage());
        }
        return new AjaxResult<>(200,"success");
    }
}
