### 啥叫内核线程？
    如果某线程的pid是2，就说明他是内核线程。
    pid为2的进程叫kthreadd进程，用来管理内核线程。

### 内核线程都有哪些？
    kthreadd
    ksoftirqd
    kswapd0: 用于内存回收
    kworker: 用于执行内核工作队列，分为绑定CPU和未绑定CPU两类
    migration: 在负载均衡过程中，把进程迁移到CPU上。每个CPU都有一个migration内核线程。
    jbd2: 用来为文件系统提供日志功能
    pdflush: 

### 内核线程CPU利用率太高的原因是什么？

### 如何排查？
    top(查看系统和进程的CPU使用情况)
    perf record 
    perf report
    火焰图
