package com.avp.guo.controller;

import com.avp.guo.entity.GroupWithUsers;
import com.avp.guo.entity.UserWithGroups;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;

/**
 * Created by guo on 2018/2/23.
 */
public class ManyToManyController {
    public void test(){
        try{
            Reader reader = Resources.getResourceAsReader("Configure.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();

            System.out.println("start test ManyToMany...");
            UserWithGroups model = session.selectOne("ManyToMany.getUserWithGroups",1);
            System.out.println("end ManyToMany!!!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void test_2(){
        try{
            Reader reader = Resources.getResourceAsReader("Configure.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();

            System.out.println("start test ManyToMany test_2...");
            GroupWithUsers model = session.selectOne("ManyToMany.getGroupWithUsers",1);
            System.out.println("end ManyToMany!!!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
