# 不可中断进程和僵尸进程

## 进程有哪几种状态
    R: 正在运行或者在cpu队列里面就绪
    D: Disk Interrupt 不可中断状态，一般进程正在与硬件进行交互，为保证进程内数据和意见数据一致，在此状态下，进程不能被中断
    S: Sleep 被系统挂起，此状态可以中断，进入就绪状态
    Z: Zombie僵尸进程 就是此进程已经结束了 但是父进程还没有回收相关资源 比如进程描述符，pid
    I: Idle状态 表示内核线程的不可中断状态 区别于D D会使负载升高，但是I不会使负载升高 
    T: Stopped Traced 状态 暂停状态 当进程接收到SIGSTOP信号时进入T，接收到SIGCONT时进入R状态，这就是断点调试的原理
    X:Dead 进程已经结束 所以不会在top或者ps中看到

## 很多D怎么办 
    1.dstat 看下io情况，D很多一般都是io问题导致的，确认下是不是io问题
    2.pidstat -d 看下进程io情况 
    3.如果是java进程的话 jstack看下栈信息，如果不是java进程perf record 看下调用信息，定位异常函数

## 很多Z怎么办
    1.pstree看下Z状态进程的父进程 直接看父进程代码fork的位置，看下有没有调用wait或者waitpid函数 ，或者注册SIGCHLD（子进程结束）回调

