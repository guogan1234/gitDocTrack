package com.avp.guo.controller;

import com.avp.guo.entity.DataModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by guo on 2018/2/11.
 */
public class TestController {
    public void test(){
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        //session factory
        SessionFactory factory = cfg.buildSessionFactory();

        //session
        Session session = factory.openSession();

        //直接SQL查询
//        String test_sql = "select * from large.largedatatable where id=1";//
//        Query query = session.createSQLQuery(test_sql);

        //使用HQL查询
//        String test_hql = "from DataModel where DataModel.id=1";//Error--运行报错
        String test_hql = "from DataModel ddd where ddd.id=1";//OK--运行成功
        Query query = session.createQuery(test_hql);
        List<DataModel> list = query.list();
        System.out.println(list.get(0).getId());

        System.out.println("#:" + list.size());
    }

    public void test_2(){
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        //session factory
        SessionFactory factory = cfg.buildSessionFactory();

        //session
        Session session = factory.openSession();

        //使用SQL直接查询--耗时159s
//        for(int i = 0;i<1000;i++){
//            String test_sql = "select * from large.largedatatable limit 1000 offset " + i*1000;//
//            Query query = session.createSQLQuery(test_sql);
//            List<DataModel> list = query.list();
//            //直接SQL查询返回的是List<Object[]>，无法直接获取entity
////            System.out.println(list.get(0).getId());//ClassCastException: [Ljava.lang.Object; cannot be cast to com.avp.guo.entity.DataModel
//
//            System.out.println("@--" + i + " len:" + list.size());
//        }

        //使用HQL查询(分页查询)
        //每页1000条，查询1000次--运行5分钟左右，OutOfMemoryError: Java heap space
        //每页100条，查询10000次--运行10分钟左右，OutOfMemoryError: GC overhead limit exceeded
        for(int i = 0;i<10000;i++){
            String hql = "from DataModel";
            Query query = session.createQuery(hql);
            //设置分页查询参数
            //传入两个参数page、size
//            int page = 2;//第几页
            int size = 100;//每页几条
            int begin = i*size;
            query.setFirstResult(begin);//设置抓取起点
            query.setMaxResults(size);//设置抓取多少条

            List<DataModel> list = query.list();
//            System.out.println("#--" + list.get(0).getId());

            System.out.println("@--" + i + " len:" + list.size());
        }
    }
}
