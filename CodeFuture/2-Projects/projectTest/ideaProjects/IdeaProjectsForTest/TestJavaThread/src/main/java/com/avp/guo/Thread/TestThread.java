package com.avp.guo.Thread;

/**
 * Created by guo on 2018/2/6.
 */
public class TestThread extends Thread {
    public int id;

    public void run(){
        for(int i = 0;i<50000;i++){
            long t_id = Thread.currentThread().getId();
            System.out.println(t_id + " ## " + i);
            System.out.println(id + " @@ " + i);
        }
    }
}
