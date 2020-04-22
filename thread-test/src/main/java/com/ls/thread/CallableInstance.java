package com.ls.thread;


import java.util.concurrent.Callable;

/**
 * @program: java-learning->CallableInstance
 * @description:
 * @author: liushuai
 * @create: 2020-04-21 10:20
 **/

public class CallableInstance<Object> implements Callable<Object> {
    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Object call() throws Exception {

        System.out.println(Thread.currentThread().getName()+"----> 我是通过实现Callable接口通过FutureTask包装器来实现的实现");
        Object object = (Object) "success";
        return object;
    }
}
