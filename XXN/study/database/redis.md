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

### 3.1简单动态字符串

SDS结果  简单动态字符串。

free:剩余空间；len:所用长度   buf:存放字符串

这种结构便于redis分配空间，扩容等。防止溢出或内存泄漏

![1561253424595](C:\Users\hy900\AppData\Roaming\Typora\typora-user-images\1561253424595.png)

### 3.2链表

### 3.3字典表

```
SET msg "hello world"
```

如何维护key与value的关系？使用字典表

![1561254185996](C:\Users\hy900\AppData\Roaming\Typora\typora-user-images\1561254185996.png)

dict：字典表中ht[2]  ht[0]存放指向dictht;当没有进行rehash时，不会给ht[1]分配空间

dictht:哈希表。table指向dictEntry,size目前hash表大小，use已存放节点数量

dictEntry：hash节点表。里面由key字段存放hash(key)。如果hash值一样，则后续链表

#### 3.3.2rehash

1.给ht[1]分配空间

2.rehash=0表示rehash正式开始。

3、在rehash 进行期间，每次对字典执行CRUD操作时，程序除了执行指定的操作以外，还会将ht[0]中的数据rehash 到ht[1]表中，并且将rehashidx加一

4、当ht[0]中所有数据转移到ht[1]中时，将rehashidx 设置成-1，表示rehash 结束

哈希表空间分配规则：

　　　　　　如果执行的是拓展操作，那么ht[1] 的大小为第一个大于等于ht[0] 的2的n次幂

　　　　　　如果执行的是收缩操作，那么ht[0] 的大小为第一个大于等于ht[1] 的2的n次幂

　　　　因此这里我们为ht[1] 分配 空间为8

### 3.4跳表

#### 3.4.1使用场景

​         有序集合。zadd，zscore,zrange...

​         集群节点内部数据结构

#### 3.4.1跳表总结

​        使用score作为创建跳表依据

-    跳跃表是有序集合的底层实现之一
-    主要有zskiplist 和zskiplistNode两个结构组成
-    每个跳跃表节点的层高都是1至32之间的随机数
-    在同一个跳跃表中，多个节点可以包含相同的分值，但每个节点的对象必须是唯一的
-    节点按照分值的大小从大到小排序，如果分值相同，则按成员对象大小排序

### 3.5压缩列表(ziplist)

压缩列表是列表键和哈希键的底层实现之一。当一个列表键只包含少量列表项，并且每个列表项要么就是小整数，要么就是长度比较短的字符串，redis就会使用压缩列表来做列表键的底层实现

压缩列表是一种为了节约内存而开发的顺序型数据结构

　　　　压缩列表被用作列表键和哈希键的底层实现之一

　　　　压缩列表可以包含多个节点，每个节点可以保存一个字节数组或者整数值

　　　　添加新节点到压缩列表，可能会引发连锁更新操作。

![img](https://images2015.cnblogs.com/blog/1053081/201701/1053081-20170112135428338-349937049.png)

1、zlbytes:用于记录整个压缩列表占用的内存字节数

　　　　2、zltail：记录要列表尾节点距离压缩列表的起始地址有多少字节

　　　　3、zllen：记录了压缩列表包含的节点数量。

　　　　4、entryX：要说列表包含的各个节点

　　　　5、zlend：用于标记压缩列表的末端

redis 3.2之前使用ziplist,  >3.2之后，使用quicklist

### 3.6集合

### 3.7对象

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

### 8.1RDB

​     保存某个时间点数据快照

​     在redis.conf配置文件中:  save 900 1  900s内有一个key被改变，那么进行备份。save ""禁用rdb配置

​      rdbcompression yes代表rdb文件压缩开启(建议关闭)

​     stop-writes-on-bgsave-error yes  代表如果备份线程出错，那么停止key写入操作，保证rdb文件正确

​     dump.rdb文件

​     SAVE:阻塞redis命令进程，直到RDB文件创建完毕

​     BGSAVE:fork出子进程进行RDB文件创建。子进程通知父进程进行rdf文件写入，父进程轮询接受子进程读信号。可以使用LASTSAVE检查文件是否生成

​      自动化RDB复制？  a)配置文件中save 900 1这种配置  b)主从复制，主结点主动触发 c)执行debug reload  d）执行shutdown且没有开持久化

### 8.2AOF

​    备份redis所有指令。(除查询命令外)

   redis.conf中的appendonly no 默认aof关掉.   appendfsync always/everysec/no   always：当内存中数据有变化时，写入aof文件。everysec:将缓存中每秒写入rof中   no：是写入aof时机交给系统；一般系统在内存满了的时候，再写入文件。

   **redis如何重写AOF文件**

  redis先fork一个子进程，写入一个全新的文件。他会根据现有的数据生成命令(不依赖老AOF文件)，再此过程中，命令持续写入内存buffer及老AOF文件中。子进程批量写入新文件中（默认32M），写完通知父进程。父进程会将原AOF的缓存中的命令回写至新文件。最终将旧aof文件淘汰掉。

### 8.3持久化方式

AOF+RDB:默认使用(redis4.0)

BGSAVE+AOF(增量)

### 8.4AOF重写导致常见问题

#### 8.4.1fork操作：

fork操作执行，会copy父进程的物理内存页，一次fork操作是非常费时间。可以通过INFO命令返回的*latest_fork_usec* 字段查询上一次fork子进程使用多长时间。

优化：1、使用物理机或高效优化fork的虚拟机内核技术2、控制redis的内存大小  3.合理内存分配策略 4.降低fork频率，减少不必要的AOF全量复制

#### 8.4.2子进程开销

不要将redis进程绑定单核CPU执行，因为子进程再重写的时候CPU使用率90%，会与父进程争抢CPU。避免与CPU密集型应用放在一起。

子进程再写AOF重写时，会对磁盘造成压力。iostat 监控磁盘io读写

确保内存足够，子进程与父进程一般使用相同的内存。防止内存不够，fork进程失败

 no-appendfsync-on-rewrite设置为no，因为子进程再AOF重写的时候磁盘IO会增大；而父进程也在同时进行原AOF文件重写。磁盘压力增大，可能会阻塞主线程。将这个指标设置为no，再AOF重写的时候，不进行新命令同步磁盘的工作，只是将新命令放入缓冲区内。(假设此时崩溃，最多损失30s数据)

#### 8.4.3AOF追加阻塞

常见的aof设置是appendfsync everysecs，每秒进行AOF文件追加。redis会使用另一条线程每秒执行fsync同步硬盘，当系统繁忙的时候，会阻塞主进程。

1.如果由于fsync指令，拖慢主进程：通过INFO指令的aofdelayedfsync查看fsync指令阻塞aof延迟问题

2.单机多实例，尽量避免实例再同一时间进行aof文件写入

## 9.REDIS主从同步

CAP:一致性、可用性、分区容忍性



### 9.1全量同步

​    slaves发送sync命令给master，master启动一个进程将数据快照保存至文件中(BGSAVE)，master将期间的命令保存至内存buffer中，文件写完后同步给slave；slave加载新的rdb文件；master期间会将新的命令更改数据同步给slave。

### 9.2增量同步

master分析命令是否要发送给slave(写命令)，追加AOF文件，将操作同步给slaves，对齐主从数据库，将缓存的命令写入指令，slave进行同步

### 9.3无盘复制

Redis 2.8.18 版开始支持无盘复制。所谓无盘复制是指主服务器直接通过套接字将快照内容发送到从节点，生成快照是一个遍历的过程，主节点会一边遍历内存，一边将序列化的内容发送到从节点，从节点还是跟之前一样，先将接收到的内容存储到磁盘文件中，再进行一次性加载

## 10哨兵模式

哨兵模式：一个独立的进程，通过发送命令，监控redis实例的可用性；master/slave切换，当master宕机，哨兵自动将slave切换为master，发布订阅至所有节点，更换配置文件，告知切换新master。可以使用多哨兵模式。

多哨兵模式:当主观下线的节点是主节点时，此时该哨兵3节点会通过指令sentinel is-masterdown-by-addr寻求其它哨兵节点对主节点的判断，当超过quorum（选举）个数，此时哨兵节点则认为该主节点确实有问题，这样就客观下线了，大部分哨兵节点都同意下线操作，也就说是客观下线

哨兵的作用:

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

## 12.redis key淘汰机制

   通过redis.conf的maxmemory-policy配置修改淘汰机制；默认是noeviction。内存不够用，报错

noeviction：当内存使用达到阈值的时候，所有引起申请内存的命令会报错。

allkeys-lru：在主键空间中，优先移除最近未使用的key。

 volatile-lru：在设置了过期时间的键空间中，优先移除最近未使用的key。

allkeys-random：在主键空间中，随机移除某个key。

volatile-random：在设置了过期时间的键空间中，随机移除某个key。

 volatile-ttl：在设置了过期时间的键空间中，具有更早过期时间的key优先移除。

 