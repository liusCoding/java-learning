package com.ls.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @program: java-learning->ThreadCreateTest2
 * @description:
 *
 * 通过Callable和FutureTask创建线程
 *
 * 1: 创建Callable接口的实现类，并实现call方法
 * 2：创建Callable实现类的实现，使用FutureTask类包装Callable对象，该FutureTask对象封装了Callable对象的call方法的返回值
 * 3：使用FutureTask对象作为Thread对象的Target创建并启动线程
 * 4：调用FutureTask对象的get（）方法来获取子线程执行结束的返回值。
 * @author: liushuai
 * @create: 2020-04-21 09:59
 **/

public class ThreadCreateTest2 {

    public static void main(String[] args) throws Exception {

        Callable<Object> callable = new CallableInstance<>();
        FutureTask<Object> task = new FutureTask<>(callable);
        Thread thread = new Thread(task);
        System.out.println(Thread.currentThread().getName());
        thread.start();

    }


}
