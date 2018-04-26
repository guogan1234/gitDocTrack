/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.resource.workorder;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.entityBO.ResponseTimeOutAndRepairTimeOut;
import com.avp.mem.njpb.entity.estate.ObjEstateType;
import com.avp.mem.njpb.entity.estate.ObjStation;
import com.avp.mem.njpb.entity.system.SysParam;
import com.avp.mem.njpb.entity.system.SysUser;
import com.avp.mem.njpb.entity.workorder.ObjWorkOrder;
import com.avp.mem.njpb.entity.workorder.ObjWorkOrderBadComponent;
import com.avp.mem.njpb.entity.workorder.ObjWorkOrderFaultType;
import com.avp.mem.njpb.entity.workorder.ObjWorkOrderOperation;
import com.avp.mem.njpb.entity.workorder.ObjWorkOrderResource;
import com.avp.mem.njpb.repository.basic.ObjEstateTypeRepository;
import com.avp.mem.njpb.repository.basic.ObjFaultTypeRepository;
import com.avp.mem.njpb.repository.basic.ObjFileRepository;
import com.avp.mem.njpb.repository.basic.SysParamRepository;
import com.avp.mem.njpb.repository.estate.ObjStationRepository;
import com.avp.mem.njpb.repository.stock.ObjStockRecordPersonalRepository;
import com.avp.mem.njpb.repository.workorder.ObjWorkOrderBadComponentRepository;
import com.avp.mem.njpb.repository.workorder.ObjWorkOrderFaultTypeRepository;
import com.avp.mem.njpb.repository.workorder.ObjWorkOrderRepository;
import com.avp.mem.njpb.repository.workorder.ObjWorkOrderResourceRepository;
import com.avp.mem.njpb.repository.workorder.ObjWorkorderOperationRepository;
import com.avp.mem.njpb.service.activiti.ActivitiService;
import com.avp.mem.njpb.service.activiti.WorkOrderWorkflowService;
import com.avp.mem.njpb.service.push.PushClientService;
import com.avp.mem.njpb.service.workorder.WorkOperateService;
import com.avp.mem.njpb.util.BusinessRefData;
import com.avp.mem.njpb.util.FileUploadUtil;
import com.avp.mem.njpb.util.MagicNumber;
import com.avp.mem.njpb.util.Validator;
import com.avp.mem.njpb.util.Variable;
import com.avp.mem.njpb.util.WorkOrderSerialNoUtil;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.avp.mem.njpb.util.MagicNumber.ONE;


/**
 * 工单操作
 *
 * @author Amber
 */
@RestController
public class WorkOrderHandleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkOrderHandleController.class);

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    protected TaskService taskService;

    @Autowired
    ObjStockRecordPersonalRepository objStockRecordPersonalRepository;

    @Autowired
    protected ObjWorkOrderRepository objWorkOrderRepository;

    @Autowired
    private WorkOperateService workOperateService;


    @Autowired
    ObjWorkorderOperationRepository objWorkorderOperationRepository;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Autowired
    private ObjFileRepository objFileRepository;

    @Autowired
    private ObjWorkOrderBadComponentRepository objWorkOrderBadComponentRepository;

    @Autowired
    private ObjWorkOrderResourceRepository objWorkOrderResourceRepository;

    @Autowired
    private WorkOrderWorkflowService workorderReportWorkflowService;

    @Autowired
    private ActivitiService activitiService;

    @Autowired
    private SysParamRepository sysParamRepository;

    @Autowired
    private PushClientService pushClientService;

    @Autowired
    private ObjEstateTypeRepository objEstateTypeRepository;

    @Autowired
    private ObjStationRepository objStationRepository;

    @Autowired
    private ObjFaultTypeRepository objFaultTypeRepository;

    @Autowired
    private ObjWorkOrderFaultTypeRepository objWorkOrderFaultTypeRepository;

    private static final int THREE = 3;
    private static final int NINE_NINE = 99;

    /**
     * 启动报修流程
     *
     * @param workOrder
     */
    @RequestMapping(value = "workFlows/start", method = RequestMethod.POST)
    public String startWorkflow(@RequestBody ObjWorkOrder workOrder) {
        try {
            Integer uid = SecurityUtils.getLoginUserId();

            workOrder.setCreateBy(uid);
            workOrder.setCreateTime(new Date());

            String processInstanceId = activitiService.startRepairWorkflow(workOrder);

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

    /**
     * 工作流操作--测试分配
     */
    @RequestMapping(value = "workFlows/testAssign", method = RequestMethod.GET)
    public List<ObjWorkOrder> testAssign(Integer workOrderId) {
        try {
            ObjWorkOrder objWorkOrder = objWorkOrderRepository.findOne(workOrderId);

            String processInstanceId = objWorkOrder.getProcessInstanceId();

            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
                    .singleResult();

            String activityId = processInstance.getActivityId();

            Map<String, Object> variables = new HashMap<>();

            variables.put("taskAssignEmployee", "1");

            variables.put("responseTimeout", true);

            variables.put("groupLeaderPassReport", true);


            // 设置当前任务信息
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().orderByTaskCreateTime().desc().singleResult();
            LOGGER.debug("task:{}", task.getId());

            taskService.complete(task.getId(), variables);

            LOGGER.debug("activityId:{}", activityId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 任务列表
     */
    @RequestMapping(value = "workFlows/task", method = RequestMethod.GET)
    public List<ObjWorkOrder> taskList() {
        try {
            Integer userId = SecurityUtils.getLoginUserId();
            List<ObjWorkOrder> workOrders = workorderReportWorkflowService.findTodoTasks(userId.toString());
            return workOrders;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取运行中的流程实例
     *
     * @return
     */
    @RequestMapping(value = "workFlows/list/running", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<ObjWorkOrder> runningList() {

        try {
            List<ObjWorkOrder> workOrders = workorderReportWorkflowService.findRunningProcessInstaces();
//            Map<String, Object> map = new HashMap<>();
//            map.put("task",workOrders.get(0).getTask());

            return workOrders;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取运行中的流程实例
     *
     * @return
     */
    @RequestMapping(value = "workFlows/list/finished")
    public List<ObjWorkOrder> finishedList() {
        List<ObjWorkOrder> workOrders = workorderReportWorkflowService.findFinishedProcessInstaces();

        return workOrders;
    }

    /**
     * 签收任务
     */
    @RequestMapping(value = "workFlows/task/claim/{id}")
    public void claim(@PathVariable("id") String taskId) {
        String userId = SecurityUtils.getLoginUserId().toString();
        taskService.claim(taskId, userId);
        LOGGER.debug("taskId:{}签收成功", taskId);
    }

    /**
     * 完成任务
     *
     * @param taskId
     * @return
     */
    @RequestMapping(value = "complete/{id}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String complete(@PathVariable("id") String taskId, Variable var) {
        try {
            Map<String, Object> variables = var.getVariableMap();
            taskService.complete(taskId, variables);
            return "success";
        } catch (Exception e) {
            LOGGER.error("error on complete task {}, variables={}", new Object[]{taskId, var.getVariableMap(), e});
            return "error";
        }
    }
// end activiti test----------------------------------

    /**
     * 工单新建--APP接口
     *
     * @param objWorkOrder
     * @return
     */
    @PostMapping("workOrders")
    @Transactional
    public ResponseEntity<RestBody<ObjWorkOrder>> saveWorkOrder(
            @RequestBody ObjWorkOrder objWorkOrder,
            @RequestParam(value = "fileIds", required = false) List<Integer> fileIds,
            @RequestParam(value = "faultTypeIds", required = false) List<Integer> faultTypeIds,
            @RequestParam(value = "badComponentTypeIds", required = false) List<Integer> badComponentTypeIds) {
        ResponseBuilder<ObjWorkOrder> builder = ResponseBuilder.createBuilder();
        List<ObjWorkOrderResource> objWorkOrderResourcesSave = new ArrayList<>();
        List<ObjWorkOrderBadComponent> objWorkOrderBadComponentsSave = new ArrayList<>();
        try {
            LOGGER.debug("工单新建，前端传入的参数:{}", objWorkOrder.toString());
            Integer uid = SecurityUtils.getLoginUserId();

            if (!checkWorkOrderParam(objWorkOrder)) {
                LOGGER.debug("前端传入参数有误");
                builder.setResponseCode(ResponseCode.PARAM_MISSING, "部分参数有误!");
                return builder.getResponseEntity();
            }
            //添加审核人
            ObjStation objStation = objStationRepository.findOne(objWorkOrder.getStationId());

            objWorkOrder.setCheckEmployee(objStation.getCheckUserId());

            //step 1:保存工单表数据
            objWorkOrder.setSerialNo(WorkOrderSerialNoUtil.createSerialNo());
            objWorkOrder.setStatusId(BusinessRefData.WOSTATUS_WO_CREATED);
            objWorkOrder.setReportEmployee(uid);
            objWorkOrder.setReportTime(new Date());
            objWorkOrder.setWorkOrderScore(MagicNumber.ZEROD);
            objWorkOrder.setWorkOrderScoreDeduct(MagicNumber.ZEROD);
            objWorkOrder = objWorkOrderRepository.save(objWorkOrder);

            Integer workOrderId = objWorkOrder.getId();
            LOGGER.debug("工单新建成功，id为:{}", workOrderId);

            //step 2:保存工单操作记录表数据
            workOperateService.updateWorkOrderOperateDetail(objWorkOrder.getId(), objWorkOrder.getStatusId(), objWorkOrder.getLongitude(), objWorkOrder.getLatitude(), objWorkOrder.getLocation(), objWorkOrder.getWorkOrderScore(), objWorkOrder.getWorkOrderScoreDeduct());

            /*
             *车辆报修时，选择自行车设备类型下的模块类型
             */
            LOGGER.debug("step 3:保存工单坏件表数据");
            //step 3:保存工单坏件表数据
//            Integer estateId = objWorkOrder.getEstateId();
            //自行车ID
            if (badComponentTypeIds != null) {
                for (Integer badComponentTypeId : badComponentTypeIds) {
                    LOGGER.debug("判断该部件类型:{}在该自行车:{}下是否存在实例", badComponentTypeId, objWorkOrder.getEstateId());


                    ObjWorkOrderBadComponent objWorkOrderBadComponent = new ObjWorkOrderBadComponent(workOrderId, badComponentTypeId);
                    objWorkOrderBadComponentsSave.add(objWorkOrderBadComponent);

                    LOGGER.debug("save workOrderBadComponent,workorderId:{},badComponentTypeId:{}", workOrderId, badComponentTypeId);
                }
                objWorkOrderBadComponentRepository.save(objWorkOrderBadComponentsSave);
            }

            LOGGER.debug("step 4:保存工单图片数据");
            //step 4:保存工单图片数据
            if (Validator.isNotNull(fileIds) && !fileIds.isEmpty()) {
                fileIds.forEach(fileId -> {
                    ObjWorkOrderResource objWorkOrderResource = new ObjWorkOrderResource();
                    objWorkOrderResource.setFileId(fileId);
                    objWorkOrderResource.setWorkOrderId(workOrderId);
                    objWorkOrderResource.setCategory(BusinessRefData.WO_RESOURCE_CATEGORY_REPORT);
                    //报修阶段资源

                    objWorkOrderResourcesSave.add(objWorkOrderResource);
                });

                objWorkOrderResourceRepository.save(objWorkOrderResourcesSave);
                LOGGER.debug("工单资源保存成功,数据量为：{}", objWorkOrderResourcesSave.size());
            }

            //step 5:保存故障类型
            if (Validator.isNotNull(faultTypeIds)) {
                List<ObjWorkOrderFaultType> objWorkOrderFaultTypes = new ArrayList<>();


                for (int i = 0; i < faultTypeIds.size(); i++) {

                    ObjWorkOrderFaultType objWorkOrderFaultType = new ObjWorkOrderFaultType();
                    objWorkOrderFaultType.setWorkOrderId(workOrderId);
                    objWorkOrderFaultType.setFaultTypeId(faultTypeIds.get(i));
                    objWorkOrderFaultTypes.add(objWorkOrderFaultType);
                }
                objWorkOrderFaultTypeRepository.save(objWorkOrderFaultTypes);
            }

            String processInstanceId = activitiService.startRepairWorkflow(objWorkOrder);

            objWorkOrder.setProcessInstanceId(processInstanceId);

            objWorkOrderRepository.save(objWorkOrder);

            LOGGER.debug("工单报修的流程id为：{}", processInstanceId);

            //TODO 消息推送
            pushClientService.pushWorkCreate(objWorkOrder);

            builder.setResultEntity(objWorkOrder, ResponseCode.CREATE_SUCCEED);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            e.printStackTrace();
            LOGGER.debug("工单新建失败");
            String message = builder.getResponseEntity().getBody().getMessage();
            if (message.contains(BusinessRefData.WO_ERROR_MESSAGE_PART)) {
                // 说明该catch代码不是手动触发的
                builder.setResponseCode(ResponseCode.FAILED, message);
            } else {
                builder.setResponseCode(ResponseCode.FAILED);
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return builder.getResponseEntity();
    }

    /**
     * 查询工单下一步操作
     *
     * @param workOrderId
     * @return ResponseEntity<RestBody<List<String>>>
     */
    @GetMapping("workOrders/findTaskNextOperation")
    public ResponseEntity<RestBody<List<String>>> findTaskNextOperation(Integer workOrderId) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            ObjWorkOrder objWorkOrder = objWorkOrderRepository.findOne(workOrderId);

            if (Validator.isNull(objWorkOrder)) {
                LOGGER.debug("工单数据为空，查询失败");
                builder.setResponseCode(ResponseCode.RETRIEVE_FAILED, "工单数据为空，请求失败");
                return builder.getResponseEntity();
            }

            if (Validator.isNull(objWorkOrder.getProcessInstanceId())) {
                LOGGER.debug("工单：{}数据中的流程为空，脏数据", workOrderId);
                builder.setResponseCode(ResponseCode.RETRIEVE_FAILED, "工单数据有误，请校验数据");
                return builder.getResponseEntity();
            }

            String processInstanceId = objWorkOrder.getProcessInstanceId();

//            Set<String> nextOperation = workorderReportWorkflowService.findNextOperation(processInstanceId, uid);

            List<String> nextOperation = activitiService.getTaskOperation(processInstanceId);

            LOGGER.debug("工单：{}的下一步操作有：{}", workOrderId, nextOperation);

            builder.setResultEntity(nextOperation, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            e.printStackTrace();
            LOGGER.debug("查询工单下一步操作失败");
            builder.setResponseCode(ResponseCode.RETRIEVE_FAILED);
        }

        return builder.getResponseEntity();
    }

    /**
     * 工单报修审核同意-->分配+消息推送
     *
     * @param workOrderIds
     * @param repairEmployee
     * @return
     */
    @PutMapping("workOrders/assign")
    @Transactional
    public ResponseEntity<RestBody<ObjWorkOrder>> workAssign(@RequestParam(value = "workOrderIds") List<Integer> workOrderIds,
                                                             @RequestParam(value = "repairEmployee") Integer repairEmployee,
                                                             @RequestParam(value = "level") Integer level,
                                                             @RequestParam(value = "assignRemark", required = false) String assignRemark) {
        ResponseBuilder<ObjWorkOrder> builder = ResponseBuilder.createBuilder();
        List<ObjWorkOrder> woList = new ArrayList<>();
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            List<Integer> canBeAssignStatus = Arrays.asList(BusinessRefData.WOSTATUS_WO_CREATED, BusinessRefData.WOSTATUS_WO_MODIFY, BusinessRefData.WOSTATUS_RESPONSE_TIMEOUT, BusinessRefData.WOSTATUS_WO_SENDED, BusinessRefData.WOSTATUS_RPR_BACK);

            if (Validator.isNull(workOrderIds) || workOrderIds.isEmpty() || Validator.isNull(repairEmployee)) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            ResponseTimeOutAndRepairTimeOut responseTimeOutAndRepairTimeOut = getResponseTimeOutAndRepairTimeOut(level);

            LOGGER.debug("响应超时时间为：{}", responseTimeOutAndRepairTimeOut.toString());

            workOrderIds.forEach(workOrderId -> {
                // 反向更新工单表数据
                LOGGER.debug("开始分配工单 ,工单id为({})", workOrderId);
                ObjWorkOrder objWorkOrder = objWorkOrderRepository.findOne(workOrderId);
                Integer status = objWorkOrder.getStatusId();
                if (!uid.equals(objWorkOrder.getAssignEmployee())) {
                    // 当前工单不能被分配
                    LOGGER.debug("工单：{}的调度人不是当前账号，不能被调度", workOrderId);

                    builder.setResponseCode(ResponseCode.FAILED, "工单:" + objWorkOrder.getSerialNo() + "派单人不是当前用户，不能派单");
                    // 事务回滚
                    throw new RuntimeException("工单派单人和当前账号不符合，不能被分配");
                }

                if (!canBeAssignStatus.contains(status)) {
                    // 当前工单不能被分配
                    LOGGER.debug("工单：{}的状态为：{}，不能被调度", workOrderId, status);

                    builder.setResponseCode(ResponseCode.FAILED, "工单:" + objWorkOrder.getSerialNo() + "当前的状态不能派单");
                    // 事务回滚
                    throw new RuntimeException("工单当前状态不能被分配");
                }

                objWorkOrder.setRepairEmployee(repairEmployee);
                objWorkOrder.setStatusId(BusinessRefData.WOSTATUS_WO_SENDED);
                // 调度已派发
                objWorkOrder.setAssignRemark(assignRemark);
                objWorkOrder.setLevel(level);
                objWorkOrder.setAssignTime(new Date());
                objWorkOrder.setResponseTimeOutDate(responseTimeOutAndRepairTimeOut.getResponseTimeOut());
                objWorkOrder.setRepairTimeOutDate(responseTimeOutAndRepairTimeOut.getRepairTimeOut());

                ObjWorkOrder wo = objWorkOrderRepository.save(objWorkOrder);
                woList.add(wo);

                //step 2:保存工单操作记录表数据
                workOperateService.updateWorkOrderOperateDetail(objWorkOrder.getId(), objWorkOrder.getStatusId(), null, null, null, MagicNumber.ZEROD, MagicNumber.ZEROD);
            });

            LOGGER.debug("工单分配数量为({})", woList.size());

            woList.forEach(wo -> {
                // step 3 完成当前节点task

                String processInstanceId = wo.getProcessInstanceId();

//            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
//                    .singleResult();
//
//            String activityId = processInstance.getActivityId();

                Map<String, Object> variables = new HashMap<>();

                variables.put("taskAssignEmployee", wo.getRepairEmployee().toString());

                LOGGER.debug("派发时间为：{}", new Date().toLocaleString());

                LOGGER.debug("设置流程响应时间...");
                variables.put("responseTimeOut_time", responseTimeOutAndRepairTimeOut.getResponseTimeOut());

                LOGGER.debug("设置流程下一步操作人...");
                variables.put("woAssignEmployee", wo.getAssignEmployee().toString());

                variables.put("groupLeaderPassReport", true);

                LOGGER.debug("activiti plan to complete task ...");
                activitiService.completeTask(variables, processInstanceId);
                LOGGER.debug("流程task完成...");
            });

            LOGGER.debug("所有工单派单完成");

            //TODO 消息推送
            woList.forEach(wo -> {
                pushClientService.pushWorkAssign(wo);
            });


            builder.setResponseCode(ResponseCode.OK);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            e.printStackTrace();
            String message = builder.getResponseEntity().getBody().getMessage();
            if (message.contains(BusinessRefData.WO_ERROR_MESSAGE_PART)) {
                // 说明该catch代码不是手动触发的
                builder.setResponseCode(ResponseCode.FAILED, message);
            } else {
                builder.setResponseCode(ResponseCode.FAILED);
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }

    private ResponseTimeOutAndRepairTimeOut getResponseTimeOutAndRepairTimeOut(int level) {
        LOGGER.debug("获取工单级别为：{}的响应时间", level);

        Date date1 = new Date();
        Date date2 = new Date();

        List<SysParam> sysParams = sysParamRepository.findAll();

        //默认的时间
        Integer defaultTime = BusinessRefData.DEFAULT_TIME_OUT_MINUTE;

        Integer responseTimeOut = null;
        Integer repairTimeOut = null;

        if (ONE.equals(sysParams.size())) {

            LOGGER.debug("数据库中系统参数条目为一条，是有效数据");
            SysParam sysParam = sysParams.get(0);

            Integer workOrderLevelOneResponseTime = sysParam.getWorkOrderLevelOneResponseTime();
            Integer workOrderLevelOneRepairTime = sysParam.getWorkOrderLevelOneRepairTime();
            Integer workOrderLevelTwoResponseTime = sysParam.getWorkOrderLevelTwoResponseTime();
            Integer workOrderLevelTwoRepairTime = sysParam.getWorkOrderLevelTwoRepairTime();
            Integer workOrderLevelThreeResponseTime = sysParam.getWorkOrderLevelThreeResponseTime();
            Integer workOrderLevelThreeRepairTime = sysParam.getWorkOrderLevelThreeRepairTime();
            Integer workOrderLevelEmergencyResponseTime = sysParam.getWorkOrderLevelEmergencyResponseTime();
            Integer workOrderLevelEmergencyRepairTime = sysParam.getWorkOrderLevelEmergencyRepairTime();

            LOGGER.debug("系统参数数据为：{}", sysParam.toString());

            switch (level) {
                case 1:
                    LOGGER.debug("工单级别为一级工单");
                    responseTimeOut = workOrderLevelOneResponseTime;
                    repairTimeOut = workOrderLevelOneRepairTime;
                    break;
                case 2:
                    LOGGER.debug("工单级别为二级工单");
                    responseTimeOut = workOrderLevelTwoResponseTime;
                    repairTimeOut = workOrderLevelTwoRepairTime;
                    break;
                case THREE:
                    LOGGER.debug("工单级别为三级工单");
                    responseTimeOut = workOrderLevelThreeResponseTime;
                    repairTimeOut = workOrderLevelThreeRepairTime;
                    break;
                case NINE_NINE:
                    LOGGER.debug("工单级别为应急工单");
                    responseTimeOut = workOrderLevelEmergencyResponseTime;
                    repairTimeOut = workOrderLevelEmergencyRepairTime;
                    break;
                default:
                    LOGGER.debug("工单级别为脏数据，使用默认响应时间:{}", defaultTime);
                    responseTimeOut = repairTimeOut = defaultTime;
                    break;
            }
        }

        if (Validator.isNull(responseTimeOut)) {
            responseTimeOut = repairTimeOut = defaultTime;
            LOGGER.debug("数据库中系统参数条目不是为一条，使用默认超时响应时间：{}", responseTimeOut);
        }

        if (Validator.isNull(responseTimeOut)) {
            responseTimeOut = defaultTime;
        }

        if (Validator.isNull(repairTimeOut)) {
            repairTimeOut = defaultTime;
        }

        date1 = new Date(new Date().getTime() + responseTimeOut * BusinessRefData.MS_PER_MINUTE);
        date2 = new Date(new Date().getTime() + repairTimeOut * BusinessRefData.MS_PER_MINUTE);

        LOGGER.debug("工单确认响应超时时间为：{}", date1.toLocaleString());
        LOGGER.debug("工单维修超时时间为：{}", date2.toLocaleString());

        return new ResponseTimeOutAndRepairTimeOut(date1, date2);
    }

    /**
     * 工单报修审核不同意-->驳回+消息推送
     *
     * @param workOrderId
     * @param backRemark
     * @return
     */
    @PutMapping("workOrders/back/{workOrderId}")
    @Transactional
    public ResponseEntity<RestBody<ObjWorkOrder>> workback(@PathVariable(value = "workOrderId") Integer workOrderId,
                                                           @RequestParam(value = "backRemark", required = false) String backRemark) {
        ResponseBuilder<ObjWorkOrder> builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();
            List<Integer> canBeBackStatus = Arrays.asList(BusinessRefData.WOSTATUS_WO_CREATED, BusinessRefData.WOSTATUS_WO_MODIFY, BusinessRefData.WOSTATUS_RPR_BACK);

            LOGGER.debug("可以退回的工单状态为:{}", canBeBackStatus);

            if (Validator.isNull(workOrderId)) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            // 反向更新工单表数据
            LOGGER.debug("开始分配工单 ,工单id为({})", workOrderId);
            ObjWorkOrder objWorkOrder = objWorkOrderRepository.findOne(workOrderId);
            Integer status = objWorkOrder.getStatusId();
            if (!uid.equals(objWorkOrder.getAssignEmployee())) {
                // 当前工单不能被分配
                LOGGER.debug("工单：{}的调度人不是当前账号，不能被退回", workOrderId);

                builder.setResponseCode(ResponseCode.FAILED, "工单:" + objWorkOrder.getSerialNo() + "派单人不是当前用户，不能退回");
                // 事务回滚
                throw new RuntimeException("工单派单人和当前账号不符合，不能被退回");
            }

            if (!canBeBackStatus.contains(status)) {
                LOGGER.debug("工单：{}的状态为：{}，不能被退回", workOrderId, status);

                builder.setResponseCode(ResponseCode.FAILED, "工单:" + objWorkOrder.getSerialNo() + "当前的状态不能退回");
                // 事务回滚
                throw new RuntimeException("工单当前状态不能被退回");
            }

            objWorkOrder.setStatusId(BusinessRefData.WOSTATUS_WO_BACK);
            // 退回
            objWorkOrder.setBackRemark(backRemark);
            ObjWorkOrder wo = objWorkOrderRepository.save(objWorkOrder);

            LOGGER.debug("工单：{}，已被退回", workOrderId);

            //step 2:保存工单操作记录表数据
            workOperateService.updateWorkOrderOperateDetail(objWorkOrder.getId(), objWorkOrder.getStatusId(), null, null, null, MagicNumber.ZEROD, MagicNumber.ZEROD);

            // step 3 完成当前节点task

            String processInstanceId = wo.getProcessInstanceId();

//            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
//                    .singleResult();
//
//            String activityId = processInstance.getActivityId();

            Map<String, Object> variables = new HashMap<>();

            variables.put("taskAssignEmployee", wo.getReportEmployee().toString());

            variables.put("groupLeaderPassReport", false);

            activitiService.completeTask(variables, processInstanceId);

            LOGGER.debug("工单退回完成");
            //TODO 消息推送
            pushClientService.pushWorkBack(objWorkOrder);

            builder.setResponseCode(ResponseCode.OK);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            e.printStackTrace();
            String message = builder.getResponseEntity().getBody().getMessage();
            if (message.contains(BusinessRefData.WO_ERROR_MESSAGE_PART)) {
                // 说明该catch代码不是手动触发的
                builder.setResponseCode(ResponseCode.FAILED, message);
            } else {
                builder.setResponseCode(ResponseCode.FAILED);
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }

    /**
     * 工单报修-->重新报修+消息推送
     *
     * @param workOrderId
     * @param objWorkOrder
     * @param fileIds
     * @param badComponentTypeIds
     * @return
     */
    @PostMapping("workOrders/reReport/{workOrderId}")
    @Transactional
    public ResponseEntity<RestBody<ObjWorkOrder>> workReReport(@PathVariable("workOrderId") Integer workOrderId,
                                                               @RequestBody ObjWorkOrder objWorkOrder,
                                                               @RequestParam(value = "fileIds", required = false) List<Integer> fileIds,
                                                               @RequestParam(value = "badComponentTypeIds", required = false) List<Integer> badComponentTypeIds) {


        ResponseBuilder<ObjWorkOrder> builder = ResponseBuilder.createBuilder();
        List<ObjWorkOrderResource> objWorkOrderResourcesSave = new ArrayList<>();
        List<ObjWorkOrderBadComponent> objWorkOrderBadComponentsSave = new ArrayList<>();
        try {
            LOGGER.debug("工单新建，前端传入的参数:{}", objWorkOrder.toString());
            Integer uid = SecurityUtils.getLoginUserId();

            if (!checkWorkOrderParam(objWorkOrder)) {
                LOGGER.debug("前端传入参数有误");
                builder.setResponseCode(ResponseCode.PARAM_MISSING, "部分参数有误!");
                return builder.getResponseEntity();
            }

            Integer canBeBackStatus = BusinessRefData.WOSTATUS_WO_BACK;

            LOGGER.debug("可以重新报修的工单状态为:{}", canBeBackStatus);

            if (Validator.isNull(workOrderId)) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            ObjWorkOrder workOrderDb = objWorkOrderRepository.findOne(workOrderId);
            Integer status = workOrderDb.getStatusId();
            if (!uid.equals(workOrderDb.getReportEmployee())) {
                // 当前工单不能被分配
                LOGGER.debug("工单：{}的报修人不是当前账号，不能重新报修", workOrderId);

                builder.setResponseCode(ResponseCode.FAILED, "工单:" + objWorkOrder.getSerialNo() + "报修人不是当前用户，不能重新报修");
                // 事务回滚
                throw new RuntimeException("工单报修人和当前账号不符合，不能重新报修");
            }

            if (!canBeBackStatus.equals(status)) {
                LOGGER.debug("工单：{}的状态为：{}，不能重新报修", workOrderId, status);

                builder.setResponseCode(ResponseCode.FAILED, "工单:" + objWorkOrder.getSerialNo() + "当前的状态不能重新报修");
                // 事务回滚
                throw new RuntimeException("工单当前状态不能重新报修");
            }

            //step 1:保存工单表数据
            workOrderDb.setSerialNo(WorkOrderSerialNoUtil.createSerialNo());
            workOrderDb.setStatusId(BusinessRefData.WOSTATUS_WO_MODIFY);
            workOrderDb.setReportEmployee(uid);
            workOrderDb.setReportTime(new Date());
            workOrderDb.setWorkOrderScore(MagicNumber.ZEROD);
            workOrderDb.setWorkOrderScoreDeduct(MagicNumber.ZEROD);
            objWorkOrder = objWorkOrderRepository.save(workOrderDb);

            LOGGER.debug("工单重新报修成功，id为:{}", workOrderId);

            //step 2:保存工单操作记录表数据
            workOperateService.updateWorkOrderOperateDetail(objWorkOrder.getId(), objWorkOrder.getStatusId(), objWorkOrder.getLongitude(), objWorkOrder.getLatitude(), objWorkOrder.getLocation(), objWorkOrder.getWorkOrderScore(), objWorkOrder.getWorkOrderScoreDeduct());

            //step 3: 删除工单坏件记录和图片
            workOperateService.deleteWorkOrderResource(workOrderId);
            /*
             *车辆报修时，选择自行车设备类型下的模块类型
             */
            //step 4:保存工单坏件表数据
//            Integer estateId = objWorkOrder.getEstateId();
            //自行车ID
            if (badComponentTypeIds != null) {
                for (Integer badComponentTypeId : badComponentTypeIds) {
                    ObjWorkOrderBadComponent objWorkOrderBadComponent = new ObjWorkOrderBadComponent(workOrderId, badComponentTypeId);
                    objWorkOrderBadComponentsSave.add(objWorkOrderBadComponent);

                    LOGGER.debug("save workOrderBadComponent,workorderId:{},badComponentTypeId:{}", workOrderId, badComponentTypeId);
                }
                objWorkOrderBadComponentRepository.save(objWorkOrderBadComponentsSave);
            }

            //step 5:保存工单图片数据
            if (!fileIds.isEmpty()) {
                fileIds.forEach(fileId -> {
                    ObjWorkOrderResource objWorkOrderResource = new ObjWorkOrderResource();
                    objWorkOrderResource.setFileId(fileId);
                    objWorkOrderResource.setWorkOrderId(workOrderId);
                    objWorkOrderResource.setCategory(BusinessRefData.WO_RESOURCE_CATEGORY_REPORT);
                    //报修阶段资源

                    objWorkOrderResourcesSave.add(objWorkOrderResource);
                });

                objWorkOrderResourceRepository.save(objWorkOrderResourcesSave);
                LOGGER.debug("工单资源保存成功,数据量为：{}", objWorkOrderResourcesSave.size());
            }

            // step 6 完成当前节点task
            String processInstanceId = objWorkOrder.getProcessInstanceId();

            Map<String, Object> variables = new HashMap<>();

            variables.put("taskAssignEmployee", objWorkOrder.getAssignEmployee().toString());
            variables.put("repeatReport", true);
            //重新报修工单

            activitiService.completeTask(variables, processInstanceId);

            LOGGER.debug("工单重新报修完成");

            //TODO 消息推送
            pushClientService.pushWorkCreate(objWorkOrder);

            builder.setResultEntity(objWorkOrder, ResponseCode.CREATE_SUCCEED);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            e.printStackTrace();
            LOGGER.debug("工单新建失败");
            String message = builder.getResponseEntity().getBody().getMessage();
            if (message.contains(BusinessRefData.WO_ERROR_MESSAGE_PART)) {
                // 说明该catch代码不是手动触发的
                builder.setResponseCode(ResponseCode.FAILED, message);
            } else {
                builder.setResponseCode(ResponseCode.FAILED);
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }

    /**
     * 工单确认-->+消息推送
     *
     * @param workOrderId
     * @return
     */
    @PostMapping("workOrders/confirm/{workOrderId}")
    @Transactional
    public ResponseEntity<RestBody<ObjWorkOrder>> workConfirm(
            @PathVariable("workOrderId") Integer workOrderId,
            @RequestParam(value = "longitude", required = false) Double longitude,
            @RequestParam(value = "latitude", required = false) Double latitude,
            @RequestParam(value = "location", required = false) String location) {
        ResponseBuilder<ObjWorkOrder> builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();
//            63548
            Integer canBeConfirmStatus = BusinessRefData.WOSTATUS_WO_SENDED;

            LOGGER.debug("可以确认的工单状态为:{}", canBeConfirmStatus);

            if (Validator.isNull(workOrderId)) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            ObjWorkOrder workOrderDb = objWorkOrderRepository.findOne(workOrderId);

            Integer status = workOrderDb.getStatusId();

            if (!uid.equals(workOrderDb.getRepairEmployee())) {
                // 当前工单不能确认
                LOGGER.debug("工单：{}的维修人不是当前账号，不能进行确认", workOrderId);

                builder.setResponseCode(ResponseCode.FAILED, "工单:" + workOrderDb.getSerialNo() + "维修人不是当前用户，不能确认");
                // 事务回滚
                throw new RuntimeException("工单维修人和当前账号不符合，不能确认");
            }

            if (!canBeConfirmStatus.equals(status)) {
                LOGGER.debug("工单：{}的状态为：{}，不能确认", workOrderId, status);

                builder.setResponseCode(ResponseCode.FAILED, "工单:" + workOrderDb.getSerialNo() + "当前的状态不能确认");
                // 事务回滚
                throw new RuntimeException("工单当前状态不能确认");
            }

            //step 1:保存工单表数据
            workOrderDb.setRepairConfirmTime(new Date());
            workOrderDb.setStatusId(BusinessRefData.WOSTATUS_RPR_CONFIRM);

            ObjWorkOrder objWorkOrder = objWorkOrderRepository.save(workOrderDb);

            LOGGER.debug("工单确认成功，id为:{}", workOrderId);

            //step 2:保存工单操作记录表数据
            workOperateService.updateWorkOrderOperateDetail(workOrderId, objWorkOrder.getStatusId(), longitude, latitude, location, MagicNumber.ZEROD, MagicNumber.ZEROD);

            // step 3 完成当前节点task
            String processInstanceId = objWorkOrder.getProcessInstanceId();

            Map<String, Object> variables = new HashMap<>();

            //确认之后的操作时签到，需要维修人去签到
            variables.put("taskAssignEmployee", objWorkOrder.getRepairEmployee().toString());

            variables.put("workOrderConfirmOrBack", true);
//            ${workOrderComfiemOrBack}
            activitiService.completeTask(variables, processInstanceId);

            LOGGER.debug("工单确认完成");

            //TODO 消息推送
            pushClientService.pushWorkRepairConfirm(objWorkOrder);

            builder.setResultEntity(objWorkOrder, ResponseCode.CREATE_SUCCEED);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            e.printStackTrace();
            LOGGER.debug("工单确认失败");
            String message = builder.getResponseEntity().getBody().getMessage();
            if (message.contains(BusinessRefData.WO_ERROR_MESSAGE_PART)) {
                // 说明该catch代码不是手动触发的
                builder.setResponseCode(ResponseCode.FAILED, message);
            } else {
                builder.setResponseCode(ResponseCode.FAILED);
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }


    /**
     * 工单退回-->+消息推送
     *
     * @param workOrderId
     * @return
     */
    @PutMapping("workOrders/repairBack/{workOrderId}")
    @Transactional
    public ResponseEntity<RestBody<ObjWorkOrder>> workBack(
            @PathVariable("workOrderId") Integer workOrderId,
            @RequestParam(value = "repairBackRemark", required = false) String repairBackRemark,
            @RequestParam(value = "longitude", required = false) Double longitude,
            @RequestParam(value = "latitude", required = false) Double latitude,
            @RequestParam(value = "location", required = false) String location) {
        ResponseBuilder<ObjWorkOrder> builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();
//            63548
            Integer canBeConfirmStatus = BusinessRefData.WOSTATUS_WO_SENDED;

            LOGGER.debug("可以确认的工单状态为:{}", canBeConfirmStatus);

            if (Validator.isNull(workOrderId)) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            ObjWorkOrder workOrderDb = objWorkOrderRepository.findOne(workOrderId);

            Integer status = workOrderDb.getStatusId();

            if (!uid.equals(workOrderDb.getRepairEmployee())) {
                // 当前工单不能确认
                LOGGER.debug("工单：{}的维修人不是当前账号，不能进行退回", workOrderId);

                builder.setResponseCode(ResponseCode.FAILED, "工单:" + workOrderDb.getSerialNo() + "维修人不是当前用户，不能退回");
                // 事务回滚
                throw new RuntimeException("工单维修人和当前账号不符合，不能退回");
            }

            if (!canBeConfirmStatus.equals(status)) {
                LOGGER.debug("工单：{}的状态为：{}，不能退回", workOrderId, status);

                builder.setResponseCode(ResponseCode.FAILED, "工单:" + workOrderDb.getSerialNo() + "当前的状态不能退回");
                // 事务回滚
                throw new RuntimeException("工单当前状态不能退回");
            }

            //step 1:保存工单表数据
            workOrderDb.setReportTime(new Date());
            workOrderDb.setRepairBackRemark(repairBackRemark);
            workOrderDb.setStatusId(BusinessRefData.WOSTATUS_RPR_BACK);

            ObjWorkOrder objWorkOrder = objWorkOrderRepository.save(workOrderDb);

            LOGGER.debug("工单退回成功，id为:{}", workOrderId);

            //step 2:保存工单操作记录表数据
            workOperateService.updateWorkOrderOperateDetail(workOrderId, objWorkOrder.getStatusId(), longitude, latitude, location, MagicNumber.ZEROD, MagicNumber.ZEROD);

            // step 3 完成当前节点task
            String processInstanceId = objWorkOrder.getProcessInstanceId();

            Map<String, Object> variables = new HashMap<>();


            variables.put("taskAssignEmployee", objWorkOrder.getAssignEmployee().toString());

            variables.put("workOrderConfirmOrBack", false);

            activitiService.completeTask(variables, processInstanceId);

            LOGGER.debug("工单退回完成");

            //TODO 消息推送
            pushClientService.pushWorkRepairConfirm(objWorkOrder);

            builder.setResultEntity(objWorkOrder, ResponseCode.CREATE_SUCCEED);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            e.printStackTrace();
            LOGGER.debug("工单退回失败");
            String message = builder.getResponseEntity().getBody().getMessage();
            if (message.contains(BusinessRefData.WO_ERROR_MESSAGE_PART)) {
                // 说明该catch代码不是手动触发的
                builder.setResponseCode(ResponseCode.FAILED, message);
            } else {
                builder.setResponseCode(ResponseCode.FAILED);
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }


    /**
     * 工单签到-->+消息推送
     *
     * @param workOrderId
     * @return
     */
    @PostMapping("workOrders/signIn/{workOrderId}")
    @Transactional
    public ResponseEntity<RestBody<ObjWorkOrder>> workSignIn(@PathVariable("workOrderId") Integer workOrderId,
                                                             @RequestParam(value = "longitude", required = false) Double longitude,
                                                             @RequestParam(value = "latitude", required = false) Double latitude,
                                                             @RequestParam(value = "location", required = false) String location) {
        ResponseBuilder<ObjWorkOrder> builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();

            Integer canBeSignInStatus = BusinessRefData.WOSTATUS_RPR_CONFIRM;

            LOGGER.debug("可以签到的工单状态为:{}", canBeSignInStatus);

            if (Validator.isNull(workOrderId)) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            ObjWorkOrder workOrderDb = objWorkOrderRepository.findOne(workOrderId);

            Integer status = workOrderDb.getStatusId();

            if (!uid.equals(workOrderDb.getRepairEmployee())) {
                // 当前工单不能签到
                LOGGER.debug("工单：{}的维修人不是当前账号，不能进行签到", workOrderId);

                builder.setResponseCode(ResponseCode.FAILED, "工单:" + workOrderDb.getSerialNo() + "维修人不是当前用户，不能签到");
                // 事务回滚
                throw new RuntimeException("工单维修人和当前账号不符合，不能签到");
            }

            if (!canBeSignInStatus.equals(status)) {
                LOGGER.debug("工单：{}的状态为：{}，不能签到", workOrderId, status);

                builder.setResponseCode(ResponseCode.FAILED, "工单:" + workOrderDb.getSerialNo() + "当前的状态不能签到");
                // 事务回滚
                throw new RuntimeException("工单当前状态不能签到");
            }

            //step 1:保存工单表数据
            workOrderDb.setRepairStartTime(new Date());
            workOrderDb.setStatusId(BusinessRefData.WOSTATUS_WO_ARRIVE);

            ObjWorkOrder objWorkOrder = objWorkOrderRepository.save(workOrderDb);

            LOGGER.debug("工单签到成功，id为:{}", workOrderId);

            //step 2:保存工单操作记录表数据
            workOperateService.updateWorkOrderOperateDetail(workOrderId, objWorkOrder.getStatusId(), longitude, latitude, location, MagicNumber.ZEROD, MagicNumber.ZEROD);

            // step 3 完成当前节点task
            String processInstanceId = objWorkOrder.getProcessInstanceId();

            Map<String, Object> variables = new HashMap<>();

            //签到之后，需要维修人去维修
            variables.put("taskAssignEmployee", objWorkOrder.getRepairEmployee().toString());

            activitiService.completeTask(variables, processInstanceId);

            LOGGER.debug("工单签到完成");

            //TODO 消息推送
            pushClientService.pushWorkAssign(objWorkOrder);

            builder.setResultEntity(objWorkOrder, ResponseCode.CREATE_SUCCEED);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            e.printStackTrace();
            LOGGER.debug("工单签到失败");
            String message = builder.getResponseEntity().getBody().getMessage();
            if (message.contains(BusinessRefData.WO_ERROR_MESSAGE_PART)) {
                // 说明该catch代码不是手动触发的
                builder.setResponseCode(ResponseCode.FAILED, message);
            } else {
                builder.setResponseCode(ResponseCode.FAILED);
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }


    /**
     * 工单取消报修-->+消息推送
     *
     * @param workOrderId
     * @return
     */
    @PutMapping("workOrders/endReport/{workOrderId}")
    @Transactional
    public ResponseEntity<RestBody<ObjWorkOrder>> workEndReport(@PathVariable("workOrderId") Integer workOrderId) {
        ResponseBuilder<ObjWorkOrder> builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();

            Integer canBeEndReportStatus = BusinessRefData.WOSTATUS_WO_BACK;

            LOGGER.debug("可以取消报修的工单状态为:{}", canBeEndReportStatus);

            if (Validator.isNull(workOrderId)) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            ObjWorkOrder workOrderDb = objWorkOrderRepository.findOne(workOrderId);

            Integer status = workOrderDb.getStatusId();

            if (!uid.equals(workOrderDb.getReportEmployee())) {
                // 当前工单不能取消报修
                LOGGER.debug("工单：{}的报修人不是当前账号，不能进行取消报修", workOrderId);

                builder.setResponseCode(ResponseCode.FAILED, "工单:" + workOrderDb.getSerialNo() + "报修人不是当前用户，不能取消报修");
                // 事务回滚
                throw new RuntimeException("工单报修人和当前账号不符合，不能取消报修");
            }

            if (!canBeEndReportStatus.equals(status)) {
                LOGGER.debug("工单：{}的状态为：{}，不能取消报修", workOrderId, status);

                builder.setResponseCode(ResponseCode.FAILED, "工单:" + workOrderDb.getSerialNo() + "当前的状态不能取消报修");
                // 事务回滚
                throw new RuntimeException("工单当前状态不能取消报修");
            }

            //step 1:保存工单表数据
            workOrderDb.setStatusId(BusinessRefData.WOSTATUS_WO_COMPLATED);

            ObjWorkOrder objWorkOrder = objWorkOrderRepository.save(workOrderDb);

            LOGGER.debug("工单取消报修成功，id为:{}", workOrderId);

            //step 2:保存工单操作记录表数据
            workOperateService.updateWorkOrderOperateDetail(workOrderId, objWorkOrder.getStatusId(), null, null, null, MagicNumber.ZEROD, MagicNumber.ZEROD);

            // step 3 完成当前节点task
            String processInstanceId = objWorkOrder.getProcessInstanceId();

            Map<String, Object> variables = new HashMap<>();

            //取消报修之后，需要维修人去维修
            variables.put("taskAssignEmployee", objWorkOrder.getAssignEmployee().toString());

            variables.put("repeatReport", false);
            //取消报修

            activitiService.completeTask(variables, processInstanceId);

            LOGGER.debug("工单取消报修完成");

            //TODO 消息推送

            builder.setResultEntity(objWorkOrder, ResponseCode.CREATE_SUCCEED);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            e.printStackTrace();
            LOGGER.debug("工单取消报修失败");
            String message = builder.getResponseEntity().getBody().getMessage();
            if (message.contains(BusinessRefData.WO_ERROR_MESSAGE_PART)) {
                // 说明该catch代码不是手动触发的
                builder.setResponseCode(ResponseCode.FAILED, message);
            } else {
                builder.setResponseCode(ResponseCode.FAILED);
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }

    /**
     * 工单维修--+消息推送
     *
     * @param workOrderId
     * @param fileIds
     * @param badComponentTypeIds
     * @return
     */
    @PutMapping("workOrders/complete/{workOrderId}")
    @Transactional
    public ResponseEntity<RestBody<ObjWorkOrder>> workRepairComplete(
            @PathVariable("workOrderId") Integer workOrderId,
            @RequestParam(value = "fileIds", required = false) List<Integer> fileIds,
            @RequestParam(value = "badComponentTypeIds", required = false) List<Integer> badComponentTypeIds,
            @RequestParam(value = "badComponentTypeCounts", required = false) List<Integer> badComponentTypeCounts,
            @RequestParam(value = "faultTypeIds", required = false) List<Integer> faultTypeIds,
            @RequestParam(value = "longitude", required = false) Double longitude,
            @RequestParam(value = "latitude", required = false) Double latitude,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "repairRemark", required = false) String repairRemark) {
        ResponseBuilder<ObjWorkOrder> builder = ResponseBuilder.createBuilder();
        List<ObjWorkOrderResource> objWorkOrderResourcesSave = new ArrayList<>();
        Set<ObjWorkOrderBadComponent> objWorkOrderBadComponentsSave = new HashSet<>();
        Set<Integer> typeIds = new HashSet<>();
        try {
            Integer uid = SecurityUtils.getLoginUserId();

            Integer canBeRepairStatus = BusinessRefData.WOSTATUS_WO_ARRIVE;

            LOGGER.debug("可以维修的工单状态为:{}", canBeRepairStatus);

            if (Validator.isNull(workOrderId)) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            if (Validator.isNotNull(badComponentTypeCounts) && Validator.isNotNull(badComponentTypeIds)) {
                if (badComponentTypeCounts.size() != badComponentTypeIds.size()) {
                    LOGGER.debug("工单坏件数据中类型和数量的集合数据长度不匹配，校验前台数据是否正确");
                    builder.setResponseCode(ResponseCode.FAILED, "工单更换备件数据有误，请确认");
                    return builder.getResponseEntity();
                }
            }

            ObjWorkOrder workOrderDb = objWorkOrderRepository.findOne(workOrderId);
            Integer status = workOrderDb.getStatusId();
            if (!uid.equals(workOrderDb.getRepairEmployee())) {
                // 当前工单不能被分配
                LOGGER.debug("工单：{}的维修人不是当前账号，不能维修", workOrderId);

                builder.setResponseCode(ResponseCode.FAILED, "工单:" + workOrderDb.getSerialNo() + "维修人不是当前用户，不能维修");
                // 事务回滚
                throw new RuntimeException("工单维修人和当前账号不符合，不能维修");
            }

            if (!canBeRepairStatus.equals(status)) {
                LOGGER.debug("工单：{}的状态为：{}，不能维修", workOrderId, status);

                builder.setResponseCode(ResponseCode.FAILED, "工单:" + workOrderDb.getSerialNo() + "当前的状态不能维修");
                // 事务回滚
                throw new RuntimeException("工单当前状态不能维修");
            }

            Double sumWorkOrderPoint = 0.0;
            if (badComponentTypeIds != null) {
                for (int i = 0; i < badComponentTypeIds.size(); i++) {
                    ObjEstateType objEstateType = objEstateTypeRepository.findOne(badComponentTypeIds.get(i));
                    sumWorkOrderPoint += objEstateType.getWorkpoints() * badComponentTypeCounts.get(i);
                }
            }


            //step 1:保存工单表数据
            workOrderDb.setSerialNo(WorkOrderSerialNoUtil.createSerialNo());
            workOrderDb.setStatusId(BusinessRefData.WOSTATUS_RPR_COMPLATED);
            workOrderDb.setRepairEmployee(uid);
//            workOrderDb.setReportEmployee(uid);
            workOrderDb.setRepairRemark(repairRemark);
            //workOrderDb.setReportTime(new Date());
            workOrderDb.setRepairEndTime(new Date());
            workOrderDb.setFixed(true);
            if (sumWorkOrderPoint > MagicNumber.DTEN) {
                workOrderDb.setWorkOrderScore(MagicNumber.DTEN);
            } else {
                workOrderDb.setWorkOrderScore(sumWorkOrderPoint);
            }

            ObjWorkOrder objWorkOrder = objWorkOrderRepository.save(workOrderDb);

            LOGGER.debug("工单维修成功，id为:{}", workOrderId);

            //step 2:保存工单操作记录表数据
            workOperateService.updateWorkOrderOperateDetail(objWorkOrder.getId(), objWorkOrder.getStatusId(), longitude, latitude, location, sumWorkOrderPoint, MagicNumber.ZEROD);

            //step 3:保存更换配件的数据,查询坏件数据，然后判断更换的备件是否是坏件中的，如果不是则新建一条记录，如果是则修改坏件记录中的更换的数量
            if (badComponentTypeIds != null) {
                List<ObjWorkOrderBadComponent> objWorkOrderBadComponentList = objWorkOrderBadComponentRepository.findByWorkOrderId(workOrderId);
                LOGGER.debug("工单：{}的坏件量为：{}", workOrderId, objWorkOrderBadComponentList.size());

                if (objWorkOrderBadComponentList != null) {
                    for (int i = 0; i < objWorkOrderBadComponentList.size(); i++) {
                        ObjWorkOrderBadComponent objWorkOrderBadComponent = objWorkOrderBadComponentList.get(i);
                        boolean t = badComponentTypeIds.contains(objWorkOrderBadComponent.getEstateTypeId());
                        if (t) {
                            objWorkOrderBadComponent.setRemoveTime(new Date());
                            objWorkOrderBadComponentRepository.save(objWorkOrderBadComponent);
                            // typeIds.add(objWorkOrderBadComponent.getEstateTypeId());
                        }
                    }
                }

                for (int j = 0; j < badComponentTypeIds.size(); j++) {
                    ObjWorkOrderBadComponent objWorkOrderBadComponent = new ObjWorkOrderBadComponent(workOrderId, badComponentTypeIds.get(j), badComponentTypeCounts.get(j));
                    objWorkOrderBadComponentsSave.add(objWorkOrderBadComponent);
                }


                objWorkOrderBadComponentRepository.save(objWorkOrderBadComponentsSave);
            }
            //减少个人库存记录
//            for (int i = 0; i < badComponentTypeIds.size(); i++) {
//                Integer badComponentTypeId = badComponentTypeIds.get(i);
//                Integer badComponentCout = badComponentTypeCounts.get(i);
//                ObjStockRecordPersonal objStockRecordPersonal = objStockRecordPersonalRepository.findByUserIdAndEstateTypeId(uid, badComponentTypeId);
//                if (Validator.isNull(objStockRecordPersonal)) {
//                    ObjStockRecordPersonal objStockRecordPersonalOne = new ObjStockRecordPersonal();
//                    objStockRecordPersonalOne.setCount((0 - badComponentCout));
//                    objStockRecordPersonalOne.setEstateTypeId(badComponentTypeId);
//                    objStockRecordPersonalOne.setUserId(uid);
//                    objStockRecordPersonalRepository.save(objStockRecordPersonalOne);
//                } else{
//                    Integer count = objStockRecordPersonal.getCount() - badComponentCout;
//                    objStockRecordPersonal.setCount(count);
//                    objStockRecordPersonalRepository.save(objStockRecordPersonal);
//                }
//            }


            //step 4:保存工单图片数据
            if (fileIds != null) {
                fileIds.forEach(fileId -> {
                    ObjWorkOrderResource objWorkOrderResource = new ObjWorkOrderResource();
                    objWorkOrderResource.setFileId(fileId);
                    objWorkOrderResource.setWorkOrderId(workOrderId);
                    objWorkOrderResource.setCategory(BusinessRefData.WO_RESOURCE_CATEGORY_REPAIR);
                    //报修阶段资源

                    objWorkOrderResourcesSave.add(objWorkOrderResource);
                });

                objWorkOrderResourceRepository.save(objWorkOrderResourcesSave);
                LOGGER.debug("工单资源保存成功,数据量为：{}", objWorkOrderResourcesSave.size());
            }


            /*添加工单故障类型数据*/
            if (Validator.isNotNull(faultTypeIds)) {
                List<ObjWorkOrderFaultType> objWorkOrderFaultTypes = new ArrayList<>();

                for (int i = 0; i < faultTypeIds.size(); i++) {

                    ObjWorkOrderFaultType objWorkOrderFaultType = new ObjWorkOrderFaultType();
                    objWorkOrderFaultType.setWorkOrderId(workOrderId);
                    objWorkOrderFaultType.setFaultTypeId(faultTypeIds.get(i));
                    objWorkOrderFaultTypes.add(objWorkOrderFaultType);
                }
                objWorkOrderFaultTypeRepository.save(objWorkOrderFaultTypes);
                LOGGER.debug("工单故障类型保存成功");
            }

            // step 6 完成当前节点task
            String processInstanceId = objWorkOrder.getProcessInstanceId();
            LOGGER.debug("完成当前节点task");
            Map<String, Object> variables = new HashMap<>();

//            variables.put("taskAssignEmployee", objWorkOrder.getAssignEmployee().toString());

            variables.put("taskAssignEmployee", objWorkOrder.getCheckEmployee().toString());

            variables.put("repairCompleteOrLeave", true);

            LOGGER.debug("taskAssignEmployee");

            activitiService.completeTask(variables, processInstanceId);

            LOGGER.debug("工单维修完成");

            //TODO 消息推送
            pushClientService.pushWorkComplete(objWorkOrder);

            builder.setResultEntity(objWorkOrder, ResponseCode.CREATE_SUCCEED);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            e.printStackTrace();
            LOGGER.debug("工单维修失败");
            String message = builder.getResponseEntity().getBody().getMessage();
            if (message.contains(BusinessRefData.WO_ERROR_MESSAGE_PART)) {
                // 说明该catch代码不是手动触发的
                builder.setResponseCode(ResponseCode.FAILED, message);
            } else {
                builder.setResponseCode(ResponseCode.FAILED);
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }


    /**
     * 工单遗留-->+消息推送
     *
     * @param workOrderId
     * @return
     */
    @PutMapping("workOrders/leaveOver/{workOrderId}")
    @Transactional
    public ResponseEntity<RestBody<ObjWorkOrder>> workLeaveOver(
            @PathVariable("workOrderId") Integer workOrderId,
            @RequestParam(value = "longitude", required = false) Double longitude,
            @RequestParam(value = "latitude", required = false) Double latitude,
            @RequestParam(value = "location", required = false) String location) {
        ResponseBuilder<ObjWorkOrder> builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();

            Integer canBeLeaveOverStatus = BusinessRefData.WOSTATUS_WO_ARRIVE;

            LOGGER.debug("可以遗留的工单状态为:{}", canBeLeaveOverStatus);

            if (Validator.isNull(workOrderId)) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            ObjWorkOrder workOrderDb = objWorkOrderRepository.findOne(workOrderId);

            Integer status = workOrderDb.getStatusId();

            if (!uid.equals(workOrderDb.getRepairEmployee())) {
                // 当前工单不能遗留
                LOGGER.debug("工单：{}的维修人不是当前账号，不能进行遗留", workOrderId);

                builder.setResponseCode(ResponseCode.FAILED, "工单:" + workOrderDb.getSerialNo() + "维修人不是当前用户，不能遗留");
                // 事务回滚
                throw new RuntimeException("工单维修人和当前账号不符合，不能遗留");
            }

            if (!canBeLeaveOverStatus.equals(status)) {
                LOGGER.debug("工单：{}的状态为：{}，不能遗留", workOrderId, status);

                builder.setResponseCode(ResponseCode.FAILED, "工单:" + workOrderDb.getSerialNo() + "当前的状态不能遗留");
                // 事务回滚
                throw new RuntimeException("工单当前状态不能遗留");
            }

            //step 1:保存工单表数据
            workOrderDb.setStatusId(BusinessRefData.WOSTATUS_WO_UNHANDLED);

            ObjWorkOrder objWorkOrder = objWorkOrderRepository.save(workOrderDb);

            LOGGER.debug("工单遗留成功，id为:{}", workOrderId);

            //step 2:保存工单操作记录表数据
            workOperateService.updateWorkOrderOperateDetail(workOrderId, objWorkOrder.getStatusId(), longitude, latitude, location, MagicNumber.ZEROD, MagicNumber.ZEROD);

            // step 3 完成当前节点task
            String processInstanceId = objWorkOrder.getProcessInstanceId();

            Map<String, Object> variables = new HashMap<>();

            //遗留之后，需要调度人去审核
//            variables.put("taskAssignEmployee", objWorkOrder.getAssignEmployee().toString());
            variables.put("taskAssignEmployee", objWorkOrder.getCheckEmployee().toString());
            variables.put("repairCompleteOrLeave", false);

            activitiService.completeTask(variables, processInstanceId);

            LOGGER.debug("工单遗留完成");

            //TODO 消息推送

            pushClientService.pushWorkUnhandled(objWorkOrder);
            builder.setResultEntity(objWorkOrder, ResponseCode.CREATE_SUCCEED);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            e.printStackTrace();
            LOGGER.debug("工单遗留失败");
            String message = builder.getResponseEntity().getBody().getMessage();
            if (message.contains(BusinessRefData.WO_ERROR_MESSAGE_PART)) {
                // 说明该catch代码不是手动触发的
                builder.setResponseCode(ResponseCode.FAILED, message);
            } else {
                builder.setResponseCode(ResponseCode.FAILED);
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }


    /**
     * 工单遗留审核-->消息推送
     *
     * @param workOrderId
     * @return
     */
    @PutMapping("workOrders/leaveCheck/{workOrderId}")
    @Transactional
    public ResponseEntity<RestBody<ObjWorkOrder>> workLeaveCheck(
            @PathVariable("workOrderId") Integer workOrderId, @RequestParam(value = "workOrderScoreDeduct") Double workOrderScoreDeduct, @RequestParam(value = "backRemark") String backRemark, @RequestParam(value = "repairEmployee") Integer repairEmployee) {
        ResponseBuilder<ObjWorkOrder> builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();

            List<Integer> canBeCheckRepairStatus = Arrays.asList(BusinessRefData.WOSTATUS_WO_UNHANDLED);

            LOGGER.debug("可以审核遗留的工单状态为:{}", canBeCheckRepairStatus);

            if (Validator.isNull(workOrderId)) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            ObjWorkOrder workOrderDb = objWorkOrderRepository.findOne(workOrderId);

            Integer status = workOrderDb.getStatusId();

            if (!uid.equals(workOrderDb.getCheckEmployee())) {
                // 当前工单不能审核维修
                LOGGER.debug("工单：{}的审核人不是当前账号，不能进行遗留审核,--{},---{}", workOrderId, uid, workOrderDb.getCheckEmployee());

                builder.setResponseCode(ResponseCode.FAILED, "工单:" + workOrderDb.getSerialNo() + "派单人不是当前用户，不能遗留审核");
                // 事务回滚
                throw new RuntimeException("工单审核人和当前账号不符合，不能遗留审核");
            }

            if (!canBeCheckRepairStatus.contains(status)) {
                LOGGER.debug("工单：{}的状态为：{}，不能遗留审核", workOrderId, status);

                builder.setResponseCode(ResponseCode.FAILED, "工单:" + workOrderDb.getSerialNo() + "当前的状态不能遗留审核");
                // 事务回滚
                throw new RuntimeException("工单当前状态不能遗留审核");
            }

            //step 1:保存工单表数据
//            workOrderDb.setStatusId(BusinessRefData.WOSTATUS_WO_COMPLATED);
            workOrderDb.setWorkOrderScoreDeduct(workOrderScoreDeduct);
            workOrderDb.setScoreDeductRemark(backRemark);
            //工单完成
            ObjWorkOrder objWorkOrder = objWorkOrderRepository.save(workOrderDb);


            ResponseTimeOutAndRepairTimeOut responseTimeOutAndRepairTimeOut = getResponseTimeOutAndRepairTimeOut(workOrderDb.getLevel());

            LOGGER.debug("响应超时时间为：{}", responseTimeOutAndRepairTimeOut.toString());

            /*复制新的工单*/
            ObjWorkOrder objWorkOrderReassign = new ObjWorkOrder();
//            objWorkOrderReassign.setId(objWorkOrder.getId());
            objWorkOrderReassign.setTypeId(objWorkOrder.getTypeId());
            objWorkOrderReassign.setEstateId(objWorkOrder.getEstateId());
            objWorkOrderReassign.setReportEmployee(objWorkOrder.getReportEmployee());
            objWorkOrderReassign.setReportTime(objWorkOrder.getReportTime());
            objWorkOrderReassign.setAssignTime(new Date());
            objWorkOrderReassign.setRepairEmployee(repairEmployee);
            objWorkOrderReassign.setStatusId(BusinessRefData.WOSTATUS_WO_SENDED);
            objWorkOrderReassign.setAssignRemark(objWorkOrder.getAssignRemark());

            objWorkOrderReassign.setCheckEmployee(objWorkOrder.getCheckEmployee());

            objWorkOrderReassign.setStationId(objWorkOrder.getStationId());
            objWorkOrderReassign.setLevel(objWorkOrder.getLevel());
            objWorkOrderReassign.setLastUpdateBy(uid);
            objWorkOrderReassign.setAssignEmployee(uid);
            objWorkOrderReassign.setFaultDescription(objWorkOrder.getFaultDescription());
            objWorkOrderReassign.setFaultId(objWorkOrder.getFaultId());
            objWorkOrderReassign.setCheckEmployee(objWorkOrder.getCheckEmployee());

            objWorkOrderReassign.setReportWay(objWorkOrder.getReportWay());
            objWorkOrderReassign.setSerialNo(WorkOrderSerialNoUtil.createSerialNo());
            objWorkOrderReassign.setProcessInstanceId(objWorkOrder.getProcessInstanceId());

            objWorkOrderReassign.setWorkOrderScore(MagicNumber.ZEROD);
            objWorkOrderReassign.setWorkOrderScoreDeduct(MagicNumber.ZEROD);
            objWorkOrderReassign.setResponseTimeOutDate(responseTimeOutAndRepairTimeOut.getResponseTimeOut());
            objWorkOrderReassign.setRepairTimeOutDate(responseTimeOutAndRepairTimeOut.getRepairTimeOut());
            ObjWorkOrder objWorkOrderAgain = objWorkOrderRepository.save(objWorkOrderReassign);


            /*复制工单报修资源*/
            List<ObjWorkOrderResource> objWorkOrderResources = objWorkOrderResourceRepository.findByWorkOrderIdAndCategory(objWorkOrder.getId(), 1);
            List<ObjWorkOrderResource> objWorkOrderResourcesAgain = new ArrayList<>();
            for (int i = 0; i < objWorkOrderResources.size(); i++) {
                ObjWorkOrderResource objWorkOrderResource = objWorkOrderResources.get(i);
                ObjWorkOrderResource objWorkOrderResourceNew = new ObjWorkOrderResource();
                objWorkOrderResourceNew.setFileId(objWorkOrderResource.getFileId());
                objWorkOrderResourceNew.setCategory(1);
                objWorkOrderResourceNew.setWorkOrderId(objWorkOrderAgain.getId());
                objWorkOrderResourcesAgain.add(objWorkOrderResourceNew);
            }
            objWorkOrderResourceRepository.save(objWorkOrderResourcesAgain);


            /*复制工单换件类型*/
            List<ObjWorkOrderBadComponent> objWorkOrderBadComponents = objWorkOrderBadComponentRepository.findByWorkOrderId(objWorkOrder.getId());
            List<ObjWorkOrderBadComponent> objWorkOrderBadComponentsAgain = new ArrayList<>();

            for (int i = 0; i < objWorkOrderBadComponents.size(); i++) {


                ObjWorkOrderBadComponent objWorkOrderBadComponent = objWorkOrderBadComponents.get(i);
                ObjWorkOrderBadComponent objWorkOrderBadComponentNew = new ObjWorkOrderBadComponent();
                objWorkOrderBadComponentNew.setEstateTypeId(objWorkOrderBadComponent.getEstateTypeId());
                objWorkOrderBadComponentNew.setWorkOrderId(objWorkOrderAgain.getId());
                objWorkOrderBadComponentsAgain.add(objWorkOrderBadComponentNew);
            }
            objWorkOrderBadComponentRepository.save(objWorkOrderBadComponentsAgain);

            LOGGER.debug("工单遗留审核成功，id为:{}", workOrderId);

            //step 2:保存工单操作记录表数据
            workOperateService.updateWorkOrderOperateDetail(workOrderId, objWorkOrder.getStatusId(), null, null, null, objWorkOrder.getWorkOrderScore(), workOrderScoreDeduct);

            // step 3 完成当前节点task
            String processInstanceId = objWorkOrderAgain.getProcessInstanceId();

            Map<String, Object> variables = new HashMap<>();

            //重新分配维修人
            variables.put("taskAssignEmployee", objWorkOrderAgain.getRepairEmployee().toString());

            LOGGER.debug("设置流程响应时间...");
            variables.put("responseTimeOut_time", responseTimeOutAndRepairTimeOut.getResponseTimeOut());

            LOGGER.debug("设置流程下一步操作人...");
            variables.put("woAssignEmployee", objWorkOrderAgain.getAssignEmployee().toString());

            variables.put("groupLeaderPassReport", true);

            activitiService.completeTask(variables, processInstanceId);


            LOGGER.debug("工单遗留审核完成");

            //TODO 消息推送
            List<Integer> userIds = new ArrayList<>();

            userIds.add(objWorkOrderAgain.getRepairEmployee());

            String content = "工单【" + objWorkOrderAgain.getSerialNo() + "】遗留审核完成";


            pushClientService.pushCommonMsg(content, userIds);

            builder.setResultEntity(objWorkOrderAgain, ResponseCode.CREATE_SUCCEED);
             /*改变原始工单工作流*/
            objWorkOrder.setProcessInstanceId("");
            objWorkOrderRepository.save(objWorkOrder);


        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            e.printStackTrace();
            LOGGER.debug("工单遗留审核失败");
            String message = builder.getResponseEntity().getBody().getMessage();
            if (message.contains(BusinessRefData.WO_ERROR_MESSAGE_PART)) {
                // 说明该catch代码不是手动触发的
                builder.setResponseCode(ResponseCode.FAILED, message);
            } else {
                builder.setResponseCode(ResponseCode.FAILED);
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }


    /**
     * 工单维修审核-->消息推送
     *
     * @param workOrderId
     * @return
     */
    @PutMapping("workOrders/repairCheck/{workOrderId}")
    @Transactional
    public ResponseEntity<RestBody<ObjWorkOrder>> workRepairCheck(
            @PathVariable("workOrderId") Integer workOrderId, @RequestParam(value = "workOrderScoreDeduct") Double workOrderScoreDeduct, @RequestParam(value = "scoreDeductRemark") String scoreDeductRemark) {
        ResponseBuilder<ObjWorkOrder> builder = ResponseBuilder.createBuilder();
        try {
            Integer uid = SecurityUtils.getLoginUserId();

            List<Integer> canBeCheckRepairStatus = Arrays.asList(BusinessRefData.WOSTATUS_RPR_COMPLATED);

            LOGGER.debug("可以审核维修的工单状态为:{}", canBeCheckRepairStatus);

            if (Validator.isNull(workOrderId)) {
                LOGGER.debug("参数缺失，请求失败");
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            ObjWorkOrder workOrderDb = objWorkOrderRepository.findOne(workOrderId);

            Integer status = workOrderDb.getStatusId();

            if (!uid.equals(workOrderDb.getCheckEmployee())) {
                // 当前工单不能审核维修
                LOGGER.debug("工单：{}的审核人不是当前账号，不能进行审核维修", workOrderId);

                builder.setResponseCode(ResponseCode.FAILED, "工单:" + workOrderDb.getSerialNo() + "派单人不是当前用户，不能审核维修");
                // 事务回滚
                throw new RuntimeException("工单审核人和当前账号不符合，不能审核维修");
            }

            if (!canBeCheckRepairStatus.contains(status)) {
                LOGGER.debug("工单：{}的状态为：{}，不能审核维修", workOrderId, status);

                builder.setResponseCode(ResponseCode.FAILED, "工单:" + workOrderDb.getSerialNo() + "当前的状态不能审核维修");
                // 事务回滚
                throw new RuntimeException("工单当前状态不能审核维修");
            }

            //step 1:保存工单表数据
            workOrderDb.setStatusId(BusinessRefData.WOSTATUS_WO_COMPLATED);
            workOrderDb.setWorkOrderScoreDeduct(workOrderScoreDeduct);
            workOrderDb.setScoreDeductRemark(scoreDeductRemark);
            //工单完成


            ObjWorkOrder objWorkOrder = objWorkOrderRepository.save(workOrderDb);

            LOGGER.debug("工单审核维修成功，id为:{}", workOrderId);

            //step 2:保存工单操作记录表数据
            workOperateService.updateWorkOrderOperateDetail(workOrderId, objWorkOrder.getStatusId(), objWorkOrder.getLongitude(), objWorkOrder.getLatitude(), objWorkOrder.getLocation(), objWorkOrder.getWorkOrderScore(), workOrderScoreDeduct);

            // step 3 完成当前节点task
            String processInstanceId = objWorkOrder.getProcessInstanceId();

            Map<String, Object> variables = new HashMap<>();

            activitiService.completeTask(variables, processInstanceId);

            LOGGER.debug("工单审核维修完成");

            //TODO 消息推送
            List<Integer> userIds = new ArrayList<>();
            userIds.add(objWorkOrder.getRepairEmployee());

            String content = "工单【" + objWorkOrder.getSerialNo() + "】维修审核完成";
            pushClientService.pushCommonMsg(content, userIds);

            builder.setResultEntity(objWorkOrder, ResponseCode.CREATE_SUCCEED);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            e.printStackTrace();
            LOGGER.debug("工单审核维修失败");
            String message = builder.getResponseEntity().getBody().getMessage();
            if (message.contains(BusinessRefData.WO_ERROR_MESSAGE_PART)) {
                // 说明该catch代码不是手动触发的
                builder.setResponseCode(ResponseCode.FAILED, message);
            } else {
                builder.setResponseCode(ResponseCode.FAILED);
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }

    /**
     * 工单自修保养
     *
     * @param objWorkOrder
     * @param fileIds
     * @param badComponentTypeIds
     * @return
     */
    @PostMapping("workOrders/maintenance")
    @Transactional
    public ResponseEntity<RestBody<ObjWorkOrder>> workOrderMaintenance(
            @RequestBody ObjWorkOrder objWorkOrder,
            @RequestParam(value = "fileIds", required = false) List<Integer> fileIds,
            @RequestParam(value = "faultTypeIds", required = false) List<Integer> faultTypeIds,
            @RequestParam(value = "badComponentTypeIds", required = false) List<Integer> badComponentTypeIds,
            @RequestParam(value = "badComponentTypeCounts", required = false) List<Integer> badComponentTypeCounts) {
        ResponseBuilder<ObjWorkOrder> builder = ResponseBuilder.createBuilder();
        List<ObjWorkOrderResource> objWorkOrderResourcesSave = new ArrayList<>();
        List<ObjWorkOrderBadComponent> objWorkOrderBadComponentsSave = new ArrayList<>();
        try {
            LOGGER.debug("工单新建，前端传入的参数:{}", objWorkOrder.toString());
            Integer uid = SecurityUtils.getLoginUserId();

            if (!checkWorkOrderMaintenanceParam(objWorkOrder)) {
                LOGGER.debug("前端传入参数有误");
                builder.setResponseCode(ResponseCode.PARAM_MISSING, "部分参数有误!");
                return builder.getResponseEntity();
            }


            if (Validator.isNotNull(badComponentTypeCounts) && Validator.isNotNull(badComponentTypeIds)) {
                if (badComponentTypeCounts.size() != badComponentTypeIds.size()) {
                    LOGGER.debug("工单坏件数据中类型和数量的集合数据长度不匹配，校验前台数据是否正确");
                    builder.setResponseCode(ResponseCode.FAILED, "工单更换备件数据有误，请确认");
                    return builder.getResponseEntity();
                }
            }


            Double sumWorkOrderPoint = 0.0;
            if (Validator.isNotNull(badComponentTypeIds)) {
                for (int i = 0; i < badComponentTypeIds.size(); i++) {
                    ObjEstateType objEstateType = objEstateTypeRepository.findOne(badComponentTypeIds.get(i));
                    sumWorkOrderPoint += objEstateType.getWorkpoints() * badComponentTypeCounts.get(i);
                }
            }
            //添加审核人
            ObjStation objStation = objStationRepository.findOne(objWorkOrder.getStationId());

            objWorkOrder.setCheckEmployee(objStation.getCheckUserId());

            //step 1:保存工单表数据
            objWorkOrder.setSerialNo(WorkOrderSerialNoUtil.createSerialNo());
            objWorkOrder.setStatusId(BusinessRefData.WOSTATUS_RPR_COMPLATED);
            objWorkOrder.setReportEmployee(uid);
            objWorkOrder.setRepairEmployee(uid);
            objWorkOrder.setReportTime(new Date());
            objWorkOrder.setRepairEndTime(new Date());
            objWorkOrder.setFixed(true);
            objWorkOrder.setTypeId(BusinessRefData.WOTYPE_MAINTENANCE_WO);

            if (sumWorkOrderPoint > MagicNumber.DTEN) {
                objWorkOrder.setWorkOrderScore(MagicNumber.DTEN);
            } else if (sumWorkOrderPoint > MagicNumber.ZEROD) {
                objWorkOrder.setWorkOrderScore(sumWorkOrderPoint);
            } else {
                objWorkOrder.setWorkOrderScore(MagicNumber.ZEROD);
            }
            //保养工单

            ObjWorkOrder objWorkOrder1 = objWorkOrderRepository.save(objWorkOrder);

            Integer workOrderId = objWorkOrder1.getId();
            LOGGER.debug("工单新建成功，id为:{}", workOrderId);

            //step 2:保存工单操作记录表数据
            workOperateService.updateWorkOrderOperateDetail(objWorkOrder1.getId(), objWorkOrder1.getStatusId(), objWorkOrder1.getLongitude(), objWorkOrder1.getLatitude(), objWorkOrder1.getLocation(), sumWorkOrderPoint, MagicNumber.ZEROD);

            /*
             *车辆报修时，选择自行车设备类型下的模块类型
             */
            //step 3:保存更换配件的数据,查询坏件数据，然后判断更换的备件是否是坏件中的，如果不是则新建一条记录，如果是则修改坏件记录中的更换的数量
            LOGGER.debug("step 3:保存更换配件的数据");
            if (badComponentTypeIds != null) {

                for (int i = 0; i < badComponentTypeIds.size(); i++) {
                    Integer badComponentTypeId = badComponentTypeIds.get(i);
                    Integer badComponentCout = badComponentTypeCounts.get(i);
                    LOGGER.debug("plan save workOrderBadComponent,workorderId:{},badComponentTypeId:{},badComponentCout:{}", workOrderId, badComponentTypeId, badComponentCout);

                    ObjWorkOrderBadComponent objWorkOrderBadComponent = new ObjWorkOrderBadComponent(workOrderId, badComponentTypeId, badComponentCout);
                    objWorkOrderBadComponentsSave.add(objWorkOrderBadComponent);

                }
                objWorkOrderBadComponentRepository.save(objWorkOrderBadComponentsSave);
            }

            LOGGER.debug("step 4:保存工单图片数据");
            //step 4:保存工单图片数据
            if (Validator.isNotNull(fileIds) && !fileIds.isEmpty()) {
                fileIds.forEach(fileId -> {
                    ObjWorkOrderResource objWorkOrderResource = new ObjWorkOrderResource();
                    objWorkOrderResource.setFileId(fileId);
                    objWorkOrderResource.setWorkOrderId(workOrderId);
                    objWorkOrderResource.setCategory(BusinessRefData.WO_RESOURCE_CATEGORY_MAINTENANCE);
                    //报修阶段资源
                    LOGGER.debug("保存工单资源，工单id：{}，文件id：{}", workOrderId, fileId);
                    objWorkOrderResourcesSave.add(objWorkOrderResource);
                });

                objWorkOrderResourceRepository.save(objWorkOrderResourcesSave);
                LOGGER.debug("工单资源保存成功,数据量为：{}", objWorkOrderResourcesSave.size());
            }

             /*添加工单故障类型数据*/
            if (Validator.isNotNull(faultTypeIds)) {
                List<ObjWorkOrderFaultType> objWorkOrderFaultTypes = new ArrayList<>();

                for (int i = 0; i < faultTypeIds.size(); i++) {

                    ObjWorkOrderFaultType objWorkOrderFaultType = new ObjWorkOrderFaultType();
                    objWorkOrderFaultType.setWorkOrderId(workOrderId);
                    objWorkOrderFaultType.setFaultTypeId(faultTypeIds.get(i));
                    objWorkOrderFaultTypes.add(objWorkOrderFaultType);
                }
                objWorkOrderFaultTypeRepository.save(objWorkOrderFaultTypes);
                LOGGER.debug("工单故障类型保存成功");
            }

            String processInstanceId = activitiService.startRepairSelfWorkflow(objWorkOrder1);

            objWorkOrder1.setProcessInstanceId(processInstanceId);

            ObjWorkOrder objWorkOrder2 = objWorkOrderRepository.save(objWorkOrder1);

            LOGGER.debug("工单报修的流程id为：{}", processInstanceId);


            //TODO 消息推送
            List<Integer> userIds = new ArrayList<>();

//            userIds.add(objWorkOrder2.getAssignEmployee());
            userIds.add(objWorkOrder2.getCheckEmployee());
            LOGGER.debug("工单报修的流程id为：{}", processInstanceId);
            String content = "自修保养工单【" + objWorkOrder2.getSerialNo() + "】";


            pushClientService.pushCommonMsg(content, userIds);


            builder.setResultEntity(objWorkOrder2, ResponseCode.CREATE_SUCCEED);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            e.printStackTrace();
            LOGGER.debug("工单新建失败,message：{},trace:{}", e.getMessage(), e.getStackTrace());
            String message = builder.getResponseEntity().getBody().getMessage();
            if (message.contains(BusinessRefData.WO_ERROR_MESSAGE_PART)) {
                // 说明该catch代码不是手动触发的
                builder.setResponseCode(ResponseCode.FAILED, message);
            } else {
                builder.setResponseCode(ResponseCode.FAILED);
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return builder.getResponseEntity();
    }


    /**
     * 校验工单参数是否正确，有误返回false，没毛病返回true
     *
     * @param objWorkOrder
     * @return
     */
    private boolean checkWorkOrderParam(ObjWorkOrder objWorkOrder) {
        if (Validator.isNull(objWorkOrder.getTypeId())) {
            LOGGER.debug("工单类型参数不能为空");
            return false;
        }

        if (Validator.isNull(objWorkOrder.getReportWay())) {
            LOGGER.debug("工单报修方式参数不能为空");
            return false;
        } else {
            Integer reportWay = objWorkOrder.getReportWay();

            if (!BusinessRefData.REPORT_WAY_STATION.equals(reportWay) && !BusinessRefData.REPORT_WAY_BIKE.equals(reportWay)) {
                LOGGER.debug("工单的维修方式有误");
                return false;
            }
        }

        if (objWorkOrder.getStationId() == null) {
            LOGGER.debug("站点不能为空");
            return false;
        }

        if (Validator.isNull(objWorkOrder.getAssignEmployee())) {
            LOGGER.debug("工单的调度人不能为空");
            return false;
        }
        return true;
    }


    /**
     * 校验工单参数是否正确，有误返回false，没毛病返回true
     *
     * @param objWorkOrder
     * @return
     */
    private boolean checkWorkOrderMaintenanceParam(ObjWorkOrder objWorkOrder) {
        if (Validator.isNull(objWorkOrder.getTypeId())) {
            LOGGER.debug("工单类型参数不能为空");
            return false;
        }

        if (Validator.isNull(objWorkOrder.getReportWay())) {
            LOGGER.debug("工单报修方式参数不能为空");
            return false;
        } else {
            Integer reportWay = objWorkOrder.getReportWay();

            if (!BusinessRefData.REPORT_WAY_STATION.equals(reportWay) && !BusinessRefData.REPORT_WAY_BIKE.equals(reportWay)) {
                LOGGER.debug("工单的维修方式有误");
                return false;
            }
        }

        if (objWorkOrder.getStationId() == null) {
            LOGGER.debug("站点不能为空");
            return false;
        }
        return true;
    }


    /**
     * 批量删除工单
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "workOrders/deleteMore", method = RequestMethod.DELETE)
    @Transactional
    ResponseEntity<RestBody<SysUser>> deleteWorkOrderMore(@RequestParam(value = "ids") List<Integer> ids) {
        ResponseBuilder builder = ResponseBuilder.createBuilder();

        Integer uid = SecurityUtils.getLoginUserId();
        try {
            LOGGER.debug("workOrders/deleteMore：ids为({})", ids);

            if (ids.isEmpty()) {
                LOGGER.debug("ids({})参数缺失", ids);
                builder.setResponseCode(ResponseCode.PARAM_MISSING);
                return builder.getResponseEntity();
            }

            if (ids.size() <= BusinessRefData.BATCH_COUNT) {
//                List<SysUser> userList = sysUserRepository.findByIdInOrderByLastUpdateTimeDesc(ids);
                List<ObjWorkOrder> objWorkOrders = objWorkOrderRepository.findByIdInOrderByLastUpdateTimeDesc(ids);
                LOGGER.debug("批量删除数据量为：", objWorkOrders.size());
                for (ObjWorkOrder objWorkOrder : objWorkOrders) {
                    objWorkOrder.setRemoveTime(new Date());
                    objWorkOrder.setLastUpdateBy(uid);

                    /*删除工单操作记录*/
                    List<ObjWorkOrderOperation> objWorkOrderOperations = objWorkorderOperationRepository.findByWorkOrderIdOrderByCreateTimeDesc(objWorkOrder.getId());
                    for (ObjWorkOrderOperation objWorkOrderOperation : objWorkOrderOperations) {
                        objWorkOrderOperation.setRemoveTime(new Date());
                        objWorkOrderOperation.setLastUpdateBy(uid);
                    }
                    objWorkorderOperationRepository.save(objWorkOrderOperations);

                      /*删除工单资源*/
                    List<ObjWorkOrderResource> objWorkOrderResources = objWorkOrderResourceRepository.findByWorkOrderId(objWorkOrder.getId());
                    for (ObjWorkOrderResource objWorkOrderResource : objWorkOrderResources) {
                        objWorkOrderResource.setRemoveTime(new Date());
                        objWorkOrderResource.setLastUpdateBy(uid);
                    }
                    objWorkOrderResourceRepository.save(objWorkOrderResources);

                    /*删除工单坏件记录*/
                    List<ObjWorkOrderBadComponent> objWorkOrderBadComponents = objWorkOrderBadComponentRepository.findByWorkOrderId(objWorkOrder.getId());
                    for (ObjWorkOrderBadComponent objWorkOrderBadComponent : objWorkOrderBadComponents) {
                        objWorkOrderBadComponent.setRemoveTime(new Date());
                        objWorkOrderBadComponent.setLastUpdateBy(uid);
                    }
                    objWorkOrderBadComponentRepository.save(objWorkOrderBadComponents);

                }
                objWorkOrderRepository.save(objWorkOrders);


                builder.setResponseCode(ResponseCode.DELETE_SUCCEED);
                LOGGER.debug("ids({})对应的用户批量删除成功", ids);
            } else {
                LOGGER.debug("批量删除的数量必须在({})以内", BusinessRefData.BATCH_COUNT);

                builder.setResponseCode(ResponseCode.FAILED, "批量删除的数量必须在30条以内");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.debug("批量删除的数量必须在30条以内");
            builder.setResponseCode(ResponseCode.FAILED, "批量删除的数量必须在30条以内");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return builder.getResponseEntity();
    }


}
