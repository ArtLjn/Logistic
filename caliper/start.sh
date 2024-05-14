# !/bin/bash
bash fisco/start.sh
echo "webase-front: http://localhost:5002/WeBASE-Front"
npx caliper benchmark run --caliper-workspace . --caliper-benchconfig config.yaml --caliper-networkconfig fisco-bcos.json