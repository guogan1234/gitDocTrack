package com.rmi.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RMIClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext ctx = new ClassPathXmlApplicationContext("com/rmi/client/client.xml");
		AccountService client = (AccountService)ctx.getBean("mobileAccountService");
		String res = client.shoopingPayment("13800138000", (byte) 5);
		System.out.println(res);
	}

}
