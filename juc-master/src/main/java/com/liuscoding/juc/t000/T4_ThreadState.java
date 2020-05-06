package com.liuscoding.juc.t000;

/**
 * @className: T4_ThreadState
 * @description: 线程状态
 * @author: liusCoding
 * @create: 2020-05-06 17:30
 */

public class T4_ThreadState {
    
    static class Mythread extends Thread{
        @Override
        public void run() {
            System.out.println(this.getState());

            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) {
        Mythread mythread = new Mythread();
        //怎么样得到这个状态呢？就是通过getState()这个方法
        System.out.println(mythread.getState());//它是一个New状态

        mythread.start();//到这start后完了以后是Runnable的状态
        try {
            mythread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //然后join之后，结束了是一个Timenated状态
        System.out.println(mythread.getState());
    }
}
