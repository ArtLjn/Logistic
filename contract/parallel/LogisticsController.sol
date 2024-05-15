// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.4.25;

import "./CompanyStorage.sol";
import "./OrderStorage.sol";
import "./TableTools.sol";
import "./Ownable.sol";
import "./ParallelContract.sol";

contract LogisticsController is TableTools,Ownable,ParallelContract {
    CompanyStorage companyStorage;
    OrderStorage orderStorage;
    string[] funcTable;
    event parallelEvent(int);
    constructor(address company,address order){
        companyStorage = CompanyStorage(company);
        orderStorage = OrderStorage(order);
        // 为合约部署者创建运输公司和采购公司写入权限
        string[] memory tableList = new string[](2);
        tableList[0] = TransCompany;
        tableList[1] = PerChaseCompany;
        setTablePermission(TypeConvertUtil.addressToString(tx.origin),tableList);
        funcTable.push("CreatePerChaseCompany(string)");
        funcTable.push("CreateTransCompany(string)");
        funcTable.push("CreatePerChaseOrder(string)");
        funcTable.push("CreateTransOrder(string)");
    }

    // 创建采购公司
    function CreatePerChaseCompany(string _fields) public {
        companyStorage.createPerChaseCompany(_fields);
    }

    // 查询采购公司
    function GetPerChaseCompany(string company_addr) public view returns(string) {
        return companyStorage.getPerChaseCompany(company_addr);
    }

    //创建运输公司
    function CreateTransCompany(string _fields) public {
        companyStorage.createTransCompany(_fields);
    }

    //获取运输公司
    function GetTransCompany(string company_addr) public view returns(string) {
        return companyStorage.getTransCompany(company_addr);
    }

    // 创建采购订单
    // 要求：只能由采购公司来创建
    // fields:  materials, perchaseTime,  perchasementCycle
    function CreatePerChaseOrder(string fields) public returns(string) {
        return orderStorage.createPerChaseOrder(fields);
    }

    // 查询采购订单信息
    function GetPerChaseOrder(string index) public view returns(string) {
        return orderStorage.getPerChaseOrder(index);
    }

    // 创建运输订单
    // 注意：只有运输公司可以创建运输订单
    function CreateTransOrder(string fields) public returns(string) {
        return orderStorage.createTransOrder(fields);
    }

    //通过索引值获取运输订单信息
    function GetTransOrder(string index) public view returns(string) {
        return orderStorage.getTransOrder(index);
    }

    // 注册并行方法
    function enableParallel() {
        for (uint k = 0; k < funcTable.length; k ++ ) {
           int code = registerParallelFunction(funcTable[k],1);
            emit parallelEvent(code);
        }
    }

    //注销并行合约方法
    function disableParallel() {
        for (uint v = 0; v < funcTable.length; v ++ ) {
            int code = unregisterParallelFunction(funcTable[v]);
            emit parallelEvent(code);
        }
    }
}
