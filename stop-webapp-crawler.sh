#!/bin/bash

process_id=`cat process_id.txt`

echo killing $process_id
kill $process_id
