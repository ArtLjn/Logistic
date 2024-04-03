// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.4.25;

import "../util/TableTools.sol";
contract CompanyStorage is TableTools {
    constructor(){
        initTableStruct(tb_company,TransCompany,Trans_PrimaryKey,TransField);
        initTableStruct(tb_perchase,PerChaseCompany,PerChase_PrimaryKey,PerChaseField);
    }

    // 创建采购公司
    function createPerChaseCompany(string _fields) public {
        string[] memory filedList = getFieldsArray(_fields);
        require(filedList.length == 2,LIST_LENGTH_ERROR);
        // new_fields : "companyName,perchaseList,transList"
        string memory new_fields = string(abi.encodePacked(filedList[1],",,"));
        int code = insertOneRecord(tb_perchase,filedList[0],new_fields,false);
        require(code == 1,INSERT_ERROR);
        emit INSERT_EVENT(code,new_fields);
    }

    // 查询采购公司
    function getPerChaseCompany(string company_addr) public view returns(string) {
        return selectOneRecordToJson(tb_perchase,company_addr);
    }

    // 查询采购公司(列表)
    function getPerChaseCompanyByArray(string company_addr) public view returns(string[]) {
        return selectOneRecordToArray(tb_perchase,company_addr,["companyAddress",company_addr]);
    }

    // 判断采购公司是否存在
    function hasExistPerChaseCompany(string company_addr) public returns(bool) {
        int code = exist(company_addr,PerChaseCompany);
        return code == SUCCESS_RETURN;
    }

    // 更新采购公司信息
    function updatePerChaseCompany(string primaryKey,string[] _fields,uint256 index, string new_val) public {
        string memory new_fields = getChangeFieldsString(_fields,index,new_val);
        int8 code = updateOneRecordByCond(tb_perchase,primaryKey,new_fields,["index",primaryKey]);
        require(code == SUCCESS_RETURN,"更新采购公司信息失败");
        emit UPDATE_EVENT(code,new_fields);
    }

    //创建运输公司
    function createTransCompany(string _fields) public {
        string[] memory filedList = getFieldsArray(_fields);
        require(filedList.length == 4,LIST_LENGTH_ERROR);
        string memory new_fields = string(abi.encodePacked(filedList[1]
            ,",",filedList[2],",",filedList[3],",,"));
        int code = insertOneRecord(tb_company,filedList[0],new_fields,false);
        require(code == 1,INSERT_ERROR);
        emit INSERT_EVENT(code,new_fields);
    }

    //获取运输公司
    function getTransCompany(string company_addr) public view returns(string) {
        return selectOneRecordToJson(tb_company,company_addr);
    }

    //获取运输公司信息(列表)
    function getTransCompanyToArray(string company_addr) public view returns(string[]) {
        return selectOneRecordToArray(tb_company,company_addr,["companyAddress",company_addr]);
    }
    // 判断运输公司是否存在
    function hasExistTransCompany(string company_addr) public returns(bool) {
        int code = exist(company_addr,TransCompany);
        return code == SUCCESS_RETURN;
    }

    //更新运输公司信息
    function updateTransCompany(string primaryKey,string[] _fields,uint256 index, string new_val) public {
        string memory new_fields = getChangeFieldsString(_fields,index,new_val);
        int8 code = updateOneRecordByCond(tb_company,primaryKey,new_fields,["index",primaryKey]);
        require(code == SUCCESS_RETURN,"更新运输公司信息失败");
        emit UPDATE_EVENT(code,new_fields);
    }
}
