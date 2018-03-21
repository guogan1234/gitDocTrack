package com.avp.guo;

import com.avp.guo.entity.User;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guo on 2018/3/7.
 */
public class TestClass {
    public static MemCachedClient mcc = new MemCachedClient();
//    public SockIOPool pool = null;//Error，static静态块不能返回非静态类成员
    public static SockIOPool pool = null;

    static {
        String str = "123456";
        // 服务器列表和其权重
        String[] servers = {"127.0.0.1:11211"};
        Integer[] hh = {1, 3};
        Integer[] weights = {3};

//    private SockIOPool sockIOPool = null;

        //获取socke连接池的实例对象
        pool = SockIOPool.getInstance();//静态方法，必须在static代码块中使用

        // 设置服务器信息
        pool.setServers(servers);
        pool.setWeights(weights);

        // 设置初始连接数、最小和最大连接数以及最大处理时间
        pool.setInitConn(5);
        pool.setMinConn(5);
        pool.setMaxConn(250);
        pool.setMaxIdle(1000 * 60 * 60 * 6);

        // 设置主线程的睡眠时间
        pool.setMaintSleep(30);

        // 设置TCP的参数，连接超时等
        pool.setNagle(false);
        pool.setSocketTO(3000);
        pool.setSocketConnectTO(0);

        // 初始化连接池
        pool.initialize();

        // 压缩设置，超过指定大小（单位为K）的数据都会被压缩
//        mcc.setCompressEnable(true);
//        mcc.setCompressThreshold(64 * 1024);
    }

    public void testAdd(){
        boolean b = mcc.add("GG","gg_value");
        System.out.println("testAdd--" + b);
    }

    public void testGet(){
        String str = "";
        str = (String) (mcc.get("GG"));
        System.out.println("testGet--" + str);
    }

    public void testUseClass(){
        try {
            User user = new User();
//            user.setName("雷军");
//            user.setAddress("武汉");
            user.setName("GG");
            user.setAddress("dmxy");

            System.out.println("-----1-----");
            //写入文件的对象，需序列化
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("b.txt")));
            oos.writeObject(user);

            System.out.println("-----2-----");
            boolean b = mcc.add("object", user);
            System.out.println("b--" + b);//若class未序列化，则add失败，返回false

            System.out.println("-----3-----");
            User temp;
            temp = (User) mcc.get("object");
            System.out.println("res--" + temp.getAddress());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void testUseList(){
        User user = new User();
        user.setName("雷军");
        user.setAddress("武汉");

        User u2 = new User();
        u2.setName("马云");
        user.setAddress("杭州");

        List<User> list = new ArrayList<User>();
        list.add(user);
        list.add(u2);

        boolean b = mcc.add("list",list);
        System.out.println("b--" + b);

        List<User> temp = new ArrayList<User>();
        temp = (List<User>) mcc.get("list");
        System.out.println("len--" + temp.size());
    }
}
