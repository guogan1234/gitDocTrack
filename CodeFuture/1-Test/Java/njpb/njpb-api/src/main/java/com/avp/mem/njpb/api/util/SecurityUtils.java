package com.avp.mem.njpb.api.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static Integer getLoginUserId(){
        try {
            String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return Integer.parseInt(principal);
        } catch (Exception e) {
            return null;
        }
    }
    
}
