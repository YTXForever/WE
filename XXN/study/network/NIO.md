# NIO

## 1.NIO优点

a)客户发起的连接操作是异步的，可以通过多路复用器Selector注册OP_CONNECT,OP_READ,OP_WRITE,OP_ACCEPT等事件。BIO是阻塞拿到以上后续结果

b)SocketChannel读写可以异步，如果没有可读写的数据，会直接返回。这样IO通信线程可以处理其他链路，无需同步等待该链路可用

c)一个selector线程可以处理更多的客户端链接

2

