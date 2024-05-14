'use strict';

module.exports.info = 'caliper-contract';

let bc, contx;

const accountList = [
    '0xd11096537db03e8ba92692cdec3df1b527390a01',
    '0x6b4e306b57774d78723127aa818e28ee806a84c4'
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