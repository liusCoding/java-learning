package com.ls.join;

/**
 * @program: java-learning->ThreadJoinTest3
 * @description:
 * 从执行结果可以看出，加入join（）方法，主线程启动了子线程后，等待子线程执行完毕才继续执行下面的操作。
 * @author: liushuai
 * @create: 2020-04-20 18:20
 **/

public class ThreadJoinTest3 implements Runnable {
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + "开始执行！");

        for (int i = 0; i < 10; i++) {
            System.out.println(name + "执行了["+i+"]次");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
       ThreadJoinTest3 joinTest3 = new ThreadJoinTest3();
        Thread thread = new Thread(joinTest3, "线程1");

        System.out.println("主线程开始执行！");
        thread.start();
        thread.join(222);
        System.out.println("主线程执行结束");
    }
}
