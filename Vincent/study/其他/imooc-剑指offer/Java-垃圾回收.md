# 垃圾回收

## 新生代中都有哪些垃圾回收器？
- Serial ==> 单线程，复制算法，暂停其他用户线程，Client的默认机制
- ParNew ==> 多线程，复制算法，暂停其他用户线程
- Parallel Scavenge ==> 多线程，复制算法，暂停其他用户线程，Server的默认机制，但是他是高吞吐量机制，要求在固定时间内完成GC，如果完成不了，就结束GC，等待下次GC。

## 老年代中都有哪些垃圾回收器？
- Serial Old ==> 单线程，标记-整理算法，暂停其他用户线程
- ParNew Old ==> 多线程，标记-整理算法，无暂停其他用户线程
- Concurrent Mark Sweep ==> 多线程，标记-清除算法，部分步骤暂停其他用户线程，  
    **执行步骤**  
    初次标记：标记直接与GCRoot关联的对象  
    并发标记：寻址标记  
    再次标记：修正并发标记时的数据  
    并发清除：清除不可达对象  
- Garbage-First