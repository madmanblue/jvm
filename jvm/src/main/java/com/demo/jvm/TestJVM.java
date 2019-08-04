package com.demo.jvm;

/**
 * 对于静态字段来说，只有直接定义了该字段的类才会被初始化
 *
 */
public class TestJVM {

    public static void main(String[] args) {
        System.out.println(MyChild1.str2);
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