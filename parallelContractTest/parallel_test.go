package main

import (
	"fmt"
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
	startGoroutine(DoCreatePerChaseOrder)
	//startGoroutine(DoCreateTransOrder)
}

// 2. 开启并行合约模式
func TestConcurrentCreatePerChaseOrder(t *testing.T) {
	// 开启并行
	GetJsonVal(commonEq(OwnUser, "enableParallel", nil))
	// 关闭并行
	defer GetJsonVal(commonEq(OwnUser, "disableParallel", nil))
	startGoroutine(DoCreatePerChaseOrder)
	//startGoroutine(DoCreateTransOrder)
}

func startGoroutine(f func()) {
	wg.Add(4)
	start := time.Now()
	for i := 0; i < 4; i++ {
		go func() {
			defer wg.Done()
			for j := 0; j < 25; j++ { // 每个goroutine执行25次
				mx.Lock()
				f()
				mx.Unlock()
			}
		}()
	}
	wg.Wait()
	elapsed := time.Since(start)
	fmt.Printf("Total execution time: %s\n", elapsed)
}
