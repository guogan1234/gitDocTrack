package com.avp.guo.controller;

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
public class ManyToOneController {
    public void test(){
        try {
            Reader reader = Resources.getResourceAsReader("Configure.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();

            System.out.println("start test ManyToOne...");
            List<UserPost> list = session.selectList("ManyToOne.postUser",1);
            System.out.println("len--" + list.size());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
