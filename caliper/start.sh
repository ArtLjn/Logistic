#!/bin/bash

# 记录当前时间
start_time=$(date +%s)

# 执行 Caliper 命令
npx caliper benchmark run --caliper-workspace . --caliper-benchconfig config.yaml --caliper-networkconfig fisco-bcos.json

# 记录完成时间
end_time=$(date +%s)

# 计算总耗时
total_time=$((end_time - start_time))

# 打印总耗时
echo "Total execution time: ${total_time} seconds"
