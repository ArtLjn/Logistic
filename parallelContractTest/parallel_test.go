package main

import (
	"fmt"
	"github.com/thedevsaddam/gojsonq"
	"sync"
	"testing"
	"time"
)

var (
	wg sync.WaitGroup
	mx sync.Mutex
)

// 1. 普通合约模式
func TestOrdinaryCreatePerChaseOrder(t *testing.T) {
	GetJsonVal(commonEq(OwnUser, "disableParallel", nil))
	//DoCreatePerChaseOrder()
	DoCreateTransOrder()
}

// 2. 开启并行合约模式
func TestConcurrentCreatePerChaseOrder(t *testing.T) {
	// 开启并行
	GetJsonVal(commonEq(OwnUser, "enableParallel", nil))
	// 关闭并行
	defer GetJsonVal(commonEq(OwnUser, "disableParallel", nil))
	//DoCreatePerChaseOrder()
	DoCreateTransOrder()
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

// 调用创建采购订单接口
func DoCreatePerChaseOrder() {
	wg.Add(4)
	start := time.Now()
	for i := 0; i < 4; i++ {
		go func() {
			defer wg.Done()
			for j := 0; j < 25; j++ { // 每个goroutine执行25次
				mx.Lock()
				funcParam := []interface{}{
					"apple,now,1",
				}
				commonEq(PerChaseCompany, "CreatePerChaseOrder", funcParam)
				mx.Unlock()
			}
		}()
	}
	wg.Wait()
	elapsed := time.Since(start)
	fmt.Printf("Total execution time: %s\n", elapsed)
}

// 调用创建转运订单接口
func DoCreateTransOrder() {
	wg.Add(4)
	start := time.Now()
	for i := 0; i < 4; i++ {
		go func() {
			defer wg.Done()
			for j := 0; j < 25; j++ { // 每个goroutine执行25次
				mx.Lock()
				funcParam := []interface{}{
					"1," + PerChaseCompany + "," + TransCompany + ",NewYork,ShangHai,171221212,178982178,0",
				}
				commonEq(TransCompany, "CreateTransOrder", funcParam)
				mx.Unlock()
			}
		}()
	}
	wg.Wait()
	elapsed := time.Since(start)
	fmt.Printf("Total execution time: %s\n", elapsed)
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
