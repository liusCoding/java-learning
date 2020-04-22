package com.ls.sleep;

/**
 * @program: java-learning->ThreadSleepTest
 * @description: Thread:sleep的用法
 * @author: liushuai
 * @create: 2020-04-20 17:40
 **/

public class ThreadSleepTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("hello");
        Thread.sleep(2000);
        System.out.println("world");
    }
}
