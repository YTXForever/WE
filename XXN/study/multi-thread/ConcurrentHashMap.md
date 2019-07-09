# ConcurrentHashMap



## 1.构造函数

new ConcurrentHashMap(int size)

该构造函数，找到一个距离size最近的一个2的n次幂(>size)的数组大小。如果size=15,那么她会生成一个容量16的数组。如果size=16,那么会生成一个容量32的数组。构造方法，主要为了计算数组的大小

初始化sizeCtl字段，为数组的大小。

## 2.Node节点

链表节点。有几个属性：hash，key，val，next指针

## 3.put方法

### 3.1初始化table

判断当前数组table是否为空，如果是空，初始化table；将sizeCtl字段为-1的时候，说明有其他线程正在执行初始化操作，那么当前线程需要yield，让出CPU控制权，等待其他线程初始化完毕；

初始化table：new一个数组(Node<K,V>[] nt)，大小为构造函数计算的大小。

sc = n - (n >>> 2);   计算下一次扩容的数组大小，将值赋给sizeCtl。（假设n=16，那么sc=12即当数组大小到达sc，需要扩容）



### 3.2 table[index]=null情况

计算key值应该放入的index值：(n-1)&hash   由于n永远是2的整数次幂，所以(n-1)永远都是111...这种数值。即标识着hash值的低哪几位有效，起决定性作用，分配到哪个index中。

如果数组中table[index]==null；那么需要创建一个node节点，放到table[index]中，即table[index] = new Node();   该步骤通过unsafe的CAS方法实现；如果放置成功，则lenth+1;如果放置失败；

那么继续for循环，将该值放入合适的位置。

### 3.3 table[index]!=null的情况

加锁，锁住table[index]上的node节点，目的防止其他线程对该节点进行增改删操作。

synchronized以后，再次检查当前node节点跟锁之前的index对应节点是否是同一个；如果不是；那么继续循环。

#### 3.3.1链表Node

如果是同一个节点，遍历node链表 (node->next)，比较key值，如果key值相等，根据ifputabsent这个字段，来决定是否将这个节点的value替换为put的val值；如果node链表并没有对应的key值，那么new Node()，将这个节点放到链表的尾部

#### 3.3.2红黑树TreeNode

如果table[index] 是一个TreeNode；那么获取key在树里面是否已存在节点；如果存在，那么据ifputabsent这个字段，来决定是否将这个节点的value替换为put的val值。

如果不存在，那么将这个key变成TreeNode，放入红黑树中。

#### 3.3.3节点添加成功后

判断当前链表数量>=8；如果是，将链表转为红黑树，将root节点存入table[index]中

### 3.4正在扩容

当table[index]所对应的node节点hash=-1，说明正在扩容。那么该线程加入了帮助扩容的计划中。该节点类型是ForwardingNode<>

### 3.4重新计算map的size

3.4.1size>=扩容阈值，进行扩容



## 4.扩容

**核心代码**：扩容重点

stride：边界(?做什么用的)

transferIndex：初始定义为tab(原来table的大小)

```java
private final void transfer(Node<K,V>[] tab, Node<K,V>[] nextTab) {
    int n = tab.length, stride;
    /**根据CPU的核数，选择合适的边界。最低是16个桶*/
    if ((stride = (NCPU > 1) ? (n >>> 3) / NCPU : n) < MIN_TRANSFER_STRIDE)
        stride = MIN_TRANSFER_STRIDE; // subdivide range
    /**下面是初始化一个新的nextTab,容量是原来tab的2倍*/
    if (nextTab == null) {            
        try {
            @SuppressWarnings("unchecked")
            Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n << 1];
            nextTab = nt;
        } catch (Throwable ex) {      // try to cope with OOME
            sizeCtl = Integer.MAX_VALUE;
            return;
        }
        nextTable = nextTab;
        transferIndex = n;
    }
    int nextn = nextTab.length;
    ForwardingNode<K,V> fwd = new ForwardingNode<K,V>(nextTab);
    boolean advance = true;
    boolean finishing = false; // to ensure sweep before committing nextTab
    for (int i = 0, bound = 0;;) {
        Node<K,V> f; int fh;
        while (advance) {
            int nextIndex, nextBound;
            if (--i >= bound || finishing)
                advance = false;
            else if ((nextIndex = transferIndex) <= 0) {
                i = -1;
                advance = false;
            }
            else if (U.compareAndSwapInt
                     (this, TRANSFERINDEX, nextIndex,
                      nextBound = (nextIndex > stride ?
                                   nextIndex - stride : 0))) {
                bound = nextBound;   //bound=0，index=1111，
                i = nextIndex - 1;
                advance = false;
            }
        }
        if (i < 0 || i >= n || i + n >= nextn) {
            int sc;
            if (finishing) {
                nextTable = null;
                table = nextTab;
                sizeCtl = (n << 1) - (n >>> 1);
                return;
            }
            /**
                第一个扩容的线程，执行transfer方法之前，会设置 sizeCtl = (resizeStamp(n) << RESIZE_STAMP_SHIFT) + 2)
                后续帮其扩容的线程，执行transfer方法之前，会设置 sizeCtl = sizeCtl+1
                每一个退出transfer的方法的线程，退出之前，会设置 sizeCtl = sizeCtl-1
                那么最后一个线程退出时：
                必然有sc == (resizeStamp(n) << RESIZE_STAMP_SHIFT) + 2)，即 (sc - 2) == resizeStamp(n) << RESIZE_STAMP_SHIFT
            */
            if (U.compareAndSwapInt(this, SIZECTL, sc = sizeCtl, sc - 1)) {
                /**说明有其他线程正在执行扩容操作，那么退出扩容操作*/
                if ((sc - 2) != resizeStamp(n) << RESIZE_STAMP_SHIFT)
                    return;
                finishing = advance = true;
                i = n; // recheck before commit
            }
        }
        else if ((f = tabAt(tab, i)) == null)
            advance = casTabAt(tab, i, null, fwd);
        else if ((fh = f.hash) == MOVED)
            advance = true; // already processed
        else {
            synchronized (f) {
                if (tabAt(tab, i) == f) {
                    Node<K,V> ln, hn;
                    if (fh >= 0) {
                        int runBit = fh & n;
                        Node<K,V> lastRun = f;
                        for (Node<K,V> p = f.next; p != null; p = p.next) {
                            int b = p.hash & n;
                            if (b != runBit) {
                                runBit = b;
                                lastRun = p;
                            }
                        }
                        if (runBit == 0) {
                            ln = lastRun;
                            hn = null;
                        }
                        else {
                            hn = lastRun;
                            ln = null;
                        }
                        for (Node<K,V> p = f; p != lastRun; p = p.next) {
                            int ph = p.hash; K pk = p.key; V pv = p.val;
                            if ((ph & n) == 0)
                                ln = new Node<K,V>(ph, pk, pv, ln);
                            else
                                hn = new Node<K,V>(ph, pk, pv, hn);
                        }
                        setTabAt(nextTab, i, ln);
                        setTabAt(nextTab, i + n, hn);
                        setTabAt(tab, i, fwd);
                        advance = true;
                    }
                    else if (f instanceof TreeBin) {
                        TreeBin<K,V> t = (TreeBin<K,V>)f;
                        TreeNode<K,V> lo = null, loTail = null;
                        TreeNode<K,V> hi = null, hiTail = null;
                        int lc = 0, hc = 0;
                        for (Node<K,V> e = t.first; e != null; e = e.next) {
                            int h = e.hash;
                            TreeNode<K,V> p = new TreeNode<K,V>
                                (h, e.key, e.val, null, null);
                            if ((h & n) == 0) {
                                if ((p.prev = loTail) == null)
                                    lo = p;
                                else
                                    loTail.next = p;
                                loTail = p;
                                ++lc;
                            }
                            else {
                                if ((p.prev = hiTail) == null)
                                    hi = p;
                                else
                                    hiTail.next = p;
                                hiTail = p;
                                ++hc;
                            }
                        }
                        ln = (lc <= UNTREEIFY_THRESHOLD) ? untreeify(lo) :
                            (hc != 0) ? new TreeBin<K,V>(lo) : t;
                        hn = (hc <= UNTREEIFY_THRESHOLD) ? untreeify(hi) :
                            (lc != 0) ? new TreeBin<K,V>(hi) : t;
                        setTabAt(nextTab, i, ln);
                        setTabAt(nextTab, i + n, hn);
                        setTabAt(tab, i, fwd);
                        advance = true;
                    }
                }
            }
        }
    }
}
```