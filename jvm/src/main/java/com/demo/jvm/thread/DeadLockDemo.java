package com.demo.jvm.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 死锁demo
 *
 * @author blue
 */
public class DeadLockDemo {

    private static String A = "a";
    private static String B = "b";

    public static void main(String[] args) {
        new DeadLockDemo().deadLock();
    }

    private void deadLock() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (A) {
                    try{
                        System.out.println("t1获得A锁");
                        Thread.sleep(2000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    synchronized (B){
                        System.out.println("t1获得B锁");
                        System.out.println(1);
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (B) {
                    System.out.println("t2获得B锁");
                    synchronized (A){
                        System.out.println("t2获得A锁");
                        System.out.println(2);
                    }
                }
            }
        });
        t1.start();
        t2.start();
    }

    /**
     * 避免死锁的几种思考方式
     * 1 避免一个线程同时获取多个锁
     * 2 避免一个线程在锁内同时占用多个资源，尽量保证每个锁只占用一个资源
     * 3 尝试使用定时锁，lock.tryLock(timeout)来替代内部锁机制
     * 4 对于数据库锁，加锁和解锁必须在同一个数据库链接里，否则会出现解锁失败的情况
     */

}
