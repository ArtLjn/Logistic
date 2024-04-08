'use strict';

module.exports.info = 'caliper-contract';

let bc, contx;
let accountList;
module.exports.init = function (blockchain,context,args) {
    bc = blockchain;
    contx = context;

    const PerChaseCompany = require('./PerChaseCompany');
    accountList = PerChaseCompany.accountList;

    let workload = [{
        'transaction_type':'CreateTransCompany(string)',
        '_fields': accountList[1].toString() + ",kkk,local,1"
    }]
    return bc.invokeSmartContract(null,'logistics','v0',workload,null);
}

function createTransOrder() {
    let workload = [];
    workload.push({
        'transaction_type':'CreateTransOrder(string)',
        'fields':'1,' + accountList[0].toString() +',' + accountList[1].toString() + ',NewYork,ShangHai,171221212,178982178,0',
    })
    return workload;
}

module.exports.run = function() {
    let args = createTransOrder();
    return bc.invokeSmartContract(contx,'logistics','v0',args,null);
}


module.exports.end = function () {
    return Promise.resolve();
};