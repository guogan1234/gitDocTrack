package com.avp.guo.Thread;

/**
 * Created by guo on 2018/2/6.
 */
public class YieldThread extends Thread {
    public void run(){
        try {
            long id = Thread.currentThread().getId();
            long beginTime = System.currentTimeMillis();
            int count = 0;
            for (int i = 0; i < 5000; i++) {
                count = count + (i + 1);
//                System.out.println(id + " -- " + beginTime);
                System.out.println(id + "--yield");
//            Thread.yield();//释放CPU时间片，交给CPU系统调度分配，有可能还分配到此线程。和并发抢占，差别不大
                Thread.sleep(1000);

                System.out.println(id + "--" + i);
            }
            long endTime = System.currentTimeMillis();
            System.out.println("用时：" + (endTime - beginTime) + " 毫秒！");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
