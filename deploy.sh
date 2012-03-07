#!/bin/bash
git pull origin master
play dependencies --sync
$PLAY_HOME/play restart
