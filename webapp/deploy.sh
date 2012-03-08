#!/bin/bash

FRONT_END_ROOT=../9dials-frontend
FRONT_END_DESTINATION=public/app

git pull origin master
play dependencies --sync


if [ -d "$FRONT_END_ROOT" ]; then
    echo "Copying Studio App"
    cp -r $FRONT_END_ROOT/public $FRONT_END_DESTINATION
    cp -r $FRONT_END_ROOT/views $FRONT_END_DESTINATION
fi

$PLAY_HOME/play restart
