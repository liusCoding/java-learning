package com.liuscoding.juc.t000;

import java.util.concurrent.TimeUnit;

/**
 * @className: T1_WhatIsThread
 * @description: 什么是线程
 * @author: liusCoding
 * @create: 2020-05-06 16:20
 */

public class T1_WhatIsThread {
        private static int MAX  = 10;
        private static class T1 extends Thread{
        @Override
        public void run() {
            for (int i = 0; i < MAX; i++) {
                try {
                    TimeUnit.MICROSECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("T1");
            }

        }
    }

    public static void main(String[] args) {

        //启动线程
        new T1().start();
        for (int i = 0; i < MAX; i++) {

            try {
                TimeUnit.MICROSECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("main");
        }
    }
}
