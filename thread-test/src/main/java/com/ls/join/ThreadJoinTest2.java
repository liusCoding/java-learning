package com.ls.join;

/**
 * @program: java-learning->ThreadJoinTest
 * @description:
     join方法中如果传入参数，则表示这样的意思：
    如果A线程中调用B线程join(10),则表示A线程会等待B线程执行10毫秒，10毫秒过后，A，B线程并行执行。
    需要注意的是：JDK规定，join（0）的意思不是A线程等待B线程0秒，而是A线程等待B线程无限时间，直到B线程执行完毕，即join（0）等于join（）；
 * @author: liushuai
 * @create: 2020-04-20 17:43
 **/

public class ThreadJoinTest2 {
    public static void main(String[] args) throws InterruptedException {
        ThreadJoin t1 = new ThreadJoin("小明");
        ThreadJoin t2 = new ThreadJoin("小花");

        t1.start();
        //join  阻塞 5秒 线程 上一级
        t1.join(1000);

        System.out.println("xxxxxxxxx");
        t2.start();

        //三线程一起执行
    }
}

class ThreadJoin2 extends Thread{
    public ThreadJoin2(String name){
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
