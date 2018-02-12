package com.avp.guo;

import com.avp.guo.Thread.TestThread;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        try {
            for (int i = 0; i < 5; i++) {
                TestThread t = new TestThread();
                t.id = i;
                t.start();
                if (i == 2) {
                    System.out.println("join!!!---------------------join!!!");
                    t.join();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

//        try {
//            YieldThread y = new YieldThread();
//            y.start();
//            y.join();//调用join方法后，阻塞调用时所在线程
//
////            Thread.sleep(1000);
//            System.out.println("after sleep!!!");
//            TestThread t = new TestThread();
//            t.start();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
    }
}
