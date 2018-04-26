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
 * Created by len on 2017/9/19.
 */
@Controller
public class MapController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapController.class);

    public static final String TITLE_MAP= "地图";


    @RequestMapping(value = "/map/")
    public ModelAndView stationList(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/map/njpbMap");
        try {
            model.addObject("njpb_title", TITLE_MAP);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }
    @RequestMapping(value = "/download")
    public ModelAndView stationListtt(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/download/download");
        try {
            model.addObject("njpb_title", TITLE_MAP);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }
}
