/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.repositories.push;

import com.avp.mems.backstage.entity.push.PushInfoWechat;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Amber on 2017/6/2.
 */
public interface PushInfoWechatRepository extends PagingAndSortingRepository<PushInfoWechat, String> {

    PushInfoWechat  findByUsername(@Param("username") String username);

    List<PushInfoWechat> findByUsernameInAndWechatidIsNotNull(@Param("usernames") List<String> usernames);}
