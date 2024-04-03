// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.4.25;

import "../util/TableTools.sol";
import "./MapStorage.sol";
import "./CompanyStorage.sol";
contract OrderStorage is TableTools {
    MapStorage mapStorage;
    CompanyStorage companyStorage;
    constructor(address map,address company){
        initTableStruct(tb_trans_order,TransOrder,Trans_PrimaryKey,TransField);
        initTableStruct(tb_perchase_order,PerChaseOrder,PerChase_PrimaryKey,PerChaseOrderField);
        mapStorage = MapStorage(map);
        companyStorage = CompanyStorage(company);
        // 初始化订单索引
        mapStorage.put(TransOrder,"0");
        mapStorage.put(PerChaseOrder,"0");
    }

    // 获取当前订单索引值
    function getOrderIndex(string _key) private returns(string) {
        string str_index = mapStorage.get(_key);
        uint256 currentIndex = 0;
        if (!str_index.empty()) {
            currentIndex = TypeConvertUtil.stringToUint256(str_index);
            currentIndex += 1;
        }
        return TypeConvertUtil.uint256ToString(currentIndex);
    }

    // 创建采购订单
    // 要求：只能由采购公司来创建
    // fields:  materials, perchaseTime,  perchasementCycle
    function createPerChaseOrder(string fields) public {
        string addrStr = TypeConvertUtil.addressToString(tx.origin);
        // 判断提交采购订单的用户是不是存在并且是已经注册的采购公司成员
        require(companyStorage.hasExistPerChaseCompany(addrStr),"采购公司不存在");
        string[] memory fieldList = getFieldsArray(fields);
        require(fieldList.length == 3,LIST_LENGTH_ERROR);
        string memory index = getOrderIndex(PerChaseOrder);
        string memory new_fields = string(abi.encodePacked(fields,",",addrStr,",,,"));
        int8 code = insertOneRecord(tb_perchase_order,index,new_fields,false);
        require(code == SUCCESS_RETURN,INSERT_ERROR);
        emit INSERT_EVENT(code,new_fields);
        // 将当前索引值push到map表中
        mapStorage.put(PerChaseOrder,index);
    }

    // 查询采购订单信息
    function getPerChaseOrder(string index) public view returns(string) {
        return selectOneRecordToJson(tb_perchase_order,index);
    }

    // 查询采购订单信息(列表)
    function getPerChaseOrderByArray(string index) public view returns(string[]) {
        return selectOneRecordToArray(tb_perchase_order,index,["index",index]);
    }

    // 判断采购订单是否存在
    function hasPerChaseOrder(string index) public returns(bool) {
        return exist(index,PerChaseOrder) == SUCCESS_RETURN;
    }

    // 更新采购订单
    function updatePerChaseOrder(string primaryKey,string[] _fields, string[2] new_val) public {
        _fields[4] = new_val[0];
        _fields[5] = new_val[1];
        string memory new_fields = LibStringUtil.strConcatWithComma(_fields);
        int8 code = updateOneRecordByCond(tb_company,primaryKey,new_fields,["index",primaryKey]);
        require(code == SUCCESS_RETURN,"更新运输公司信息失败");
        emit UPDATE_EVENT(code,new_fields);
    }

    // 创建运输订单
    // 注意：只有运输公司可以创建运输订单
    function createTransOrder(string fields) public {
        string addrStr = TypeConvertUtil.addressToString(tx.origin);
        // 判断提交运输订单的用户是不是存在并且是已经注册的运输公司成员
        require(companyStorage.hasExistTransCompany(addrStr),"运输公司不存在");
        string[] memory fieldList = getFieldsArray(fields);
        require(fieldList.length == 8,LIST_LENGTH_ERROR);
        // 判断采购订单是否存在
        require(hasPerChaseOrder(fieldList[0]),"采购订单不存在");
        string memory index = getOrderIndex(TransOrder);
        int8 code = insertOneRecord(tb_trans_order,index,fields);
        require(code == SUCCESS_RETURN,INSERT_ERROR);
        emit INSERT_EVENT(code,fields);
        // 将当前订单索引push到map表中
        mapStorage.put(TransOrder,index);
        // 更新采购公司信息
        string[] memory perChaseMsg = companyStorage.getPerChaseCompanyByArray(fieldList[1]);
        //绑定采购公司与运输订单信息
        string memory transList = concatTrans(perChaseMsg[2],index);
        companyStorage.updatePerChaseCompany(fieldList[1],perChaseMsg,2,transList);
        //绑定运输公司与运输订单信息
        string[] memory transCompanyMsg = companyStorage.getTransCompanyToArray(addrStr);
        //绑定运输公司与运输订单信息
        string memory transListForTrans = concatTrans(transCompanyMsg[3],index);
        companyStorage.updateTransCompany(addrStr,transCompanyMsg,3,transListForTrans);
        //绑定采购订单与运输订单的信息
        string[] memory perChaseOrder = getPerChaseOrderByArray(fieldList[1]);
        //添加运输公司信息
        string memory transCompanyList = concatTrans(perChaseOrder[4],fieldList[2]);
        //绑定采购订单与运输公司的信息
        string memory tranOrderList = concatTrans(perChaseOrder[5],index);
        updatePerChaseOrder(fieldList[1],perChaseOrder,[transCompanyList,tranOrderList]);
    }

    function concatTrans(string memory transList,string memory index) private returns(string) {
        if (transList.empty()) {
            transList = index;
        } else {
            transList = string(abi.encodePacked(transList,"-",index));
        }
        return transList;
    }

    //通过索引值获取运输订单信息
    function getTransOrder(string index) public view returns(string) {
        return selectOneRecordToJson(tb_trans_order,index);
    }
}
