package com.avp.guo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
//        //不是Spring容器构造的类TestController，其内部的autowire注册的对象为null
//        TestController t = new TestController();
//        t.test();
        ApplicationContext appContext = SpringApplication.run(App.class,args);
    }
}
