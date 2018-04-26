package com.avp.mem.njpb.oauth.repository.eventHandler;

import com.avp.mem.njpb.oauth.entity.SysUser;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Hongfei
 */
@Component
@RepositoryEventHandler(SysUser.class)
public class SysUserEventHandler {
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    
    @HandleBeforeCreate
    public void handleBeforeCreateUser(SysUser user) {
        user.setUserPassword(encoder.encode(user.getUserPassword()));
    }
}
