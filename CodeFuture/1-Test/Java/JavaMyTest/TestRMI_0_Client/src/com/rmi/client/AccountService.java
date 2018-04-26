package com.rmi.client;

public interface AccountService {
	int queryBalance(String mobileNo);  
    String shoopingPayment(String mobileNo, byte protocol);  
}
