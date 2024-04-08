'use strict';

module.exports.info = 'caliper-contract';

let bc, contx;

const accountList = [
    '0x8c06164f16ea5c203f7e14be47feadf18a985428',
    '0xf6eeccb84e4502cd4c2e2a57b5e9875119ecaa15'
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