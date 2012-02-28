#!/bin/bash
git pull origin master
play dependencies --sync
play run
