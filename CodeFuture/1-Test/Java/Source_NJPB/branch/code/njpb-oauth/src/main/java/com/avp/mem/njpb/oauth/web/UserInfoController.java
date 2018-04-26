package com.avp.mem.njpb.oauth.web;

import java.io.IOException;
import java.security.Principal;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Hongfei
 */
@RestController
public class UserInfoController {

    @RequestMapping("/user")
    public Principal user(Principal user) {
        Authentication principal = ((OAuth2Authentication) user).getUserAuthentication();

        return user;
    }

    @RequestMapping(value = "/lout")
    public void logoutPage(@RequestParam String b, HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            SecurityContextHolder.clearContext();
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
        }
        Cookie cookie = new Cookie("JSESSIONID", "");
        cookie.setPath(request.getContextPath() + "/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        try {
            response.sendRedirect(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
