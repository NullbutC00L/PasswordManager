Password Manager
================

Password Manager Server
### Running
To start the project use the `start.sh` script
You can pass the number of replicas, `NUM_REPLICAS` and starting port, `PORT`.

**Running the tests**
Make sure to have the server running
```
$ ./start.sh
```

The default usage will start 4 replicas, on ports 8080 to 8083.

**Example**: To start 3 replicas beggining on port 8080:
```
$ NUM_REPLICAS=5 ./start.sh
```

### Controlling

The script `control.sh` provides a way control the execution of the replicas.

**Examples**: 
Stoping (killing) all replicas
``` 
$ ./control stop all
STOPPED all replicas
```
See the status of replica n.2
``` 
$ ./control status 2
STATUS for 12695
STAT   PID      TIME
S    12695   0:08.29
COMMAND   PID       USER   FD   TYPE             DEVICE SIZE/OFF NODE NAME
java    12695 johnytiago   80u  IPv6 0x655dc2a20880972d      0t0  TCP 127.0.0.1:8081 (LISTEN)
```

It supports the following commands:
- `pause`
- `stop`
- `resume`
- `status`

