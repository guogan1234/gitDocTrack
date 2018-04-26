package org.shirdrn.spring.remote.rmi;

public interface AccountService {
	int queryBalance(String mobileNo);  
    String shoopingPayment(String mobileNo, byte protocol);  
}
