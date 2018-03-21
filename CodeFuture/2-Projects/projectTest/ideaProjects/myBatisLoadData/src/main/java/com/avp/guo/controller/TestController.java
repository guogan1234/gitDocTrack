package com.avp.guo.controller;

import com.avp.guo.entity.TestConn;
import com.avp.guo.repository.TestConnDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;
import java.util.List;

/**
 * Created by guo on 2018/2/22.
 */
public class TestController {
    public void testConn(){
        try{
            Reader reader = Resources.getResourceAsReader("Configure.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();

            System.out.println("start test conn...");
            //数据库操作
            TestConn temp = (TestConn)session.selectOne("myMapper.GetUserByID",1);
            System.out.println("temp--" + temp.getRemark());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void testInsert(){
        try{
            Reader reader = Resources.getResourceAsReader("Configure.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();

            System.out.println("start test insert...");
            TestConnDao testConnDao = session.getMapper(TestConnDao.class);
            TestConn model = new TestConn();
            model.setId(2);
            model.setRemark("insert model!!!");
            testConnDao.insertOne(model);

            //使用MyBatis必须commit才会将数据更新到数据库。若只是查询(不会修改数据库数据)，则可以不用commit。
            session.commit();
            System.out.println("end insert!!!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void testUpdate(){
        try{
            Reader reader = Resources.getResourceAsReader("Configure.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();

            System.out.println("start test update...");
            TestConnDao testConnDao = session.getMapper(TestConnDao.class);
            TestConn testConn = new TestConn();
            testConn.setId(1);
            testConn.setRemark("update model!!!");

            testConnDao.updateOne(testConn);
            session.commit();
            System.out.println("end update!!!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void testDelete(){
        try {
            Reader reader = Resources.getResourceAsReader("Configure.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();

            System.out.println("start test delete...");
            TestConnDao testConnDao = session.getMapper(TestConnDao.class);
            testConnDao.deleteOne(2);

            session.commit();
            System.out.println("end delete!!!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void testSelectOne(){
        try{
            Reader reader = Resources.getResourceAsReader("Configure.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();

            System.out.println("start test selectOne...");
            TestConnDao testConnDao = session.getMapper(TestConnDao.class);

            TestConn model = testConnDao.getOne(1);
            System.out.println("end selectOne!!!");
            System.out.println("##--" + model.getRemark());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void testSelectList(){
        try{
            Reader reader = Resources.getResourceAsReader("Configure.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sqlSessionFactory.openSession();

            System.out.println("start test selectList...");
            TestConnDao testConnDao = session.getMapper(TestConnDao.class);

            List<TestConn> list = testConnDao.getList();
            System.out.println("end selectList!!!");
            System.out.println("len--" + list.size());
            for(TestConn t:list){
                System.out.println("@@--" + t.getId());
                System.out.println("##--" + t.getRemark());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
