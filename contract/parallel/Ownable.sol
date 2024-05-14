pragma solidity ^0.4.25;
pragma experimental ABIEncoderV2;

import "./PermissionPrecompiled.sol";

// 权限控制
contract Ownable {
    PermissionPrecompiled pp;
    event EventTablePermissionCode(int256,string);
    constructor() public {
        pp = PermissionPrecompiled(0x1005);
    }

    // dev: 为用户添加某个表格的写入权限
    // user_addr ：用户地址信息
    // contractList :合约列表
    function setTablePermission(string user_addr,string[] contractList) internal {
        require(contractList.length > 0,"contractList can not empty!");
        for (uint i = 0; i < contractList.length; i ++ ) {
            int256 resultCode = pp.insert(contractList[i],user_addr);
            if (resultCode < 0) {
                emit EventTablePermissionCode(resultCode,
                string(abi.encodePacked("Failed to add ",contractList[i]," table permissions for user ",user_addr)));
            }
        }
    } 

    // dev: 移除用户某个表的写入权限
    // user_addr ：用户地址信息
    // contractList :合约列表
    function removeTablePermission(string user_addr,string[]  contractList) internal {
        require(contractList.length > 0,"contractList can not empty!");
        for (uint i = 0; i < contractList.length; i ++ ) {
            int256 resultCode = pp.remove(contractList[i],user_addr);
            if (resultCode < 0) {
                emit EventTablePermissionCode(resultCode,
                string(abi.encodePacked("Failed to add ",contractList[i]," table permissions for user ",user_addr)));
            }
        }
    }

    function queryByName(string table_name) public view returns (string) {
        return pp.queryByName(table_name);
    }
    // 授权指定用户对指定合约的写权限。
    function grantWrite(address contractAddr, address user)  internal returns (int256) {
        return pp.grantWrite(contractAddr, user);
    }
    // 撤销指定用户对指定合约的写访问权限。
    function revokeWrite(address contractAddr, address user) internal returns (int256) {
        return pp.revokeWrite(contractAddr, user);
    }
    // 查询对指定表有写权限的账户地址信息。
    function queryPermission(address contractAddr) public view returns (string) {
        return pp.queryPermission(contractAddr);
    }
}
    