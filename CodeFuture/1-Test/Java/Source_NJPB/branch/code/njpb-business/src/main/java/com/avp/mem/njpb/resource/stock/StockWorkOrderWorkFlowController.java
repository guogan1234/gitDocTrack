/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.stock;

import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.stock.ObjStockWorkOrder;

import com.avp.mem.njpb.service.activiti.ActivitiService;
import org.activiti.engine.ActivitiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by six on 2017/8/19.
 */
@RestController
public class StockWorkOrderWorkFlowController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockWorkOrderWorkFlowController.class);

    @Autowired
    private ActivitiService activitiService;

    /**
     * 启动库存申请流程
     *
     * @param stockWorkOrder
     */
    @RequestMapping(value = "stockWorkFlows/start", method = RequestMethod.POST)
    public String startStockWorkflow(@RequestBody ObjStockWorkOrder stockWorkOrder) {
        try {
            Integer uid = SecurityUtils.getLoginUserId();

            stockWorkOrder.setCreateBy(uid);
            stockWorkOrder.setCreateTime(new Date());
            String processInstanceId = activitiService.startStockWorkflow(stockWorkOrder);

            LOGGER.info("流程已启动，流程ID：{}", processInstanceId);
        } catch (ActivitiException e) {
            if (e.getMessage().indexOf("no processes deployed with key") != -1) {
                LOGGER.error("没有部署流程!", e);
            } else {
                LOGGER.error("启动请假流程失败：", e);
            }
        } catch (Exception e) {
            LOGGER.error("启动报修流程失败：", e);
        }
        return "redirect:/oa/leave/apply";
    }


}
