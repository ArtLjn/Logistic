pragma solidity ^0.4.25;
pragma experimental ABIEncoderV2;

import "../util/TableTools.sol";
contract MapStorage is TableTools {

    event PutResult(int count);

    /**
    * map表
    * +----------------------+------------------------+-------------------------+
    * | Field                | Type                   | Desc                    |
    * +----------------------+------------------------+-------------------------+
    * | key                  | string                 | key                     |
    * | value                | string                 | value                   |
    * +----------------------+------------------------+-------------------------+
    */
    constructor() public {
        initTableStruct(tb_map,Map,Key,Val);
    }

    /**
    * 插入数据，已有数据不添加
    */
    function put(string memory _key, string memory _value) public returns(int) {
        int count = int(0);
        if(!_key.empty() && !_value.empty()){
            if (exist(_key,Map) == 1) {
                count = updateOneRecord(tb_map,_key,_value);
            } else {
                count = insertOneRecord(tb_map,_key,_value,false);
            }
        }
        emit PutResult(count);
        return count;
    }

    /**
    * 通过key获取value
    */
    function get(string memory _key) public view returns(string memory){
        string[] memory result =  selectOneRecordToArray(tb_map,_key,["key",_key]);
        if (result.length == 0) {
            return "";
        }
        return result[0];
    }

}