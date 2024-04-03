package org.example.back.demos.controller;

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
@RequestMapping("/api/perchase")
public class PerChaseController {
    @PostMapping("/createPerchaseOrder")
    public String createPerchaseOrder(){
        return "success";
    }

    @GetMapping("/queryPerchaseOrder")
    public String queryPerchaseOrder(){
        return "success";
    }

    @GetMapping("queryPerchaseOrderById")
    public String queryPerchaseOrderById(){
        return "success";
    }
}
