package com.avp.guo;

import com.avp.guo.controller.DynamicSQLController;
import com.avp.guo.controller.TestController;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        TestController testController = new TestController();
        //测试数据库连接
//        testController.testConn();

        //测试CRUD
//        testController.testInsert();
//        testController.testUpdate();
//        testController.testDelete();
//        testController.testSelectOne();
//        testController.testSelectList();

        //测试表关联关系一对多
//        OneToManyController oneToManyController = new OneToManyController();
//        oneToManyController.test();

        //测试表关联关系多对一
//        ManyToOneController manyToOneController = new ManyToOneController();
//        manyToOneController.test();

        //测试表关联关系多对多
//        ManyToManyController manyToManyController = new ManyToManyController();
////        manyToManyController.test();
//        manyToManyController.test_2();

        //测试动态SQL
        DynamicSQLController dynamicSQLController = new DynamicSQLController();
//        dynamicSQLController.test();
        dynamicSQLController.testContainClassId();
    }
}
