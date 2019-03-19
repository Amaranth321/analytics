#!/bin/bash

set -e

readonly SCRIPT_DIR="${0%/*}"
readonly PID_FILE="kup-analytics.pid"

# make the script runnable from anywhere
cd $SCRIPT_DIR
cd ..
echo "cd: $(pwd)"

# create necessary dirs
mkdir -p logs

# stop the process if PID file exists
if [ -f $PID_FILE ]; then
   ./scripts/stop.sh
fi

# get the latest jar, and run
nohup java -jar $(ls -t kup-analytics-*.jar | head -1) \
> /dev/null \
> logs/scripts.log \
2>&1 &

echo $! > $PID_FILE
