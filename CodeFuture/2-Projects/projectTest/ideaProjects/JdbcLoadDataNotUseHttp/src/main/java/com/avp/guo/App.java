package com.avp.guo;

import com.avp.guo.controller.testController;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        testController testClass = new testController();
//        testClass.test();

        testClass.test_2(1000);//3000比1000耗时少
    }
}
