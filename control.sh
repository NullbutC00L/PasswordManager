#!/bin/bash
# Read pids into an array
OIFS="$IFS"; IFS=$'\n'; PIDS=($(<pids.txt)); IFS="$OIFS"

# Prevent error assigning variable
if [ $2 != "all" ]
then
  PROCESS=${PIDS[((--$2))]}
fi 

case $1 in
  resume )
    kill -SIGCONT $PROCESS
    echo $PROCESS resumed
    ;;
  stop )
    if [ $2 == "all" ]
    then
      kill -SIGTERM ${PIDS[*]} >& /dev/null
      echo Stopped all replicas
    else
      kill -SIGTERM $PROCESS
      echo $PROCESS stopped
    fi
    ;;
  pause )
    kill -SIGSTOP $PROCESS
    echo $PROCESS paused
    ;;
  status )
    ps 28288 -o state,pid,time
    ;;
  * )
    echo Command $1 not found.
    ;;
esac
