/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.push.entities.converters;

import com.avp.mems.push.entities.PushInfoPK;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author sky
 */
public class StringToPushInfoPK implements Converter<String, PushInfoPK> {

    @Override
    public PushInfoPK convert(String s) {
        PushInfoPK pk = new PushInfoPK();
        Pattern p = Pattern.compile("\\[(.+)\\]");
        Matcher m = p.matcher(s);
        if(m.find()) {
            String[] its = m.group(1).split(",");
            for(String g : its){
                if(g.contains("username"))
                    pk.setUsername(g.split("=")[0]);
                else if(g.contains("os"))
                    pk.setOs(g.split("=")[1]);
            }
        }
        return pk;
    }

}
