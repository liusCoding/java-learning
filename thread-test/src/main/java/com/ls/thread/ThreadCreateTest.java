package com.ls.thread;

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
