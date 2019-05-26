**前言**

AQS是concurrent包中的abstract类。extends该类，可以轻松自定义实现锁。ReentrantLock,Semphore,CountdownLatch,CyclicBarrier。AQS是一个框架，可以提供以FIFO的等待队列模式实现独占所、共享锁逻辑。

AQS队列中**关键**Node:

​     a)Cancel=1:该线程已取消

​     b)SIGNAL=-1：该信号一处，后续阻塞线程解除阻塞，可以启动

​     c)CONDITION=-2：当前线程等待

​     d)PROPAGATE=-3后续共享锁节点无条件传播

**一、ReentrantLock**

ReentrantLock有两个构造模式；一个是notFair，另一个是Fair。ReentrantLock是独占锁

state：0代表没有线程获取到锁，每加一次锁，都会将该值+锁的次数

  a)lock()获得锁；如果锁被占用则等待

公平锁：按照等待队列中的顺序，去获取锁。for死循环，当当前node的前驱是head，且当前线程成为了owner，结束循环。

非公平锁：CAS将某个state赋值，如果赋值成功，则当前线程为owner线程，用state记录当前锁的计数；否则将他放入等待队列中。for死循环，当当前node的前驱是head，且当前线程成为了owner，结束循环。

  b)lockInterruptibly() 获得锁；再for死循环获取锁的时候，如果此时中断，则throw InterruptExcepti'on

  c)tryLock()尝试锁，如果获取锁失败，返回false

  d)unlock()：判断state-=staterelease();如果为0,将owner线程置为null。

**二、Semaphore**

1.用法：Semaphore semaphore = new Semaphore(5);说明最多有5个线程可以持有锁，其他线程放入等待队列中。  semaphore.acquire();获取锁；semaphore.release()释放锁。

1.acquire()

2.acquireUninterruptibly

3.tryAcquire

4.release

**三、ReentrantReadWriteLock读写锁**

 1.读写分离锁。多个线程同时读，不加锁。如果有一个线程进行写操作，那么需要持有锁。

​    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

​    readWriteLock.readLock();

​    readWriteLock.writeLock();

源码解析：同样继承了AQS；readLock实现方式，共享锁；writeLock独占锁，放等待队列；

**四、CountDownLatch计数器**

CountDownLatch countDownLatch = new CountDownLatch(10);设置计数器

countDownLatch.countDown();每个线程执行的时候，计数器-1

countDownLatch.await();等待所有线程执行完毕，继续处理下面主流程

**五、循环栅栏：CyclicBarrier（不是AQS实现）**

CyclicBarrier cyclic = new CyclicBarrier(N, new BarrierRun(flag, N));

实现机制：

实例化：可带runnable

await实现原理：

获取reentrantlock锁，判断count，如果count为0，那么new generation ,且执行runnable；然后重新将count恢复原位；如果不为0，condition.await();如果当前线程被interrupted了，那么当前线程throw interruptException,并且将generation的isBroken置为true,其他线程throw BarrierBrokenException;

**六、LockSupport**

线程阻塞工具，不需要获取到对象锁，也不会抛InterruptedException。