#!/bin/bash
pushd ~/projects/crawlerwebapp
nohup play run . 2>&1 > log/crawlerwebapp-nohup.log &
echo $! > process_id.txt
popd
