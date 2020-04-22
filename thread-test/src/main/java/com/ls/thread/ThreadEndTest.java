package com.ls.thread;

/**
 * @program: java-learning->ThreadEndTest
 * @description:
 * @author: liushuai
 * @create: 2020-04-21 10:36
 **/

public class ThreadEndTest extends Thread{
    public volatile boolean exit = false;
    @Override
    public void run() {
        while (!exit){
            System.out.println("我还在");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadEndTest endTest = new ThreadEndTest();
        endTest.start();
        //主线程延迟5秒
        sleep(5000);
        endTest.exit=true;
        endTest.join();
        System.out.println("线程退出");
    }
}
