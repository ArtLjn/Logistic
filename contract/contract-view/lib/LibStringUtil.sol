pragma solidity ^0.4.25;
pragma experimental ABIEncoderV2;

import "../util/Table.sol";

/**
    @title 将Bean格式化为json
*/
library LibStringUtil {

    function getEntry(string[] memory fields, Entry entry) internal view returns (string[] memory) {
        string[] memory values = new string[](fields.length);
        for (uint i = 0; i < fields.length; i++) {
            values[i] = entry.getString(fields[i]);
        }
        return values;
    }

    function getJsonString(string[] memory fields,string primaryKey, Entries entries) internal view returns (int8, string memory) {
        string memory detail;
        if (0 == entries.size()) {
            return (int8(-2), detail);
        }
        else {
            detail = "[";
            for (uint i = 0; i < uint(entries.size()); i++) {
                string[] memory values = getEntry(fields, entries.get(int(i)));
                for (uint j = 0; j < values.length; j++) {
                    if (j == 0) {
                        detail = strConcat4(detail, "{\"primaryKey\":\"", primaryKey, "\",\"fields\":{");//这里订正了！！fields左边缺个双引号
                    }
                    detail = strConcat6(detail, "\"", fields[j], "\":\"", values[j], "\"");

                    if (j == values.length - 1) {
                        detail = strConcat2(detail, "}}");
                    } else {
                        detail = strConcat2(detail, ",");
                    }
                }
                if (i != uint(entries.size()) - 1) {
                    detail = strConcat2(detail, ",");
                }
            }
            detail = strConcat2(detail, "]");
            return (int8(1), detail);
        }
    }

    function strConcat7(
        string memory str1,
        string memory str2,
        string memory str3,
        string memory str4,
        string memory str5,
        string memory str6,
        string memory str7
    ) internal pure returns (string memory) {
        string[] memory strings = new string[](7);
        strings[0] = str1;
        strings[1] = str2;
        strings[2] = str3;
        strings[3] = str4;
        strings[4] = str5;
        strings[5] = str6;
        strings[6] = str7;
        return strConcat(strings);
    }

    function strConcat6(
        string memory str1,
        string memory str2,
        string memory str3,
        string memory str4,
        string memory str5,
        string memory str6
    ) internal pure returns (string memory) {
        string[] memory strings = new string[](6);
        strings[0] = str1;
        strings[1] = str2;
        strings[2] = str3;
        strings[3] = str4;
        strings[4] = str5;
        strings[5] = str6;
        return strConcat(strings);
    }

    function strConcat5(
        string memory str1,
        string memory str2,
        string memory str3,
        string memory str4,
        string memory str5
    ) internal pure returns (string memory) {
        string[] memory strings = new string[](5);
        strings[0] = str1;
        strings[1] = str2;
        strings[2] = str3;
        strings[3] = str4;
        strings[4] = str5;
        return strConcat(strings);
    }

    function strConcat4(
        string memory str1,
        string memory str2,
        string memory str3,
        string memory str4
    ) internal pure returns (string memory) {
        string[] memory strings = new string[](4);
        strings[0] = str1;
        strings[1] = str2;
        strings[2] = str3;
        strings[3] = str4;
        return strConcat(strings);
    }

    function strConcat3(
        string memory str1,
        string memory str2,
        string memory str3
    ) internal pure returns (string memory) {
        string[] memory strings = new string[](3);
        strings[0] = str1;
        strings[1] = str2;
        strings[2] = str3;
        return strConcat(strings);
    }

    function strConcat2(string memory str1, string memory str2) internal pure returns (string memory) {
        string[] memory strings = new string[](2);
        strings[0] = str1;
        strings[1] = str2;
        return strConcat(strings);
    }

    function strConcat(string[] memory strings) internal pure returns (string memory) {
        // 计算字节长度
        uint bLength = 0;
        for (uint i = 0; i < strings.length; i++) {
            bLength += bytes(strings[i]).length;
        }

        // 实例化字符串
        string memory result = new string(bLength);
        bytes memory bResult = bytes(result);

        // 填充字符串
        uint currLength = 0;
        for (uint k = 0; k < strings.length; k++) {
            // 将当前字符串转换为字节数组
            bytes memory bs = bytes(strings[k]);
            for (uint j = 0; j < bs.length; j++) {
                bResult[currLength] = bs[j];
                currLength++;
            }
        }

        return string(bResult);
    }
   /*
    * 字符串数组拼接为字符串的工具，各字符串之间以逗号进行连接。
    *
    * @param strings     字符串数组
    *
    * @return            拼接后的字符串
    */
    function strConcatWithComma(string[] memory strings) internal pure returns (string memory) {

        uint bLength = 0;
        string memory commaString = ",";
        bytes memory oneCommaBytes = bytes(commaString);

        for (uint i = 0; i < strings.length; i++) {
            if(i > 0){
                bLength += oneCommaBytes.length;
            }
            bLength += bytes(strings[i]).length;
        }
        string memory result = new string(bLength);
        bytes memory bResult = bytes(result);
        uint currLength = 0;
        for ( i = 0; i < strings.length; i++) {
            bytes memory bs = bytes(strings[i]);
            if(i > 0){
                for(uint k=0;k < oneCommaBytes.length; k++){
                    bResult[currLength] = oneCommaBytes[k];
                    currLength++;
                }
            }
            for (uint j = 0; j < bs.length; j++) {
                bResult[currLength] = bs[j];
                currLength++;
            }
        }

        return string(bResult);
    }

    /*
    * 比较两个字符串是否相符
    *
    * @param string1  各字段名
    * @param string2  记录的实例
    *
    * @return        各字段值
    */

    function compareTwoString(string string1, string string2) pure internal returns (bool) {
        if (bytes(string1).length != bytes(string2).length) {
            return false;
        } else {
            return keccak256(string1) == keccak256(string2);
        }
    }

}