#                                                     redis

## 1.memcache

 memcache：底层hash。支持简单的数据类型，不支持持久化，不支持主从，不支持分片

## 2.redis特点

1.单线程，数据存储内存

2.使用NIO

3.数据类型

string,

hash:eg:    hmset lilei name "lilei" age 16 title "senior"

​         hget lilei name

​         hset lilei title "djjde"

list:   lpush mylist "aa" (从左边插入)

​         lrange mylist  0 10

set: sadd myset 111

​    smember myset（获取myset中所有元素）

sortedset(有序)：zadd mysortset 3 abc;zadd mysortset 1 def;

​                            zrangebyscore mysortset 0 10(按照score排序)

4.高级数据类型

  HyperLogLog：不精确计数：eg  PFADD  databases  "Redis"  "MongoDB"  "MySQL"  PFCOUNT database  (去重数量 3)。占据12k内存(pf使用2的14次幂的桶，每个桶maxbit 6bit，)，数值量小，使用稀疏矩阵

  Geo：地理位置

## 3.redis底层数据结构

3.1String

​    长度最大512M

## 4.海量key，如何查询出某一固定前缀的key

KEYS parttern一次性获取所有key(慎用:仅再数据量少的时候可以使用，会有性能影响)

SCAN 

  SCAN cursor [MATCH pattern] [COUNT count]

## 5.分布式锁

### 5.1redis实现

​        setnx：如果值不存在，创建。返回值0，设置失败，key存在

​        SET key value [EX seconds] [PX milliseconds] [NX|XX]  

​        NX：当值不存在的时候插入 XX:只有键key存在的时候才会设置key的值

# 

### 5.2zookeeper实现

## 6.redis各种常见问题

### 6.1redis雪崩问题如何解决

 可以在过期时间+随机串，防止同一时间缓存透传。

### 6.2缓存穿透如何解决

  缓存没有，db没有。使用布隆过滤器。bloom filter：他返回结果，判断key是否在，可能会出现误判，但是判断key不存在，就一定不存在。eg: bf.exists codehole user4

### 6.3缓存预热

系统上线前，提前将数据放入缓存中，防止缓存没有，直接打到db

## 7.异步队列

### 7.1使用LIST实现

可以使用list

RPUSH mytestlist aaa

BLPOP mytestlist

BLPOP  KEY[KEY...] TIMEOUT   阻塞blpop,超时时间是timeout

### 7.2生产者/消费者模式

​    pub/sub模式（无法保证可达）

​    消费者:SUBSCRIBE channel 订阅频道

​    生产者:PUBLISH channel  message 生产消息

## 8.redis持久化

8.1RDB

​     保存某个时间点数据快照

​     在redis.conf配置文件中:  save 900 1  900s内有一个key被改变，那么进行备份。save ""禁用rdb配置

​      rdbcompression yes代表rdb文件压缩开启(建议关闭)

​     stop-writes-on-bgsave-error yes  代表如果备份线程出错，那么停止key写入操作，保证rdb文件正确

​     dump.rdb文件

​     SAVE:阻塞redis命令进程，直到RDB文件创建完毕

​     BGSAVE:fork出子进程进行RDB文件创建。子进程通知父进程进行rdf文件写入，父进程轮询接受子进程读信号。可以使用LASTSAVE检查文件是否生成

​      自动化RDB复制？  a)配置文件中save 900 1这种配置  b)主从复制，主结点主动触发 c)执行debug reload  d）执行shutdown且没有开持久化

8.2AOF

​    备份redis所有指令。(除查询命令外)

   redis.conf中的appendonly no 默认aof关掉.   appendfsync always/everysec/no   always：当内存中数据有变化时，写入aof文件。everysec:将缓存中每秒写入rof中   no：是写入aof时机交给系统；一般系统在内存满了的时候，再写入文件。

8.3redis如何重写AOF文件

  redis先fork一个子进程，写入一个全新的文件。他会根据现有的数据生成命令(不依赖老AOF文件)，再此过程中，命令持续写入内存buffer及老AOF文件中，新文件会追加buffer中的命令行。最终将旧aof文件淘汰掉。

8.4持久化方式

AOF+RDB:默认使用(redis4.0)

BGSAVE+AOF(增量)

## 9.REDIS主从同步

CAP:一致性、可用性、分区容忍性



### 9.1全量同步

​    slaves发送sync命令给master，master启动一个进程将数据快照保存至文件中(BGSAVE)，master将期间的命令保存至内存buffer中，文件写完后同步给slave；slave加载新的rdb文件；master期间会将新的命令更改数据同步给slave。

### 9.2增量同步

master分析命令是否要发送给slave(写命令)，追加AOF文件，将操作同步给slaves，对齐主从数据库，将缓存的命令写入指令，slave进行同步

### 9.3无盘复制

Redis 2.8.18 版开始支持无盘复制。所谓无盘复制是指主服务器直接通过套接字将快照内容发送到从节点，生成快照是一个遍历的过程，主节点会一边遍历内存，一边将序列化的内容发送到从节点，从节点还是跟之前一样，先将接收到的内容存储到磁盘文件中，再进行一次性加载

## 10哨兵模式

#### 10.1流言协议

每个节点随机与对方通信，最终节点状态达成一致

种子节点定时随机向其他节点发送节点列表及广播信息

不保证信息会送达，但是最终一致性。

### 10.2master挂掉，消息丢失怎么办?

redis使用异步同步，如果master挂掉，slaves可能没有完全获取完，造成数据不一致的现象。

可以使用配置文件 

min-slaves-to-write 1master下面至少有一个slave保持正常的复制，否则停止对外写服务

min-slaves-max-lag 10        如果10s未收到slave同步反馈，说明该slaves复制状态不正常

### 11集群

一致性hash算法，分配哈希槽。

hash环数据倾斜问题:增加虚拟节点

## 10.redis key淘汰机制

   