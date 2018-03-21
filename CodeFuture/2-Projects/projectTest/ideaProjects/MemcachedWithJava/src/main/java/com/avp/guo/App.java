package com.avp.guo;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        TestClass testClass = new TestClass();
        //1.测试简单的读写
//        testClass.testAdd();
//        testClass.testGet();
        //2.测试对象的读写
        testClass.testUseClass();
        //3.测试对象列表的读写
//        testClass.testUseList();
    }
}
