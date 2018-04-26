package com.avp.mem.njpb.oauth.web;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Hongfei
 */
@RestController
public class UserInfoController {

    @RequestMapping("/user")
    public Principal user(Principal user) {
        Authentication principal = ((OAuth2Authentication)user).getUserAuthentication();
        return principal;
    }
}
