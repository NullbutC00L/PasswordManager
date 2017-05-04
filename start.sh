#!/bin/bash 

NUM_REPLICAS=${NUM_REPLICAS:=6}
PORT=${PORT:=8080}
NUM_FAULTS = ${NUM_FAULTS:=3}

# function that calculates the number of N processes based on the number of f
# faulty servers
# ATM returning hardcoded value
function getN(){
  f
  echo $(($PORT+$NUM_REPLICAS-1))
}

echo Stopping any running replicas
for REPLICA in $(cat pids.txt)
do
  kill -SIGTERM $REPLICA &> /dev/null
  if [ $? -eq 0 ]
  then 
    echo Stopped $REPLICA
  fi
done

# Cleans the contents of pids.txt
> pids.txt

# Prepare keys directory
mkdir keys &> /dev/null
rm -rf keys/* &> /dev/null

FINAL_PORT=$(getN)
for CURRENT_PORT in $(seq $PORT $FINAL_PORT)
do
  LOG=/tmp/$CURRENT_PORT.log 
  PORT=$CURRENT_PORT NUM_REPLICAS=$NUM_REPLICAS NUM_FAULTS=$NUM_FAULTS mvn exec:java > $LOG & 
  # Store pid for futher control (e.g pause, stop)
  echo $! >> pids.txt
  echo -e "\033[1;32mSUCCESS\033[0m" started replica running on port $CURRENT_PORT. Process PID=$!.
  echo Output will be printed to $LOG
done

echo Replicas started, you can control them using control.sh
