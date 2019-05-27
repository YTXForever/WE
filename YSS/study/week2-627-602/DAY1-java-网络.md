## 每日算法训练
    1.有序数组
    2.动态数组
    3.归并两个有序的数组
    4.数组实现循环队列
## 网络
 ### TCP/IP 模型分层
    1.应用层 代表协议 Http/telnet/ftp 主要用来处理完成业务处理
    2.传输层 代表协议 Tcp/Udp         主要用来控制网络包的发送，重传，确保连接的可靠性
    3.网络层 代表协议  Ip             主要用来寻址，网络包的路由
    4.链路层 代表协议  ARP            主要在物理层面进行数据包的传输 
### TCP三次握手
    1.描述一下三次握手的过程
        1.c向s发送syn c处于syn_sent
        2.s向c发出syn+ack s处于syn_recv
        3.c向s发出ack 此时双方都处于established
    2.为什么要进行三次握手
        因为在正式发送数据之前要确定两边的seq的值 这也导致了syn要消耗一个seq的值
        为什么不是2次 两次不能保证s确定c知道了它的seq
        为什么不是4次 三次就能保证双方都收到了对方的seq了，四次没必要
    3.如何应对syn flood攻击
        syn_flood就是利用大量的syn包，导致s端出现大量的syn_recv socket 占用大量的系统资源
        设置tcp_syn_cookies后，当listen队列满了之后（ss -nltp查看) 系统便不会在新建socket syn_recv状态
        而是将上一个syn,ack和时间戳信息计算出来一个seq发送，如果是攻击方不会回复，如果是正常请求，则会回复这个seq，s端或根据这个ack反向计算出是否是满足条件的请求，从而建立连接 
    4.建立连接后client出现问题怎么办
        可以自己采用探活机制，发送心跳包，当超一定时间没有心跳的时候，则移除established队列
    5.Tcp的标志位
        URG 紧急数据位  seq加上URG就是紧急数据的第一个字节offset
        SYN 建立连接    
        ACK 回复
        RST 重置连接 要求重新建立连接
        PSH 要求另一方尽快读缓冲区数据
        FIN 断开连接
    6.tcp的头
    |src port(2 bytes)|dst port(2 bytes)|seq(4 bytes)|ack(4 bytes)|header length * 4bytes(4 bit)|reserve(6 bit)|state(6 bit)|win length(2 bytes)|crc head+data(2 bytes)|urg(2 bytes)|opt(less than 40bytes)
    7.seq和ack的增长规律
        seq是根据自己发送的数据量增长的 SYN FIN无数据但是也会消耗一个seq
        ack是根据对方的seq和len计算的 seq+len 也就是对方的下一个seq