package com.ls.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: count++ 没毛病
 * @author: liushuai
 * @create: 2020-04-17 18:40
 **/

public class AtomicIntegerTest3 {
    public static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            new Thread(()->count.getAndIncrement()).start();
        }

        TimeUnit.SECONDS.sleep(2);
        System.out.println("atomicInteger count:" +count);
    }
}
