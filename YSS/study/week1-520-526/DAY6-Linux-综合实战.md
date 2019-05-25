# 综合实战

## 服务器丢包问题
    1.发现网络不畅是可以使用hping3进行丢包测试，如果发现package loss的话，证明有丢包问题
    2.丢包一般会发生在如下几层
        1）网卡层 校验不通过 rb溢出等 netstat -i 查看丢弃情况
        2) iptables conntrack超限 sysctl net.netfilter.nf_conntrack_max/count filter过滤条件等 iptables -t -nvL
        3) ip 路由错误
        4) tcp 内核资源限制 socket数量等 
        5）http请求不过去直接进行抓包就ok了 客户端服务端均可 tcpdump -i eth0 -nn host xxx/tcp port xxx

## 内核线程
    1.所有的进程由pid=1的systemd启动
    2.所有内核线程由pid=2的kthreadd进程启动 所有内核线程名称[*]
    3.内核线程cpu使用很高的时候使用perf record -a -g -p查看函数调用信息，进一步定位问题

## 软中断问题
    1.查看ksoftirq线程cpu比较高 
    2.查看perf 查看调用是在收网络包
    3.sar一下确认是网络小包 
    4.tcpdump确认是syn flood攻击

## 不知道是TM啥案例 他也不按照剧本来啊
    1.连接数优化 ss -s 如果发现timewait很多 按照剧本来说就是 nf_conntrack_max导致的 调一下就行了
    2.nginx返回499 就是后端处理过造成的
    3.查看socket的溢出情况 netstat -s|grep socket
     63502335 resets received for embryonic SYN_RECV sockets
    91741 TCP sockets finished time wait in fast timer
    3 delayed acks further delayed because of locked socket
    7456 times the listen queue of a socket overflowed(溢出)
    7456 SYNs to LISTEN sockets dropped(不知道啥几把玩意 反正是不对)
    如果溢出比较多 那么查看ss -nltp 
    SO_BACKLOG指的是accpet的连接数 程序会取somaxconn与SO_BACKLOG之间较小值 ss-nltp查看
    SEND-Q是最大限制 REV-Q是当前长度
    4.查看当前可以自由分配的端口 
        sysctl net.ipv4.ip_local_port_range
        net.ipv4.ip_local_port_range=20000 20050
        可以将timewait的端口设置为可分配端口 以缓解端口资源问题
        sysctl net.ipv4.tcp_tw_reuse
        net.ipv4.tcp_tw_reuse = 0 整成1就能鸡巴用了
    5.sysctl 这堆狗屎 要是想改的话 就-w =x 就O鸡巴K了

