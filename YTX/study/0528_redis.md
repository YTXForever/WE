# REDIS
## redis与memcache
*memcache*
- 支持简单数据类型
- 不支持数据持久化
- 不支持主从
- 不支持分片

*redis*
- 数据类型丰富
- 支持数据持久化
- 支持主从
- 支持分片
## redis为什么快？
- 基于内存，摆脱硬盘I/O的限制；
- 数据结构简单，没有关系型数据库的数据与数据间的关联，存储结构是hashmap（O（1））；
- 单线程（主线程是单线程的）配合多路I/O复用，避免了频繁的上下文切换和锁竞争。
- 使用多路I/O复用，非阻塞I/O。
## 多路I/O复用模型
**redis采用的I/O多路复用函数：epoll/kqueue/evport/select**，select是保底备选方案。

## redis常用的数据类型
1. string：最基本，二进制安全，set/get；
2. hash：string组成的字典，适用于存储对象数据，hmset/hset/hmget/hget/hgetall；

    - *hmset YTX name "YTX" age 19*
    - *hset YTX age 18*
    - *hget YTX age*
    - *hmget YTX age name*
    - *hgetall YTX*
3. list：列表，按插入顺序排序，lphush/lrange/lpop/，从表头进行操作，后进先出；
rpush/rpop，从表尾进行操作；rpush和lpop组合实现先进先出；
命令：https://www.runoob.com/redis/redis-lists.html
    - lpush mylish YSS；lpush mylish XXN；lpush mylish YTX；
    - lrange mylist 0 10（取[0,10)位置的数据）：YTX-XXN-YSS
    - lpop mylist：移除YTX
4. set：string元素的无序集合，不允许重复，使用哈希表实现，sadd/smembers
    - *sadd myset YTX*：重复添加会失败，成功返回1，失败返回0；
    - *smembers myset*：无序
5. sorted set:string元素的**有序集合，不允许重复**，使用分数进行从小到大排序，zadd/
    - zadd key 分数 数据值：*zadd myzset 3 XXN；zadd myzset 1 YSS*；
    - zrangebyscore key startIndex（包含） endIndex(不包含)。
6. 用于支持存储地理位置信息的geo，用于计数的hyperLogLog。

## 从海量的数据中获取某前缀的数据

- keys pattern：keys k1*,查找所有符合模式的key，数据量大时，对redis服务器和内存都是隐患；
- scan cursor [MATCH pattern][COUNT count]：scan 0 match k1* count 10,返回0时，扫描结束，否则将返回的cusor用于下一次命令的cusor。

**注：scan有可能返回重复数据**

## redis实现分布式锁
#### 分布式锁需要解决的问题
- 互斥性
- 安全性
- 死锁
- 容错性
#### 命令 setnx 
- setnx key value  
- getnx key
- expire key seconds 设置过期时间
- 两条命令：setnx和expire，原子性得不到满足。
#### set key value [EX seconds] [PX milliseconds] [NX|XX]
- EX seconds:设置过期时间为seconds秒
- PX milliseconds]：设置过期时间为milliseconds毫秒
- NX|XX：NX表示只在键不存在时，才进行设置操作；XX表示只在键存在时，才进行操作。
- 成功返回OK，失败返回nil
#### 大量key同时过期问题
- 问题：*大量清除key很耗时，会卡顿*
- 解决方案：*在过期时间上加随机值，分散key的过期时间*

## redis实现异步队列
1. 使用redis list作为队列，rpush生产消息，lpop消费消息
    - 缺点：不会检测到队列里有值就消费
    - 弥补：在应用层引入sleep机制去调用lpop重试
2. blpop key[key] timeout:阻塞一直等到队列有消息或者超时
    - 缺点：只能提供给一个消费者消费
3. pub/sub 主题订阅模式
    - 发送者pub发送消息，订阅着sub消费消息
    - 订阅着可以订阅多个主题
    - 命令：subscribe topic 订阅主题
    - 命令：publish topic value 发布消息
    - 缺点：消息的发布是无状态的，无法保证可达（专业消息队列可解决）
## redis持久化
1. RDB（快照）持久化：保存某个时间点点全量数据快照，内存数据的全量同步，因I/O而严重影响性能。
    - save:阻塞redis服务器进行，直到RDB文件创建完毕；
    - bgsave：fork储一个子进程创建RDB文件，不阻塞服务器进程，lastsave命令记录上一次持久化时间。
    
2. 自动触发RDB持久化点方式
    - redif.conf配置文件中的save m n定时触发（使用bgsave）
    - 主从复制时，主节点自动触发
    - 执行debug reload
    - redis服务shutdown，并且没有开启AOF持久化的时。
3. AOF（append-only-file）持久化：保存写状态
    - 记录所有更新操作指令
    - 以append形式追加到AOF文件中（appendonly.aof）
4. RDB与AOF的对比
    - RDB的优点：全量数据快照，文件小，恢复快
    - RDB的缺点：数据量大时严重影响性能；无法保存最近一次快照之后的数据。
    - AOF的优点：可读性高，保存增量数据，数据不易丢失；
    - AOF的缺点：文件体积大，恢复时间长。
5. RDB-AOF混合持久化方式（默认方式）
    - bgsave做镜像全量持久化，AOF做增量持久化
    
## redis主从同步（最终一致性）
## redis sentinel
*解决主从同步master宕机后的主从切换*

## redis集群
#### 一致性哈希算法
*对2^32取模将哈希值空间组织成虚拟的圆环*
#### 引入虚拟节点解决数据倾斜问题

   




    
















