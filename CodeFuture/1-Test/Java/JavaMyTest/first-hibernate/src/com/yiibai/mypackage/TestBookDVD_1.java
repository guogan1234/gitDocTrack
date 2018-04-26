package com.yiibai.mypackage;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class TestBookDVD_1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Configuration cfg=new Configuration();  
        cfg.configure("hibernate.cfg.xml");//populates the data of the configuration file  

        //creating seession factory object  
        SessionFactory factory=cfg.buildSessionFactory();  

        //creating session object  
        Session session=factory.openSession();  

        //creating transaction object  
        Transaction t=session.beginTransaction();
        
        TBOOK tbook = new TBOOK();
        tbook.setId(3);
        tbook.setName("book3");
        tbook.setMaker("maker3");
        tbook.setPageCount(123);
        
        TDVD tdvd = new TDVD();
        tdvd.setId(3);
        tdvd.setName("DVD3");
        tdvd.setMaker("maker3");
        tdvd.setRegionCode("regionCode3");
        
        session.persist(tbook);
        session.persist(tdvd);
        
        /*
        Query query = session.createQuery("from TItem");
        List list = query.list();
        Iterator it = list.iterator();
        while(it.hasNext()) {
        	TItem item = (TItem)it.next();
        	System.out.println(item.getName());
        }*/
        
        t.commit();//transaction is committed  
        session.close();  

	}

}
