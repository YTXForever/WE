# ZOOKEEPER

## 1.简介

zookeeper存放在内存中的，类似树状结构的文件系统

zoo.cfg文件中:

tickTime 2000心跳时间

dataDir:文件数据存放地方

initLimit=10:初始化时，节点之间信息同步，最大心跳。代表10*tickTime

syncTime=5 主从同步时间 5*tickTime

3个端口号: clientPort：client连接端口号，数据同步，leader选举

### 1.1znode

znode:1.节点数据 存储的协调数据(状态、位置、配置等信息)

​           2.节点元数据

​           3.数据大小上线1M

#### 1.1.1znode节点类型

持久节点(create /net 666)，顺序节点(create -s /net 666)，临时节点(create -e /net ppp)，临时顺序节点(create -e -s /net ppp)

临时节点，当client与server端断开，session断掉，临时节点将不存在

#### 1.1.2临时节点

一个client连接到server，会分配一个id，客户端会定期发送心跳至服务端(ticktime)，server端如果超时未收到心跳(2ticktime),z则判定client死掉。会话中请求FIFO执行

#### 1.1.3命令

create -e /etc 111创建临时节点

get /etc 获取/etc节点内容

rmr /etc 递归删除/etc节点内容

set /etc 345 修改节点/etc的内容

## 2.zookeeper配置中心

### 2.1watch机制

客户端发送watch命令，可以监听节点变化

两类watch

data watch:监听数据变化  

child watch:接听子节点状态变更(增删)

watch:一次性触发，触发后即删除。需要持续设置watch。客户端先得到watch通知，在得到变化结果。

### 2.2配置中心

## 3.ZOOKEEPER特性

### 3.1有序性

#### 3.1.1

Zxid:Zookeeper中每次更改操作都对应一个唯一的事务id，是全局有序的标记。如果Zxid1<Zxid2，说明Zxid1发生在前。

Version numbers:版本号，每次更改，版本号增加

Ticks:使用多服务器ZK时，服务器使用Tick来定义事件的时间，如状态上传、会话超时、对等点之间连接超时等

Real time:  znode节点创建修改将时间戳放入state。

#### 3.1.2 节点上元数据信息stat

![1561044762677](C:\Users\hy900\AppData\Roaming\Typora\typora-user-images\1561044762677.png)

#### 3.1.3内存存储

#### 3.1.4可集群化

## 4.zookeeper集群

### 4.1可靠的集群

每个zk部署在单个节点，使用奇数个节点(选举机制决定)，大多数节点准备即可使用