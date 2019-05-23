怎么查看系统的上下文切换情况？

vmstat

### 留言1
过多上下文切换会缩短进程运行时间
vmstat 1 1：分析内存使用情况、cpu上下文切换和中断的次数。cs每秒上下文切换的次数，in每秒中断的次数，r运行或等待cpu的进程数，b中断睡眠状态的进程数。
pidstat -w 5：查看每个进程详细情况。cswch（每秒自愿）上下文切换次数，如系统资源不足导致，nvcswch每秒非自愿上下文切换次数，如cpu时间片用完或高优先级线程
案例分析：
sysbench：多线程的基准测试工具，模拟context switch
终端1：sysbench --threads=10 --max-time=300 threads run
终端2：vmstat 1：sys列占用84%说明主要被内核占用，ur占用16%；r就绪队列8；in中断处理1w，cs切换139w==>等待进程过多，频繁上下文切换，内核cpu占用率升高
终端3：pidstat -w -u 1：sysbench的cpu占用100%（-wt发现子线程切换过多），其他进程导致上下文切换
watch -d cat /proc/interupts ：查看另一个指标中断次数，在/proc/interupts中读取，发现重调度中断res变化速度最快
总结：cswch过多说明资源IO问题，nvcswch过多说明调度争抢cpu过多，中断次数变多说明cpu被中断程序调用



### 留言2
案例分析 ：

登录到服务器，现在系统负载怎么样 。 高的话有三种情况，首先是cpu使用率 ，其次是io使用率 ，之后就是两者都高 。 

cpu 使用率高，可能确实是使用率高， 也的可能实际处理不高而是进程太多切换上下文频繁 ， 也可能是进程内线程的上下文切换频繁

io 使用率高 ， 说明 io 请求比较大， 可能是 文件io 、 网络io 。 

工具 ：
系统负载 ： uptime （ watch -d uptime）看三个阶段平均负载
系统整体情况 ： mpstat （mpstat -p ALL 3） 查看 每个cpu当前的整体状况，可以重点看用户态、内核态、以及io等待三个参数
系统整体的平均上下文切换情况 ： vmstat (vmstat 3) 可以重点看 r （进行或等待进行的进程）、b （不可中断进程/io进程） 、in （中断次数） 、cs（上下文切换次数） 
查看详细的上下文切换情况 ： pidstat （pidstat -w(进程切换指标)/-u（cpu使用指标）/-wt(线程上下文切换指标)） 注意看是自愿上下文切换、还是被动上下文切换
io使用情况 ： iostat

模拟场景工具 ：
stress ： 模拟进程 、 io
sysbench ： 模拟线程数