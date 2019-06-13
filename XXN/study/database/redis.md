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

  HyperLogLog：计数

  Geo：地理位置

## 3.redis底层数据结构

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

## 6.如何防止redis雪崩

   可以在过期时间+随机串，防止同一时间缓存透传。

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