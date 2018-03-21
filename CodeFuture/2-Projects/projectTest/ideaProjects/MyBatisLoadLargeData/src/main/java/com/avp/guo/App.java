package com.avp.guo;

import com.avp.guo.controller.TestController;

import java.util.Date;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        Date start = new Date();
        System.out.println(start);

        TestController testController = new TestController();
//        testController.test();
        testController.test2();

        Date end = new Date();
        System.out.println(end);
    }
}
