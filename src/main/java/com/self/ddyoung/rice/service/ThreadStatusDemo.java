package com.self.ddyoung.rice.service;

/**
 * Created by sanbian on 2017/8/17.
 */
public class ThreadStatusDemo {

    public static void main(String[] args) {
        timedWaitingStatus();
    }

    static void newStatus(){
        Thread thread = new Thread();
        System.out.println(thread.getState());
    }

    static void runnableStatus(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < Integer.MAX_VALUE; i++) {
                    try {
                        Thread.sleep(10000l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(i);
                }
            }
        }, "ThreadStatusDemo");//线程命名
        thread.start();

    }

    static void blockedStatus(){
        Object lock = new Object();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<Integer.MAX_VALUE; i++){
                    synchronized (lock) {
                        System. out.println(i);
                    }
                }
            }
        };

        Thread thread0 = new Thread(run, "ThreadStatusDemo-0");
        Thread thread1 = new Thread(run, "ThreadStatusDemo-1");

        thread0.start();
        thread1.start();
    }

    static void waitingStatus(){
        Object lock = new Object();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<Integer.MAX_VALUE; i++){
                    synchronized (lock) {
                        System. out.println(i);
                    }
                }
            }
        };

        Runnable run1 = new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<Integer.MAX_VALUE; i++){
                    synchronized (lock) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System. out.println(i);
                    }
                }
            }
        };

        Thread thread0 = new Thread(run, "ThreadStatusDemo-0");
        Thread thread1 = new Thread(run1, "ThreadStatusDemo-1");

        thread0.start();
        thread1.start();
    }

    static void timedWaitingStatus(){
        Object lock = new Object();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<Integer.MAX_VALUE; i++){
                    synchronized (lock) {
                        System. out.println(i);
                    }
                }
                lock.notifyAll();
            }
        };

        Runnable run1 = new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<Integer.MAX_VALUE; i++){
                    synchronized (lock) {
                        try {
                            lock.wait(60 * 1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System. out.println(i);
                    }
                }
            }
        };

        Thread thread0 = new Thread(run, "ThreadStatusDemo-0");
        Thread thread1 = new Thread(run1, "ThreadStatusDemo-1");

        thread0.start();
        thread1.start();
    }


    static void waiting2Status(){
        Object lock = new Object();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<Integer.MAX_VALUE; i++){
                    synchronized (lock) {
                        System. out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getState());
                    }
                }
            }
        };

        Runnable run1 = new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<Integer.MAX_VALUE; i++){
                    synchronized (lock) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System. out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getState());
                    }
                }
            }
        };

        Runnable run2 = new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<Integer.MAX_VALUE; i++){
                    synchronized (lock) {
                        lock.notify();
                        System. out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getState());
                    }
                }
            }
        };

        Runnable run3 = new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<Integer.MAX_VALUE; i++){
                    synchronized (lock) {
                        lock.notifyAll();
                        System. out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getState());
                    }
                }
            }
        };

        Thread thread0 = new Thread(run, "ThreadStatusDemo-0");
        Thread thread1 = new Thread(run1, "ThreadStatusDemo-1");
        Thread thread2 = new Thread(run2, "ThreadStatusDemo-2");
        Thread thread3 = new Thread(run3, "ThreadStatusDemo-3");

        thread1.start();
        thread2.start();
        thread3.start();
        thread0.start();
    }

}
