package com.zhch.example.java.reflection;

/**
 * from: Java获取当前类名、方法名 http://blog.csdn.net/a578559967/article/details/7688971
 * @author zhch 2017年5月25日
 *
 */
public class GetMethodName {

    /**
     * 比较： 1）方法1不知有没有什么使用限制？
     * 2）方法2通过异常机制获取调用栈，性能最差，但能提供其它方法所不具有的功能，还可以获取方法名，行号等等；但这么使用多少有点不太常规；
     * 3）方法3只是简单分析了一下匿名类的名称，显然要简单多，事实上性能也是最高的；
     * 4）方法4感觉和方法3有点类似，比方法3正规了点
     *
     *
     * 执行100w次， 第一种方法：1718ms 第二种方法：4843ms 第三种方法：47ms 第四种方法：6484ms
     */

    public static void testGetClassName() {
        // 方法1：通过SecurityManager的保护方法getClassContext()
        String clazzName = new SecurityManager() {
            public String getClassName() {
                return getClassContext()[1].getName();
            }
        }.getClassName();
        System.out.println(clazzName);
        // 方法2：通过Throwable的方法getStackTrace()
        String clazzName2 = new Throwable().getStackTrace()[1].getClassName();
        System.out.println(clazzName2);
        // 方法3：通过分析匿名类名称()
        String clazzName3 = new Object() {
            public String getClassName() {
                String clazzName = this.getClass().getName();
                return clazzName.substring(0, clazzName.lastIndexOf('$'));
            }
        }.getClassName();
        System.out.println(clazzName3);
        // 方法4：通过Thread的方法getStackTrace()
        String clazzName4 = Thread.currentThread().getStackTrace()[2].getClassName();
        System.out.println(clazzName4);
    }

    /**
     * 执行100w次： 第一种：4856ms 第二种：6337ms
     *
     *
     * 说明：
     *
     * 1.Exception类继承于Throwable,所以有的地方用Exception调用那个getStackTrace，其实调用的还是Throwable的
     *
     * 2.不同的jdk版本调用getStackTrace后得到的数组不太一样，下标多试几次就知道了，以上是jdk1.6版本下的
     */
    public static void testGetFunctionName() {
        // 方法1：通过Throwable的方法getStackTrace()
        String funcName2 = new Throwable().getStackTrace()[1].getMethodName();
        System.out.println(funcName2);
        // 方法2：通过Thread的方法getStackTrace()
        String clazzName4 = Thread.currentThread().getStackTrace()[2].getMethodName();
        System.out.println(clazzName4);
    }

    public static void testEntry() {

        testGetClassName();
        testGetFunctionName();
    }

    public static void main(String[] args) {
        testEntry();
    }
}
