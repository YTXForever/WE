# 网络基础

#网络的分层 
    应用层 传输层 网络层 网路接口层

# 收包的流程
    1.网卡通过将数据包收到收包队列中
    2.网卡发出一个硬中断通知中断程序，将数据包从收包队列中拷贝到sk_buffer中
    3.然后中断中断程序发出一个软中断，告诉内核有新的网络包到达
    4.内核就此网络包拷贝到socket

# 发包的流程
    1.应用程序将数据包放入socket中
    2.网络协议栈从socket中取出数据包并且逐层进行封装和分片
    3.分片后的数据包会放入网卡的发包队列中，会有软中断通知网卡驱动程序需要发包

## MTU
    Max Transfer Unit : 最大传输单元：协议规定网络层数据包的大小为1500字节，超过这个大小就会在网络层分片

## 网络性能的指标
    带宽 BandWidth ：表示网络的最大的收发能力 bit/s
    吞吐量 ： 网络目前的收发数据量 bit/s  网络使用率=吞吐量/带宽
    延时：表示一个数据白从发送到收到响应的所花费的时间
    PPS: packet per second 每秒包的数量

## 查看网络状态
    ifconfig
    网卡                     已经连接到路由器或者交换机     ip最大传输单元1500
    eth0: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500
            ip地址           子网掩码 ip&netmask=网关地址       广播地址
        inet 172.17.207.53  netmask 255.255.240.0  broadcast 172.17.207.255
        Mac地址
        ether 00:16:3e:30:8c:8f  txqueuelen 1000  (Ethernet)
        收到的数据
        RX packets 413120  bytes 593537965 (593.5 MB)
        校验错误的数据包    到达rb但是内存不足丢弃    速率过快rb内丢弃
        RX errors 0  dropped 0              overruns 0  frame 0
        发送的数据
        TX packets 190540  bytes 39124964 (39.1 MB)
                                            发生carrier错误的数据包，比如双工模式不匹配    发生碰撞的数据包
        TX errors 0  dropped 0 overruns 0  carrier 0                                    collisions 0

    lo: flags=73<UP,LOOPBACK,RUNNING>  mtu 65536
        inet 127.0.0.1  netmask 255.0.0.0
        loop  txqueuelen 1000  (Local Loopback)
        RX packets 4831  bytes 14703811 (14.7 MB)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 4831  bytes 14703811 (14.7 MB)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0

    ip addr
                                    已经连接到路由器或者交换机
    2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc fq_codel state UP group default qlen 1000
    link/ether 00:16:3e:30:8c:8f brd ff:ff:ff:ff:ff:ff
    inet 172.17.207.53/20 brd 172.17.207.255 scope global dynamic eth0
       valid_lft 315319103sec preferred_lft 315319103sec
    
## netstat & ss
    Proto Recv-Q Send-Q Local Address           Foreign Address         State       PID/Program name
    tcp        0      0 127.0.0.53:53           0.0.0.0:*               LISTEN      334/systemd-resolve
    tcp        0      0 0.0.0.0:22              0.0.0.0:*               LISTEN      741/sshd

    对于netstat 来说 rev表示收包缓冲区还没有取走的数据 sendv表示发包缓冲区还没有取走的数据
    对于ss来说 在established状态下和netstat是一致的 但是在listen状态下 表示的是 synbacklog的当前数量 和 最大数据
    synbacklog 就是半连接的数量 即没没收到客户机的ack

## 网络吞吐量和PPS
    sar -n DEV 
                    网卡   接受pps      发送pps  接受数据量   发送数据量  接受的压缩的数据 发送压缩        (rxkB/s+stxkB/s)/Bandwidth
    07:56:58 AM     IFACE   rxpck/s   txpck/s    rxkB/s    txkB/s       rxcmp/s   txcmp/s  rxmcst/s   %ifutil
    07:56:59 AM        lo      0.00      0.00      0.00      0.00       0.00      0.00      0.00      0.00
    07:56:59 AM      eth0      2.00      1.00      0.12      0.17       0.00      0.00      0.00      0.00

## what's ping
    ping是基于ICMP协议 此协议位于网络层（ip） 用来测试网络的连通性和延时
    PING 114.114.114.114 (114.114.114.114) 56(84) bytes of data.
                                     序号     剩余跳数
    64 bytes from 114.114.114.114: icmp_seq=1 ttl=82 time=29.9 ms
    64 bytes from 114.114.114.114: icmp_seq=2 ttl=82 time=29.9 ms
    64 bytes from 114.114.114.114: icmp_seq=3 ttl=90 time=29.9 ms
    64 bytes from 114.114.114.114: icmp_seq=4 ttl=92 time=29.9 ms
    64 bytes from 114.114.114.114: icmp_seq=5 ttl=89 time=29.9 ms

    --- 114.114.114.114 ping statistics ---
                                        丢包比例        总耗时
    5 packets transmitted, 5 received, 0% packet loss, time 4004ms
       Round Trip time 每个包的往返时间 最小 平均 最大
    rtt min/avg/max/mdev = 29.919/29.931/29.961/0.190 ms

## select poll epoll
    select 将文件描述符放入一个数组当中，select会遍历这个数组并且返回ready的数目 内核会修改fd的状态，所以执行select的时候 必须将fd_set从用户态拷贝到内核态，然后还要讲fd_set从内核态拷贝到用户态，有性能损耗，然后通过fd状态的变化，拿到最终需要操作的句柄O(n)
    poll 模式与select相同 但是没有fd_set的句柄数限制O(n)
    epoll event poll 采用事件注册机制 先要把关注的事件注册到epoll实例上 调用epoll_wait返回准备好的句柄数 并且在readyList里面取出准备好的句柄  调用注册epoll_wait时拷贝进内核保存 其余时间不拷贝 O(1)
