package com.ls.join;

/**
 * @program: java-learning->ThreadJoinTest
 * @description: t1.join() 方法会让t1执行完，再继续执行t2
 * @author: liushuai
 * @create: 2020-04-20 17:43
 **/

public class ThreadJoinTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadJoin t1 = new ThreadJoin("小明");
        ThreadJoin t2 = new ThreadJoin("小花");

        t1.start();
        t1.join();

        System.out.println("xxxxxxxxx");
        t2.start();
    }
}

class ThreadJoin extends Thread{
    public ThreadJoin(String name){
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.getName() +" " +i);
        }
    }
}
