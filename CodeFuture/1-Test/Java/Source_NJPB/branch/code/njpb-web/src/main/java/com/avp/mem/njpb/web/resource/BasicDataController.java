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
public class BasicDataController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicDataController.class);

    public static final String TITLE_SUPPLIERLIST = "供应商列表";
    public static final String TITLE_ESTATETYPELIST = "设备列表";
    public static final String TITLE_MODULELIST = "部件列表";
    public static final String TITLE_FAULTELIST = "故障列表";

    @RequestMapping(value = "/basicDataManage/supplier/")
    public ModelAndView supplierList(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/basicDataManage/supplier");
        try {
            model.addObject("njpb_title", TITLE_SUPPLIERLIST);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

    @RequestMapping(value = "/basicDataManage/estateType/")
    public ModelAndView estateTypeList(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/basicDataManage/estateType");
        try {
            model.addObject("njpb_title", TITLE_ESTATETYPELIST);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

    @RequestMapping(value = "/basicDataManage/moduleType/")
    public ModelAndView moduleList(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/basicDataManage/moduleType");
        try {
            model.addObject("njpb_title", TITLE_MODULELIST);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

    @RequestMapping(value = "/basicDataManage/faultType/")
    public ModelAndView faultList(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/basicDataManage/faultType");
        try {
            model.addObject("njpb_title", TITLE_FAULTELIST);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

}
