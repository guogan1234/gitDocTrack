package com.avp.guo.controller;

import com.avp.guo.entity.DataModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by guo on 2018/2/12.
 */
public class TestController {
    public void test(){
        //Session session = new AnnotationConfiguration().configure().buildSessionFactory().openSession();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml")
                .build();
        // 2. 根据服务注册类创建一个元数据资源集，同时构建元数据并生成应用一般唯一的的session工厂
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        /**** 上面是配置准备，下面开始我们的数据库操作 ******/
        Session session = sessionFactory.openSession();// 从会话工厂获取一个session
        Transaction t = session.beginTransaction();

        String hql = "from DataModel d where d.id=1";
        Query query = session.createQuery(hql);
        List list = query.list();
        System.out.println("@--" + list.size());

        t.commit();
        session.close();
    }

    public void test_2() {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        //session factory
        SessionFactory factory = cfg.buildSessionFactory();

        //session
        Session session = factory.openSession();

        //测试获取session的第二种方式--运行成功
//        String hql = "from DataModel d where d.id=1";
//        Query query = session.createQuery(hql);
//        List list = query.list();
//        System.out.println("#--" + list.size());

        //使用HQL查询(分页查询)
        //100W条数据记录，每条记录大约512Byte(共50个字段，每个字段10Byte)
        //每页1000条，查询1000次--运行5分钟左右，OutOfMemoryError: Java heap space
        //每页100条，查询10000次--运行10分钟左右，OutOfMemoryError: GC overhead limit exceeded
        for (int i = 0; i < 10000; i++) {
            String hql = "from DataModel";
            Query query = session.createQuery(hql);
            //设置分页查询参数
            //传入两个参数page、size
//            int page = 2;//第几页
            int size = 100;//每页几条
            int begin = i * size;
            query.setFirstResult(begin);//设置抓取起点
            query.setMaxResults(size);//设置抓取多少条

            List<DataModel> list = query.list();
//            System.out.println("#--" + list.get(0).getId());

            System.out.println("@--" + i + " len:" + list.size());
        }
        session.close();
    }
}
