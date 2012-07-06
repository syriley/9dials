#!/bin/bash
pushd ../flex/recorder/
./build.sh fb
popd
cp ../flex/recorder/recorder.swf public/assets/recorder.swf 


