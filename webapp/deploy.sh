#!/bin/bash

FRONT_END_ROOT=../../9dials-frontend
FRONT_END_DESTINATION=public/app
FLEX_ROOT=../flex/recorder

echo "Resolving dependencies..."
play dependencies --sync

echo "Building flash recorder..."
pushd $FLEX_ROOT
./build.sh fb
popd
cp $FLEX_ROOT/recorder.swf public/assets/


if [ -d "$FRONT_END_ROOT" ]; then
    echo "Copying Studio App"
    rm -r $FRONT_END_DESTINATION/*
    cp -r $FRONT_END_ROOT/public/* $FRONT_END_DESTINATION
    cp -r $FRONT_END_ROOT/views $FRONT_END_DESTINATION
fi

$PLAY_HOME/play stop
rm server.pid
$PLAY_HOME/play start

