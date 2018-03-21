package com.avp.guo.controller;

import com.avp.guo.entity.User;
import com.avp.guo.entity.UserForMany;
import com.avp.guo.entity.UserPost;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;
import java.util.List;

/**
 * Created by guo on 2018/2/23.
 */
public class DynamicSQLController {
    public void test(){
        try{
            Reader reader = Resources.getResourceAsReader("Configure.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();

            System.out.println("start test dynamicSQL...");
            List<UserForMany> list_1 = session.selectList("DynamicSQL.GetUserByID");
            System.out.println("len-1:" + list_1.size());

            UserForMany model = new UserForMany();
            model.setId(1);
            List<UserForMany> list_2 = session.selectList("DynamicSQL.GetUserByID",model);
            System.out.println("len-2:" + list_2.size());

            //报错--ReflectionException: There is no getter for property named 'id' in 'class java.lang.Integer'
            List<UserForMany> list_3 = session.selectList("DynamicSQL.GetUserByID",1);
            System.out.println("len-3:" + list_3.size());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void testContainClassId(){
        try{
            Reader reader = Resources.getResourceAsReader("Configure.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();

            System.out.println("start test dynamicSQL with ContainClassId...");

            User u = new User();
            u.setId(1);
            u.setUsername("test");
            UserPost userPost = new UserPost();
            userPost.setPostId(111);
            userPost.setUserId(111);
            userPost.setTitle("title");
            userPost.setContent("content");
            userPost.setUser(u);

            List<UserForMany> list = session.selectList("DynamicSQL.getUserByContainClassId",userPost);
            System.out.println("len:" + list.size());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
