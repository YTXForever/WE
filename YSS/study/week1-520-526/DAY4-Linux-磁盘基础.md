# 磁盘基础

## 什么是linux文件系统
    我们指的文件系统是VFS(virtual file system) ,linux隐藏了下面的具体实现，提供了一个公共的接口。

## 文件系统的具体实现有哪些类型
    1.磁盘文件系统 ext4 nfs等
    2.内存文件系统 /proc等
    3.网络文件系统 SMB等 
    在linux中一切都会被映射到vfs文件系统中，即一切皆文件

## 磁盘文件系统的组成
    1.inode 称为索引节点 ，存储文件的元数据，大小，修改日志，最重要的存储文件在数据区的位置
    2.dentry 称为目录项 , 这个是在内核缓存之中的，存储的目录信息，比如父目录，dentry有指向inode的指针，和inode是多对一
    3.数据 存储在数据区 存储在逻辑块上 一般来说一个逻辑块大小是4k，占用四个磁盘扇区大小（512B），这样是为了提高I/O性能

## 磁盘文件系统的组件
    超级块：存储的是整个文件系统的状态信息
    索引节点区：存储的inode
    数据块区：存储的数据

## 磁盘I/O的分类
    1.缓冲IO/非缓冲IO 是否使用标准的缓存
    2.直接IO/非直接IO 是否是用cache文件页缓存
    3.阻塞IO/非阻塞IO IO时是否执行线程
    4.同步IO/异步IO  是否同步等待接受IO完成通知，还是异步等待系统通知 一般同步=阻塞 异步=非阻塞

## df的使用
    文件系统       实际大小 使用了 还剩  用了多少 vfs挂载点
    Filesystem      Size  Used  Avail Use% Mounted on
    udev            955M     0  955M   0% /dev
    tmpfs           197M  1.8M  196M   1% /run
    /dev/sda1        49G  8.9G   38G  20% /
    tmpfs           985M     0  985M   0% /dev/shm
    /dev/loop0       13M   13M     0 100% /snap/gnome-characters/139

    df -i 查看inode使用磁盘情况

## 缓存
    inode和dentry被缓存在slab缓存中 cat /proc/slabinfo 

## I/O的分层
    1.文件系统层 ： 一般指vfs,用于给上层应用提供接口
    2.通用块层：用于给vfs和其他直接读取块设备的应用提供接口 本层提供I/O合并以及排序等多种算法，用于提升I/O性能
    3.块设备层 : 设备驱动  直接与块设备交互

## HDD 与 SSD 
    HDD 是磁头读取磁道中的数据 随机访问性能很差
    SSD 是固态截止存储 性能比HDD好一些 但是随机访问依然很差

## 磁盘的类型和编号
    sd          a           1 
    磁盘类型    第一块盘    第一个分区

## 狂打日志问题的定位步骤
    1.top一下看是否出现了io问题 看下iowait 看下cache和buffer
    2.iostat 确认一下写入磁盘使用率 读写速度 队列长度
    3.pidstat -d 看一下是哪个进程IO比较频繁
    4.lsof -p 看下这个进程打开了哪些文件或者socket