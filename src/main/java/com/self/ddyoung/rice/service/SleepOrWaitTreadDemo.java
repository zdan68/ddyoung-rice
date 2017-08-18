package com.self.ddyoung.rice.service;

/**
 * Created by sanbian on 2017/8/18.
 * Thread sleep和wait区别
 */
public class SleepOrWaitTreadDemo implements Runnable {
    int number = 10;

    public void firstMethod() throws Exception {
        synchronized (this) {
            number += 100;
            System.out.println(Thread.currentThread().getName() +   "**************firstMethod**************"+number);
        }
    }

    public void secondMethod() throws Exception {
        synchronized (this) {
            /**
             * (休息2S,阻塞线程)
             * 以验证当前线程对象的机锁被占用时,
             * 是否被可以访问其他同步代码块
             */
//            Thread.sleep(2000);
            this.wait(2000);
            number *= 200;
            System.out.println(Thread.currentThread().getName() +   "**************secondMethod**************"+number);
        }
    }

    @Override
    public void run() {
        try {
            firstMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        SleepOrWaitTreadDemo sleepOrWaitTreadDemo = new SleepOrWaitTreadDemo();
        Thread thread = new Thread(sleepOrWaitTreadDemo);
        thread.start();
        sleepOrWaitTreadDemo.secondMethod();
        System.out.println("number="+sleepOrWaitTreadDemo.number);
    }
}
