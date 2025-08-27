package pers.zxt.java.concurrent;

// 枚举类
import java.util.concurrent.TimeUnit;
// 相关接口
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
// 具体实现类
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Executors;

public class ThreadExecutorDemos {
    public static void main(String[] args) {
        System.out.println("ThreadExecutorsDemo");
        ThreadExecutorDemos executorsDemo = new ThreadExecutorDemos();
        executorsDemo.fixedThreadPoolUsage();
        executorsDemo.cachedThreadPoolUsage();
        executorsDemo.singleThreadExecutorUsage();
        executorsDemo.scheduledThreadPoolUsage();
    }

    public void fixedThreadPoolUsage() {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        // 提交5个任务给线程池执行
        for (int i = 0; i < 5; i++) {
            int taskId = i + 1;
            executor.submit(() -> {
                System.out.println("正在执行任务 " + taskId + " 在线程 " + Thread.currentThread().getName());
                try {
                    // 模拟任务处理时间
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("完成任务 " + taskId);
            });
        }
        executor.shutdown();
    }

    public void cachedThreadPoolUsage(){
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 3; i++) {
            int taskId = i + 1;
            executor.submit(() -> {
                System.out.println("正在执行任务 " + taskId + " 在线程 " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("完成任务 " + taskId);
            });
        }
        executor.shutdown();
    }

    public void singleThreadExecutorUsage(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 3; i++) {
            int taskId = i + 1;
            executor.submit(() -> {
                System.out.println("正在执行任务 " + taskId + " 在线程 " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("完成任务 " + taskId);
            });
        }
        executor.shutdown();
    }

    public void scheduledThreadPoolUsage(){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        // 延迟2秒后执行一次任务
        scheduler.schedule(
                () -> System.out.println("延迟2秒后执行的任务"),
                2,
                TimeUnit.SECONDS
        );

        // 延迟1秒后开始执行任务，之后每隔3秒重复执行一次
        scheduler.scheduleAtFixedRate(
                () -> System.out.println("每3秒执行一次的任务"),
                1,
                3,
                TimeUnit.SECONDS
        );

        // 注意：为了保持主线程存活以便观察结果，在实际应用中应该合理安排shutdown时机
         scheduler.shutdown();
    }
}
