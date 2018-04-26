package com.gg.spring.bean;

/**
 * hello world ¿‡
 * @author sjf0115
 *
 */
public class HelloWorld {
	private String name;
 
	public void setName(String name) {
		this.name = name;
	}
	
	public void sayHello(){
		System.out.println("welcome "+ name +" to spring world...");
	}
}
