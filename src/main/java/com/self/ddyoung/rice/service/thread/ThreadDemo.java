package com.self.ddyoung.rice.service.thread;

/**
 * Created by sanbian on 2017/8/16.
 */
public class ThreadDemo {


    public static void main(String[] args) {
//        MultiTread multiTread1 = new MultiTread("window 1");
//        MultiTread multiTread2 = new MultiTread("window 2");
//        MultiTread multiTread3 = new MultiTread("window 3");
//
//        Thread thread1 = new Thread(multiTread1);
//        Thread thread2 = new Thread(multiTread2);
//        Thread thread3 = new Thread(multiTread3);
//
//        thread1.start();
//        thread2.start();
//        thread3.start();

//        MultiTreadRunnable multiTread1 = new MultiTreadRunnable("window 1");
//        MultiTreadRunnable multiTread2 = new MultiTreadRunnable("window 2");
//        MultiTreadRunnable multiTread3 = new MultiTreadRunnable("window 3");
//
//        Thread thread1 = new Thread(multiTread1);
//        Thread thread2 = new Thread(multiTread2);
//        Thread thread3 = new Thread(multiTread3);
//
//        thread1.start();
//        thread2.start();
//        thread3.start();

        MultiTreadRunnable2 multiTreadRunnable = new MultiTreadRunnable2();
        Thread thread1 = new Thread(multiTreadRunnable);
        Thread thread2 = new Thread(multiTreadRunnable);
        Thread thread3 = new Thread(multiTreadRunnable);

        thread1.start();
        thread1.run();
        thread2.run();
//        thread3.start();

    }


}

class MultiTreadRunnable2 implements Runnable{
    private int ticket = 10;

    @Override
    public void run(){
        while (ticket > 0){
            System.out.println(ticket-- +" is sold by " + Thread.currentThread().getName());
        }
    }
}


class MultiTreadRunnable implements Runnable{

    private int ticket = 10;

    private String name;

    public MultiTreadRunnable(String name){
        this.name = name;
    }

    @Override
    public void run(){
        while (ticket > 0){
            System.out.println(ticket-- +" is sold by " + name);
        }
    }
}

class MultiTread extends Thread {

    private int ticket = 10;

    public MultiTread(String name){
        super(name);
    }

    public void run(){
        while (ticket > 0){
            System.out.println(ticket-- +" is sold by " + Thread.currentThread().getName());
        }
    }
}

