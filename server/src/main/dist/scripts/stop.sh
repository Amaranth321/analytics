#!/bin/bash

set -e

readonly SCRIPT_DIR="${0%/*}"
readonly PID_FILE="kup-analytics.pid"

# make the script runnable from anywhere
cd $SCRIPT_DIR
cd ..
echo "cd: $(pwd)"

# stop the process if PID file exists
if [ -f $PID_FILE ]; then
    pid=`cat $PID_FILE`
    echo "Stopping kup-analytics (pid=$pid) ..."
    kill 2 $pid
    rm $PID_FILE

    # force kill the process if it's taking too long to terminate
    n=0
    maxWait=60
    while [ -n "`ps -p $pid|grep $pid`" ]
    do
        sleep 1
        n=`expr $n + 1`
        if [ $n -gt $maxWait ]; then
            kill -9 $pid
            break
        fi
    done
fi
