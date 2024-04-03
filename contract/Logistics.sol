pragma solidity ^0.4.24;
pragma experimental ABIEncoderV2;


contract Logistics {
    
    struct PerchaseCompany {
        address companyAddress;
        string companyName;
        uint[] perchaseList; // 采购订单
        uint[] transList; // 运输订单
        
    }
    
    //定义运输公司
    struct TransCompany {
        address companyAddress;
        string companyName;
        string location;
        string bussinessScope;
        uint[] transList;
    }
    
    //采购订单
    struct PerchaseOrder {
        uint index; //订单id
        string materials; //采购物资
        uint perchaseTime; //使用时间戳
        uint perchasementCycle; // 采购周期, e.g. 20
        address perchaseCompany;
        address[] transCompanyList;
        uint[] tranOrderList;
    }
    
    // 运输订单
    struct TransOrder {
        uint index; //运输订单ID
        uint perchaseIndex; //采购订单ID
        address perchaseCompany;
        address transCompany;
        string clearanceLocation; // 定义出关地点
        string entryLocation; //定义入关地点
        uint clearanceTime; //出关时间
        uint entryTime; // 入关时间
        uint situation; // 0：良好 1：有问题
    }
    
    uint perchaseIndex; //采购订单索引
    uint transIndex; //运输订单索引

    mapping(address => PerchaseCompany) perchaseCompanyMap; // 保存采购公司信息
    mapping(address => TransCompany) transCompanyMap; //保存运输公司信息
    mapping(uint => PerchaseOrder) perchaseOrderMap; //保存采购订单信息
    mapping(uint => TransOrder) transOrderMap; // 保存运输订单信息


    // 业务
    constructor() {
        perchaseIndex = 0;
        transIndex = 0;
    }

    // 创建采购公司
    function createPerchaseCompany(address companyAddress, string companyName) public {
        PerchaseCompany memory pc = PerchaseCompany(companyAddress, companyName ,new uint[](0), new uint[](0));
        perchaseCompanyMap[companyAddress] = pc;
    }

    //通过地址获取采购公司信息
    function getPerchaseCompany(address companyAddress) public view  returns(address, string, uint[], uint[]){
        PerchaseCompany memory pc = perchaseCompanyMap[companyAddress];
        return (pc.companyAddress, pc.companyName, pc.perchaseList, pc.transList);
    }

    //创建运输公司
    function createTransCompany(address companyAddress, string companyName, string location, string bussinessScope) public {
        TransCompany memory tc = TransCompany(companyAddress, companyName, location, bussinessScope, new uint[](0));
        transCompanyMap[companyAddress] = tc;
    }

    //获取运输公司
    function getTransCompany(address  companyAddress) public view returns(address, string, string, string, uint[]) {
        TransCompany memory tc = transCompanyMap[companyAddress];
        return (tc.companyAddress, tc.companyName, tc.location, tc.bussinessScope, tc.transList);

    }
    
        // 创建采购订单
    // 要求：只能由采购公司来创建
    function createPerchaseOrder(string materials, uint perchaseTime, uint perchasementCycle) public {
        // 判断提交采购订单的用户是不是存在并且是已经注册的采购公司成员
        PerchaseCompany pCom = perchaseCompanyMap[msg.sender];
        require(pCom.companyAddress != 0x0, "采购公司不存在");
        perchaseIndex = perchaseIndex+1; //如果在赋值进行++操作，那么起始ID为1，否则为0
        PerchaseOrder memory po = PerchaseOrder(perchaseIndex, materials, perchaseTime, perchasementCycle, msg.sender, new address[](0), new uint[](0));
        perchaseOrderMap[perchaseIndex] = po;
        //开发绑定操作
        pCom.perchaseList.push(perchaseIndex); //将采购订单与采购公司绑定
    }

    //获取采购订单信息
    function getPerchaseOrder(uint index) public view returns(string, uint, uint, address, address[]) {
        PerchaseOrder memory po = perchaseOrderMap[index];
        return (po.materials, po.perchaseTime, po.perchasementCycle, po.perchaseCompany, po.transCompanyList);
    }
    
        
    //创建运输订单
    // 注意：只有运输公司可以创建运输订单
    function createTransOrder(uint perchaseIndex, address perchaseCompany, address transCompany, string clearanceLocation, string entryLocation, uint clearanceTime, uint entryTime, uint situation) public {
        // 判断提交运输订单的用户是不是存在并且是已经注册的运输公司成员
        TransCompany tCom = transCompanyMap[msg.sender];
        require(tCom.companyAddress != 0x0, "运输公司不存在");
        transIndex = transIndex+1;
        TransOrder memory to = TransOrder(transIndex, perchaseIndex, perchaseCompany, transCompany, clearanceLocation, entryLocation, clearanceTime, entryTime, situation);
        transOrderMap[transIndex] = to;
        //开发绑定操作
        // 获取采购公司信息
        PerchaseCompany pc = perchaseCompanyMap[perchaseCompany];
        //绑定采购公司与运输订单信息
        pc.transList.push(transIndex);

        //绑定运输公司与运输订单信息
        tCom.transList.push(transIndex);

        //绑定采购订单与运输订单的信息
        PerchaseOrder po = perchaseOrderMap[perchaseIndex]; //获取采购订单信息
        po.transCompanyList.push(transCompany); //添加运输公司信息
        //绑定采购订单与运输公司的信息
        po.tranOrderList.push(transIndex);
    }

    //通过索引值获取运输订单信息
    function getTransOrder(uint index) view public returns(uint, address, address, string, string, uint, uint, uint)  {
        TransOrder memory to = transOrderMap[index];
        return (to.perchaseIndex, to.perchaseCompany, to.transCompany, to.clearanceLocation, to.entryLocation, to.clearanceTime, to.entryTime, to.situation);
    }
}