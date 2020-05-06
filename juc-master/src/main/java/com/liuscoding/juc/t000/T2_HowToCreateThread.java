package com.liuscoding.juc.t000;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @className: T2_HowToCreateThread
 * @description: 创建线程的方式
 * @author: liusCoding
 * @create: 2020-05-06 16:28
 */

public class T2_HowToCreateThread {

     static class MyThread extends Thread{
        @Override
        public void run() {
            System.out.println("Hello MyThread");
        }
    }

     static class MyRun implements Runnable{

        @Override
        public void run() {
            System.out.println("Hello MyRun");
        }
    }

    static class Mycall implements Callable<String>{

        @Override
        public String call() throws Exception {
            System.out.println("Hello MyCall");
            return "success";
        }
    }

    public static void main(String[] args) {

         //启动线程的五种方式

        new MyThread().start();

        new Thread(new MyRun()).start();

        new Thread(()->{
            System.out.println("Hello lambda");
        }).start();

        Thread thread = new Thread(new FutureTask<String>(new Mycall()));
        thread.start();


        ExecutorService service = Executors.newCachedThreadPool();

        service.execute(()->{
            System.out.println("Hello ThreadPool");
        });

        service.shutdown();
    }
}
