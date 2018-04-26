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
 * Created by len on 2017/9/18.
 */
@Controller
public class ReportFormsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportFormsController.class);

    public static final String TITLE_WORKORDERPOINT= "工分管理";
    public static final String TITLE_STATIONESTATEFAULTSTATISTICS = "站点设备故障情况统计";
    public static final String TITLE_BIKEFAULTSTATISTICS  = "车辆故障统计";
    public static final String TITLE_BIKECHECKSTATISTICS = "车辆盘点统计";

    public static final String TITLE_REPAIRFAULTSTATISTICS = "维修汇总";
    public static final String TITLE_STATIONREPAIRFAULTSTATISTICS = "站点维修汇总";
    public static final String TITLE_STATIONREPAIRLIST = "站点维修列表";
    public static final String TITLE_BIKEREPAIRSTATISTICS = "自行车维修汇总";
    public static final String TITLE_BIKEREPAIRLIST = "自行车维修列表";
    public static final String TITLE_STATIONMAINTAINFAULTSTATISTICS = "站点自修保养汇总";
    public static final String TITLE_STATIONMAINTAINFAULTLIST = "站点自修保养列表";
    public static final String TITLE_BIKEMAINTAINFAULTSTATISTICS = "自行车自修保养汇总";
    public static final String TITLE_BIKEMAINTAINFAULTLIST = "自行车自修保养列表";

    @RequestMapping(value = {"/report/workOrderScore/" })
    public ModelAndView workOrderPoint(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/reportForms/workOrderScore");
        try {
            model.addObject("njpb_title", TITLE_WORKORDERPOINT);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

    @RequestMapping(value = {"/report/stationEstateFaultStatistics/" })
    public ModelAndView stationEstateFaultStatistics(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/reportForms/stationEstateFaultStatistics");
        try {
            model.addObject("njpb_title", TITLE_STATIONESTATEFAULTSTATISTICS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

    @RequestMapping(value = {"/report/bikeFaultStatistics/" })
    public ModelAndView bikeFaultStatistics(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/reportForms/bikeFaultStatistics");
        try {
            model.addObject("njpb_title", TITLE_BIKEFAULTSTATISTICS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }


    @RequestMapping(value = {"/report/bikeCheckStatistics/" })
    public ModelAndView bikeCheckStatistics(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/reportForms/bikeCheckStatistics");
        try {
            model.addObject("njpb_title", TITLE_BIKECHECKSTATISTICS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }



    @RequestMapping(value = {"/report/repairFaultStatistics/" })
    public ModelAndView repairFaultStatistics(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/reportForms/repairFaultStatistics");
        try {
            model.addObject("njpb_title", TITLE_REPAIRFAULTSTATISTICS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }




    @RequestMapping(value = {"/report/stationRepairFaultStatistics/" })
    public ModelAndView stationRepairFaultStatistics(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/reportForms/stationRepairFaultStatistics");
        try {
            model.addObject("njpb_title", TITLE_STATIONREPAIRFAULTSTATISTICS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

    @RequestMapping(value = {"/report/stationRepairList/" })
    public ModelAndView stationRepairList(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/reportForms/stationRepairList");
        try {
            model.addObject("njpb_title", TITLE_STATIONREPAIRLIST);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

    @RequestMapping(value = {"/report/bikeRepairStatistics/" })
    public ModelAndView bikeRepairStatistics(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/reportForms/bikeRepairStatistics");
        try {
            model.addObject("njpb_title", TITLE_BIKEREPAIRSTATISTICS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

    @RequestMapping(value = {"/report/bikeRepairList/" })
    public ModelAndView bikeRepairList(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/reportForms/bikeRepairList");
        try {
            model.addObject("njpb_title", TITLE_BIKEREPAIRLIST);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }


    @RequestMapping(value = {"/report/stationMainTainStatistics/"})
    public ModelAndView stationMainTainStatistics(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/reportForms/stationMainTainStatistics");
        try {
            model.addObject("njpb_title", TITLE_STATIONMAINTAINFAULTSTATISTICS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }


    @RequestMapping(value = {"/report/stationMainTainList/"})
    public ModelAndView stationMainTainList(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/reportForms/stationMainTainList");
        try {
            model.addObject("njpb_title", TITLE_STATIONMAINTAINFAULTLIST);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }


    @RequestMapping(value = {"/report/bikeMainTainStatistics/"})
    public ModelAndView bikeMainTainStatistics(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/reportForms/bikeMainTainStatistics");
        try {
            model.addObject("njpb_title", TITLE_BIKEMAINTAINFAULTSTATISTICS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

    @RequestMapping(value = {"/report/bikeMainTainList/"})
    public ModelAndView bikeMainTainList(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/reportForms/bikeMainTainList");
        try {
            model.addObject("njpb_title", TITLE_BIKEMAINTAINFAULTLIST);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

}
