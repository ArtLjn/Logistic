cd webase-front
bash stop.sh
cd ..
bash nodes/127.0.0.1/stop_all.sh
rm -rf nodes
source ~/.bash_profile
bash build_chain.sh -l 127.0.0.1:2 -p 30300,20200,8545 -e ./fisco-bcos
cp -f nodes/127.0.0.1/sdk/* ./webase-front/conf
bash nodes/127.0.0.1/start_all.sh
cd webase-front
bash start.sh
