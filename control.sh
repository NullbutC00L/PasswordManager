#!/bin/bash
# Read pids into an array
OIFS="$IFS"; IFS=$'\n'; PIDS=($(<pids.txt)); IFS="$OIFS"

PROCESS=${PIDS[((--$2))]}
case $1 in
  #start )
    #echo starting $2
    #;;
  stop )
    kill -SIGTERM $PROCESS
    echo $PROCESS stopped
    ;;
  pause )
    kill -19 $PROCESS
    echo $PROCESS paused
    ;;
  status )
    echo status $2
    ;;
  * )
    echo Command $1 not found.
    ;;
esac
