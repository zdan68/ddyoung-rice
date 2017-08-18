package com.self.ddyoung.rice.service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by sanbian on 2017/8/16.
 */
public class ListDemo {
    public static final int N=10;

    public static List values;

    public static void main(String args[]){
        create();

        final List<Integer> list = new Vector<>(values);

        // 线程一：通过Iterator遍历List
        new Thread(new Runnable() {
            @Override
            public void run() {
                printList(list);
            }
        }).start();

        // 线程二：remove一个元素
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 由于程序跑的太快，这里sleep了1秒来调慢程序的运行速度
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.remove(4);
                System.out.println("list.remove(4)");
            }
        }).start();
    }

    static void create(){
        Integer vals[]=new Integer[N];

        Random r=new Random();

        for(int i = 0; i<N; i++){
            vals[i] = i;
        }

        values=Arrays.asList(vals);
    }
    
    static void printList( List list){
        list.forEach(item -> {
            System.out.println(item);
            // 由于程序跑的太快，这里sleep了1秒来调慢程序的运行速度
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 比较 get随机访问 arrayList 优于 linkedList
     * 二分查找法使用的随机访问(random access)策略，而LinkedList是不支持快速的随机访问的。对一个LinkedList做随机访问所消耗的时间与这个list的大小是成比例的。而相应的，在ArrayList中进行随机访问所消耗的时间是固定的
     * @param lst
     * @return
     */
    static long getTime(List lst){
        long start=System.currentTimeMillis();
        for(int i=0;i<N;i++){
            int index=Collections.binarySearch(lst, values.get(i));
            System.out.println("***遍历元素***"+index);
            if(index!=i)
                System.out.println("***错误***index="+index+",i="+i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return System.currentTimeMillis()-start;
    }

    /**
     * 比较 重复的在一个列表的开端插入一个元素 linkedList 优于 arrayList
     * 利用Collections.reverse方法对列表进行反转时，linkedList性能就要好些。
     * 当一个元素被加到ArrayList的最开端时，所有已经存在的元素都会后移，这就意味着数据移动和复制上的开销。相反的，将一个元素加到LinkedList的最开端只是简单的未这个元素分配一个记录，然后调整两个连接。在LinkedList的开端增加一个元素的开销是固定的，而在ArrayList的开端增加一个元素的开销是与ArrayList的大小成比例的。
     * @param list
     * @return
     */
    static long addTime(List list){
        long start=System.currentTimeMillis();
        Object o = new Object();
        for(int i=0;i<N;i++){
            list.add(0, o);
        }
        return System.currentTimeMillis()-start;
    }

    static long removeTime(List list){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long start=System.currentTimeMillis();
        for(int i=0;i<N;i++){
            list.remove(0);
        }
        return System.currentTimeMillis()-start;
    }

}
