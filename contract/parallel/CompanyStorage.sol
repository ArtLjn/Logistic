// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.4.25;
pragma experimental ABIEncoderV2;
import "./TableTools.sol";
import "./Ownable.sol";
contract CompanyStorage is TableTools,Ownable {
    constructor(){
        initTableStruct(tb_company,TransCompany,Trans_PrimaryKey,TransField);
        initTableStruct(tb_perchase,PerChaseCompany,PerChase_PrimaryKey,PerChaseField);
    }

    // 创建采购公司
    // _fields: companyAddress, companyName
    // exam: 0x82b0f287dbb0f6622d9b8a350493a47595a8a54b,ddd
    function createPerChaseCompany(string _fields) public {
        string[] memory filedList = getFieldsArray(_fields);
        require(filedList.length == 2,LIST_LENGTH_ERROR);
        // new_fields : "companyName,perchaseList,transList"
        string memory new_fields = string(abi.encodePacked(filedList[1],",,"));
        int8 code = insertOneRecord(tb_perchase,filedList[0],new_fields,false);
        require(code == 1,INSERT_ERROR);
        emit INSERT_EVENT(code,new_fields);
        string[] memory tableList = new string[](2);
        tableList[0] = PerChaseCompany;
        tableList[1] = PerChaseOrder;
        setTablePermission(filedList[0],tableList);
    }

    // 查询采购公司
    function getPerChaseCompany(string company_addr) public view returns(string) {
        (int8 code,string memory result) = selectOneRecordToJson(tb_perchase,company_addr);
        require(code == SUCCESS_RETURN,QUERY_ERROR);
        return result;
    }

    // 查询采购公司(列表)
    function getPerChaseCompanyByArray(string company_addr) public view returns(string[]) {
        (int8 code,string[] memory result) = selectOneRecordToArray(tb_perchase,company_addr,["companyAddress",company_addr]);
        require(code == SUCCESS_RETURN,QUERY_ERROR);
        return result;
    }

    // 判断采购公司是否存在
    function hasExistPerChaseCompany(string company_addr) public returns(bool) {
        int code = exist(company_addr,PerChaseCompany);
        return code == SUCCESS_RETURN;
    }

    // 更新采购公司信息
    function updatePerChaseCompany(string primaryKey,string[] _fields,uint256 index, string new_val) public {
        string memory new_fields = getChangeFieldsString(_fields,index,new_val);
        int8 code = updateOneRecordByCond(tb_perchase,primaryKey,new_fields,["companyAddress",primaryKey]);
        require(code == SUCCESS_RETURN,"更新采购公司信息失败");
        emit UPDATE_EVENT(code,new_fields);
    }

    //创建运输公司
    // _fields:  companyAddress,companyName,location,bussinessScope
    // exam: 0x149b37fd5f05fe348166d44fe64daac6a4b936b1,kkk,local,1
    function createTransCompany(string _fields) public {
        string[] memory filedList = getFieldsArray(_fields);
        require(filedList.length == 4,LIST_LENGTH_ERROR);
        string memory new_fields = string(abi.encodePacked(filedList[1]
            ,",",filedList[2],",",filedList[3],",,"));
        int8 code = insertOneRecord(tb_company,filedList[0],new_fields,false);
        require(code == 1,INSERT_ERROR);
        emit INSERT_EVENT(code,new_fields);
        string[] memory tableList = new string[](2);
        tableList[0] = TransCompany;
        tableList[1] = TransOrder;
        setTablePermission(filedList[0],tableList);
    }

    //获取运输公司
    function getTransCompany(string company_addr) public view returns(string) {
        (int8 code,string memory result) = selectOneRecordToJson(tb_company,company_addr);
        require(code == SUCCESS_RETURN,QUERY_ERROR);
        return result;
    }

    //获取运输公司信息(列表)
    function getTransCompanyToArray(string company_addr) public view returns(string[]) {
        (int8 code,string[] memory result) = selectOneRecordToArray(tb_company,company_addr,["companyAddress",company_addr]);
        require(code == SUCCESS_RETURN,QUERY_ERROR);
        return result;
    }
    // 判断运输公司是否存在
    function hasExistTransCompany(string company_addr) public returns(bool) {
        int code = exist(company_addr,TransCompany);
        return code == SUCCESS_RETURN;
    }

    //更新运输公司信息
    function updateTransCompany(string primaryKey,string[] _fields,uint256 index, string new_val) public {
        string memory new_fields = getChangeFieldsString(_fields,index,new_val);
        int8 code = updateOneRecordByCond(tb_company,primaryKey,new_fields,["companyAddress",primaryKey]);
        require(code == SUCCESS_RETURN,"更新运输公司信息失败");
        emit UPDATE_EVENT(code,new_fields);
    }
}
