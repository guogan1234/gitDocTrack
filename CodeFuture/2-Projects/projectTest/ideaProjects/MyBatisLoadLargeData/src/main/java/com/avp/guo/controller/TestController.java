package com.avp.guo.controller;

import com.avp.guo.entity.DataModel;
import com.avp.guo.model.paramModel;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guo on 2018/2/24.
 */
public class TestController {
    public void test(){
        try{
            Reader reader = Resources.getResourceAsReader("Configure.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();

            System.out.println("start...");
            //数据库操作
            paramModel p = new paramModel();
            p.setPageSize(1000);
            p.setOffset(0);
            List<DataModel> list = session.selectList("LoadData.getData",p);
            System.out.println("end!!!--" + list.size());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void test2(){
        try{
            Reader reader = Resources.getResourceAsReader("Configure.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();

            System.out.println("start...");
            //数据库操作
            List<DataModel> list = new ArrayList<DataModel>();
            for(int i = 0;i<1000;i++){
                list.clear();
                paramModel p = new paramModel();
                p.setPageSize(1000);
                p.setOffset(1000*i);
                //方式-：运行出错，5分钟左右内存溢出
//                List<DataModel> list = session.selectList("LoadData.getData",p);
                //方式二：运行耗时--170s(带每页打印1条调试信息)，占用内存8%左右(8G总内存)
                list = session.selectList("LoadData.getData",p);
                if(i == 0){
                    String str = list.get(0).getCol_3();
                    System.out.println("str--" + str);
                }
                System.out.println( i + "--page--" +list.size());
            }
//            System.out.println("end!!!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
