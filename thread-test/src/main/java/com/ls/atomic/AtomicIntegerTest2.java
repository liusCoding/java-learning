package com.ls.atomic;

import java.util.concurrent.TimeUnit;

/**
 * @description: volatile 仅仅保证变量在线程间保持可见性，却依然不能保证非原子性操作。
 * @author: liushuai
 * @create: 2020-04-17 18:36
 **/

public class AtomicIntegerTest2 {
    public static volatile  int count =0;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> count++).start();
        }

        TimeUnit.SECONDS.sleep(2);
        System.out.println("volatile count:" + count);
    }
}
