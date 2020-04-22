package com.ls.threadpool;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: java-learning->ThreadPoolExecutorTest
 * @description:
 * ThreadPoolExecutor
 * 当任务数超过核心线程数时，会将超出的任务放在队列中，只会创建3个线程重复利用
 * @author: liushuai
 * @create: 2020-04-21 13:44
 **/

public class ThreadPoolExecutorTest2 {

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () ->{
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName()+"run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        //核心线程数为3，最大线程数为6，超时时间为5秒，队列是LinkedBlockingDeque
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 6, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<>());


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
