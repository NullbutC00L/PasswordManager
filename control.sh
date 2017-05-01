#!/bin/bash
# Read pids into an array
OIFS="$IFS"; IFS=$'\n'; PIDS=($(<pids.txt)); IFS="$OIFS"

# Prevent error assigning variable
if [ $2 != "all" ]
then
  PROCESS=${PIDS[(($(($2-1))))]}
fi 

case $1 in
  resume )
    kill -SIGCONT $PROCESS
    echo $PROCESS resumed
      echo -e "\033[1;32mRESUMED\033[0m $PROCESS" 
    ;;
  stop )
    if [ $2 == "all" ]
    then
      kill -SIGTERM ${PIDS[*]} >& /dev/null
      echo -e "\033[1;32mSTOPPED\033[0m all replicas" 
    else
      kill -SIGTERM $PROCESS
      echo -e "\033[1;32mSTOPPED\033[0m $PROCESS" 
    fi
    ;;
  pause )
    kill -SIGSTOP $PROCESS
    echo -e "\033[1;32mPAUSED\033[0m $PROCESS" 
    ;;
  status )
    if [ $2 == "all" ]
    then
      for PROCESS in $PIDS; do
        echo -e "\033[1;32mSTATUS\033[0m for $PROCESS"
        ps $PROCESS -o state,pid,time
        lsof -Pan -p $PROCESS -i
      done
    else
      echo -e "\033[1;32mSTATUS\033[0m for $PROCESS" 
      ps $PROCESS -o state,pid,time 
      lsof -Pan -p $PROCESS -i
    fi 
    ;;
  * )
    echo Command $1 not found.
    ;;
esac
