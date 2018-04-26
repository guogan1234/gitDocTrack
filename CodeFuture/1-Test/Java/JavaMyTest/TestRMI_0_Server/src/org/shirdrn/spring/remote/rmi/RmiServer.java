package org.shirdrn.spring.remote.rmi;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RmiServer {

	public static void main(String[] args) throws InterruptedException{
		// TODO Auto-generated method stub
		new ClassPathXmlApplicationContext("org/shirdrn/spring/remote/rmi/server.xml");  
		
		System.out.println("start...");
        Object lock = new Object();  
        synchronized (lock) {  
            lock.wait();  
        } 
        System.out.println("end!!!");
	}

}
