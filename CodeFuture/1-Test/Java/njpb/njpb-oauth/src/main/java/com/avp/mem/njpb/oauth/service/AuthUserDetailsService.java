package com.avp.mem.njpb.oauth.service;

import com.avp.mem.njpb.oauth.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Hongfei
 */
@Component("authUserDetailsService")
public class AuthUserDetailsService implements UserDetailsService {
    
    @Autowired
    SysUserRepository sysUserRepository;

    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        UserDetails userDetails = sysUserRepository.findOneByUserAccount(string);;
        return userDetails;
    }
    
}
