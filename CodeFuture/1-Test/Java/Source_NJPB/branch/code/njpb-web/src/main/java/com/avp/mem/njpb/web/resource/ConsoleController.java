package com.avp.mem.njpb.web.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * Created by len on 2017/9/12.
 */
@Controller
public class ConsoleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleController.class);

    public static final String TITLE_DASHBOARD = "控制台";

    @RequestMapping(value = { "/", "/dashboard" })
    public ModelAndView gasStationMap(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/index");

        try {
            model.addObject("njpb_title", TITLE_DASHBOARD);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return model;
    }
}
