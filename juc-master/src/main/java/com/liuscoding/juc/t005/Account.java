package com.liuscoding.juc.t005;

import java.util.concurrent.TimeUnit;


/**
 * 面试题： 模拟银行账户
 *
 * 对业务写方法加锁
 * 对业务读方法不加锁
 * 这样行不行？
 *
 * 容易产生脏读问题（dirtyRead)
 */
public class Account {
    private String name;
    private  double balance;


    public synchronized void set(String name,double balance){
        this.name = name;

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
        this.balance = balance;
    }

    public /*synchronized*/ double getBalance(String name){
        System.out.println(Thread.currentThread().getName());
        return this.balance;
    }


    public static void main(String[] args) {
        Account account = new Account();
        new Thread(()->account.set("zhangsan",100.0)).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(account.getBalance("zhangsan"));


        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(account.getBalance("zhangsan"));
    }
}
