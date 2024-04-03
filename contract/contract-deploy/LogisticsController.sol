// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.4.25;

import "./CompanyStorage.sol";
import "./OrderStorage.sol";
contract LogisticsController {
    CompanyStorage companyStorage;
    OrderStorage orderStorage;
    constructor(address company,address order){
        companyStorage = CompanyStorage(company);
        orderStorage = OrderStorage(order);
    }

    // 创建采购公司
    function CreatePerChaseCompany(string _fields) public {
        return companyStorage.createPerChaseCompany(_fields);
    }

    // 查询采购公司
    function GetPerChaseCompany(string company_addr) public view returns(string) {
        return companyStorage.getPerChaseCompany(company_addr);
    }

    //创建运输公司
    function CreateTransCompany(string _fields) public {
        return companyStorage.createTransCompany(_fields);
    }

    //获取运输公司
    function GetTransCompany(string company_addr) public view returns(string) {
        return companyStorage.getTransCompany(company_addr);
    }

    // 创建采购订单
    // 要求：只能由采购公司来创建
    // fields:  materials, perchaseTime,  perchasementCycle
    function CreatePerChaseOrder(string fields) public {
        return orderStorage.createPerChaseOrder(fields);
    }

    // 查询采购订单信息
    function GetPerChaseOrder(string index) public view returns(string) {
        return orderStorage.getPerChaseOrder(index);
    }

    // 创建运输订单
    // 注意：只有运输公司可以创建运输订单
    function CreateTransOrder(string fields) public {
        return orderStorage.createTransOrder(fields);
    }

    //通过索引值获取运输订单信息
    function GetTransOrder(string index) public view returns(string) {
        return orderStorage.getTransOrder(index);
    }
}
