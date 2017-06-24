## Distribute Network Calculate Framework
This is a Distribute Calculate Framework which is based on the number of worker cpu to run.
### Depend
JDK1.8  
Maven3+  
Netty4  
FastJson  
SpringBoot  

### Master Node
**The Master-Node's source code in dncf_server.**  
**What has been achieved:**  
* resolve Worker-node Heartbeat  
* resolve Worker-node Material(cpu, memory and so on)  
* According to the cpu count for data distribution  
* According to the cpu count for task distribution  
* Timing to check Worker-node alive

There is a need to implement BaseService interface to make Netty Channel Handler initialization successful. BaseService is the interface used to resolve the personal protocol.  
### Worker Node
**The Worker-Node's source code in dncf_client.**  
**What has been achieved:**  
* Timing to send Heartbeat  
* Timing to send node's status
* According to the cpu count for data distribution
* fast to reconnect master  

There is a need to implement AccessService interface to make Netty Channel Handler initialization successful. AccessService is the interface used to resolve the protocol.
