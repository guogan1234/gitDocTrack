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
 * Created by len on 2017/9/14.
 */
@Controller
public class WorkOrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkOrderController.class);

    public static final String TITLE_WORKORDER = "工单查看";


    @RequestMapping(value = "/workOrder/")
    public ModelAndView userList(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/workOrderManage/workOrderCheck");
        try {
            model.addObject("njpb_title", TITLE_WORKORDER);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

}
