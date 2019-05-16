# Load Average

## 概念
    1.Load Average 可以理解为当前时间段内平均的活跃的进程数，分为1分钟，5分钟，15分钟三个阶段，可以体现服务器负载的变化趋势。

    2.活跃的进程指的是Running + Runnable + iowait三种进程的总和，这三种进程的共性的就是都需要cpu。

## 问题
    1.load过高怎么排查？
        1）mpstat 查看cpu的使用率和iowait的情况
        2）如果是iowait很高的话，那么是由八九就是io问题，此时用pidstat -u 查出是哪个进程出现了io问题
        3）如果是cpu使用率过高，则要看看是user高，还是sys高，如果是user高，那么top一下基本就知道是哪个进程的问题了，如果是sys高，那么可能是因为cpu上下文切换导致的，可以vmstat看下cs是否很高，就绪队列r是否比较长，如果是的就pidstat -wt 看下是哪几个进程线程比较多造成cpu不正常的cs，如果cs不高，就看下in cpu中断多不多，就要看下/proc/interrupts反应的中断情况，一般是RES 重调度比较多，具体不清楚了，就到这吧


    

    
