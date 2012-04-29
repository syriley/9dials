#!/bin/bash
RESTORE_DIR=~/restore
RESTORE_FILE=ninedials-SNAP.dump
if [ ! -d "$RESTORE_DIR" ]; then
    mkdir $RESTORE_DIR
fi
scp nine@ninedials.com:/home/nine/backup/$RESTORE_FILE $RESTORE_DIR/$RESTORE_FILE
pg_restore -c -U nine -d ninedials $RESTORE_DIR/$RESTORE_FILE
