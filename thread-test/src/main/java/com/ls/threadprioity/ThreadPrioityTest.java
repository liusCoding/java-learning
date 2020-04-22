package com.ls.threadprioity;

/**
 * @program: java-learning->ThreadPrioityTest
 * @description:
 * @author: liushuai
 * @create: 2020-04-21 14:35
 **/

public class ThreadPrioityTest {

    public static void main(String[] args) {

        Runnable runnable = () ->{

            while (true){
                System.out.println("\t"+Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread = new Thread(runnable,"_______________A _____________________");
        Thread thread1 = new Thread(runnable,"______________B _____________________");
        Thread thread2 = new Thread(runnable,"______________C _____________________");

        thread.setPriority(8);
        thread1.setPriority(10);
        thread2.setPriority(1);
        thread.start();
        thread1.start();
        thread2.start();

    }
}
