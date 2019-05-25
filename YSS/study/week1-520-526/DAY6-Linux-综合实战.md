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