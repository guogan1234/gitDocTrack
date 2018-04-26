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
 * Created by len on 2017/9/13.
 */
@Controller
public class StockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);

    public static final String TITLE_INSTOCK = "入库管理";
    public static final String TITLE_STOCK= "出入库明细";
    public static final String TITLE_STATIONDEVICESTOCK= "站点设备出入库汇总";
    public static final String TITLE_STATIONDEVICESTOCKLIST= "站点设备出入库列表";
    @RequestMapping(value = "/stock/inStock/")
    public ModelAndView inStock(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/stockManage/inStock");
        try {
            model.addObject("njpb_title", TITLE_INSTOCK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

    @RequestMapping(value = "/stock/stockDetails/")
    public ModelAndView stockDetails(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/stockManage/stockDetails");
        try {
            model.addObject("njpb_title", TITLE_STOCK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }
    @RequestMapping(value = "/stock/stationDeviceStock/")
    public ModelAndView stationDeviceStock(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/stockManage/stationDeviceStock");
        try {
            model.addObject("njpb_title", TITLE_STATIONDEVICESTOCK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }
    @RequestMapping(value = "/stock/stationDeviceStockList/")
    public ModelAndView stationDeviceStockList(HttpSession session, HttpServletRequest request, Principal user) {
        ModelAndView model = new ModelAndView("njpb-pages/stockManage/stationDeviceStockList");
        try {
            model.addObject("njpb_title", TITLE_STATIONDEVICESTOCKLIST);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return model;
    }
}
