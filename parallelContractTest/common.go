package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
)

const (
	//PerChaseCompany 采购公司地址
	PerChaseCompany = "0x82b0f287dbb0f6622d9b8a350493a47595a8a54b"
	//TransCompany 物流公司地址
	TransCompany = "0x022b95852a2b2d2bc338b7ec50859307cab9eaea"
	//OwnUser 合约部署部署人地址
	OwnUser = "0x29e2db8ad37fb85a2466db8517d380dc98c2d51c"
)

const (
	contractAddr = "0xcf61e8f345691c977d37b75e8a9cb4d3c4ff64e8"
	contractAbi  = `[{"constant":true,"inputs":[{"name":"company_addr","type":"string"}],"name":"GetPerChaseCompany","outputs":[{"name":"","type":"string"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[{"name":"table_name","type":"string"}],"name":"queryByName","outputs":[{"name":"","type":"string"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"functionName","type":"string"},{"name":"criticalSize","type":"uint256"}],"name":"registerParallelFunction","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"index","type":"string"}],"name":"GetPerChaseOrder","outputs":[{"name":"","type":"string"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[{"name":"company_addr","type":"string"}],"name":"GetTransCompany","outputs":[{"name":"","type":"string"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[{"name":"contractAddr","type":"address"}],"name":"queryPermission","outputs":[{"name":"","type":"string"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"functionName","type":"string"}],"name":"unregisterParallelFunction","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"fields","type":"string"}],"name":"CreatePerChaseOrder","outputs":[{"name":"","type":"string"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"index","type":"string"}],"name":"GetTransOrder","outputs":[{"name":"","type":"string"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_fields","type":"string"}],"name":"CreateTransCompany","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[],"name":"enableParallel","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[],"name":"disableParallel","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"fields","type":"string"}],"name":"CreateTransOrder","outputs":[{"name":"","type":"string"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_fields","type":"string"}],"name":"CreatePerChaseCompany","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"inputs":[{"name":"company","type":"address"},{"name":"order","type":"address"}],"payable":false,"stateMutability":"nonpayable","type":"constructor"},{"anonymous":false,"inputs":[{"indexed":false,"name":"","type":"int256"},{"indexed":false,"name":"","type":"string"}],"name":"EventTablePermissionCode","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"name":"","type":"int8"},{"indexed":false,"name":"","type":"string"}],"name":"INSERT_EVENT","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"name":"","type":"int8"},{"indexed":false,"name":"","type":"string"}],"name":"UPDATE_EVENT","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"name":"","type":"int8"}],"name":"QUERY_EVENT","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"name":"","type":"int8"}],"name":"REMOVE_EVENT","type":"event"}]`
	contractName = "LogisticsController"
	url          = "http://localhost:5002/WeBASE-Front/trans/handle"
)

func commonEq(user, funcName string, funcParam []interface{}) string {
	defer func() {
		if err := recover(); err != nil {
			fmt.Println("commonEq panic:", err)
		}
	}()
	requestData := map[string]interface{}{
		"user":            user,
		"contractName":    contractName,
		"contractAddress": contractAddr,
		"funcName":        funcName,
		"contractAbi":     json.RawMessage(contractAbi),
		"funcParam":       funcParam,
		"groupId":         1,
		"useCns":          false,
		"useAes":          false,
		"cnsName":         contractName,
		"version":         "",
	}
	requestDataBytes, err := json.Marshal(requestData)
	req, err := http.NewRequest(http.MethodPost, url, bytes.NewBuffer(requestDataBytes))
	if err != nil {
		panic(err)
	}
	req.Header.Set("Content-Type", "application/json")

	client := &http.Client{}
	resp, err := client.Do(req)
	if err != nil {
		panic(err)
	}
	defer resp.Body.Close()
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		panic(err)
	}
	return string(body)
}
