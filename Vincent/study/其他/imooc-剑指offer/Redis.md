# Redis
## 常用类型？
    String: set get hmset hmget
    List: rpush lpop
    Set: sadd smembers
    SortSet: zadd zrangebscore

## 海量数据筛选某一固定key？
    数据量不大直接使用keys    
    大数据量时通过scan然后代码里循环获取

## 分布式锁的简单实现？
    setnx + expire (不推荐，避免中间异常无法设置过期时间)
    set {key} {value} ex {seconds} nx

## 普通队列和阻塞队列？
    rpush lpop 
    brpush blpop

## RDB和AOF的优缺点？
    RDB：是对整个数据作为一个快照存储
        优点：数据量小
        缺点：容易丢失数据
    AOF：是对用户的命令进行增量的存储
        优点：数据完整
        缺点：数据量大、由主线程操作会影响主线程

## 主从？

## 一致性hash？        
        