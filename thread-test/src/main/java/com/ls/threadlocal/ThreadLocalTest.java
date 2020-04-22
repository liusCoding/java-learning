package com.ls.threadlocal;

/**
 * @program: java-learning->ThreadLocalTest
 * @description:
 * @author: liushuai
 * @create: 2020-04-21 10:40
 **/

public class ThreadLocalTest {

    private static final ThreadLocal<Object> threadLocal = new ThreadLocal<Object>(){

        /**
         *功能描述
         * ThreadLocal 没有被当前线程赋值时或当前线程刚调用remove方法后调用get方法，返回此方法值
         */
        @Override
        protected Object initialValue() {
            System.out.println("调用get方法时，当前线程共享变量没有设置，调用initialValue获取默认值");
            return null;
        }
    };

    public static void main(String[] args) {
        new Thread(new MyIntegerTask("IntegerTask1",10)).start();
      //  new Thread(new MyIntegerTask("IntegerTask20",20)).start();

    }

    public static class MyIntegerTask implements Runnable{
        private String name;
        private int startValue;

        MyIntegerTask(String name,int startValue){
            this.name = name;
            this.startValue = startValue;
        }
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                System.out.println("线程"+name+"第："+i+"次运行");

                //ThreadLocal.get方法设置线程变量
                if(null == ThreadLocalTest.threadLocal.get()){
                    //ThreadLocal.get()方法设置线程变量
                    ThreadLocalTest.threadLocal.set(startValue);
                    System.out.println("线程"+name+"value=null");
                }else {
                    int num = (int) ThreadLocalTest.threadLocal.get();
                    System.out.println("线程"+name+"      value = "+ThreadLocalTest.threadLocal.get());
                    if (i == 3){
                        ThreadLocalTest.threadLocal.remove();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


