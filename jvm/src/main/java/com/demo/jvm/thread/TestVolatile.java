package com.demo.jvm.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile应用
 *
 * @author blue
 */
public class TestVolatile {

    /*
     * 多线程并发编程中，synchronized , volatile 都扮演着重要的角色，volatile是轻量级的synchronized，他在
     * 多处理器开发中保证了共享变量的可见性。
     *
     * volatile：
     *       编译后代码中插入lock前缀指令，而lock 前缀指令会使变量缓存写回系统内存。这个写回内存的操作也会导致其他CPU缓存的数据无效。
     *       通过嗅探总线技术来完成
     *
     * synchronized:
     *       java对象可以作为锁。
     *       对于普通同步方法，锁是当前实例对象。
     *       对于静态同步方法，锁是当前类的class对象。
     *       对于同步方法块，锁是Synchronized括号里的对象。
     *
     *       synchronized锁：jvm基于进入和退出Monitor对象来实现方法同步和代码块同步。
     *       代码块同步是使用monitorenter和monitorexit指令实现的
     *
     *       monitorenter指令是编译后插入同步代码块开始的位置，而monitorexit是在方法结束和异常处。
     *
     * Java对象头：
     *       synchronized用的锁存在对象头中
     *       长度：32/64bit  MarkWord 对象的hashCode()或锁信息==》是否有锁+hashcode+对象分代年龄+是否是偏向锁+标志位
     *            32/64bit  Class Metadata Address 存储对象类型数据的指针
     *            32/32bit  Array length           数组的长度（如果对象是数组）
     *
     * 锁的升级与对比：
     *       无锁状态：
     *       偏向锁：
     *               线程获取对象锁的时候，在对象头mark word存储偏向线程ID，以后该线程进入和退出，不需要进行CAS操作进行加锁和解锁，只需要测试当前
     *               对象头中是否保存了指向该线程的偏向锁。如果成功，则获取到锁，如果失败，则测试下当前偏向锁状态是否为1，如果没有，则
     *               CAS竞争锁，如果设置了，尝试使用CAs将对象头的偏向锁指向当前线程。
     *
     *               撤销的时候，当前偏向锁指向的线程暂停，检查该线程是否或者，如果线程不活动，则将对象头设置为无锁状态。
     *               如果活动，则拥有偏向锁的栈会被执行。遍历偏向对象的锁记录，栈中的锁记录和对象的Mark word要么重新偏向于其他线程
     *               要么恢复无锁状态或标记对象不适合作为偏向锁，最后唤醒暂停的线程。
     *       轻量级锁：
     *
     *
     *       重量级锁：
     *
     * 原子性：
     *   CPU中
     *   1。使用总线锁
     *   2。使用缓存锁
     *   Java中
     *   java中通过锁和CAS保证原子性
     *   循环CAS直到成功
     *
     *
     * */
    private volatile String vo;

    private synchronized String test(){
        return "";
    }

    private synchronized static void testt(){
        System.out.println("=");
    }

    private int i = 0;
    private AtomicInteger au = new AtomicInteger(0);

    public static void main(String[] args) {
        final TestVolatile counter = new TestVolatile();
        List<Thread> list = new ArrayList<>(600);
        long start = System.currentTimeMillis();
        for (int j = 0; j < 100; j++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        counter.unsafeIncrement();
                        counter.safeIncrement();
                    }
                }
            });
            list.add(thread);
        }

        for (Thread thread : list) {
            thread.start();
        }

        for (Thread thread : list) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis();
        System.out.println(counter.i);
        System.out.println(counter.au.get());
        System.out.println("total time : " + (end - start));
    }

    private void safeIncrement() {
        for (; ; ) {
            int i = au.get();
            boolean compareAndSet = au.compareAndSet(i, ++i);
            if (compareAndSet) {
                break;
            }
        }
    }

    private void unsafeIncrement() {
        i++;
    }
}
