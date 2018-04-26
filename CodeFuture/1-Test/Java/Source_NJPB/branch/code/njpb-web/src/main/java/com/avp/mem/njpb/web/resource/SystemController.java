package com.avp.mem.njpb.web.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.security.Principal;

/**
 * Created by len on 2017/8/30.
 */
@Controller
public class SystemController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);

    @Value("${app.oauth2.logout}")
    private String logout;

    public static final String TITLE_USRLIST = "用户列表";
    public static final String TITLE_ROLELIST = "角色列表";
    public static final String TITLE_PARAM = "参数配置";
    public static final String TITLE_MESSAGE = "参数配置";
    public static final String TITLE_VERSION = "版本";

    @RequestMapping(value = "/user/")
    public ModelAndView userList(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/systemOption/user");
        try {
            model.addObject("njpb_title", TITLE_USRLIST);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

    @RequestMapping(value = "/role/")
    public ModelAndView roleList(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/systemOption/role");
        try {
            model.addObject("njpb_title", TITLE_ROLELIST);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

    @RequestMapping(value = "/sysParam/")
    public ModelAndView sysParamList(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/systemOption/sysParam");
        try {
            model.addObject("njpb_title", TITLE_PARAM);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

    @RequestMapping(value = "/message/")
    public ModelAndView messageList(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/systemOption/message");
        try {
            model.addObject("njpb_title", TITLE_MESSAGE);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

    @RequestMapping(value = "/lout")
    public void logoutPage(HttpServletRequest request, HttpServletResponse response) {
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
            String base = URI.create(request.getRequestURL().toString()).resolve(request.getContextPath())
                    .toASCIIString();
            LOGGER.debug("base:{}", base);
            response.sendRedirect(logout + "?b=" + URLEncoder.encode(base, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/sysVersion/")
    public ModelAndView sysVersionList(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/systemOption/sysVersion");
        try {
            model.addObject("njpb_title", TITLE_VERSION);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

}
