package com.ls.atomic;

import java.util.concurrent.TimeUnit;

/**
 * @description: 输出的结果为count:9999x,这个不固定，每次测试可能不一样，很显然，多线程跑++操作，结果
并没有像预期的那样：count: 10000
 * @author: liushuai
 * @create: 2020-04-17 18:25
 **/


public class AtomicIntegerTest1 {
    public static  int  count = 0;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            new Thread(() ->  count ++ ).start();
        }

        TimeUnit.SECONDS.sleep(3);
        System.out.println("count:" + count);
    }
}
