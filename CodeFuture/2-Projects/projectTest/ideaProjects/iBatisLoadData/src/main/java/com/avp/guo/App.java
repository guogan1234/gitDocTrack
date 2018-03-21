package com.avp.guo;

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
        //测试iBatis连接postgreSQL数据库
//        testController.testConn();
        //测试ibatis连接postgreSQL数据库，并插入记录
//        testController.testInsert();
        //测试ibatis连接postgreSQL数据库，并删除记录
//        testController.testDelete();
        //测试ibatis连接postgreSQL数据库，并修改记录
//        testController.testUpdate();
        //查看结果
//        testController.testConn();
        //测试将查询结果映射为自定义对象
//        testController.testSelectToMappedClass();
        //测试动态SQL
        testController.testDynamicSQL();
    }
}
