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
 * Created by len on 2017/9/2.
 */
@Controller
public class EstateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstateController.class);

    public static final String TITLE_STATIONS= "站点列表";
    public static final String TITLE_ESTATES= "设备列表";
    public static final String TITLE_BARCODES= "条码列表";

    @RequestMapping(value = "/estateManage/stations")
    public ModelAndView stationList(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/estateManage/stationsManage");
        try {
            model.addObject("njpb_title", TITLE_STATIONS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

    @RequestMapping(value = "/estateManage/estates")
    public ModelAndView estateList(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/estateManage/estatesManage");
        try {
            model.addObject("njpb_title", TITLE_ESTATES);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

    @RequestMapping(value = "/estateManage/barCodes")
    public ModelAndView barCodeList(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/estateManage/barCodesManage");
        try {
            model.addObject("njpb_title", TITLE_BARCODES);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }


}
