/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.entity.user.converters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.avp.mems.backstage.entity.user.ProjectLocationPK;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author Amber
 * 
 * @date 2016-8-25 17:48:42
 */
public class StringToProjectLocationPK implements Converter<String, ProjectLocationPK>{
    
    @Override
    public ProjectLocationPK convert(String s) {
        ProjectLocationPK pk = new ProjectLocationPK();
        Pattern p = Pattern.compile("\\[(.+)\\]");
        Matcher m = p.matcher(s);
        if(m.find()) {
            String[] its = m.group(1).split(",");
            for(String g : its){
                if(g.contains("projectId"))
                    pk.setProjectId(Long.parseLong(g.split("=")[1]));
                else if(g.contains("locationId"))
                    pk.setLocationId(Long.parseLong(g.split("=")[1]));
            }
        }
        return pk;
    }
}
