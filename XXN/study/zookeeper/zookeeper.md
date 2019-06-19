# ZOOKEEPER

## 1.简介

zookeeper存放在内存中的，类似树状结构的文件系统

zoo.cfg文件中:

tickTime 2000心跳时间

dataDir:文件数据存放地方

### 1.1znode

znode:1.节点数据 存储的协调数据(状态、位置、配置等信息)

​           2.节点元数据

​           3.数据大小上线1M

#### 1.1.1znode节点类型

持久节点(create /net 666)，顺序节点(create -s /net 666)，临时节点(create -e /net ppp)，临时顺序节点(create -e -s /net ppp)

临时节点，当client与server端断开，session断掉，临时节点将不存在

#### 1.1.2临时节点

一个client连接到server，会分配一个id，客户端会定期发送心跳至服务端(ticktime)，server端如果超时未收到心跳(2ticktime),z则判定client死掉。会话中请求FIFO执行

## 2.zookeeper配置中心

### 2.1watch机制

客户端发送watch命令，可以监听节点变化

### 2.2配置中心