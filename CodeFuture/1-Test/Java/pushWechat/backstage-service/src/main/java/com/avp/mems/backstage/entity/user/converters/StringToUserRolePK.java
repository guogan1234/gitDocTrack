/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.entity.user.converters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.avp.mems.backstage.entity.user.UserRolePK;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author Amber
 * 
 * @date 2016-8-25 17:48:42
 */
public class StringToUserRolePK implements Converter<String, UserRolePK>{
    
    @Override
    public UserRolePK convert(String s) {
        UserRolePK pk = new UserRolePK();
        Pattern p = Pattern.compile("\\[(.+)\\]");
        Matcher m = p.matcher(s);
        if(m.find()) {
            String[] its = m.group(1).split(",");
            for(String g : its){
                if(g.contains("userName"))
                    pk.setUserName(g.split("=")[1]);
                else if(g.contains("roleName"))
                    pk.setRoleName(g.split("=")[1]);
            }
        }
        return pk;
    }
}
