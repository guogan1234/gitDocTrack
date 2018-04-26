package com.avp.mems.backstage.resource.workorder;


import com.avp.mems.backstage.entity.push.Message;
import com.avp.mems.backstage.entity.user.User;
import com.avp.mems.backstage.entity.work.*;
import com.avp.mems.backstage.entity.work.vo.WorkOrderVO;
import com.avp.mems.backstage.repositories.basic.FaultTypeRepository;
import com.avp.mems.backstage.repositories.basic.FixApproachRepository;
import com.avp.mems.backstage.repositories.push.PushInfoWechatRepository;
import com.avp.mems.backstage.repositories.user.UserRepository;
import com.avp.mems.backstage.repositories.work.*;
import com.avp.mems.backstage.rest.ResponseBuilder;
import com.avp.mems.backstage.rest.ResponseCode;
import com.avp.mems.backstage.rest.RestBody;
import com.avp.mems.backstage.util.PicurlUtil;
import com.avp.mems.backstage.util.SecurityUtil;
import com.avp.mems.backstage.util.Validator;
import com.avp.mems.backstage.util.WorkOrderSerialNoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

/**
 * Created by Amber on 2017/5/28.
 */
@RestController
public class WorkOrderController {

    @Autowired
    private WorkOrderRepository workOrderRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FixApproachWorkRepository fixApproachWorkRepository;

    @Autowired
    private FixApproachRepository fixApproachRepository;

    @Autowired
    FaultTypeRepository faultTypeRepository;

    @Autowired
    private WorkOrderViewRepository workOrderViewRepository;

    @Autowired
    private BadComponentRepository badComponentRepository;

    @Autowired
    PushInfoWechatRepository pushInfoWechatRepository;

    @Value("${app.resource.push.url}")
    private String pushResourceUrl;


    @Value("${wechat.push.url}")
    private String url;

    private Integer count = 0;

    RestTemplate restTemplate = new RestTemplate();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "workOrders", method = RequestMethod.POST)
    @Transactional
    ResponseEntity saveWorkOrder(@RequestBody WorkOrder workOrder) {

        logger.debug("url:workOrders----------");
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        String username = SecurityUtil.getLoginUserName();
        if (Validator.isNotNull(workOrder)) {
            if (Validator.isNull(workOrder.getEquipmentId()) || Validator.isNull(workOrder.getLocationId()) || Validator.isNull(workOrder.getProjectId()) || Validator.isNull(workOrder.getBadComponentId())) {
                builder.setErrorCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }
            workOrder.setIsWechatWorkorder("wechatworkorder");
            workOrder.setTypeId(1L);
            workOrder.setCreationTime(new Date());
            workOrder.setLastUpdateTime(new Date());
            workOrder.setSerialNo(WorkOrderSerialNoUtil.createSerialNo());
            workOrder.setStatusId(2L);
            workOrder.setReportEmployee(username);
            workOrder.setReportTime(new Date());
            workOrder = workOrderRepository.save(workOrder);

            BadComponent badComponent = new BadComponent();
            BadComponentPK badComponentPK = new BadComponentPK();

            badComponentPK.setComponentId(workOrder.getBadComponentId());
            badComponentPK.setWorkOrderId(workOrder.getId().intValue());

            badComponent.setBadComponentPK(badComponentPK);

            badComponentRepository.save(badComponent);

            Assignment assignment = new Assignment();
            assignment.setWorkOrderId(workOrder.getId());
            assignment.setAssignEmployee(workOrder.getAssignEmployee());
            assignment.setAssignTime(new Date());
            assignmentRepository.save(assignment);
            builder.setResultEntity(workOrder, ResponseCode.CREATE_SUCCEED);

            try {
                logger.debug("推送工单--save，工单信息：id:{},工单编号:{}", workOrder.getId(), workOrder.getSerialNo());
                pushToWechat(workOrder);
            } catch (Exception e) {
                logger.error("工单推送失败");
            }

        } else {
            builder.setErrorCode(ResponseCode.PARAM_MISSING);
        }
        return builder.getResponseEntity();
    }


    @RequestMapping(value = "userWechats/workorderDetails/search/findById", method = RequestMethod.GET)
    ResponseEntity getWorkOrderDetail(@RequestParam("id") Long id) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            //TODO 部分字段未显示中文
            if (Validator.isNotNull(id)) {
                WorkOrderVO workOrderVO = new WorkOrderVO();
                WorkOrderView workOrder = workOrderViewRepository.findOne(id);
                workOrderVO.setWorkOrderView(workOrder);

                List<FixApproach> fixApproach = fixApproachWorkRepository.findByWorkOrderId(workOrder.getId().intValue());
                if (fixApproach.size() > 0) {
                    logger.info("查询工单维修方法开始："+fixApproach.get(0).getFixApproachPK().getFixApproachId());
                    Long fixApprachId = Long.valueOf(fixApproach.get(0).getFixApproachPK().getFixApproachId());
                    if(fixApprachId >0){
                        com.avp.mems.backstage.entity.basic.FixApproach fixApproach1 = fixApproachRepository.findOne(fixApprachId);
                        workOrderVO.setFixApproachNameCn(fixApproach1.getNameCn());
                    }

                    logger.info("查询工单故障原因开始："+fixApproach.get(0).getFixApproachPK().getFaultTypeId());
                    Long faultTypeId = Long.valueOf(fixApproach.get(0).getFixApproachPK().getFaultTypeId());
                    if(faultTypeId > 0){
                        com.avp.mems.backstage.entity.basic.FaultType faultType = faultTypeRepository.findOne(faultTypeId);
                        logger.info("查询工单故障原因结束："+faultType.getNameCn());
                        workOrderVO.setFaultTypeNameCn(faultType.getNameCn());
                    }
                }
                builder.setResultEntity(workOrderVO, ResponseCode.RETRIEVE_SUCCEED);
            } else {
                builder.setErrorCode(ResponseCode.PARAM_MISSING);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.getResponseEntity();
    }

    @RequestMapping("userWechats/workorderHistories/search/findByWorkOrderId")
    ResponseEntity getWorkOrderHistory(@Param("workOrderId") Long workOrderId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        if (Validator.isNotNull(workOrderId)) {
            WorkOrder workOrder = workOrderRepository.findOne(workOrderId);
            List resultList = getWorkOrderHistory(workOrder);

            builder.setResultEntity(resultList, ResponseCode.RETRIEVE_SUCCEED);
            return builder.getResponseEntity();
        }
        builder.setErrorCode(ResponseCode.PARAM_MISSING);
        return builder.getResponseEntity();
    }

    /*
     * 工单列表TAB(上拉加载更多)-查询工单数据-APP
     *
     * @param Date firstOperationTime ,Pageable p
     *
     * @param Long projectId
     *
     * @return ResultEntity<VwUserWorkOrder>
     */
    @RequestMapping(value = "workOrders/findByTypeIdAndOperationTimeLessThan", method = RequestMethod.GET)
    ResponseEntity<RestBody<WorkOrderView>> findByTypeIdAndOperationTimeLessThan(Date firstOperationTime, Long projectId, Pageable p) {
        ResponseBuilder<WorkOrderView> builder = ResponseBuilder.createBuilder();
        logger.debug("url:workOrders/findByTypeIdAndOperationTimeLessThan,firstOperationTime:{},projectId:{},p:{}", firstOperationTime, projectId, p);
        if (Validator.isNotNull(firstOperationTime)) {
            String uid = SecurityUtil.getLoginUserName();
            Page<WorkOrderView> workOrders = workOrderViewRepository.findByLastUpdateTimeLessThanAndProjectId(uid, firstOperationTime, projectId, p);

            logger.debug("上拉加载查询工单数据成功,数据量为:{}", workOrders.getContent().size());
            builder.setResultEntity(workOrders, ResponseCode.RETRIEVE_SUCCEED);
            return builder.getResponseEntity();
        }
        builder.setErrorCode(ResponseCode.PARAM_MISSING);
        return builder.getResponseEntity();
    }

    /*
     * 工单列表TAB(下拉刷新)-查询工单数据-APP
     *
     * @param Date firstOperationTime
     *
     * @param Long projectId
     * @return ResultEntity<VwUserWorkOrder>
     */
    @RequestMapping(value = "workOrders/findByTypeIdAndOperationTimeGreaterThan", method = RequestMethod.GET)
    public ResponseEntity<RestBody<WorkOrderView>> findByTypeIdAndOperationTimeGreaterThan(Date firstOperationTime, Long projectId) {
        ResponseBuilder<WorkOrderView> builder = ResponseBuilder.createBuilder();
        logger.debug("url:workOrders/findByTypeIdAndOperationTimeGreaterThan,firstOperationTime:{},projectId:{}", firstOperationTime, projectId);
        if (Validator.isNotNull(firstOperationTime)) {
            String uid = SecurityUtil.getLoginUserName();
            List<WorkOrderView> workOrders = workOrderViewRepository.findByLastUpdateTimeGreaterThanAndProjectId(uid, firstOperationTime, projectId);

            logger.debug("下拉刷新查询工单数据成功,数据量为:{}", workOrders.size());
            builder.setResultEntity(workOrders, ResponseCode.RETRIEVE_SUCCEED);
            return builder.getResponseEntity();
        }
        builder.setErrorCode(ResponseCode.PARAM_MISSING);
        return builder.getResponseEntity();
    }


    /**
     * 企业微信 工单推送
     *
     * @param workOrder
     */
    private void pushToWechat(WorkOrder workOrder) {

        logger.debug("plan to push wechat ....");
        Message message = new Message();
        List<String> targetApps = message.getTargetApps();
        targetApps.add("wechat");
        targetApps.add("6");
        message.setWechattitle("工单：" + workOrder.getSerialNo());

        WorkOrderView workOrderView = workOrderViewRepository.findOne(workOrder.getId());
        String reportEmployee = workOrderView.getReportEmployeeName();
        String repairEmployeeName = workOrderView.getRepairEmployeeName();
        String assignEmployee = workOrderView.getAssignEmployeeName();

        logger.debug("根据工单ID：{}获取工单成功,报修人:{}", workOrder.getId(), reportEmployee);
        if (repairEmployeeName == null) {
            repairEmployeeName = "无";
        }
        if (assignEmployee == null) {
            assignEmployee = "无";
        }
        message.setWechatcontent("报修人:" + reportEmployee + " " + "调度人:" + assignEmployee + " " + "维修人:" + repairEmployeeName + "");
        message.getArgs().put("objectType", "WorkOrder");
        message.getArgs().put("objectId", String.valueOf(workOrder.getId()));
        message.setIsWechatWorkorder(workOrder.getIsWechatWorkorder());

        List<String> targetUsers = new ArrayList();
        targetUsers.add("admin");
        List<String> topartys = new ArrayList();

        message.setTargetUsers(targetUsers);
        message.setTopartys(topartys);
        message.setUrl("http://" + url + "/skip?path=work/listDetail&workorderId=" + workOrder.getId());
        message.setPicurl(PicurlUtil.picurlRound(count,url));
        String t = PicurlUtil.picurlRound(count,url);
        logger.debug("picurl:{}", t);
        count++;
        logger.debug("pushToWechat,message:{}", message.toString());

        try {
            URI uri = UriComponentsBuilder.fromUriString(pushResourceUrl).path("/push").build().toUri();
            logger.debug("URI-host:{}  path:{}  port:{}", uri.getHost(), uri.getPath(), uri.getPort());

            restTemplate.postForObject(uri, message, Object.class);
            logger.debug("push wechat finished...");

        } catch (Exception e) {
            logger.debug("Push create msg failed ------{}", e.getMessage());
        }

    }

    private User getUserFullName(String username) {
        User user = new User();
        user = userRepository.findByUsername(username);
        return user;
    }

    private List<Map<String, Object>> getWorkOrderHistory(WorkOrder workOrder) {
        int workOrderStatus = new Long(workOrder.getStatusId()).intValue();
        List<Map<String, Object>> workOrderHistoryList = new ArrayList<>();
//        Assignment assignment = assignmentRepository.findTopByWorkOrderId(workOrder.getId());
        List<Assignment> assignmentList = assignmentRepository.findByWorkOrderIdOrderByAssignTimeAsc(workOrder.getId());

        Assignment assign = new Assignment();
        Map<String, Object> workOrderHistory = new HashMap<>();
        User user;
        logger.debug("工单:{}的状态为:{},调度信息数据量为:{}", workOrder.getId(), workOrderStatus, assignmentList.size());

        switch (workOrderStatus) {
            case 2:
                //报修信息
                user= getUserFullName(workOrder.getReportEmployee().trim());
                workOrderHistory.put("employee", user.getFullName());
                workOrderHistory.put("finished", 2);
                workOrderHistory.put("status", "报修已派发");
                workOrderHistory.put("time", workOrder.getReportTime());
                workOrderHistory.put("headimgurl",user.getHeadimgurl());
                workOrderHistoryList.add(workOrderHistory);
                break;
            case 4:
                user = getUserFullName(workOrder.getReportEmployee().trim());
                workOrderHistory.put("employee", user.getFullName());
                workOrderHistory.put("finished", 2);
                workOrderHistory.put("status", "报修已派发");
                workOrderHistory.put("time", workOrder.getReportTime());
                workOrderHistory.put("headimgurl",user.getHeadimgurl());
                workOrderHistoryList.add(workOrderHistory);

                for (Assignment assignment : assignmentList) {
                    //调度信息
                    if (assignment.getAssignEmployee() != null) {
                        workOrderHistory = new HashMap<>();
                        user = getUserFullName(assignment.getAssignEmployee().trim());
                        workOrderHistory.put("employee", user.getFullName());
                        workOrderHistory.put("finished", 4);
                        workOrderHistory.put("status", "调度已派发");
                        workOrderHistory.put("time", assignment.getAssignTime());
                        workOrderHistory.put("headimgurl",user.getHeadimgurl());
                        workOrderHistoryList.add(workOrderHistory);
                    }
                }
                break;
            case 5:
                user = getUserFullName(workOrder.getReportEmployee().trim());
                workOrderHistory.put("employee", user.getFullName());
                workOrderHistory.put("finished", 2);
                workOrderHistory.put("status", "报修已派发");
                workOrderHistory.put("time", workOrder.getReportTime());
                workOrderHistory.put("headimgurl",user.getHeadimgurl());
                workOrderHistoryList.add(workOrderHistory);

                //调度信息
                for (Assignment assignment : assignmentList) {
                    //调度信息
                    if (assignment.getAssignEmployee() != null) {
                        workOrderHistory = new HashMap<>();
                        user = getUserFullName(assignment.getAssignEmployee().trim());
                        workOrderHistory.put("employee", user.getFullName());
                        workOrderHistory.put("finished", 4);
                        workOrderHistory.put("status", "调度已派发");
                        workOrderHistory.put("time", assignment.getAssignTime());
                        workOrderHistory.put("headimgurl",user.getHeadimgurl());
                        workOrderHistoryList.add(workOrderHistory);
                    }
                }

                assign = assignmentList.get(assignmentList.size() - 1);
                //维修已确认
                workOrderHistory = new HashMap<>();
                user = getUserFullName(assign.getRepairEmployee().trim());
                workOrderHistory.put("employee", user.getFullName());
                workOrderHistory.put("finished", 5);
                workOrderHistory.put("status", "维修已确认");
                workOrderHistory.put("time", assign.getRepairConfirmTime());
                workOrderHistory.put("headimgurl",user.getHeadimgurl());
                workOrderHistoryList.add(workOrderHistory);
                break;
            case 6:
                user = getUserFullName(workOrder.getReportEmployee().trim());
                workOrderHistory.put("employee", user.getFullName());
                workOrderHistory.put("finished", 2);
                workOrderHistory.put("status", "报修已派发");
                workOrderHistory.put("time", workOrder.getReportTime());
                workOrderHistory.put("headimgurl",user.getHeadimgurl());
                workOrderHistoryList.add(workOrderHistory);

                //调度信息
                for (Assignment assignment : assignmentList) {
                    //调度信息
                    if (assignment.getAssignEmployee() != null) {
                        workOrderHistory = new HashMap<>();
                        user = getUserFullName(assignment.getAssignEmployee().trim());
                        workOrderHistory.put("employee", user.getFullName());
                        workOrderHistory.put("finished", 4);
                        workOrderHistory.put("status", "调度已派发");
                        workOrderHistory.put("time", assignment.getAssignTime());
                        workOrderHistory.put("headimgurl",user.getHeadimgurl());
                        workOrderHistoryList.add(workOrderHistory);
                    }
                }
                assign = assignmentList.get(assignmentList.size() - 1);
                //维修已确认
                workOrderHistory = new HashMap<>();
                user = getUserFullName(assign.getRepairEmployee().trim());
                workOrderHistory.put("employee", user.getFullName());
                workOrderHistory.put("finished", 5);
                workOrderHistory.put("status", "维修已确认");
                workOrderHistory.put("time", assign.getRepairConfirmTime());
                workOrderHistory.put("headimgurl",user.getHeadimgurl());
                workOrderHistoryList.add(workOrderHistory);
                //维修已到达
                workOrderHistory = new HashMap<>();
                user = getUserFullName(assign.getRepairEmployee().trim());
                workOrderHistory.put("employee", user.getFullName());
                workOrderHistory.put("finished", 6);
                workOrderHistory.put("status", "维修已到达");
                workOrderHistory.put("time", assign.getRepairStartTime());
                workOrderHistory.put("headimgurl",user.getHeadimgurl());
                workOrderHistoryList.add(workOrderHistory);
                break;
            case 7:
                user = getUserFullName(workOrder.getReportEmployee().trim());
                workOrderHistory.put("employee", user.getFullName());
                workOrderHistory.put("finished", 2);
                workOrderHistory.put("status", "报修已派发");
                workOrderHistory.put("time", workOrder.getReportTime());
                workOrderHistory.put("headimgurl",user.getHeadimgurl());
                workOrderHistoryList.add(workOrderHistory);

                //璋冨害淇℃伅
                for (Assignment assignment : assignmentList) {
                    //璋冨害淇℃伅
                    if (assignment.getAssignEmployee() != null) {
                        workOrderHistory = new HashMap<>();
                        user = getUserFullName(assignment.getAssignEmployee().trim());
                        workOrderHistory.put("employee", user.getFullName());
                        workOrderHistory.put("finished", 4);
                        workOrderHistory.put("status", "调度已派发");
                        workOrderHistory.put("time", assignment.getAssignTime());
                        workOrderHistory.put("headimgurl",user.getHeadimgurl());
                        workOrderHistoryList.add(workOrderHistory);
                    }
                }

                assign = assignmentList.get(assignmentList.size() - 1);

                //缁翠慨宸茬‘璁?
                workOrderHistory = new HashMap<>();
                user = getUserFullName(assign.getRepairEmployee().trim());
                workOrderHistory.put("employee", user.getFullName());
                workOrderHistory.put("finished", 5);
                workOrderHistory.put("status", "维修已确认");
                workOrderHistory.put("time", assign.getRepairConfirmTime());
                workOrderHistory.put("headimgurl",user.getHeadimgurl());
                workOrderHistoryList.add(workOrderHistory);
                //缁翠慨宸插埌杈?
                workOrderHistory = new HashMap<>();
                user = getUserFullName(assign.getRepairEmployee().trim());
                workOrderHistory.put("employee", user.getFullName());
                workOrderHistory.put("finished", 6);
                workOrderHistory.put("status", "维修已到达");
                workOrderHistory.put("time", assign.getRepairStartTime());
                workOrderHistory.put("headimgurl",user.getHeadimgurl());
                workOrderHistoryList.add(workOrderHistory);
                //缁翠慨宸插畬鎴?
                workOrderHistory = new HashMap<>();
                user = getUserFullName(assign.getRepairEmployee().trim());
                workOrderHistory.put("employee", user.getFullName());
                workOrderHistory.put("finished", 7);
                workOrderHistory.put("status", "维修已完成");
                workOrderHistory.put("time", assign.getRepairEndTime());
                workOrderHistory.put("headimgurl",user.getHeadimgurl());
                workOrderHistoryList.add(workOrderHistory);
                break;
            case 8:
                user = getUserFullName(workOrder.getReportEmployee().trim());
                workOrderHistory.put("employee", user.getFullName());
                workOrderHistory.put("finished", 2);
                workOrderHistory.put("status", "报修已派发");
                workOrderHistory.put("time", workOrder.getReportTime());
                workOrderHistory.put("headimgurl",user.getHeadimgurl());
                workOrderHistoryList.add(workOrderHistory);

                //璋冨害淇℃伅
                for (Assignment assignment : assignmentList) {
                    //璋冨害淇℃伅
                    if (assignment.getAssignEmployee() != null) {
                        workOrderHistory = new HashMap<>();
                        user = getUserFullName(assignment.getAssignEmployee().trim());
                        workOrderHistory.put("employee", user.getFullName());
                        workOrderHistory.put("finished", 4);
                        workOrderHistory.put("status", "调度已派发");
                        workOrderHistory.put("time", assignment.getAssignTime());
                        workOrderHistory.put("headimgurl",user.getHeadimgurl());
                        workOrderHistoryList.add(workOrderHistory);
                    }
                }

                assign = assignmentList.get(assignmentList.size() - 1);

                //缁翠慨宸茬‘璁?
                workOrderHistory = new HashMap<>();
                user = getUserFullName(assign.getRepairEmployee().trim());
                workOrderHistory.put("employee", user.getFullName());
                workOrderHistory.put("finished", 5);
                workOrderHistory.put("status", "维修已确认");
                workOrderHistory.put("time", assign.getRepairConfirmTime());
                workOrderHistory.put("headimgurl",user.getHeadimgurl());
                workOrderHistoryList.add(workOrderHistory);
                //缁翠慨宸插埌杈?
                workOrderHistory = new HashMap<>();
                user = getUserFullName(assign.getRepairEmployee().trim());
                workOrderHistory.put("employee", user.getFullName());
                workOrderHistory.put("finished", 6);
                workOrderHistory.put("status", "维修已到达");
                workOrderHistory.put("time", assign.getRepairStartTime());
                workOrderHistory.put("headimgurl",user.getHeadimgurl());
                workOrderHistoryList.add(workOrderHistory);
                //缁翠慨宸插畬鎴?
                workOrderHistory = new HashMap<>();
                user = getUserFullName(assign.getRepairEmployee().trim());
                workOrderHistory.put("employee", user.getFullName());
                workOrderHistory.put("finished", 8);
                workOrderHistory.put("status", "遗留");
                workOrderHistory.put("time", workOrder.getLastUpdateTime());
                workOrderHistory.put("headimgurl",user.getHeadimgurl());
                workOrderHistoryList.add(workOrderHistory);
                break;
            case 9:
                user = getUserFullName(workOrder.getReportEmployee().trim());
                workOrderHistory.put("employee", user.getFullName());
                workOrderHistory.put("finished", 2);
                workOrderHistory.put("status", "报修已派发");
                workOrderHistory.put("time", workOrder.getReportTime());
                workOrderHistory.put("headimgurl",user.getHeadimgurl());
                workOrderHistoryList.add(workOrderHistory);

                //璋冨害淇℃伅
                for (Assignment assignment : assignmentList) {
                    //璋冨害淇℃伅
                    if (assignment.getAssignEmployee() != null) {
                        workOrderHistory = new HashMap<>();
                        user = getUserFullName(assignment.getAssignEmployee().trim());
                        workOrderHistory.put("employee", user.getFullName());
                        workOrderHistory.put("finished", 4);
                        workOrderHistory.put("status", "调度已派发");
                        workOrderHistory.put("time", assignment.getAssignTime());
                        workOrderHistory.put("headimgurl",user.getHeadimgurl());
                        workOrderHistoryList.add(workOrderHistory);
                    }
                }

                assign = assignmentList.get(assignmentList.size() - 1);

                //缁翠慨宸茬‘璁?
                workOrderHistory = new HashMap<>();
                user = getUserFullName(assign.getRepairEmployee().trim());
                workOrderHistory.put("employee", user.getFullName());
                workOrderHistory.put("finished", 5);
                workOrderHistory.put("status", "维修已确认");
                workOrderHistory.put("time", assign.getRepairConfirmTime());
                workOrderHistory.put("headimgurl",user.getHeadimgurl());
                workOrderHistoryList.add(workOrderHistory);
                //缁翠慨宸插埌杈?
                workOrderHistory = new HashMap<>();
                user = getUserFullName(assign.getRepairEmployee().trim());
                workOrderHistory.put("employee", user.getFullName());
                workOrderHistory.put("finished", 6);
                workOrderHistory.put("status", "维修已到达");
                workOrderHistory.put("time", assign.getRepairStartTime());
                workOrderHistory.put("headimgurl",user.getHeadimgurl());
                workOrderHistoryList.add(workOrderHistory);
                //缁翠慨宸插畬鎴?
                workOrderHistory = new HashMap<>();
                user = getUserFullName(assign.getRepairEmployee().trim());
                workOrderHistory.put("employee", user.getFullName());
                workOrderHistory.put("finished", 9);
                workOrderHistory.put("status", "工单完成");
                workOrderHistory.put("time", workOrder.getLastUpdateTime());
                workOrderHistory.put("headimgurl",user.getHeadimgurl());
                workOrderHistoryList.add(workOrderHistory);
                break;
            case 10:
                user = getUserFullName(workOrder.getReportEmployee().trim());
                workOrderHistory.put("employee", user.getFullName());
                workOrderHistory.put("finished", 2);
                workOrderHistory.put("status", "报修已派发");
                workOrderHistory.put("time", workOrder.getReportTime());
                workOrderHistory.put("headimgurl",user.getHeadimgurl());
                workOrderHistoryList.add(workOrderHistory);

                //调度信息
                for (Assignment assignment : assignmentList) {
                    //调度信息
                    if (assignment.getAssignEmployee() != null) {
                        workOrderHistory = new HashMap<>();
                        user = getUserFullName(assignment.getAssignEmployee().trim());
                        workOrderHistory.put("employee", user.getFullName());
                        workOrderHistory.put("finished", 4);
                        workOrderHistory.put("status", "调度已派发");
                        workOrderHistory.put("time", assignment.getAssignTime());
                        workOrderHistory.put("headimgurl",user.getHeadimgurl());
                        workOrderHistoryList.add(workOrderHistory);
                    }
                }

                assign = assignmentList.get(assignmentList.size() - 1);

                //维修已确认
                workOrderHistory = new HashMap<>();
                user = getUserFullName(assign.getRepairEmployee().trim());
                workOrderHistory.put("employee", user.getFullName());
                workOrderHistory.put("finished", 5);
                workOrderHistory.put("status", "维修已确认");
                workOrderHistory.put("time", assign.getRepairConfirmTime());
                workOrderHistory.put("headimgurl",user.getHeadimgurl());
                workOrderHistoryList.add(workOrderHistory);
                //维修已到达
                workOrderHistory = new HashMap<>();
                user = getUserFullName(assign.getRepairEmployee().trim());
                workOrderHistory.put("employee", user.getFullName());
                workOrderHistory.put("finished", 6);
                workOrderHistory.put("status", "维修已到达");
                workOrderHistory.put("time", assign.getRepairStartTime());
                workOrderHistory.put("headimgurl",user.getHeadimgurl());
                workOrderHistoryList.add(workOrderHistory);
                //维修已完成
                workOrderHistory = new HashMap<>();
                user = getUserFullName(assign.getRepairEmployee().trim());
                workOrderHistory.put("employee", user.getFullName());
                workOrderHistory.put("finished", 10);
                workOrderHistory.put("status", "维修已完成");
                workOrderHistory.put("time", assign.getRepairEndTime());
                workOrderHistory.put("headimgurl",user.getHeadimgurl());
                workOrderHistoryList.add(workOrderHistory);
                break;
            default:
                break;
        }
        return workOrderHistoryList;
    }
}
