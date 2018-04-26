/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.push.domain;

import com.avp.mems.push.entities.PushInfo;
import com.avp.mems.push.repositories.PushInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author GD
 */
public abstract class AbstractPushService implements PushService {

    @Autowired
    PushInfoRepository pushInfoRepository;

    @Override
    public void push(Message message) {

        //应至少指定一个App名称，否则抛出异常
        if(isListNullOrEmpty(message.getTargetApps())) {
            throw new RuntimeException("应至少指定一个应用名称！");//TODO: throw AppException
        }

        //若指定了多个app，则不考虑用户与角色，仅按照app名称整体推送
        if(message.getTargetApps().size() > 2) {
            pushToApp(message);
            return;
        }

        //若目标用户列表与目标角色列表均为空，则不论指定一个或是多个App，统一按照App整体推送
        if(isListNullOrEmpty(message.getTargetUsers())
                && isListNullOrEmpty(message.getTargetRoles())){
            pushToApp(message);
            return;
        }

//        if(message.getTargetUsers() != null && message.getTargetUsers().size() == 1
//                && isListNullOrEmpty(message.getTargetRoles())) {
//            pushToUser(message);
//            return;
//        }

        pushToUsers(message, getClientIds(message));
    }
//
//    protected abstract void pushToUser(Message message);
//
//    protected abstract void pushToUsers(Message message);

    protected abstract void pushToUsers(Message message, List<PushInfo> clientIds);

    protected abstract void pushToApp(Message message);

    private boolean isListNullOrEmpty(List list) {
        if(list == null || list.isEmpty())
            return true;

        return false;
    }

    /**
     * 根据传入Message对象的实际特征获取设备ID。目前仅支持获取单个用户的一或多个设备ID
     * @param message
     * @return
     */
    protected List<PushInfo> getClientIds(Message message){
        List<PushInfo> clientIdList = new ArrayList<>();

        //若用户名列表与角色列表均为空，直接返回
        if(isListNullOrEmpty(message.getTargetUsers())
                && isListNullOrEmpty(message.getTargetRoles())) {
            return clientIdList;
        }

        List<String> targetUsers = message.getTargetUsers();
        targetUsers.addAll(getUsersByRoles(message.getTargetRoles()));
        clientIdList = pushInfoRepository.findByUserNames(targetUsers);
        return clientIdList;
    }

    protected Collection<? extends String> getUsersByRoles(List<String> targetRoles) {
        //TODO:填充实际方法逻辑
        return new ArrayList<String>();
    }
}
