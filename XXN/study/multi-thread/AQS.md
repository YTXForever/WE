# **前言**

AQS是concurrent包中的abstract类。extends该类，可以轻松自定义实现锁。ReentrantLock,Semphore,CountdownLatch,CyclicBarrier。AQS是一个框架，可以提供以FIFO的等待队列模式实现独占所、共享锁逻辑。

AQS队列中**关键**Node:

​     a)Cancel=1:该线程已取消

​     b)SIGNAL=-1：该信号一处，后续阻塞线程解除阻塞，可以启动

​     c)CONDITION=-2：当前线程等待

​     d)PROPAGATE=-3 与共享模式相关,当线程以共享模式去获取或释放锁时,对后续线程的释放动作需要不断往后传播

## **一、ReentrantLock**

ReentrantLock有两个构造模式；一个是notFair，另一个是Fair。ReentrantLock是独占锁

state：0代表没有线程获取到锁，每加一次锁，都会将该值+锁的次数

  a)lock()获得锁；如果锁被占用则等待

公平锁：按照等待队列中的顺序，去获取锁。for死循环，当当前node的前驱是head，且当前线程成为了owner，结束循环。

非公平锁：CAS将某个state赋值，如果赋值成功，则当前线程为owner线程，用state记录当前锁的计数；否则将他放入等待队列中。for死循环，当当前node的前驱是head，且当前线程成为了owner，结束循环。

  b)lockInterruptibly() 获得锁；再for死循环获取锁的时候，如果此时中断，则throw InterruptExcepti'on

  c)tryLock()尝试锁，如果获取锁失败，返回false

  d)unlock()：判断state-=staterelease();如果为0,将owner线程置为null。

## **二、Semaphore**源码解析

用法：Semaphore semaphore = new Semaphore(5);说明最多有5个线程可以持有锁，其他线程放入等待队列中。  semaphore.acquire();获取锁；semaphore.release()释放锁。

### 2.1.acquire()

默认获取1个许可

```
public final void acquireSharedInterruptibly(int arg)
            throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        if (tryAcquireShared(arg) < 0)
            doAcquireSharedInterruptibly(arg);
    }
```

如果没有足够的许可，会将该线程放入等待队列中。

```java

    private void doAcquireSharedInterruptibly(int arg)   throws InterruptedException {
        //生成一个共享锁的Node节点，插入队列尾部。(head是个空节点，head节点特性：thread=null，head->node，Node里面属性有锁的类型、状态state初始值0，当前线程)
        final Node node = addWaiter(Node.SHARED);  
        boolean failed = true;
        try {
            for (;;) {
                //获取该节点的先驱
                final Node p = node.predecessor();
                if (p == head) {
                     //如果该节点已经排到了队列第一的位置，尝试获取锁
                    int r = tryAcquireShared(arg);
                    if (r >= 0) {
                        //如果获取成功，设置该节点为head节点
                        setHeadAndPropagate(node, r);
                        p.next = null; // help GC
                        failed = false;
                        return;
                    }
                }
                //获取锁失败，检查是否可以被block;如果可以被阻塞，则将node节点对应的线程阻塞(前一个节点状态是SIGNAL，后一个节点的线程是阻塞的)
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())
                    throw new InterruptedException();
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }
```



```java
private void doReleaseShared() {
        /*
         * Ensure that a release propagates, even if there are other
         * in-progress acquires/releases.  This proceeds in the usual
         * way of trying to unparkSuccessor of head if it needs
         * signal. But if it does not, status is set to PROPAGATE to
         * ensure that upon release, propagation continues.
         * Additionally, we must loop in case a new node is added
         * while we are doing this. Also, unlike other uses of
         * unparkSuccessor, we need to know if CAS to reset status
         * fails, if so rechecking.
         */
        for (;;) {
            Node h = head;
            if (h != null && h != tail) {
                int ws = h.waitStatus;
                if (ws == Node.SIGNAL) {
                    //如果该头节点状态是signal；那么会将state以CAS方式置为0
                    if (!compareAndSetWaitStatus(h, Node.SIGNAL, 0))
                        continue;            // loop to recheck cases
                    //找到h的后继节点之一，非取消，唤醒该线程
                    unparkSuccessor(h);
                }
                //如果节点状态是初始状态，则置为PROPAGATE
                else if (ws == 0 &&
                         !compareAndSetWaitStatus(h, 0, Node.PROPAGATE))
                    continue;                // loop on failed CAS
            }
            if (h == head)                   // loop if head changed
                break;
        }
    }

//获取锁失败,判断自己是否可以被阻塞，当节点state为SIGNAL可以，如果ws>0,那么需要找到前一个不是取消的node节点|将该节点状态置为SIGNAL，return false;
 private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
        int ws = pred.waitStatus;
        if (ws == Node.SIGNAL)
            /*
             * This node has already set status asking a release
             * to signal it, so it can safely park.
             */
            return true;
        if (ws > 0) {
            /*
             * Predecessor was cancelled. Skip over predecessors and
             * indicate retry.
             */
            do {
                node.prev = pred = pred.prev;
            } while (pred.waitStatus > 0);
            pred.next = node;
        } else {
            /*
             * waitStatus must be 0 or PROPAGATE.  Indicate that we
             * need a signal, but don't park yet.  Caller will need to
             * retry to make sure it cannot acquire before parking.
             */
            compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
        }
        return false;
    }
```

总结人话：

当请求获取permit，发现现有的permit并不满足需要，于是会为该线程创建一个Node节点(thread),放入等待队列中；轮询查看是否该节点的前驱是head节点(此刻说明，终于要排队排到了),如果此时有足够的许可，那么会将该节点置为head(将thread置为null)。如果head节点是signal，那么需要唤醒他下一个节点；如果不是，head状态是初始，则置为PROPAGATE(这个状态是干嘛的，不清楚)。如果许可不够，或者自己的前驱不是head，那么如果当前节点状态是0，那么置为propgate；如果是signal，则阻塞他下一个节点的线程。

例如：当前队列中有3个线程等待；如果第一个线程hold 3个permit，第二个hold 2个permit；可是当前线程permit只有1个，那么第一个线程会一直阻塞到拿到3个permit，才会将第二个线程唤醒。

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