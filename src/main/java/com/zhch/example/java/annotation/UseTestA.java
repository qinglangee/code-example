package com.zhch.example.java.annotation;

import java.util.HashMap;
import java.util.Map;

/**
 * 这个类专门用来测试注解使用
 * @author tmser
 */
@TestA(name = "inClass", gid = Long.class) // 使用了类注解
public class UseTestA {

    @TestA(name = "field-age", gid = Long.class) // 使用了类成员注解
    private Integer age;

    @TestA(name = "constructor-UseTestA", id = 1234, gid = Long.class) // 使用了构造方法注解
    public UseTestA() {

    }

    @TestA(name = "method-haha", id = 1234, gid = Long.class) // 使用了类方法注解
    public void haha() {
        @TestA(name = "local-mimi", id = 1234, gid = Long.class) // 使用了局部变量注解
        Map mimi = new HashMap(0);
    }

    public void b(@TestA(name = "param-a", id = 1234, gid = Long.class) Integer a) { // 使用了方法参数注解

    }
}
