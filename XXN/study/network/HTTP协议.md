# HTTP协议

## 1.HTTP特点

支持客服端/server，无连接，无状态

http1.1支持长连接



## 2.URL在浏览器输入后，点击回车，经历流程

DNS解析，解析域名对应ip(浏览器缓存，系统缓存，路由器缓存，IPS服务器缓存，域名服务器缓存，顶级域名服务器缓存)

建立服务器建立TCP链接，发送HTTP请求，浏览器接受HTTP响应头，浏览器释放TCP链接

## 3.post/get区别

get:请求长度有限制

报文位置不一样

## 4.cookie、session区别

   由于HTTP无状态的，

**cookie**： 客户端存储，每次向服务器请求带上这些信息。服务端返回的数据存储在服务端。浏览器会将这些文本信息存储在统一位置。cookie有两种生命周期，一种是存活在用户一个会话期，浏览器关闭后，cookie自动清除，另一种是保存在内存中。cookie不可跨域。maxAge设置了cookie的存货时间(s)。如果maxAge为正数，写入cookie对应文件中，在maxAge秒之前，该cookie均有效。maxAge<0说明该cookie仅在本窗口及子窗口有效(不会持久化)。cookie信息保存至浏览器内存中，关闭即失效。

cookie：单站点大小不超过3k

**session**：服务端存储。解析客户端请求中的sessionid，如果包含sessionId，按需保存信息。如果服务端没有保存sessionId，则生成sessionId

session超时时间：maxInactiveInterval。如果超过了超时时间，没有访问过服务器，服务器会删除session信息

session实现方式：服务端返回cookie中有JSESSION

url实现方式，通过url回写实现

session比cookie安全，

## 5.SSL (Security Socket Layer)安全套阶层

通过身份认证、数据加密保证传输安全可靠性

HTTPS传输方式:
浏览器将加密算法发送给服务器，服务器根据浏览器支持的加密算法，将验证身份信息以证书信息(CA)回发浏览器

验证证书是否守信，web生成密码，使用CA证书的公钥进行加密，使用约定好哈希算法将数据加密发送至服务器

服务器使用私钥对密码进行解密，验证哈希，加密数据回传浏览器

浏览器解密，消息验真，加密与服务器互通信息

http与https区别:http明文传输，https密文传输

