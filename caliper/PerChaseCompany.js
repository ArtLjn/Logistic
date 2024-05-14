'use strict';

module.exports.info = 'caliper-contract';

let bc, contx;

const accountList = [
    '0x896c79502b93f69b0b1dae553a74ae4fa9112923',
    '0x36aa8d3ff842a5e8d286bf4fb72d54119a58e9d2'
]

module.exports.init = function (blockchain,context,args) {
    bc = blockchain;
    contx = context;

    let workload = [{
        'transaction_type':'CreatePerChaseCompany(string)',
        '_fields': accountList[0].toString() + ',percompany'
    }]

    return bc.invokeSmartContract(null,'logistics','v0',workload,null);
}

function createPerChaseOrder() {
    let workload = [];
    workload.push({
        'transaction_type':'CreatePerChaseOrder(string)',
        'fields':'apple,1745454545,1,' + accountList[0].toString(),
    })
    return workload;
}

module.exports.run = function() {
    let args = createPerChaseOrder();
    return bc.invokeSmartContract(contx,'logistics','v0',args,null);
}


module.exports.end = function () {
    return Promise.resolve();
};


module.exports.accountList = accountList;