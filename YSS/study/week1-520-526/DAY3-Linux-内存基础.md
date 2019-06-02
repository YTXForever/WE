# Linux内存基础

## 我们说的内存是什么
    我们所说的内存全都是DRAM(随机访问内存)，也就是物理内存。

## 物理内存和虚拟内存的联系
    进程是不能直接访问物理内存的，只有内核才可以直接访问物理内存。所以进程只能访问虚拟内存，虚拟内存和物理内存对应用的是页表映射，页表上会标明，这块虚拟内存指向的物理内存有哪些。并不是所有的虚拟内存都有物理内存对应，虚拟内存是在第一次使用的时候发现没有物理内存对应发生缺页异常才会对应上物理内存，页表保存在cpu内存管理单元MMU中，TLB是MMU的高速缓存。

## 虚拟内存出现的意义是什么
    虚拟内存的意义就是按需使用内存，比如说一个进程申请4G内存但是只使用一个1G，那么就只对应1个G的物理内存。

## 内存回收的机制
    1.LRU机制，系统会释放掉最近没有被使用掉的缓存
    2.Swap机制，系统会把很久未使用到的内存交换到磁盘中的swap分区中，写入磁盘叫做换入，恢复到内存叫做换出，swap通常会发生严重的性能问题。

## 上下文切换对于TLB的影响
    CPU发生上下文切换时会刷新TLB的内存，而MMU的访问速度又比TLB慢

## top的用法
     VIRT:进程使用的虚拟内存的大小  
     RES:进程使用的物理内存的大小 
     SHR:进程使用的共享内存的大小
     %MEM:进程使用物理内存的大小占用实际物理内存大小的比值

## free的用法             
    total       used       free     shared    buffers     cached
    Mem:      16466956   16251700     215256        468     407532     683852
    -/+ buffers/cache:   15160316    1306640
    Swap:            0          0          0

    total：总物理内存
    free：空闲物理内存
    shared:被进程共享的物理内存
    buffers：对于磁盘读写的缓存
    cache对于文件读写的缓存 实际上是cache页缓存和slab缓存的和
    -/+ buffers/cache: 实际使用的内存和实际空余的内存 used-buffers-cache/free+buffer+cache
    used包括buffer和cache free不包括buffer和cache
    vmstat可以查看 buffer 和 cache 情况

## 啥是脏页
    指的是写入buffers或者cache但是还没有刷新到硬盘上的文件页

## linux内存的分类
    内存可以分为文件页和匿名页，文件页一般指的是cache和buffers，匿名页一般指的是应用程序占用的内存

## linux的内存不足的时候怎么办
    1.根据LRU算法释放文件页的内存
    2.杀死发生OOM的进程
    3.swap匿名页的内存

## linux什么时候内存会不足
    内核线程kswapd0会定期扫描内存的使情况，当可用内存小于pages_low并且大于pages_min时，kswapd0会触发内存回收，使用cat /proc/zoneinfo查看内存的阈值
    pages_low=pages_min*5/4
    pages_high=pages_min*3/2

## NUMA架构
    在NUMA架构下，不同的cpu会被划分到不同的Node上，每个Node又有自己的内存空间，当然cpu也可以使用别的Node下的内存空间，使用numactl --hardware查看处理器在Node上的分布情况


