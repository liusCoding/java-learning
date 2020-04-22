# 多线程相关面试题

<a name="GM3eW"></a>
#### 1.为什么用多线程？
有时候，系统需要处理非常多的执行时间很短的需求，如果每一个请求都开启一个新线程的话，系统就要不断的进行线程的创建和销毁，有时花在创建和销毁线程上的时间会比线程真正执行的时间还长。<br />而且线程数量太多时，系统不一定能受得了。<br />
<br />使用线程池主要为了解决一下几个问题：<br />通过重用线程池中的线程，来减少每个线程创建和销毁的性能开销。<br />对线程进行一些维护和管理，比如定时开始，周期执行，并发数控制。<br />

<a name="lkQ1Q"></a>
#### 2.线程池参数什么意思？
比如去火车站买票，有10个售票窗口，但只有5个窗口对外开放，那么对外开放的5个窗口称为**核心线程数。**<br />而**最大线程数**是10个窗口。如果5个窗口都被占用，那么后来的人就必须在后面排队，但后来售票厅越来越多，已经人满为患，就类似**线程队列**已满，这时候火车站站长下令，把剩下的5个窗口也打开，也就是目前有10个窗口同时运行。后来又来了一批人。10个窗口也处理不过来了，而且售票厅人已经满了，这时候站长就下令封锁入口，不允许其他人再进来，这就是**线程异常处理策略**，而**线程存活时间**指的是，允许售票员休息的最长时间，以此限制售票员偷懒的行为。

<a name="sfglr"></a>
#### 3.讲一讲线程池中的ThreadPoolExecutor,每个参数是干什么的？

<br />Executor是一个接口，跟线程池有关的基本都要跟它打交道。ThreadPoolExecutor的关系：<br />![image.png](https://cdn.nlark.com/yuque/0/2020/png/440247/1587369123354-82328f24-1110-420d-bc8e-1d202f457742.png#align=left&display=inline&height=370&margin=%5Bobject%20Object%5D&name=image.png&originHeight=370&originWidth=203&size=15121&status=done&style=none&width=203)<br />Executor接口很简单，只有一个execute方法。<br />ExecutorService是Executor的子接口，增加了一些常用的对线程的控制方法，之后使用线程池主要也是使用这些方法。<br />AbstractExecutorService是一个抽象类。ThreadPoolExecutor就是继承了这个类。<br />
<br />ThreadPoolExecutor的参数：
```java
ThreadPoolExecutor mExecutor = new ThreadPoolExecutor(corePoolSize,// 核心线程数
						maximumPoolSize, // 最大线程数
						keepAliveTime, // 闲置线程存活时间
						TimeUnit.MILLISECONDS,// 时间单位
						new LinkedBlockingDeque<Runnable>(),// 线程队列
						Executors.defaultThreadFactory(),// 线程工厂
						new AbortPolicy()// 队列已满,而且当前线程数已经超过最大线程数时的异常处理策略
				);
```
**corePoolSize:**<br />核心线程数，默认情况下核心线程会一直存活，即使处于闲置状态也不会受存keepAliveTime限制。除非allowCoreThreadTimeOut设置为true。

**maximumPoolSize：**<br />线程池所能容纳的最大线程数。超过这个数的线程将被阻塞。当任务队列为没有设置大小的LinkedBlockingDeque时，这个值无效。

**keepAliveTime:**<br />非核心线程的闲置超时时间，超过这个时间就会被回收。

**unit：**<br />指定keepAliveTime的单位，如TimeUnit.SECONDS。当allowCoreThreadTim他Out，设置为ture时对corePoolSize生效。

**woreQueue：**<br />线程池中的任务队列，常有的有三种队列：

  - SynchronousQueue
  - LinkedBlockingDeque
  - ArrayBlockingQueue


<br />
<br />**threadFactory:**<br />线程工厂，提供创建新线程的功能。ThreadFactory是一个接口，只有一个方法：
```java
public interface ThreadFactory {
  Thread newThread(Runnable r);
}
```
通过线程工厂可以对线程的一些属性进行定制。<br />
<br />默认的工厂：
```java
static class DefaultThreadFactory implements ThreadFactory {
  private static final AtomicInteger poolNumber = new AtomicInteger(1);
  private final ThreadGroup group;
  private final AtomicInteger threadNumber = new AtomicInteger(1);
  private final String namePrefix;

  DefaultThreadFactory() {
      SecurityManager var1 = System.getSecurityManager();
      this.group = var1 != null?var1.getThreadGroup():Thread.currentThread().getThreadGroup();
      this.namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
  }

  public Thread newThread(Runnable var1) {
      Thread var2 = new Thread(this.group, var1, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
      if(var2.isDaemon()) {
          var2.setDaemon(false);
      }
      if(var2.getPriority() != 5) {
          var2.setPriority(5);
      }
      return var2;
  }
}
```

<br />**RejectedExecutionHandler：**<br />RejectedExcutionHandler也是一个接口，只有一个方法：
```java
public interface RejectedExecutionHandler {
  void rejectedExecution(Runnable var1, ThreadPoolExecutor var2);
}
```
当线程池中的资源已经全部使用，添加新线程被拒绝时，会调用RejectedExcutionHandler的rejectExecution方法。<br />

<a name="sp3kp"></a>
#### 4.说下线程池内部使用规则？

<br />线程池的线程规则执行跟任务队列有很大的关系：<br />下面都假设任务队列没有大小限制：<br />
<br />如果线程数量<=核心线程数量，那么直接启动一个核心线程来执行任务，不会放入队列中。<br />
<br />如果线程数量>核心线程数量，但<=最大线程数，并且任务队列是LinkedBlockingDeque的时候，超过核心线程数量的任务会放在任务队列中排队。<br />
<br />如果线程数量>核心线程数，但<=最大线程数，并且任务队列是SynchronousQueue队列的时候，线程池会创建新的线程执行任务，这些任务也不会被放在任务队列中。这些线程属于非核心线程，在任务完成后，闲置时间达到了超时时间就会清除。<br />
<br />如果线程数量>核心线程数，并且>最大线程数，当任务队列是LinkedBlockingDeque，会将超过核心线程的任务放在任务队列中排队。也就是当任务队列是LinkedBlockingDeque并且没有大小限制的时候，线程池的最大线程数设置是无效的，它的线程数最多不会超过核心线程数。

如果线程数量>核心线程数，并且>最大线程数，当任务队列是SynchronousQueue 的时候，会因为线程池拒绝添加任务而抛出异常。

任务队列大小有限时：<br />当LinkedBlockingDeque 塞满时，新增的任务会直接创建新线程来执行，当创建的线程数量超过最大线程数量时会抛异常。<br />SynchronousQueue 没有数量限制，因为它根本不保持这些任务，而是直接交给线程池去执行。当任务数量超过最大线程数时会直接抛出异常。

<a name="FOGb8"></a>
#### 5.用过AtomicInteger吗？怎么用的？
AtomicInteger是Integer类型的原子操作类型，对于全局全量的数值类型操作num++，若没有加synchronized关键字则是线程不安全的，num++,解析为num=num+1,明显，这个操作不具备原子性，多线程操作必然会出现问题。<br />
<br />看代码：
```java
public class AtomicIntegerTest1 {
    public static  int  count = 0;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            new Thread(() ->  count ++ ).start();
        }

        TimeUnit.SECONDS.sleep(3);
        System.out.println("count:" + count);
    }
}

```
执行结果：<br />![image.png](https://cdn.nlark.com/yuque/0/2020/png/440247/1587466282250-5b472ff3-2aa7-4d8c-8d34-882f2a5f7ce4.png#align=left&display=inline&height=529&margin=%5Bobject%20Object%5D&name=image.png&originHeight=529&originWidth=1269&size=803050&status=done&style=none&width=1269)<br />
<br />**要是换成volatile修饰count变量呢？**<br />volatile修饰的变量能够在多线程间保持可见性，能被多个线程同时读但是又能保证只被单个线程写，并且不会读取到过期值（由JMM模型中的happen-before原则决定的）volatile修饰字段的写入操作总是由于读操作，即使多个线程同时修改volatile变量字段，总能获取到最新的值。<br />
<br />但是volatile 仅仅保证变量在线程间保持可见性，却依然不能保证非原子性操作。
```java

/**
 * @description: volatile 仅仅保证变量在线程间保持可见性，却依然不能保证非原子性操作。
 * @author: liushuai
 * @create: 2020-04-17 18:36
 **/

public class AtomicIntegerTest2 {
    public static volatile  int count =0;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> count++).start();
        }

        TimeUnit.SECONDS.sleep(2);
        System.out.println("volatile count:" + count);
    }
}

```
执行结果：<br />![image.png](https://cdn.nlark.com/yuque/0/2020/png/440247/1587466659712-d76edab3-3869-4146-aaa9-c6863a052f1f.png#align=left&display=inline&height=620&margin=%5Bobject%20Object%5D&name=image.png&originHeight=620&originWidth=1263&size=960407&status=done&style=none&width=1263)<br />
<br />atomicnteger常用方法：
```java
public final int getAndSet(int newValue)       //给AtomicInteger设置newValue并返回加oldValue
public final boolean compareAndSet(int expect, int update)    //如果输入的值和期望值相等就set并返回true/false
public final int getAndIncrement()     //对AtomicInteger原子的加1并返回当前自增前的value
public final int getAndDecrement()   //对AtomicInteger原子的减1并返回自减之前的的value
public final int getAndAdd(int delta)   //对AtomicInteger原子的加上delta值并返加之前的value
public final int incrementAndGet()   //对AtomicInteger原子的加1并返回加1后的值
public final int decrementAndGet()    //对AtomicInteger原子的减1并返回减1后的值
public final int addAndGet(int delta)   //给AtomicInteger原子的加上指定的delta值并返回加后的值
```


<a name="21ty0"></a>
#### 6.用过ThreadLocal吗？怎么用的？
早在JDK1.2的版本中就提供java.lang.ThreadLocal，ThreadLocal为解决多线程程序的并发问题提供了一种新的思路。使用这个工具类可以很简洁编写出优美的多线程程序。<br />
<br />ThreadLocal很容易让人望文生义，想当然的认为是一个"本地线程"。<br />
<br />其实，ThreadLocal并不是一个Thread，而是Thread的局部变量，也许把它命名为为ThreadLocalVariable更容易让人理解一些。<br />
<br />ThreadLocal为变量在每个线程中都创建了一个副本，那个每个线程可以访问自己内部的局部变量。<br />
<br />ThreadLocal是一个本地线程副本变量工具类。主要用于将私有线程和该线程存放的副本对象做一个映射，各个线程之间的变量各不干扰，在高并发的场景下，可以实现无状态的调用，特别适用于各个线程依赖不同的变量值完成操作的场景。<br />

```java
class ConnectionManager {
     
    private static Connection connect = null;
    public static Connection openConnection() {
        if(connect == null){
            connect = DriverManager.getConnection();
        }
        return connect;
    }
    public static void closeConnection() {
        if(connect!=null)
            connect.close();
    }
}
```
假设有这样一个数据库连接管理类，这段代码在单线程中使用是没有任何问题的，但是如果再多线程中使用呢?<br />很显然，在多线程中使用会存在线程安全问题：

  - 第一，这里面的2个方法都没有进行同步，很可能在openConnection方法中会多次创建connect；
  - 第二,由于connect是共享变量，那么必然在调用connect的地方需要使用到同步来保证线程安全，因为很可能一个线程在使用connect进行数据库操作，而另外一个线程调用closeConnection关闭连接。


<br />所以出于线程安全的考虑，必须将这段代码的两个方法进行同步处理，并且在调用connect的地方需要进行同步处理。<br />
<br />这样将会大大影响程序执行效率，因为一个线程在使用connect进行数据库操作的时候，其它线程只有等待。<br />
<br />那么大家来仔细分析一下这个问题，这地方到底需不需要将connect变量进行共享呢？事实上，是不需要的。假如每个线程中都有一个connect变量，各个线程之间对connect变量的访问实际上是没有依赖关系的，即一个不需要关心其它线程是否对这个connect进行了修改的。<br />
<br />到这里，可能会有朋友想到，既然不需要在线程之间共享这个变量，可以直接这样处理，在每个需要使用数据库连接的方法中具体使用时才创建数据库连接，然后在方法调用完毕再释放这个连接。比如下面这样：
```java
class ConnectionManager {
    private  Connection connect = null;
    public Connection openConnection() {
        if(connect == null){
            connect = DriverManager.getConnection();
        }
        return connect;
    }
     
    public void closeConnection() {
        if(connect!=null)
            connect.close();
    }
}
 
 
class Dao{
    public void insert() {
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = connectionManager.openConnection();
         
        //使用connection进行操作
         
        connectionManager.closeConnection();
    }
```
这样处理确实也没有任何问题，由于每次都是在方法内部创建的连接，那么线程之间自然不存在线程安全问题。但是这样会有一个致命的影响：导致服务器压力非常大，并且严重影响程序执行性能。由于在方法中需要频繁地开启和关闭数据库连接，这样不尽严重影响程序执行效率，还可能导致服务器压力巨大。<br />
<br />那么这种情况下使用ThreadLocal是再适合不过的了，因为ThreadLocal在每个线程中对该变量会创建一个副本，即每个线程内部都会有一个该变量，且在线程颞部任何地方都可以使用，线程之间互不影响，这样一来就不存在线程安全问题，也不会影响程序执行性能。<br />
<br />但是要注意，虽然ThreadLocal能够解决上面说的问题，但是由于在每个线程中都创建了副本，所以要考虑它对资源的消耗，比如内存的占用会比不使用ThreadLocal要大。<br />
<br />![image.png](https://cdn.nlark.com/yuque/0/2020/png/440247/1587522031082-2114ebe8-186f-4b97-8f77-658cd3c728e5.png#align=left&display=inline&height=511&margin=%5Bobject%20Object%5D&name=image.png&originHeight=511&originWidth=485&size=140096&status=done&style=none&width=485)<br />
<br />从上面的结构图，我们已经看见ThreadLocal的核心机制：

  - 每个Thread线程内部都有一个Map
  - Map里面存在线程本地对象key和线程变量副本（value）


<br />但是，Thread内部的Map是由ThreadLocal维护的，由ThreadLocal负责向map获取和设置线程的变量值。<br />所以对于不同的线程，每次获取副本值时，别的线程并不能获取到当前线程的副本值，形成了副本的隔离，互不干扰。<br />
<br />ThreadLocal类提供了如下几个核心方法：<br />
```java
public T get()
public void set(T value)
public void remove()
```

- get()方法用于获取当前线程的副本变量值。
- set()方法用于保存当前线程的副本变量值。
- initialValue()为当前线程初始化副本变量值。
- remove（）方法移除当前线程的副本变量值。



<a name="yydDF"></a>
#### 7、程序、进程、线程的区别是什么？举个现实的例子说明。

<br />** 程序（Program）：**<br />是一个指令的集合。程序不能独立运行，只有被加载到内存中，系统为它分配资源后才能执行。<br />
<br />**进程（Process）:**<br />如上所述，一个执行中的程序称为进程。

进程是系统分配资源的独立单位，每个进程占有特定的地址空间。

程序是进程的静态文本描述，进程是程序在系统内顺序执行的动态活动。

**线程（Thread）： **是进程的"单一执的连续控制流程"<br />线程是cpu的调度和分配的基本单位，是比进程更小的能独立运行的基本单位，也被称为轻量级的进程。<br />
<br />线程不能独立存在，必须依附于某个进程。一个进程可以包括多个并行的线程，一个线程肯定属于一个进程。Java虚拟机允许应用程序并发地执行多个线程。<br />
<br />举例：如一个车间是程序，一个正在进行生产任务的车间是一个进程，车间内每个从事不同工作的工人是一个线程。<br />
<br />
<br />**<br />

<a name="TS03K"></a>
#### 8.java中通过哪些方式创建多个线程类？分别使用代码说明。并调用

- 继承Thread类创建线程。
```java
/**
 * @program: java-learning->ThreadCreateTest
 * @description:
 *
 * Thread类本质上实现了Runnable接口的一个实例，代表线程的一个实例。
 * 启动线程的唯一方法就是通过Thread类的start（）实例方法
 * 这种方式实现多线程很简单，通过自己的类直接 extends Thread;
 * 并复写run（）方法，就可以启动新线程并执行自定定义的run（）方法。
 * start
 * @author: liushuai
 * @create: 2020-04-21 09:51
 **/

public class ThreadCreateTest extends Thread{
    @Override
    public void run() {
        System.out.println("ThreadCreateTest.run()");
    }

    public static void main(String[] args) {
        new ThreadCreateTest().start();
        ThreadCreateTest createTest = new ThreadCreateTest();
        createTest.start();
    }
}

```

- 实现Runnable接口创建线程
```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: java-learning->ThreadPoolTest
 * @description:
 * @author: liushuai
 * @create: 2020-04-21 13:33
 **/

public class ThreadPoolTest {
    /** 线程池数量 10个 */
    private static int POOL_NUM = 10;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < POOL_NUM; i++) {
            RunnableThread thread = new RunnableThread();
            executorService.execute(thread);
        }
        executorService.shutdown();
    }

}

class RunnableThread implements Runnable{
    @Override
    public void run() {
        System.out.println("通过线程池方式创建的线程：" + Thread.currentThread().getName());
    }
}

```

- 实现Callable接口通过FutureTask包装器来创建Thread线程。
```java
public class CallableInstance<Object> implements Callable<Object> {
    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Object call() throws Exception {

        System.out.println(Thread.currentThread().getName()+"----> 我是通过实现Callable接口通过FutureTask包装器来实现的实现");
        Object object = (Object) "success";
        return object;
    }
}


/**
 * @program: java-learning->ThreadCreateTest2
 * @description:
 *
 * 通过Callable和FutureTask创建线程
 *
 * 1: 创建Callable接口的实现类，并实现call方法
 * 2：创建Callable实现类的实现，使用FutureTask类包装Callable对象，该FutureTask对象封装了Callable对象的call方法的返回值
 * 3：使用FutureTask对象作为Thread对象的Target创建并启动线程
 * 4：调用FutureTask对象的get（）方法来获取子线程执行结束的返回值。
 * @author: liushuai
 * @create: 2020-04-21 09:59
 **/

public class ThreadCreateTest2 {

    public static void main(String[] args) throws Exception {

        Callable<Object> callable = new CallableInstance<>();
        FutureTask<Object> task = new FutureTask<>(callable);
        Thread thread = new Thread(task);
        System.out.println(Thread.currentThread().getName());
        thread.start();

    }



```

<br />

<a name="Ky1X1"></a>
#### 9.Thead类有没有实现Runnable接口？
有实现
```java
public class Thread implements Runnable {
    /* Make sure registerNatives is the first thing <clinit> does. */
    private static native void registerNatives();
    static {
        registerNatives();
    }
```


<a name="LODGw"></a>
#### 10.当调用一个线程对象的start方法后，线程马上进入运行状态码？

<br />不是，只是进行就绪（可运行）状态，等待分配CPU时间片。一旦得到CPU时间片，即进入运行状态。<br />
<br />
<br />

<a name="HRbAZ"></a>
#### 11. 下面的代码，实际上有几个线程在运行？
```java
public static void main(String[] argc) throws Exception {
		Runnable r = new Thread6();
		Thread t = new Thread(r, "Name test");
		t.start();
}
```
两个：线程t和main（）方法（主线程）。<br />
<br />

<a name="fgvwG"></a>
#### 12.线程的几种状态
线程通常有五种状态：

- 新建（NEW）
  - 新创建了一个线程对象
- 就绪(Runnable)
  - 线程对象创建后，其它线程调用了该对象的start（）方法。该状态的线程位于可运行线程池中，变得可运行，等待获取CPU的使用权。
- 运行（Running）
  - 就绪状态的线程获取了CPU，执行程序代码
- 阻塞
  - 阻塞状态是线程因为某种原因放弃CPU使用权，暂时停止运行。直到线程进入就绪状态，才有机会转到运行状态。
- 死亡
  - 线程执行玩了或者因为异常退出了run（）方法，该线程结束生命


<br />
<br />阻塞的情况又分为三种：

- 等待阻塞：运行的线程执行wait（）方法，该线程会释放占用的所有资源，JVM会把该线程放入"等待池"中。

进入这个状态后，是不能自动唤醒的，必须依靠其它线程调用notify（）或notifyAll（）方法才能唤醒。<br />**wait和notify、notifyAll（）是Object类的方法。**<br />

- 同步阻塞：运行的线程在获取对象同步锁时，若该同步锁被别的线程占用，则JVM会把线程放入"锁池"中。
- 其他阻塞：运行的线程执行sleep（）或join（）方法，或者发出I/O请求时，JVM会把该线程置为阻塞状态。当Sleep（）状态超时、Join（）等待线程终止或者超时、或者I/O处理完毕时，线程重新转入就绪状态。

**<br />**sleep是Thread类的方法。**<br />**<br />**
<a name="KG6iN"></a>
#### 13.说说： sleep、yield、join、wait方法的区别？

<br />**sleep()方法**需要指定等待的时间，它可以让当前的线程正在执行的线程在指定的时间内暂停执行，进入阻塞状态，该方法既可以让其它同优先级或者高优先级的线程得到执行的机会，也可以让低优先级的线程得到执行机会。但是sleep（）方法不会释放锁"锁标志"，也就是说如果有sychronized同步块，其它线程仍然不能访问共享数据。  **作用域线程**<br />

- Thread.sleep()方法用于来暂停线程的执行，将CPU放给线程调度器。
- Thread.sleep()方法是一个静态方法，它暂停的是当前执行的线程。
- Java有两种sleep方法，一个只有一个毫秒参数，另一个有毫秒参数，另一个有毫秒和纳秒两个参数。
- 与wait方法不同，sleep方法不会释放锁。
- 如果其它的线程中断了一个休眠的线程，sleep方法会抛出Interrupted Exception
- 休眠的线程在唤醒之后不保证能获取到cpu，它会先进入就绪状态，与其它线程竞争cpu。
-  有一个易错的地方，当调用t.sleep()的时候，会暂停线程t。这是不对的，因为Thread.sleep是一个静态方法，它会使当前线程等待而不是线程t进入休眠状态。

**<br />**<br />join()方法 <br />当前线程等待，调用此方法的线程执行结束在继续执行。如：在main方法中调用t.join(）,那main（）方法在此时进入阻塞状态，一直等待t线程执行完，main方法再恢复到就绪状态，准备继续执行。

join方法必须在线程start方法调用之后调用才有意义。这个也很容易理解；如果一个线程都没有start，那就它也就无法同步了。**作用域线程**<br />**<br />实现原理：
```java
    public final synchronized void join(long millis)
    throws InterruptedException {
        long base = System.currentTimeMillis();
        long now = 0;

        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (millis == 0) {
            while (isAlive()) {
                wait(0);
            }
        } else {
            while (isAlive()) {
                long delay = millis - now;
                if (delay <= 0) {
                    break;
                }
                wait(delay);
                now = System.currentTimeMillis() - base;
            }
        }
    }
```

<br />**yield()：**它仅仅释放线程所占有的CPU的资源，从而让其它线程有机会运行，但是并不能保证某个特定的线程能够获得CPU资源。谁能获取CPU完全取决于调度器，在有些情况下调用yield（）方法的线程甚至会再次得到CPU资源。所以，依赖于yield方法是不可靠的，它只能尽力而为。**作用域线程**<br />
<br />**wait():**

- wait只能在同步（sychronzied）环境中被调用，而sleep不需要
- 进入wait状态的线程能够被notify和notifyAll线程唤醒，但是进入sleeping状态的线程不能被notify方法唤醒。
- wait通常有条件地执行，线程会一直处于wait状态，直到某个条件变为真，但是sleep仅仅让你的线程进入睡眠状态。
- wait方法在进入wait状态的时候会释放对象的锁，但是sleep方法不会。


<br />wait方法是针对一个被同步代码块加锁的对象。<br />![image.png](https://cdn.nlark.com/yuque/0/2020/png/440247/1587533340341-d23e6aff-946b-47bd-a35e-c26569e551b4.png#align=left&display=inline&height=311&margin=%5Bobject%20Object%5D&name=image.png&originHeight=311&originWidth=542&size=103214&status=done&style=none&width=542)<br />

<a name="ViNDJ"></a>
#### 14.为什么不推荐使用stop和destroy方法来结束线程的运行？

<br />**stop(): **此方法可以强行终止一个正在运行或挂起的线程。但stop方法不安全，就像强行切断计算机电源，而不是正常程序关机，可能会产生不可预料的结果

举例来说：<br />当一个线程对象上调用stop（）方法时，这个线程对象所运行的线程就会立即停止，并抛出特殊的ThreadDeath（）异常。这里的"立即"因为太"立即"了。<br />假如一个线程正在执行：
```java
synchronized void {
 x = 3;
 y = 4;
}
```
由于方法时同步的，多个线程访问时总能保证想x,y被同时赋值，而如果一个线程正在执行到x=3时，被调用了 stop()方法，即使在同步块中，它也干脆地stop了，这样就产生了不完整的残废数据。而多线程编程中最最基础的条件要保证数据的完整性，所以请忘记 线程的stop方法，以后我们再也不要说“停止线程”了。<br />
<br />****destroy****():该方法最初用于破坏该线程，但不作任何资源释放。它所保持的任何监视器都会保持锁定状态。不过，该方法决不会被实现。即使要实现，它也极有可能以 suspend() 方式被死锁。如果目标线程被破坏时保持一个保护关键系统资源的锁，则任何线程在任何时候都无法再次访问该资源。如果另一个线程曾试图锁定该资源，则会出现死锁。<br />

<a name="K7xbw"></a>
#### 15.写个代码说明，终止线程的典型方式。


- 1. 当run（）方法执行完后，线程就自动终止了。 
- 2. 但有些时候run()方法不会结束（如服务器端监听程序），或者其它需要用循环来处理的任务。在这种情况下，一般是将这些任务放在一个循环中，如while循环。如果想让循环永远运行下去，可以使用while（true）{……}来处理。但要想使while循环在某一特定条件下退出，最直接的方法就是设一个boolean类型的标志，并通过设置这个标志为true或false来控制while循环是否退出。


<br />见代码：
```java
public class ThreadEndTest extends Thread{
    public volatile boolean exit = false;
    @Override
    public void run() {
        while (!exit){
            System.out.println("我还在");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadEndTest endTest = new ThreadEndTest();
        endTest.start();
        //主线程延迟5秒
        sleep(5000);
        endTest.exit=true;
        endTest.join();
        System.out.println("线程退出");
    }
}

```


<a name="94cEC"></a>
#### 16.A线程的优先级是10，B线程的优先级是1，那么当进行调度时一定会调用A吗?
 不一定。线程优先级对于不同的线程调度器可能有不同的含义，可能并不是用户直观的推测。

<a name="y0J20"></a>
#### 17.synchronized修饰在方法前是什么意思？
一次只能有一个线程进入该方法,其他线程要想在此时调用该方法,只能排队等候,当前线程(就是在synchronized方法内部的线程)执行完该方法后,别的线程才能进入.
```java
public class SynchronizedTest {

    public static synchronized void read(String name){
        System.out.println(name + "开始执行Read 方法");

        try {
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println(name + "结束执行Read 方法");
    }

    public static void main(String[] args) {
        Runnable runnable = () -> {
            System.out.println("线程："+Thread.currentThread().getName()+"开始");
            read(Thread.currentThread().getName());
            System.out.println("线程："+Thread.currentThread().getName()+"结束");
        };

        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();

    }
}

```



<a name="Yciwf"></a>
####  18.wait方法被调用时，所在的线程是否会释放所持有的锁资源？sleep方法呢？

<br />**wait**：释放cpu，释放锁<br />**sleep**：释放cpu，不释放锁<br />

<a name="SI5wA"></a>
####  19.wait、notify、notifyAll是在Thread类中定义的方法吗？作用分别是什么？
wait(),notify(),notifyAll()<br />不属于Thread类,而是属于Object类,也就是说每个对象都有wait(),notify(),notifyAll()的功能。因为每个对象都有锁,锁是每个对象的基础,而wait(),notify(),notifyAll()都是跟锁有关的方法。<br />
<br />三个方法的作用分别是：

- wait：导致当前线程等待，进入阻塞状态，直到其他线程调用此对象的 notify() 方法或 notifyAll() 方法。当前线程必须拥有此对象监视器（对象锁）。该线程释放对此监视器的所有权并等待，直到其他线程通过调用 notify 方法，或 notifyAll 方法通知在此对象的监视器上等待的线程醒来。然后该线程将等到重新获得对监视器的所有权后才能继续执行.



- notify：唤醒在此对象监视器（对象锁）上等待的单个线程。如果所有线程都在此对象上等待，则会选择唤醒其中一个线程。直到当前线程放弃此对象上的锁定，才能继续执行被唤醒的线程。此方法只应由作为此对象监视器的所有者的线程来调用.

"当前线程必须拥有此对象监视器"与"此方法只应由作为此对象监视器的所有者的线程来调用"说明wait方法与           notify方法必须在同步块内执行,即synchronized(obj之内).<br />

- notifyAll: 唤醒在此对象监视器（对象锁）上等待的所有线程。



<a name="B3CYq"></a>
#### 20.notify是唤醒所在对象wait pool中的第一个线程吗？
不是。<br />调用 notify() 方法导致解除阻塞的线程是从因调用该对象的 wait() 方法而阻塞的线程中随机选取的，我们无法预料哪一个线程将会被选择。
