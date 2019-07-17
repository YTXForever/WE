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

#### 3.3.3当前节点为ForwardingNode

如果当前节点为ForwardingNode，说明其他线程将该桶已经resize完毕。那么该线程加入到transfer()方法，帮助扩容；直到扩容成功或者检查所有的桶没有待resize时，将该节点加到newtab数组中；在newtab中按照上述步骤增加节点

#### 3.3.4节点添加成功后

判断当前链表数量>=8；且tab的长度>64时将链表转为红黑树，将root节点存入table[index]中；当tab的长度为<64，且链表长度>=8，会扩容。缩短链表长度

### 3.4正在扩容

当table[index]所对应的node节点hash=-1，说明正在扩容。那么该线程加入了帮助扩容的计划中。该节点类型是ForwardingNode<>

### 3.4重新计算map的size

3.4.1size>=扩容阈值，进行扩容



## 4.扩容

**核心代码**：扩容重点

stride：边界(?做什么用的)

transferIndex：初始定义为tab(原来table的大小)

扩容操作：是从原tab[]最后一个桶开始resize

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
            /**--i>=bount  相当于将整个tab数组从最后一个桶到第一个桶的顺序进行resize*/
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
        /**进入if语句的条件：
        *i<0 说明tab中所有的桶已经完成了resize操作
        */
        if (i < 0 || i >= n || i + n >= nextn) {
            int sc;
            /**结束扩容，sizeCtl设置为那一次扩容触发的容量   0.75*新容量大小*/
            if (finishing) {
                /**当最后一个线程重新检查了所有的桶以后，resize完成，重置sizeCtl*/
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
                /*如果当前线程不是最后一个线程，那么return*/
                if ((sc - 2) != resizeStamp(n) << RESIZE_STAMP_SHIFT)
                    return;
                /**这段最变态，会留下最后一个线程，检查原先tab所有的桶，如果真的是全部完成了，才算真正的resize结束*/
                finishing = advance = true;
                i = n; // 
            }
        }
        /**判断如果tab[index]节点为null，那么将ForwardingNode置为该位置，标志该桶扩容完毕*/
        else if ((f = tabAt(tab, i)) == null)
            advance = casTabAt(tab, i, null, fwd);
        /**说明该桶，其他线程已经完成扩容操作*/
        else if ((fh = f.hash) == MOVED)
            advance = true; //
        else {
            synchronized (f) {
                /**重新判断tab[index]是否是原先的那个节点；如果不是，说明其他的线程已经将这个桶扩容完毕*/
                /**重组链表，将链表放置在nextTab[index],nextTab[index+n]中。nextTab[index]创建完毕以后，将原来的tab[index]=ForwardingNode节点，说明这个桶已经resize成功*/
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

## 5.get方法

## 6.computeIfAbsent

### 6.1用法

```java
public static void main(String[] args) {
		ConcurrentHashMap< String,  Object> map = new ConcurrentHashMap<>();
		map.computeIfAbsent("123", K->{return new Student("zhangsan");});

}
```

### 6.2源码解析

当tab[index]中的头结点是ReservationNode(hash:-3)；说明这个桶正在进行function.apply()运算。ReservationNode节点是一个hash=-3，key=null,value=null,next=null的节点，作用：标识(我是中间结果，生命周期极短)

当function执行结束后，会将tab[index]=new Node<>(hash,key,value,null)

```java
public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
    if (key == null || mappingFunction == null)
        throw new NullPointerException();
    int h = spread(key.hashCode());
    V val = null;
    int binCount = 0;
    for (Node<K,V>[] tab = table;;) {
        Node<K,V> f; int n, i, fh;
        if (tab == null || (n = tab.length) == 0)
            tab = initTable();
        else if ((f = tabAt(tab, i = (n - 1) & h)) == null) {
            Node<K,V> r = new ReservationNode<K,V>();
            /**这个锁加的一点用处都没有，是个废锁*/
            synchronized (r) {
                if (casTabAt(tab, i, null, r)) {
                    binCount = 1;
                    Node<K,V> node = null;
                    try {
                        /**在这个步骤里面，非常容易出现嵌套插入。一旦嵌套插入，且嵌套插入的key所在桶是该桶，那么无法跳出循环*/
                        if ((val = mappingFunction.apply(key)) != null)
                            node = new Node<K,V>(h, key, val, null);
                    } finally {
                        setTabAt(tab, i, node);
                    }
                }
            }
            if (binCount != 0)
                break;
        }
        else if ((fh = f.hash) == MOVED)
            tab = helpTransfer(tab, f);
        else {
            boolean added = false;
            synchronized (f) {
                if (tabAt(tab, i) == f) {
                    if (fh >= 0) {
                        binCount = 1;
                        for (Node<K,V> e = f;; ++binCount) {
                            K ek; V ev;
                            if (e.hash == h &&
                                ((ek = e.key) == key ||
                                 (ek != null && key.equals(ek)))) {
                                val = e.val;
                                break;
                            }
                            Node<K,V> pred = e;
                            if ((e = e.next) == null) {
                                if ((val = mappingFunction.apply(key)) != null) {
                                    added = true;
                                    pred.next = new Node<K,V>(h, key, val, null);
                                }
                                break;
                            }
                        }
                    }
                    else if (f instanceof TreeBin) {
                        binCount = 2;
                        TreeBin<K,V> t = (TreeBin<K,V>)f;
                        TreeNode<K,V> r, p;
                        if ((r = t.root) != null &&
                            (p = r.findTreeNode(h, key, null)) != null)
                            val = p.val;
                        else if ((val = mappingFunction.apply(key)) != null) {
                            added = true;
                            t.putTreeVal(h, key, val);
                        }
                    }
                }
            }
            if (binCount != 0) {
                if (binCount >= TREEIFY_THRESHOLD)
                    treeifyBin(tab, i);
                if (!added)
                    return val;
                break;
            }
        }
    }
    if (val != null)
        addCount(1L, binCount);
    return val;
}
```
### 6.3computeIfAbsent方法死循环

map.computeIfAbsent("AaAa", key -> map.computeIfAbsent("BBBB", key2 -> "value"));

在jdk1.8中，如果当hash(ke)分配的桶是同一个桶；然后是嵌套插入，会造成死循环，CPU使用率飙升。

死循环出现的情况：如果被分配的桶内没有节点，会将ReservationNode放入桶中，标识该桶正在进行function.apply方法操作；此时在执行嵌套方法(如上述例子中的  "BBBB",这个操作)，被分配到了同一个桶里面；那么会进入循环，不满足跳出语句的条件。CPU使用率飙升

## 7.如何保证多线程的可见性

使用volatile关键字。确保各个线程看到的都是主存中的字段，不从工作内存中获取该值。

volatile(敲黑板，重点)修饰tab  数组,nexttab  resize的时候正在扩容中的nextTab

`sizeCtl`(敲黑板，重点)：sizeCtl这个字段有多层含义：构造函数时，它是tab的容量。当initTable进行中时，为-1，标识有线程在执行初始化操作。当tab初始化完成以后，会将sizeCtl这个值设置为0.75*n。执行resize的时候，是resizeStamp(n)<<16+2;  每当有一个线程加入resize操作时，sizeCtl+1；每当一个线程退出resize操作，sizeCtl-1；sizeCtl此时为计数器，标识当前线程是否是最后一个正在进行扩容的线程。

获取tab[index]时，使用U.getObjectVolatile;设置头节点，U.compareAndSwapInt  cas方法；