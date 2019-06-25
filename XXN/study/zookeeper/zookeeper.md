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

### 4.2Paxos算法

#### 4.2.1角色

​        Proposer(提议者):负责提议

​        Acceptor(接收者):负责投票

​        Learner(学习者):不参与投票，只接受投票结果

#### 4.2.2分布式一致性算法

​        阶段一:提议

​        阶段二:批准

![1561388982291](C:\Users\hy900\AppData\Roaming\Typora\typora-user-images\1561388982291.png)

!![1561387519184](C:\Users\hy900\AppData\Roaming\Typora\typora-user-images\1561387519184.png)1561387151740](C:\Users\hy900\AppData\Roaming\Typora\typora-user-images\1561387151740.png)

#### 4.2.4Zookeeper选举算法

选举消息:服务器id(myid)，事务id(Zxid)，逻辑时钟(发起投票轮数)，选举状态(LOOKING竞选，FOLLOWING随从，投票，OBSERVING观察不投票，LEADING领导)

1.每个服务实例发起选取自己为leader的投票

2.其他服务实例收到投票邀请，比较Zxid大小，如果大，投票，如果相等，则比较服务器id，投票；否则不投票。

3.发起者收到投票回馈，如果投票数>半数，胜出；否则重新发起

#### 4.2.5数据一致性问题

ZAB(zookeeper atomic broadcast原子广播协议) 解决数据一致性

一个事务请求，在一个节点请求成功了，那么在所有节点都能处理成功。丢弃只在leader节点被提出的事务

**ZAB请求响应流程**

1)follower转发请求到leader

2)leader递增Zxid，广播事务提议

3)Followe做出反馈

4)leader收到>半数反馈，提交事务

**ZAB崩溃恢复**

leader所在服务器崩溃，或失去>半数follower连接，重新进入选举

**ZAB协议丢弃老提案处理**

Zxid：64位。高32位leader纪元值编号，低32位单调递增计数器。每一次客户端事务请求，leader服务器产生一个新的提议，都会对计数器进行+1。每次选取出leader，都会取最大的zxid的高32位+1,作为新的纪元，并将低32位置0生成新的Zxid。

选举出新的leader后，老的leader重新连接成功后，将事务发到leader上，对比高32位，发现是选举之前未提交的，要求该follower回退，回退到大部分机器提交的最新方案。

**ZAB数据同步**

leader选举完，follower与leader数据同步，>半数数据同步完，提供服务

1)leader为每一个follower准备一个队列

2)没有被follower执行的事务，被leader放到队列中以proposal形式，逐个发给follower

3)紧接着发送一个commit，表示事务已提交

4)follower同步数据至本地后，leader将该follower加入可用列表

#### 4.2.6集群中节点关系

follower数量慢慢递减，会怎样

集群可用性如何

follower数据状态与leader状态一致又如何