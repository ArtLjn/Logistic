package org.example.back.demos.controller;

import org.example.back.demos.controller.aop.BuildClientOptsForPrivateKey;
import org.example.back.demos.model.bo.CreateTransOrderBo;
import org.example.back.demos.service.OrderService;
import org.example.back.demos.service.TransService;
import org.example.back.demos.util.AjaxResult;
import org.example.back.demos.util.ReflectionUtils;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static org.example.back.demos.controller.aop.BuildClientOptsForPrivateKeyImpl.currentUser;

/**
 * @author ljn
 * @Description: 物流公司订单接口
 * @date 2024/4/5/17:11
 */

@RestController
@RequestMapping("/api/trans")
public class TransController {

    private final TransService transService;
    private final OrderService orderService;
    @Autowired
    public TransController(TransService transService, OrderService orderService) {
        this.transService = transService;
        this.orderService = orderService;
    }

    @BuildClientOptsForPrivateKey
    @PostMapping("/createTrans")
    public AjaxResult<String> createTrans(@RequestBody CreateTransOrderBo createTransOrderBo) {
        try {
            ReflectionUtils.checkNonNullFields(createTransOrderBo, CreateTransOrderBo.class);
            TransactionResponse transactionResponse = transService.CreateTransOrder(createTransOrderBo);
            if (!Objects.equals(transactionResponse.getReceiptMessages(),"Success")) {
                return new AjaxResult<>(400,transactionResponse.getReceiptMessages());
            }else if (!orderService.setSaveData(currentUser.get(), transactionResponse.getReturnObject().get(0).toString())) {
                return new AjaxResult<>(400,"持久化失败");
            }
        } catch (Exception e) {
            return new AjaxResult<>(400,e.getMessage());
        }
        return new AjaxResult<>(200,"success");
    }

    @BuildClientOptsForPrivateKey
    @GetMapping("/queryTransOrder")
    public AjaxResult<Object> queryTransOrder(){
        List<Object> list;
        try {
            list = transService.getOwnerAllTransOrderData(currentUser.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        AjaxResult<Object> ajaxResult = new AjaxResult<>(200,"success");
        ajaxResult.setData(list);
        return ajaxResult;
    }
    @BuildClientOptsForPrivateKey
    @GetMapping("/queryPerChaseOrderByTransOrder")
    public AjaxResult<Object> queryPerChaseByTransOrder() {
        List<Object> list;
        try {
            list = transService.getPerChaseHasTransOrderData(currentUser.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        AjaxResult<Object> ajaxResult = new AjaxResult<>(200,"success");
        ajaxResult.setData(list);
        return ajaxResult;
    }
    @GetMapping("queryTransOrderById")
    public AjaxResult<Object> queryTransOrderById(@RequestParam("orderId") String orderId){
        Object list;
        try {
            list = transService.getTransOrder(orderId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        AjaxResult<Object> ajaxResult = new AjaxResult<>(200,"success");
        ajaxResult.setData(list);
        return ajaxResult;
    }
}
