/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.entity.user.converters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.avp.mems.backstage.entity.user.UserProjectPK;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author Amber
 * 
 * @date 2016-9-18 17:09:13
 */
public class StringToUserProjectPK implements Converter<String, UserProjectPK>{
    
    @Override
    public UserProjectPK convert(String s) {
        UserProjectPK pk = new UserProjectPK();
        Pattern p = Pattern.compile("\\[(.+)\\]");
        Matcher m = p.matcher(s);
        if(m.find()) {
            String[] its = m.group(1).split(",");
            for(String g : its){
                if(g.contains("userName"))
                    pk.setUserName(g.split("=")[1]);
                else if(g.contains("projectName"))
                    pk.setProjectName(Integer.parseInt(g.split("=")[1]));
            }
        }
        return pk;
    }
}
