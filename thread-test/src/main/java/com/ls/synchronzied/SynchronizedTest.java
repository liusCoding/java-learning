package com.ls.synchronzied;

/**
 * @program: java-learning->SynchronizedTest
 * @description: synchronized关键字用法
 * @author: liushuai
 * @create: 2020-04-20 17:31
 **/

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
