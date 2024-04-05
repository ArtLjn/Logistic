package org.example.back.demos.controller;

import org.example.back.demos.controller.apo.BuildClientOptsForPrivateKey;
import org.example.back.demos.model.bo.CreatePerChaseOrderBo;
import org.example.back.demos.service.PerChaseService;
import org.example.back.demos.util.AjaxResult;
import org.example.back.demos.util.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ljn
 * @Description: 物流相关接口
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
    public AjaxResult<String> createPerChaseOrder(CreatePerChaseOrderBo createPerChaseOrderBo){
        try {
            ReflectionUtils.checkNonNullFields(createPerChaseOrderBo,CreatePerChaseOrderBo.class);
            if (!perChaseService.CreatePerChase(createPerChaseOrderBo)) {
                return new AjaxResult<>(400,"创建失败");
            }
        } catch (Exception e) {
            return new AjaxResult<>(400,e.getMessage());
        }
        return new AjaxResult<>(200, "success");
    }

    @GetMapping("/queryPerChaseOrder")
    public String queryPerChaseOrder(){
        return "success";
    }

    @GetMapping("queryPerChaseOrderById")
    public String queryPerChaseOrderById(){
        return "success";
    }

    @GetMapping("/queryPerChaseCompany")
    public String queryPerChaseCompany(){

        return "success";
    }
}
