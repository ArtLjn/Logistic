    pragma solidity ^0.4.25;

/*
* solidity类型转换工具合约
*
*/
library TypeConvertUtil {

   /*
    * 将string转化为bytes32的工具方法
    *
    * @param source      字符串
    *
    * @return            bytes32值
    */
    function stringToBytes32(string memory source) internal pure returns (bytes32 result) {

        bytes memory tempEmptyStringTest = bytes(source);
        if (tempEmptyStringTest.length == 0) {
            return 0x0;
        }
        assembly {
            result := mload(add(source, 32))
        }
    }


   /*
    * 将bytes32转化为string的工具方法
    *
    * @param source     bytes32值
    *
    * @return           字符串
    */
    function bytes32ToString(bytes32 source) internal pure returns (string) {
        bytes memory bytesString = new bytes(32);
        uint charCount = 0;
        for (uint j = 0; j < 32; j++) {
            byte char = byte(bytes32(uint(source) * 2 ** (8 * j)));
            if (char != 0) {
                bytesString[charCount] = char;
                charCount++;
            }
        }
        bytes memory bytesStringTrimmed = new bytes(charCount);
        for (j = 0; j < charCount; j++) {
            bytesStringTrimmed[j] = bytesString[j];
        }
        return string(bytesStringTrimmed);
    }


   /*
    * 将string转化为uint的工具方法
    *
    * @param source     字符串
    *
    * @return           uint值
    */
    function stringToUint(string source) internal pure returns (uint result) {
        bytes memory b = bytes(source);
        uint i;
        result = 0;
        for (i = 0; i < b.length; i++) {
            uint c = uint(b[i]);
            if (c >= 48 && c <= 57) {
                result = result * 10 + (c - 48);
            }
        }
    }


   /*
    * 将uint转化为string的工具方法
    *
    * @param source     uint值
    *
    * @return           字符串
    */
    function uintToString(uint source) internal pure returns (string){
        if (source == 0) return "0";
        uint j = source;
        uint length;
        while (j != 0){
            length++;
            j /= 10;
        }
        bytes memory bstr = new bytes(length);
        uint k = length - 1;
        while (source != 0){
            bstr[k--] = byte(48 + source % 10);
            source /= 10;
        }
        return string(bstr);
    }


   /*
    * 将bytes转化为address的工具方法
    *
    * @param source     bytes值
    *
    * @return           address值
    */
    function bytesToAddress (bytes source) internal pure returns (address) {
        uint result = 0;
        for (uint i = 0; i < source.length; i++) {
            uint c = uint(source[i]);
            if (c >= 48 && c <= 57) {
                result = result * 16 + (c - 48);
            }
            if(c >= 65 && c<= 90) {
                result = result * 16 + (c - 55);
            }
            if(c >= 97 && c<= 122) {
                result = result * 16 + (c - 87);
            }
        }
        return address(result);
    }

    function stringToUint256(string memory _origin) internal pure returns(uint256 result) {
        bytes memory originBytes = bytes(_origin);

        for (uint256 i = 0; i < originBytes.length; i++) {
            //在ASCII码中 48-57为0-9
            uint256 number = uint256(uint8(originBytes[i])) - 48;
            require(number >= 0 && number <= 9, "TypeUtils::stringToUint256Exception: Invalid number in string");

            result = result * 10 + number;
        }

        return result;
    }

    function uint256ToString(uint256 _origin) internal pure returns (string memory result) {
        if (_origin == 0) {
            return "0";
        }
        //计算字符串长度
        uint256 length = 0;
        uint256 temp = _origin;
        while (temp != 0){
            length++;
            temp /= 10;
        }
        //赋值
        bytes memory resultBytes = new bytes(length);
        while (_origin != 0){
            resultBytes[length - 1] = bytes1(uint8(48 + _origin % 10));
            _origin /= 10;
            length--;
        }
        result = string(resultBytes);
    }

        function addressToString(address addr) internal pure returns(string memory){
        //Convert addr to bytes
        bytes20 value = bytes20(uint160(addr));
        bytes memory strBytes = new bytes(42);
        //Encode hex prefix
        strBytes[0] = '0';
        strBytes[1] = 'x';
        //Encode bytes usig hex encoding
        for(uint i=0;i<20;i++){
            uint8 byteValue = uint8(value[i]);
            strBytes[2 + (i<<1)] = encode((byteValue >> 4) & 0x0f);
            strBytes[3 + (i<<1)] = encode(byteValue & 0x0f);
        }
        return string(strBytes);
    }

    //-----------HELPER METHOD--------------//

    //num represents a number from 0-15 and returns ascii representing [0-9A-Fa-f]
    function encode(uint8 num) private pure returns(bytes1){
        //0-9 -> 0-9
        if(num >= 0 && num <= 9){
            return bytes1(num + 48);
        }
        //10-15 -> a-f
        return bytes1(num + 87);
    }
        
    //asc represents one of the char:[0-9A-Fa-f] and returns consperronding value from 0-15
    function decode(bytes1 asc) private pure returns(uint8){
        uint8 val = uint8(asc);
        //0-9
        if(val >= 48 && val <= 57){
            return val - 48;
        }
        //A-F
        if(val >= 65 && val <= 70){
            return val - 55;
        }
        //a-f
        return val - 87;
    }

    function stringToAddress(string memory data) internal pure returns(address){
        bytes memory strBytes = bytes(data);
        require(strBytes.length >= 39 && strBytes.length <= 42, "Not hex string");
        //Skip prefix
        uint start = 0;
        uint bytesBegin = 0;
        if(strBytes[1] == 'x' || strBytes[1] == 'X'){
            start = 2;
        }
        //Special case: 0xabc. should be 0x0abc
        uint160 addrValue = 0;
        uint effectPayloadLen = strBytes.length - start;
        if(effectPayloadLen == 39){
            addrValue += decode(strBytes[start++]);
            bytesBegin++;
        }
        //Main loop
        for(uint i=bytesBegin;i < 20; i++){
            addrValue <<= 8;
            uint8 tmp1 = decode(strBytes[start]);
            uint8 tmp2 = decode(strBytes[start+1]);
            uint8 combined = (tmp1 << 4) + tmp2;
            addrValue += combined;
            start+=2;
        }
        
        return address(addrValue);
    }

}