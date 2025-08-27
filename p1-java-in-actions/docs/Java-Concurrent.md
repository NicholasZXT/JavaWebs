[TOC]

# Java并发编程实践


## 线程池

### 基本接口/类

JDK中线程池的实现主要在`java.util.concurrent`包中。

线程池基本接口/实现类：
- `Executor`: 线程池基本接口
  - 定义了线程池的基本方法——`execute()`。
- `ExecutorService`: 线程池**接口**，继承了`Executor`接口
  - 主要扩展了线程池生命周期相关的方法，比如关闭线程池，取消任务，状态查询等。
  - 此外还定义了一个 `submit()`方法，用于提交任务，对 `Executor.execute()`方法进行了封装
- `ScheduledExecutorService`: 扩展自`ExecutorService`的**接口**，用于执行定时任务
- `AbstractExecutorService`: 实现 `ExecutorService` 接口的抽象类
- **`ThreadPoolExecutor`**: 线程池基本实现类，继承了`AbstractExecutorService`抽象类，**一般使用都基于这个类**
- `ScheduledThreadPoolExecutor`: 扩展自`ThreadPoolExecutor`的**实现类**，用于执行定时任务

线程池的辅助接口/类：
- `ThreadFactory`: 创建线程的工厂**接口**
- `TimeUnit`: 时间单位枚举类
- `BlockingQueue`: 线程池候选的阻塞队列**接口**
- `RejectedExecutionHandler`: 线程池调度策略的**接口**
- `Future`: 异步任务结果的**接口**
- `RunnableFuture`: 扩展自 `Future`的**接口**，增加了`run()`方法，表示一个可执行的`Future`对象
- `FutureTask`: 实现了 `RunnableFuture` 接口的**类**，**一般使用都基于这个类**


### `Executors`工具类

线程池工具类 `Executors` —— 最重要的工具类，它定义了上述一些线程池组件接口的默认实现类（`static`内部类），
并提供了一系列`static`方法（工厂方法）来创建不同策略的线程池对象`ThreadPoolExecutor`，常用方法如下：

**基本线程池**
- `newFixedThreadPool(int nThreads)`
- `newFixedThreadPool(int nThreads, ThreadFactory threadFactory)`
- `newSingleThreadExecutor()`
- `newSingleThreadExecutor(ThreadFactory threadFactory)`
- `newCachedThreadPool()`
- `newCachedThreadPool(ThreadFactory threadFactory)`

**定时任务线程池**
- `newScheduledThreadPool(int corePoolSize)`
- `newScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory)`
- `newSingleThreadScheduledExecutor()`
- `newSingleThreadScheduledExecutor(ThreadFactory threadFactory)`

**基于Fork/Join的线程池**
- `newWorkStealingPool()`
- `newWorkStealingPool(int parallelism)`


### `ThreadPoolExecutor`使用说明

**构造方法参数**：
- `int corePoolSize`: 线程池中核心线程数，即线程池中一直存活的线程数，即使它们是空闲的。
- `int maximumPoolSize`: 线程池中允许的最大线程数，即线程池中能够同时存活的线程数。
- `long keepAliveTime`: 线程空闲时间，即（超出corePoolSize的线程数）线程空闲了多少时间后，线程池会判断线程是否存活，如果存活则不回收，否则回收。
- `TimeUnit unit`: 时间单位枚举类的值，用于指定`keepAliveTime`参数的单位。
- `ThreadFactory threadFactory`: 创建线程的工厂，即线程池创建线程时使用的工厂类。
- `BlockingQueue<Runnable> workQueue`: 线程池的阻塞队列，即任务队列，线程池会从任务队列中获取任务。
- `RejectedExecutionHandler handler`: 线程池的拒绝策略，即当线程池中的线程数达到最大值时，线程池会拒绝添加新的任务。

**拒绝策略**：  
`ThreadPoolExecutor`中以`static`内部类的方式，提供了`RejectedExecutionHandler`接口的4个实现类：
- `AbortPolicy`: **默认**拒绝策略，会抛出`RejectedExecutionException`异常。
- `DiscardPolicy`: 拒绝策略，会忽略拒绝的任务，不抛出异常。
- `DiscardOldestPolicy`: 拒绝策略，会丢弃队列中**最老**的未处理的任务，然后尝试添加新的任务。
- `CallerRunsPolicy`: 拒绝策略，会尝试执行被拒绝的任务，如果执行被拒绝的任务时，线程池中的线程数已经达到最大值，则直接抛出`RejectedExecutionException`异常。

**线程工厂**：  
`Executors`工具类中，提供了`ThreadFactory`接口两个实现：
- `Executors.defaultThreadFactory()`: **默认**的线程工厂，创建的线程会继承父线程的优先级、名称、Daemon属性。
- `Executors.privilegedThreadFactory()`: 创建的线程会继承父线程的优先级、名称、Daemon属性，并且会具有权限。
