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

- keys pattern：keys k1*,**注意数据量**
查找所有符合模式的key，数据量大时，对**redis服务器**和**内存**都是隐患；

- scan cursor [MATCH pattern][COUNT count]：
scan 0 match k1* count 10,返回0时，扫描结束，否则将返回的cusor用于
下一次命令的cusor。count 10 不能保证返回10条数据，只能大概率返回10条数据

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
    - bgsave：fork储一个子进程创建RDB文件，不阻塞服务器进程，lastsave命令记录上一次持久化时间，
    linux使用copy-on-write机制实现rdb持久化。
    
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
 
6. aof重写带来的问题：
原因：aof rewrite与fsync同时进行时（操作的是同一个文件），会导致I/O性能受损
（redis主线程为单线程啊），无法响应客户端请求
解决方案：no-appendfsync-on-rewrite的值设置为yes表示rewrite期间对
新写操作不fsync,暂时存在内存中,等rewrite完成后再写入。

## 解决aof重写问题的几个相关配置
config key | value含义
--- | ---
no-appendfsync-on-rewrite | 设置为yes表示rewrite期间对新写操作不fsync,暂时存在内存中,等rewrite完成后再写入
auto-aof-rewrite-percentage | 当前AOF文件大小是上次日志重写得到AOF文件大小的X/100倍时，自动启动新的日志重写过程
auto-aof-rewrite-min-size | 当前AOF文件启动新的日志重写过程的最小值，避免刚刚启动Reids时由于文件尺寸较小导致频繁的重写

## redis的坑
1、aof rewrite与fsync同时进行导致的I/O性能问题
2、rdb或者aof重写 fork子线程导致的阻塞问题，
所以在一台服务器上最好要预留一半的内存（防止出现AOF重写集中发生，出现swap
和OOM）。虽说子进程可以共享父进程的数据，但还是需要复制父进程的内存页表，
如果redis的内存很大，那么这个内存页表也将是非常巨大的，可能达到百兆，此时进
行复制，会导致堵塞。

**另外，fork导致内存不足，占用虚拟内存，导致朱进称阻塞**

## redis主从同步（最终一致性）
## redis主从同步过程
*全量同步过程*
1. slave发送sync到master
2. master启动后台进程 生成rdb快照
3. master将写rdb快照期间的写命令缓存
4. master将文件发给slave
5. slave恢复数据快照
6. master将写命令的缓存数据发送给slave
*增量同步过程*：
正常运行期间的更新操作之后，都会将被执行的写命令发送给从节点
1. master接收用户指令，判断是否需要同步到slave
2. master将命令append到aof文件中
3. master同步给slave节点，将指令写入master的响应缓存中（首先要对其主从数据库，保证需要同步到slave是正确的）
4. 将缓存中的数据发送给slave


## redis sentinel
*解决主从同步master宕机后的主从切换*

## redis集群
#### 一致性哈希算法
*对2^32取模将哈希值空间组织成虚拟的圆环*
#### 引入虚拟节点解决数据倾斜问题
在实际应用中一般将虚拟节点设置为32或者更大

## 以上笔记如有不对，请以以下文档为主
http://youzhixueyuan.com/redis-high-availability.html

https://mp.weixin.qq.com/s/nOfd37J21axDMnlYK9WDrg




   




    
















