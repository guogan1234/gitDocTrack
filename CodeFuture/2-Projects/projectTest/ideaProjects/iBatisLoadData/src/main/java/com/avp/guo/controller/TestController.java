package com.avp.guo.controller;

import com.avp.guo.entity.MappedModel;
import com.avp.guo.entity.TestConn;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.io.Reader;
import java.util.List;

/**
 * Created by guo on 2018/2/22.
 */
public class TestController {
    public void testConn(){
        try {
            Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlMapClient smc = SqlMapClientBuilder.buildSqlMapClient(rd);
            System.out.println("start test conn...");
            List<TestConn> list = smc.queryForList("TestConn.getAll",null);
            System.out.println("len:" + list.size());
            for(TestConn temp:list){
                System.out.println("------");
                System.out.println("A--" + temp.getId());
                System.out.println("B--" + temp.getRemark());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void testInsert(){
        try{
            Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlMapClient smc = SqlMapClientBuilder.buildSqlMapClient(rd);
            System.out.println("start test insert...");
            TestConn model = new TestConn();
            model.setId(2);
            model.setRemark("insert model!!!");
            smc.insert("TestConn.insert",model);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void testDelete(){
        try{
            Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlMapClient smc = SqlMapClientBuilder.buildSqlMapClient(rd);
            System.out.println("start test delete...");
            smc.delete("TestConn.delete",2);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void testUpdate(){
        try {
            Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlMapClient smc = SqlMapClientBuilder.buildSqlMapClient(rd);
            System.out.println("start test update...");
            TestConn model = new TestConn();
            model.setId(1);
            model.setRemark("111");
            smc.update("TestConn.update",model);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void testSelectToMappedClass(){
        try{
            Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlMapClient smc = SqlMapClientBuilder.buildSqlMapClient(rd);
            System.out.println("start testSelectToMappedClass...");
            MappedModel model = (MappedModel)smc.queryForObject("TestConn.selectForMyClass",1);
            System.out.println("model--" + model.getValue());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void testDynamicSQL(){
        try {
            Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlMapClient smc = SqlMapClientBuilder.buildSqlMapClient(rd);
            System.out.println("start test dynamicSQL...");
            List<TestConn> list_1 = smc.queryForList("TestConn.dynamicSQL");
            System.out.println("len-1:" + list_1.size());

            TestConn model = new TestConn();
            model.setId(1);
            List<TestConn> list_2 = smc.queryForList("TestConn.dynamicSQL",model);
            System.out.println("len-2:" + list_2.size());

            //报错，传入参数object中没有属性“id”
            //ProbeException: There is no READABLE property named 'id' in class 'java.lang.Integer'
            List<TestConn> list_3 = smc.queryForList("TestConn.dynamicSQL",1);
            System.out.println("len-3:" + list_3.size());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
