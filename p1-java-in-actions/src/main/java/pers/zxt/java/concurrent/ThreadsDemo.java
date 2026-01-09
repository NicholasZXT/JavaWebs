package pers.zxt.java.concurrent;

import java.util.concurrent.FutureTask;
import java.util.concurrent.ExecutionException;

public class ThreadsDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 打印主线程名称
        System.out.println("main method thread name: " + Thread.currentThread().getName());

        // 第1种创建线程方式：直接实例化 Thread 的子类
        System.out.println("---------- Thread-1: SubClass Thread ----------");
        ThreadSubClass th1 = new ThreadSubClass();
        // 启动线程，每个线程对象只能调用一次
        th1.start();
        th1.join();  // 等待线程结束

        // 第2种创建线程方式：实例化 Thread 类，并传入一个 Runnable 接口实例对象
        System.out.println("---------- Thread-2: Runnable Thread ----------");
        Thread th2 = new Thread(new ThreadInterface());
        th2.start();
        th2.join();

        // 第3种，需要获取线程返回值，使用FutureTask
        System.out.println("---------- Thread-3: FutureTask Thread ----------");
        FutureTask<Integer> task = new FutureTask<>(
            () -> {
                int sum = 0;
                for (int i = 0; i < 100; i++) {
                    sum += i;
                }
                return sum;
            }
        );
        Thread t3 = new Thread(task, "FutureTask-Thread");
        t3.start();
        t3.join();
        System.out.println("FutureTask result: " + task.get());

        // 手动执行 run 方法的话，实际上是在调用run方法的线程里执行
        //ThreadSubClass th4 = new ThreadSubClass();
        //th4.start();
        //// 这里执行的run方法其实是在main线程中执行的，从线程名称可以看出来
        //th4.run();

        // 线程属性
        System.out.println("---------- Thread-4: Show Thread Property ----------");
        ThreadProperty th4 = new ThreadProperty();
        th4.start();

    }

    // 第一种创建线程对象的方式：继承 java.lang.Thread 类，重写其中的 run 方法。
    // 实际上，Thread类就是Runnable接口的一个实现类。
    static class ThreadSubClass extends Thread {
        @Override
        public void run() {
            String cls_name = this.getClass().getSimpleName();
            String th_name = Thread.currentThread().getName();
            System.out.println(cls_name + "-Thread is running with thread name: " + th_name);
        }
    }

    // 第二种创建线程对象的方式：实现 java.lang.Runnable 接口及run方法
    static class ThreadInterface implements Runnable {
        @Override
        public void run() {
            String cls_name = this.getClass().getSimpleName();
            String th_name = Thread.currentThread().getName();
            System.out.println(cls_name + "-Thread is running with thread name: " + th_name);
        }
    }

    // 展示线程的属性，这里必须要使用 继承 Thread 类的方式，因为这些属性是 Thread 类封装的
    static class ThreadProperty extends Thread {
        @Override
        public void run() {
            String cls_name = this.getClass().getSimpleName();
            // 线程名称，可设置
            this.setName(cls_name + "-Thread");
            System.out.printf("Thread properties: <ID: %s, Name: %s, Daemon: %s, Priority: %s>", this.getId(), this.getName(), this.isDaemon(), this.getPriority());
        }
    }
}
