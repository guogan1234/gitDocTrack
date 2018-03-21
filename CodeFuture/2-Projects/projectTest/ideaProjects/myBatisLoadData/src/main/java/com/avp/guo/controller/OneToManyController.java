package com.avp.guo.controller;

import com.avp.guo.entity.User;
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
public class OneToManyController {
    public void test(){
        try{
            Reader reader = Resources.getResourceAsReader("Configure.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();

            System.out.println("start test OneToMany...");
            User user = session.selectOne("OneToMany.GetUserPosts",1);
            System.out.println("-----------" + user.getUsername());
            List<UserPost> list = user.getPosts();
            System.out.println("len--" + list.size());
            for(UserPost up:list){
                System.out.println("##--" + up.getTitle());
            }
            System.out.println("end OneToMany!!!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
