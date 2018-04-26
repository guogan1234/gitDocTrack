/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.service.push;

import com.avp.mem.njpb.api.push.PushMessage;
import com.avp.mem.njpb.entity.workorder.ObjWorkOrder;
import com.avp.mem.njpb.service.rest.RestClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amber on 2017/09/22.
 */

@Service
public class PushClientService {

    private final Logger logger = LoggerFactory.getLogger(PushClientService.class);

    @Autowired
    RestClientService restClientService;

    @Autowired
    @Value("${app.resource.push.url}")
    private String pushUrl;

    @Autowired
    @Value("${app.resource.push.title}")
    private String appTitle;

    protected void doPush(PushMessage message) {
        URI uri = UriComponentsBuilder.fromUriString(pushUrl).path("/messages/push").build().toUri();
        restClientService.postByUri(uri, message);
    }

    /**
     * 工单已创建  ##
     * 推送对象：派单人
     *
     * @param workOrder
     * @
     */
    public void pushWorkCreate(ObjWorkOrder workOrder) {
        List<Integer> userIds = new ArrayList<>();
        try {
            //调度人
            userIds.add(workOrder.getAssignEmployee());
            String content = "新工单【" + workOrder.getSerialNo() + "】已创建，请注意处理";
            PushMessage message = new PushMessage("公共自行车移动运维", content, userIds);
            logger.debug(message.getTitle() + ".............................");
            doPush(message);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("pushWorkOrder {}", e.getMessage());
        }
    }






    /**
     * 工单报修审核通过-分配##
     * 推送对象：维修人
     *
     * @param workOrder
     * @
     */
    public void pushWorkAssign(ObjWorkOrder workOrder) {
        List<Integer> userIds = new ArrayList<>();
        try {
            //接单人
            userIds.add(workOrder.getRepairEmployee());

            String content = "工单【" + workOrder.getSerialNo() + "】已分配您处理，请注意查看。";
            PushMessage message = new PushMessage("公共自行车移动运维", content, userIds);
            logger.debug(message.getTitle());
            doPush(message);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("pushWorkAssign {}", e.getMessage());
        }
    }

    /**
     * 工单报修审核不通过-分配##
     * 推送对象：维修人
     *
     * @param workOrder
     * @
     */
    public void pushWorkBack(ObjWorkOrder workOrder) {
        List<Integer> userIds = new ArrayList<>();
        try {
            //报修人
            userIds.add(workOrder.getReportEmployee());

            String content = "工单【" + workOrder.getSerialNo() + "】已被维修组长驳回，请注意处理。";
            PushMessage message = new PushMessage("公共自行车移动运维", content, userIds);

            doPush(message);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("pushWorkAssign {}", e.getMessage());
        }
    }

    /**
     * 工单维修确认 ##
     * 推送对象：调度人、报修人
     *
     * @param workOrder
     */
    public void pushWorkRepairConfirm(ObjWorkOrder workOrder) {
        try {

            List<Integer> userIds = new ArrayList<>();
            //userIds.add();
            //调度人
            userIds.add(workOrder.getAssignEmployee());
            //报修人
            userIds.add(workOrder.getRepairEmployee());
            String content = "工单【" + workOrder.getSerialNo() + "】维修已确认。";
            PushMessage message = new PushMessage("公共自行车移动运维", content, userIds);

            doPush(message);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("pushWorkConfirm {}", e.getMessage());
        }
    }

    /**
     * 工单已完成  ##
     * 推送对象：调度人、报修人
     *
     * @param workOrder
     * @
     */
    public void pushWorkComplete(ObjWorkOrder workOrder) {
        try {
            List<Integer> userIds = new ArrayList<>();
            //userIds.add();
            //调度人
            userIds.add(workOrder.getCheckEmployee());
            //报修人
            userIds.add(workOrder.getReportEmployee());
            String content = "工单【" + workOrder.getSerialNo() + "】维修已完成";
            PushMessage message = new PushMessage("公共自行车移动运维", content, userIds);

            doPush(message);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("pushWorkRepairComplete {}", e.getMessage());
        }
    }

    /**
     * 维修未完成--遗留  ##
     * 推送对象：调度人、报修人
     *
     * @param workOrder
     */
    public void pushWorkUnhandled(ObjWorkOrder workOrder) {
        try {

            List<Integer> userIds = new ArrayList<>();
            //userIds.add();
            //调度人
            userIds.add(workOrder.getCheckEmployee());
            //报修人
            userIds.add(workOrder.getReportEmployee());
            String content = "工单【" + workOrder.getSerialNo() + "】维修未完成，遗留";
            PushMessage message = new PushMessage("公共自行车移动运维", content, userIds);

            doPush(message);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("pushWorkUnhandled {}", e.getMessage());
        }
    }

    /**
     * 工单签到(维修已到达) ##
     * 推送对象：调度人、报修人
     *
     * @param workOrder
     * @
     */
    public void pushWorkSignIn(ObjWorkOrder workOrder) {
        try {

            List<Integer> userIds = new ArrayList<>();
            //userIds.add();
            //调度人
            userIds.add(workOrder.getAssignEmployee());
            //报修人
            userIds.add(workOrder.getReportEmployee());
            String content = "工单【" + workOrder.getSerialNo() + "】维修已到达";
            PushMessage message = new PushMessage("公共自行车移动运维", content, userIds);

            doPush(message);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("pushWorkSignIn {}", e.getMessage());
        }
    }

    /**
     * 通用消息推送##
     *
     * @param
     */
    public void pushCommonMsg(String content, List<Integer> userIds) {
        try {
            PushMessage message = new PushMessage("公共自行车移动运维", content, userIds);
            doPush(message);

        } catch (Exception e) {
            logger.error("pushCommonMsg {}", e.getMessage());
        }
    }
}
