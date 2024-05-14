pragma solidity ^0.4.25;
pragma experimental ABIEncoderV2;
import "./StorageInterface.sol";
contract NewEnergy {

    StorageInterface public energyInterface;
    StorageInterface public equipInterface;
    RoleInterface public roleInterface;
    EquipInterface public equipInterfaceTwo;
    EnergyInterface public energyInterfaceTwo;
    constructor(address energy,address role,address equip) {
        energyInterface = StorageInterface(energy);
        equipInterface = StorageInterface(equip);
        roleInterface = RoleInterface(role);
        equipInterfaceTwo = EquipInterface(equip);
        energyInterfaceTwo = EnergyInterface(energy);
    }

    /*
    描述 : 账户管理功能
    参数 :
            _address: 用户地址
            _role: 用户角色（用户、电力公司、管理员）
            _password: 密码
    返回值 :
            return
    功能   :
            Account_register：注册账号
            Account_remove：删除账号
            Account_login：登录账号
            Account_update: 修改账户密码
            Account_getAll：查询
    */

    //注册用户功能
    function Register(string _fields) public returns(int8)  {
        return roleInterface.insert(_fields);
    }

    // 删除用户功能
    function AccountRemove(string _address) public returns(int8)  {
        return roleInterface.remove(_address);
    }

    //登录用户功能
    function Login(string _fields) public returns(int8) {
        return roleInterface.login(_fields);
    }

    //查询所有用户和角色
    function QueryAllAccount() public view returns(string[] memory, string[] memory){
        return roleInterface.selectAllUser();
    }

    //修改账户密码
    function AccountUpdate(string _fields) public returns(int8) {
        return roleInterface.update(_fields);
    }

    /*
    描述 : 太阳能板交易市场
    参数 :
            string: 太阳能板详情信息
            _numId： 太阳能板编号
    返回值 :
    功能 ：
          SPU_insert ： 新增太阳能板
          SPU_update ： 更新太阳能板信息
          SPU_remove ： 移除太阳能板
          SPU_seller ： 出售太阳能板（设置单价）
          SPU_transfer : 转让太阳能板（购买）
          SPU_order_insert ： 新增太阳能板交易订单
          SPU_order_getOrder： 查询太阳能板交易订单
          SPU_getAllDetail : 查询所有太阳能板信息
          SPU_getByAddress ： 通过地址获取该地址的所有太阳能板
          SPU_getById ： 通过id获取单个太阳能板
    */

    /*
    * 添加太阳能板（创建）
    */
    function SPU_Insert(string _fields) public returns(int8) {
        return equipInterface.insert(_fields);
    }

    //更新太阳能板
    function SPU_Update(string _fields) public returns(int8) {
        return equipInterface.update(_fields);
    }

    //删除太阳能板
    function SPU_Remove(string _numId) public returns(int8) {
        return equipInterface.remove(_numId);
    }

    //出售太阳能板、设置单价
    function SPU_Seller(string _fields) public returns(int8) {
        return equipInterfaceTwo.seller(_fields);
    }

    //太阳能板转让(购买)
    function SPU_Transfer(string _fields) public returns(int8) {
        return equipInterfaceTwo.transfer(_fields);
    }

    //新增交易订单
    function SPU_Order_Insert(string _fields) public returns(int8) {
        return equipInterface.insertOrder(_fields);
    }

    //查询交易订单
    function SPU_Order_Query() public view returns(string memory) {
        return equipInterfaceTwo.selectOrderTwo();
    }

    //获取所有太阳能板信息
    function SPU_Query() public view returns(string memory) {
        return equipInterface.selectOrder();
    }

    //通过地址获取该地址的所有太阳能板
    function SPU_QueryByAddr(string _ownerShip) public view returns(string memory) {
        return equipInterfaceTwo.selectOrderByCond("Owner_ship",_ownerShip);
    }

    //通过id获取单个太阳能板
    function SPU_QueryByID(string numId) public view returns(string memory) {
        return equipInterfaceTwo.selectOrderByCond("num_id",numId);
    }

    /*
     描述 : 能源市场
     参数 :
             _num=Id： 编号
             _energy: 能源量
     返回值 :
           true 成功
           false 失败
     功能 ：
           Energy_Init: 初始化
           Energy_Update ：更新
           Energy_Select： 查看能量列表
           Energy_Remove: 清除
           Energy_Order_insert： 新增
           Energy_Order_get 获取订单列表
     */

    function Energy_Init(string _numId) public returns(int8) {
        return energyInterface.insert(_numId);
    }

    function Energy_Update(string _fields) public returns(int8){
        return energyInterface.update(_fields);
    }

    function Energy_Select() public view returns(string[] memory){
        return energyInterfaceTwo.select();
    }

    function Energy_Remove(string _numId) public returns(int8){
        return energyInterface.remove(_numId);
    }

    function Energy_Order_insert(string _fields) public returns(int8) {
        return energyInterface.insertOrder(_fields);
    }
    function Energy_Order_get() public view returns(string memory){
        return energyInterface.selectOrder();
    }
}