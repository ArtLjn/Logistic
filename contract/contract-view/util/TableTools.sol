pragma solidity ^0.4.25;
pragma experimental ABIEncoderV2;
import "../lib/LibStrings.sol";
import "../lib/SafeMath.sol";
import "../lib/LibStringUtil.sol";
import "../lib/TypeConvertUtil.sol";
import "../lib/LibString.sol";
import "../LibString.sol";

contract TableTools {

    /******* 执行状态码常量 *******/
    int8 constant internal INITIAL_STATE = 0;
    int8 constant internal SUCCESS_RETURN = 1;
    int8 constant internal FAIL_RETURN = -1;
    int8 constant internal FAIL_NULL_RETURN = -2;
    int8 constant internal FAIL_ALREADY_EXIST = -3;
    int8 constant internal FAIL_INSERT = -4;
    int8 constant internal FAIL_LACK_BALANCE = -5;
    int8 constant internal FAIL_NO_REWARD = -6;

    /******* 事件状态码常量 *******/
    uint8 constant internal EVENT_GIVE_LIKES = 10;
    uint8 constant internal EVENT_OBTAIN_LIKES = 11;
    uint8 constant internal EVENT_ATTEND_LOCATION = 12;


    /********** 错误消息 ************/
    string constant internal LIST_LENGTH_ERROR = "The list length is wrong!";
    string constant internal INSERT_ERROR = "Insert failed!";
    string constant internal UPDATE_ERROR = "update failed!";
    string constant internal QUERY_ERROR = "query failed!";
    string constant internal REMOVE_ERROR = "remove failed!";
    /************* event *********************/
    event INSERT_EVENT(int8,string);
    event UPDATE_EVENT(int8,string);
    event QUERY_EVENT(int8);
    event REMOVE_EVENT(int8);
    /******************表格名称,主键,属性***********************/
    //运输公司信息
    string constant TransCompany = "tb_company";
    string constant Trans_PrimaryKey = "companyAddress";
    string constant TransField = "companyName,location,bussinessScope,transList";

    //采购公司信息
    string constant PerChaseCompany = "tb_perchase";
    string constant PerChase_PrimaryKey = "companyAddress";
    string constant PerChaseField = "companyName,perchaseList,transList";

    //采购订单信息
    string constant PerChaseOrder = "tb_perchase_order";
    string constant PerChase_PrimaryKey = "index";
    string constant PerChaseOrderField = "materials,perchaseTime,perchasementCycle,perchaseCompany,transCompanyList,tranOrderList";

    // 运输订单信息
    string constant TransOrder = "tb_trans_order";
    string constant Trans_PrimaryKey = "index";
    string constant TransField = "perchaseIndex,perchaseCompany,transCompany,clearanceLocation,entryLocation,clearanceTime,entryTime,situation";

    // map
    string constant Map = "tb_map";
    string constant Key = "key";
    string constant Val = "value";
    /***********表结构**************/
    struct Bean {
        // 表名称
        string tableName;
        // 主键
        string primaryKey;
        // 表字段
        string[] fields;
    }
    /***********引入库*****************/
    using LibStrings for *;
    using LibStringUtil for*;
    using SafeMath for *;
    using TypeConvertUtil for *;
    using LibString for *;
    /******* 使用到的表结构 *******/
    Bean internal tb_company;
    Bean internal tb_perchase;
    Bean internal tb_perchase_order;
    Bean internal tb_trans_order;
    Bean internal tb_map;
    /*
     * 1.初始化一个表结构
     *
     * @param _tableStruct  表结构体
    * @param _tableName    表名称
    * @param _primaryKey   表主键
    * @param _fields       表字段组成的字符串（除主键）
    *
    * @return 无
    *
    */
    function initTableStruct(
        Bean storage _tableStruct,
        string memory _tableName,
        string memory _primaryKey,
        string memory  _fields)
        internal {
            TableFactory tf = TableFactory(0x1001);
            tf.createTable(_tableName, _primaryKey, _fields);
            _tableStruct.tableName = _tableName;
            _tableStruct.primaryKey = _primaryKey;
            _tableStruct.fields = new string[](0);
            LibStrings.slice memory s = _fields.toSlice();
            LibStrings.slice memory delim = ",".toSlice();
            uint params_total = s.count(delim) + 1;
            for(uint i = 0; i < params_total; i++) {
                _tableStruct.fields.push(s.split(delim).toString());
            }
    }

    /*
    * 查询表中一条记录并以字符串数组的格式输出
    *
    * @param _tableStruct    表结构
    * @param _primaryKey     待查记录的主键
    * @param _conditionPair  筛选条件（一个字段）
    *
    * @return 执行状态码
    * @return 该记录的字符串数组
    */

     function selectOneRecordToArray(Bean storage _tableStruct, string memory _primaryKey,
         string[2]  _conditionPair) internal  returns (int8, string[] memory) {
        // 打开表
        Table table = openTable(_tableStruct.tableName);
        // 查询
        Condition condition = table.newCondition();
        if(_conditionPair.length == 2){
             condition.EQ(_conditionPair[0], _conditionPair[1]);
        }
        Entries entries = table.select(_primaryKey, condition);
        // int8 statusCode = constDef.INITIAL_STATE;
        int8 statusCode = 0;
        string[] memory retContent;
        if (entries.size() > 0) {
            // statusCode = constDef.SUCCESS_RETURN;
            statusCode = 1;
            retContent = LibStringUtil.getEntry(_tableStruct.fields, entries.get(0));
        }else{
            statusCode = FAIL_NULL_RETURN;
            retContent = new string[](0);
        }
        return (statusCode ,retContent);
        // 将查询结果解析为json字符串
    }

    function selectAllArray(Bean storage _tableStruct, string memory _primaryKey,string memory key)
    internal  returns (int8, string[] memory) {
        // 打开表
        Table table = openTable(_tableStruct.tableName);
        // 查询
        Condition condition = table.newCondition();
        Entries entries = table.select(_primaryKey, condition);
        int8 statusCode = 0;
        string[] memory retContent;
        if (entries.size() > 0) {
            retContent = new string[](uint256(entries.size()));
            for (uint256 i = 0;i < uint256(entries.size()); i ++) {
                Entry entry = entries.get(int(i));
                retContent[i] = entry.getString(key);
            }
            statusCode = 1;
        }else{
            statusCode = FAIL_NULL_RETURN;
            retContent = new string[](0);
        }
        return (statusCode ,retContent);
    }


    function selectByMoreCond(Bean storage _tableStruct,
        string memory _primaryKey,string[] cond,string[] val
    ) internal returns(int8, string[] memory) {
        int8 statusCode = 0;
        if (cond.length == 0) {
            statusCode = FAIL_RETURN;
        }
        if (val.length == 0) {
            statusCode = FAIL_RETURN;
        }
        if (val.length != cond.length) {
            statusCode = FAIL_RETURN;
        }
        // 打开表
        Table table = openTable(_tableStruct.tableName);
        // 查询
        Condition condition = table.newCondition();
        for (uint k = 0;k < cond.length; k ++) {
            condition.EQ(cond[k],val[k]);
        }
        Entries entries = table.select(_primaryKey, condition);
        string[] memory retContent;
        if (entries.size() > 0) {
            statusCode = 1;
            retContent = LibStringUtil.getEntry(_tableStruct.fields, entries.get(0));
        }else{
            statusCode = FAIL_NULL_RETURN;
            retContent = new string[](0);
        }
        return (statusCode,retContent);
    }

    /*
    * 4.查询表中一条记录并以Json格式输出
    *
    * @param _tableStruct    表结构
    * @param _primaryKey     待查记录的主键
    *
    * @return 执行状态码
    * @return 该记录的json字符串
    */
    function selectOneRecordToJson(Bean storage _tableStruct, string memory _primaryKey) internal view returns (int8, string memory) {
        // 打开表
        Table table = openTable(_tableStruct.tableName);
        Condition condition = table.newCondition();
        // 查询
        Entries entries = table.select(_primaryKey, condition);
        // 将查询结果解析为json字符串
        return LibStringUtil.getJsonString(_tableStruct.fields, _primaryKey, entries);
    }
    
    /**
     * @dev 根据添加查询并返回json格式
     * @param _tableStruct 
     * @param _primaryKey 
     * @param _conditionPair 
     * @return 
     * @return 
     */
    function selectOneRecordByCondToJson(Bean storage _tableStruct, string memory _primaryKey,string[2]  _conditionPair) internal view returns (int8, string memory) {
        // 打开表
        Table table = openTable(_tableStruct.tableName);
        Condition condition = table.newCondition();
        if(_conditionPair.length == 2){
             condition.EQ(_conditionPair[0], _conditionPair[1]);
        }
        // 查询
        Entries entries = table.select(_primaryKey, condition);
        // 将查询结果解析为json字符串
        return LibStringUtil.getJsonString(_tableStruct.fields, _primaryKey, entries);
    }


    /*
    * 6.向指定表中插入一条记录
    *
    * @param _tableStruct    表结构
    * @param _primaryKey     待插记录的主键
    * @param _fields         各字段值
    * @param _isRepeatable   主键下记录是否可重复
    *
    * @return 执行状态码
    */
    function insertOneRecord(Bean storage _tableStruct, string memory _primaryKey, string memory _fields, bool _isRepeatable) internal returns (int8) {
        // require(bytes(_primaryKey).length > 0);
        // require(_fields.length == _tableStruct.fields.length);
        int8 setStatus = INITIAL_STATE;
        int8 getStatus = INITIAL_STATE;
        string memory getContent;
        string[] memory fieldsArray;
        Table table = openTable(_tableStruct.tableName);
        if(_isRepeatable){
            getStatus = FAIL_NULL_RETURN;
        }else{
            (getStatus, getContent) = selectOneRecordToJson(_tableStruct, _primaryKey);
        }
        if (getStatus == FAIL_NULL_RETURN) {
            fieldsArray = getFieldsArray(_fields);
            // 创建表记录
            Entry entry = table.newEntry();
            entry.set(_tableStruct.primaryKey, _primaryKey);
            for (uint i = 0; i < _tableStruct.fields.length; i++) {
                entry.set(_tableStruct.fields[i], fieldsArray[i]);
            }
            setStatus = FAIL_INSERT;
            //新增表记录
            if (table.insert(_primaryKey, entry) == 1) {
                setStatus = SUCCESS_RETURN;
            } else {
                setStatus = FAIL_RETURN;
            }
        } else {
            setStatus = FAIL_ALREADY_EXIST;
        }
        return  setStatus;
    }

    function openTable(string memory _table_name) private view returns (Table) {
        TableFactory tf = TableFactory(0x1001);
        return tf.openTable(_table_name);
    }

   /*
    * 7.向指定表中更新一条记录
    *
    * @param _tableStruct    表结构
    * @param _primaryKey     待更新记录的主键
    * @param _fields         各字段值组成的字符串
    *
    * @return 执行状态码
    */
    function updateOneRecord(Bean storage _tableStruct, string memory _primaryKey, string memory _fields) internal returns(int8) {
        Table table = openTable(_tableStruct.tableName);
        string[] memory fieldsArray = getFieldsArray(_fields);
        Entry entry = table.newEntry();
        Condition condition = table.newCondition();
        entry.set(_tableStruct.primaryKey, _primaryKey);
        for (uint i = 0; i < _tableStruct.fields.length; i++) {
            entry.set(_tableStruct.fields[i], fieldsArray[i]);
        }
        int count = table.update(_primaryKey, entry, condition);
        if(count == 1){
            return SUCCESS_RETURN;
        }else{
            return FAIL_RETURN;
        }
    }

    /**
     * @dev 根据条件更新字段
     * @param _tableStruct 
     * @param _primaryKey 
     * @param _fields 
     * @param _conditionPair 
     */
    function updateOneRecordByCond(Bean storage _tableStruct, string memory _primaryKey, string memory _fields,string[2]  _conditionPair) internal returns(int8) {
        Table table = openTable(_tableStruct.tableName);
        string[] memory fieldsArray = getFieldsArray(_fields);
        Entry entry = table.newEntry();
        Condition condition = table.newCondition();
        if(_conditionPair.length == 2){
             condition.EQ(_conditionPair[0], _conditionPair[1]);
        }
        entry.set(_tableStruct.primaryKey, _primaryKey);
        for (uint i = 0; i < _tableStruct.fields.length; i++) {
            entry.set(_tableStruct.fields[i], fieldsArray[i]);
        }
        int count = table.update(_primaryKey, entry, condition);
        if(count == 1){
            return SUCCESS_RETURN;
        }else{
            return FAIL_RETURN;
        }
    }

    function remove(string memory primaryKey,string memory _table_name) internal returns (int8) {
        // 打开表
        Table table = openTable(_table_name);
        // 删除记录
        if (table.remove(primaryKey, table.newCondition()) == 1) {
            return SUCCESS_RETURN; // 删除成功
        } else {
            return FAIL_RETURN; // 删除失败
        }
    }

    function removeByCond(string memory primaryKey,string memory _table_name,string[2]  _conditionPair) internal returns(int8) {
        Table table = openTable(_table_name);
        Condition condition = table.newCondition();
        if(_conditionPair.length == 2){
            condition.EQ(_conditionPair[0], _conditionPair[1]);
        }
        // 删除记录
        if (table.remove(primaryKey, condition) == 1) {
            return SUCCESS_RETURN; // 删除成功
        } else {
            return FAIL_RETURN; // 删除失败
        }
    }

    function exist(string memory primaryKey,string memory _table_name) internal view returns (int) {
        // 打开表
        Table table = openTable(_table_name);
        // 构建查询条件
        Condition condition = table.newCondition();
        // 查询
        Entries entries = table.select(primaryKey, condition);
        if (entries.size() == 0) {
            return FAIL_RETURN;
        }else {
            return SUCCESS_RETURN;
        }
    }
   /*
    * 字符串数组生成工具
    *
    * @param _fields  带解析的字符串
    *
    * @return 解析后的字符串数组
    *
    */
    function getFieldsArray(string _fields) internal pure returns(string[]){
        string[] memory arrays ;
        LibStrings.slice memory s = _fields.toSlice();
        LibStrings.slice memory delim = ",".toSlice();
        uint params_total = s.count(delim) + 1;
        arrays = new string[](params_total);
        for(uint i = 0; i < params_total; i++) {
            arrays[i] = s.split(delim).toString();
        }
        return arrays;
    }

    /*
    * 8.修改各字段中某一个字段，字符串格式输出
    *
    * @param _fields  各字段值的字符串数组
    * @param index    待修改字段的位置
    * @param values   修改后的值
    *
    * @return         修改后的各字段值，并以字符串格式输出
    *
    */
    function getChangeFieldsString(string[] memory _fields, uint index, string values) internal returns (string){
        string[] memory fieldsArray = _fields;
        fieldsArray[index] = values;
        return LibStringUtil.strConcatWithComma(fieldsArray);
    }
}