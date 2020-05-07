package com.liuscoding.juc.t006;

import java.util.concurrent.TimeUnit;


/**
 *  一个同步方法可以调用另外一个同步方法，一个线程已经拥有某个对象的锁，再次申请的时候仍然会得到
 *  该对象的锁。
 *
 *  也就是说synchronized获得锁是可重入的
 */
public class T {

    synchronized void m1(){
        System.out.println("------------------m1 start----------------");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m2();
        System.out.println("------------------m1 end------------------");

    }

    synchronized void m2(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("m2");
    }

    public static void main(String[] args) {
        new T().m1();
    }
}
