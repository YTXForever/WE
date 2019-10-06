# 多线程与并发
## 进程和线程

## java定时任务实现的几种方式

http://www.360doc.com/content/18/0529/08/36490684_757862975.shtml

1. 创建一个thread，然后让它在while循环里一直运行着，通过sleep方法来达到定时任务的效果；
2. Timer和TimerTask
    - 简单
    - 基于绝对时间
    - 不会捕获异常，遇到异常将导致整个线程终止
    - 单线程
3. ScheduledExecutorService：其实现为ScheduledThreadPoolExecutor
    - 基于线程池
    - 基于相对时间
    - 只有在任务时间到来时才会占用线程
    - 不同任务间是并发当，互不干扰
4. Quartz
    - Scheduler:调度器，所有调度都有它控制
    - Trigger：定义触发都条件，包括SimpleTrigger和CronTrigger
    - Job & JobDetail
## Spring 定时任务
- ScheduledTimerTask:继承TimerTask
-  Spring-Task：Spring自带的定时任务工具，spring task，可以将它比作一个轻量级的Quartz







