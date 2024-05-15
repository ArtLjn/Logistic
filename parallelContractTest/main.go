package main

import (
	"fmt"
	"github.com/google/uuid"
	"github.com/thedevsaddam/gojsonq"
	"net/http"
)

func main() {
	// 开启并行
	GetJsonVal(commonEq(OwnUser, "enableParallel", nil))
	// 关闭并行
	//GetJsonVal(commonEq(OwnUser, "disableParallel", nil))
	http.HandleFunc("/doOrder", func(w http.ResponseWriter, r *http.Request) {
		DoCreatePerChaseOrder()
		//DoCreateTransOrder()
		_, err := w.Write([]byte("success"))
		if err != nil {
			return
		}
	})
	err := http.ListenAndServe(":8080", nil)
	if err != nil {
		return
	}
}

// DoCreatePerChaseOrder 调用创建采购订单接口
func DoCreatePerChaseOrder() {
	funcParam := []interface{}{
		"apple,now," + uuid.New().String()[:4],
	}
	commonEq(PerChaseCompany, "CreatePerChaseOrder", funcParam)
}

// DoCreateTransOrder 调用创建转运订单接口
func DoCreateTransOrder() {
	funcParam := []interface{}{
		"1," + PerChaseCompany + "," + TransCompany + ",NewYork,ShangHai,171221212,178982178,0",
	}
	commonEq(TransCompany, "CreateTransOrder", funcParam)

}

func init() {
	if len(commonEq(OwnUser, "GetPerChaseCompany", []interface{}{
		PerChaseCompany,
	})) == 0 {
		// 创建采购公司
		GetJsonVal(commonEq(OwnUser, "CreatePerChaseCompany", []interface{}{
			PerChaseCompany + ",ddd",
		}))
	} else if len(commonEq(OwnUser, "GetTransCompany", []interface{}{
		TransCompany,
	})) == 0 {
		trans := fmt.Sprintf("%s,%s,%s,%s", TransCompany, "kkk", "local", "1")
		GetJsonVal(commonEq(OwnUser, "CreateTransCompany", []interface{}{
			trans,
		}))
	}
}

func GetJsonVal(body string) {
	data := gojsonq.New().JSONString(body)
	val := data.Find("message")
	if val == nil {
		panic(fmt.Errorf("key message not found"))
	}
	if val.(string) != "Success" {
		panic(fmt.Errorf("write chain error"))
	}
}
