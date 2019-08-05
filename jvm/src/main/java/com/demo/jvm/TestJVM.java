package com.demo.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 对于静态字段来说，只有直接定义了该字段的类才会被初始化
 *
 */
public class TestJVM {

    public static void main(String[] args) throws InterruptedException {
//        System.out.println(MyChild1.str2);
//        List<MyChild1> list = new ArrayList<>();
//        while (true) {
//            list.add(new MyChild1());
//            Thread.sleep(1000);
//        }
        String s1 = "good";
        String s2 = "good";
        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));
    }
}

class MyParent1 {

    public static String str = "hello world";

    static {
        System.out.println("MyParent1 static block");
    }
}

class MyChild1 extends MyParent1{

    public static String str2 = "hi";

    static {
        System.out.println("MyChild1 static block");
    }
}