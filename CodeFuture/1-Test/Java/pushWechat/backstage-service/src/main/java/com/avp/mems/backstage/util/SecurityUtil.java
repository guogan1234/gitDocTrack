package com.avp.mems.backstage.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static String getLoginUserName(){
        String principal = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal;
    }
    
}
