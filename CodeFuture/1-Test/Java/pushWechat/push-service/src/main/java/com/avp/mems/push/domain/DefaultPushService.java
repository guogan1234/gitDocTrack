/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.push.domain;

import org.springframework.stereotype.Service;

/**
 *
 * @author GD
 */
//@Service
public class DefaultPushService implements PushService {
    public void push(Message message){
        System.out.println("PushMessage{"
                + ", targetUsers=" + message.getTargetUsers() + ", targetRoles=" + message.getTargetRoles() + "}");
        message.getArgs().forEach((String key, String value)->{
            System.out.println( key + " : " + value);
        });
    }
}
