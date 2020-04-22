package com.ls.yield;

/**
 * @program: java-learning->ThreadYieldTest
 * @description:
 * 它让掉当前线程 CPU 的时间片，使正在运行中的线程重新变成就绪状态，并重新竞争 CPU 的调度权。它可能会获取到，也有可能被其他线程获取到。
 * @author: liushuai
 * @create: 2020-04-20 18:35
 **/

public class ThreadYieldTest implements Runnable{

    public volatile boolean isRunning = true;
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + "开始执行！");
        while (isRunning){
            for (int i = 0; i < 6; i++) {
                System.out.println(name + "执行了["+ i +"]次");
                Thread.yield();
            }
        }
        System.out.println(name + "执行结束！");
    }

    public static void main(String[] args) {
        ThreadYieldTest yieldTest1 = new ThreadYieldTest();
        ThreadYieldTest yieldTest2 = new ThreadYieldTest();
        Thread thread = new Thread(yieldTest1, "线程1");
        Thread thread1 = new Thread(yieldTest1, "线程2");

        System.out.println("两个线程准备开始执行");
        thread.start();
        thread1.start();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        yieldTest1.isRunning = false;
        yieldTest2.isRunning = false;
    }
}
