package com.ls.threadpool;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: java-learning->ThreadPoolExecutorTest
 * @description:
 * ThreadPoolExecutor
 * 当队列是SynchronousQueue，超过核心线程的任务会创建新的线程来执行，看到一共有6个线程。
 * 但是这些线程不是核心线程，受超时时间限制，在任务完成后限制超过5秒就会被回收，所以最后看到线程池还是只有三个线程。
 * @author: liushuai
 * @create: 2020-04-21 13:44
 **/

public class ThreadPoolExecutorTest3 {

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () ->{
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName()+"run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        //核心线程数为3，最大线程数为6，超时时间为5秒，SynchronousQueue
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 6, 5, TimeUnit.SECONDS, new SynchronousQueue<>());


        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        System.out.println("-------先开三个线程------------");
        System.out.println("核心线程数：  "+ executor.getCorePoolSize());
        System.out.println("线程池数：" + executor.getPoolSize());
        System.out.println("队列任务数："  + executor.getQueue().size());

        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        System.out.println("再开3个线程");

        System.out.println("核心线程数：  "+ executor.getCorePoolSize());
        System.out.println("线程池数：" + executor.getPoolSize());
        System.out.println("队列任务数："  + executor.getQueue().size());
        Thread.sleep(8000);
        System.out.println("----------8秒之后-------");

        System.out.println("核心线程数：  "+ executor.getCorePoolSize());
        System.out.println("线程池数：" + executor.getPoolSize());
        System.out.println("队列任务数："  + executor.getQueue().size());

        executor.shutdown();

    }
}
