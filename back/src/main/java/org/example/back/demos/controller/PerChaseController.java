package org.example.back.demos.controller;

import org.example.back.demos.controller.apo.BuildClientOptsForPrivateKey;
import org.example.back.demos.model.bo.CreatePerChaseOrderBo;
import org.example.back.demos.service.PerChaseService;
import org.example.back.demos.util.AjaxResult;
import org.example.back.demos.util.ReflectionUtils;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static org.example.back.demos.controller.apo.BuildClientOptsForPrivateKeyImpl.currentUsername;

/**
 * @author ljn
 * @Description: 采购相关接口
 * @date 2024/4/2/17:16
 */

@RestController
@RequestMapping("/api/perChase")
public class PerChaseController {
    private final PerChaseService perChaseService;

    @Autowired
    public PerChaseController(PerChaseService perChaseService) {
        this.perChaseService = perChaseService;
    }

    @BuildClientOptsForPrivateKey
    @PostMapping("/createPerChaseOrder")
    public AjaxResult<String> createPerChaseOrder(@RequestBody CreatePerChaseOrderBo createPerChaseOrderBo){
        try {
            ReflectionUtils.checkNonNullFields(createPerChaseOrderBo,CreatePerChaseOrderBo.class);
            TransactionResponse transactionResponse = perChaseService.CreatePerChase(createPerChaseOrderBo);
            if (!Objects.equals(transactionResponse.getReceiptMessages(),"Success")) {
                return new AjaxResult<>(400,transactionResponse.getReturnMessage());
            } else if (!perChaseService.setSaveData(currentUsername.get(), transactionResponse.getReturnObject().get(0).toString())) {
                return new AjaxResult<>(400,"持久化失败");
            }
        } catch (Exception e) {
            return new AjaxResult<>(400,e.getMessage());
        }
        return new AjaxResult<>(200, "success");
    }

    @BuildClientOptsForPrivateKey
    @GetMapping("/queryPerChaseOrder")
    public AjaxResult<Object> queryPerChaseOrder(){
        List<Object> list;
        try {
            list = perChaseService.getOwnerAllPerChaseOrderData(currentUsername.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        AjaxResult<Object> ajaxResult = new AjaxResult<>(200,"success");
        ajaxResult.setData(list);
        return ajaxResult;
    }

    @GetMapping("queryPerChaseOrderById")
    public AjaxResult<Object> queryPerChaseOrderById(@RequestParam("orderId") String orderId){
        Object list;
        try {
            list = perChaseService.getPerChaseData(orderId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        AjaxResult<Object> ajaxResult = new AjaxResult<>(200,"success");
        ajaxResult.setData(list);
        return ajaxResult;
    }

    @GetMapping("/queryPerChaseCompany")
    public String queryPerChaseCompany(){
        return "success";
    }
}
