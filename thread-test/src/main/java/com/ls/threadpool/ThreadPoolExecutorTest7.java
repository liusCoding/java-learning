package com.ls.threadpool;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: java-learning->ThreadPoolExecutorTest
 * @description:
 * ThreadPoolExecutor
 * LinkedBlockingDeque根本不受最大线程数影响，但是当LinkedBlockingDeque有大小限制时就会受最大线程数影响了
 * 直接出错在第6个excute方法上，因为核心线程3个，当加入第四个任务的时候，就把第四个放在队列中，加入第五个任务时，因为队列满了
 * 就直接创建线程执行，创建线程4，当加入第六个线程时，也会尝试创建新线程，但是因为达到了线程池最大线程数，所以直接抛异常了。
 * @author: liushuai
 * @create: 2020-04-21 13:44
 **/

public class ThreadPoolExecutorTest7 {

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
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 4, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1));


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
