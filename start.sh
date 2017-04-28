#!/bin/bash 

# funcion that calculates the number of N processes based on the number of f
# faulty servers
# ATM returning hardcoded value
function getN(){
  echo 8082
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
make keys &> /dev/null

N=$(getN)
for PORT in $(seq 8080 $N)
do
  LOG=/tmp/$PORT.log 
  PORT=$PORT mvn exec:java >> $LOG & 
  # Store pid for futher control (e.g pause, stop)
  echo $! >> pids.txt
  echo -e "\033[1;32mSUCCESS\033[0m" started replica running on port $PORT. Process PID=$!.
  echo Output will be printed to $LOG
done

echo Replicas started, you can control them using control.sh
