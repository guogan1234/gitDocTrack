/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.service.activiti;

import com.avp.mem.njpb.entity.stock.ObjStockWorkOrder;
import com.avp.mem.njpb.entity.workorder.ObjWorkOrder;
import com.avp.mem.njpb.util.Validator;
import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 该部分功能点：启动维修工单流程、启动库存工单流程、获取下一节点操作、完成节点任务
 * <p>
 * Created by Amber Wang on 2017-07-04 下午 03:56.
 */
@Service
@Transactional
public class ActivitiService {

    private static Logger LOGGER = LoggerFactory.getLogger(WorkOrderWorkflowService.class);

    @Autowired
    protected RepositoryService repositoryService;

    @Autowired
    protected FormService formService;

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    protected TaskService taskService;

    @Autowired
    protected IdentityService identityService;

    @Value("${business.service.processKey}")
    private String repairWoProcessKey;

    @Value("${business.service.processKeySelf}")
    private String repairSelfWoProcessKey;

    @Value("${business.service.processKeyOfStock}")
    private String stockWoProcessKey;

    /**
     * 启动维修工单流程
     *
     * @param entity
     */
    public String startRepairWorkflow(ObjWorkOrder entity) {
        String businessKey = entity.getId().toString();

        ProcessInstance processInstance = null;
        String processInstanceId = "";
        try {
            // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
            identityService.setAuthenticatedUserId(entity.getCreateBy().toString());

            Map<String, Object> params = new HashMap<>();
            params.put("taskAssignEmployee", entity.getAssignEmployee().toString());

            processInstance = runtimeService.startProcessInstanceByKey(repairWoProcessKey, businessKey, params);

            processInstanceId = processInstance.getId();

            LOGGER.debug("start process of key={}, businessKey={}, processInstanceId={}", entity.getId(), businessKey, processInstanceId);

        } finally {
            identityService.setAuthenticatedUserId(null);
        }
        return processInstanceId;
    }

    /**
     * 启动库存工单流程
     *
     * @param stockWorkOrder
     */
    public String startStockWorkflow(ObjStockWorkOrder stockWorkOrder) {
        String businessKey = stockWorkOrder.getId().toString();

        ProcessInstance processInstance = null;
        String processInstanceId = "";
        try {
            // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
            identityService.setAuthenticatedUserId(stockWorkOrder.getCreateBy().toString());

            Map<String, Object> params = new HashMap<>();

            params.put("taskAssignEmployee", stockWorkOrder.getProcessUserId().toString());

            processInstance = runtimeService.startProcessInstanceByKey(stockWoProcessKey, businessKey, params);

            processInstanceId = processInstance.getId();

            LOGGER.debug("start process of key={}, businessKey={}, processInstanceId={}", stockWorkOrder.getId(), businessKey, processInstanceId);

        } finally {
            identityService.setAuthenticatedUserId(null);
        }
        return processInstanceId;
    }




    /**
     * 启动自修保养工单流程
     *
     * @param entity
     */
    public String startRepairSelfWorkflow(ObjWorkOrder entity) {
        String businessKey = entity.getId().toString();

        ProcessInstance processInstance = null;
        String processInstanceId = "";
        try {
            // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
            identityService.setAuthenticatedUserId(entity.getCreateBy().toString());

            Map<String, Object> params = new HashMap<>();
//            params.put("taskAssignEmployee", entity.getAssignEmployee().toString());
            params.put("taskAssignEmployee", entity.getCheckEmployee().toString());

            processInstance = runtimeService.startProcessInstanceByKey(repairSelfWoProcessKey, businessKey, params);

            processInstanceId = processInstance.getId();

            LOGGER.debug("start process of key={}, businessKey={}, processInstanceId={}", entity.getId(), businessKey, processInstanceId);

        } finally {
            identityService.setAuthenticatedUserId(null);
        }
        return processInstanceId;
    }






    /**
     * 获取下一节点操作
     *
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public List<String> getTaskOperation(String processInstanceId) throws Exception {
        List<String> nextOperation = new ArrayList<>();
        /*Execution execution = runtimeService.createExecutionQuery().executionId(processInstanceId).singleResult();//执行实例
        Object property = PropertyUtils.getProperty(execution, "activityId");
        String activityId = "";
        if (property != null) {
            activityId = property.toString();
        }
*/
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        if (Validator.isNull(task)) {
            LOGGER.debug("该流程：{}已经完成", processInstanceId);
            return nextOperation;
        }

        String activityId = task.getTaskDefinitionKey();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
                .singleResult();
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(processInstance.getProcessDefinitionId());

//            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
//                    .singleResult();
//            ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
//                    .getDeployedProcessDefinition(processInstance.getProcessDefinitionId());

        ActivityImpl activityImpl = processDefinition.findActivity(activityId);

        List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();

        for (PvmTransition tr : outTransitions) {

            String type = (String) tr.getDestination().getProperty("type");

            if ("exclusiveGateway".equals(type)) {
                //说明是网关
                PvmActivity ac = tr.getDestination(); //获取线路的终点节点
                System.out.println("下一步任务任务：" + ac.getProperty("name"));

                List<PvmTransition> pvmTransitions = ac.getOutgoingTransitions();

                pvmTransitions.forEach(pvmTransition -> {
                    LOGGER.debug("网关的操作为：{}", pvmTransition.getId());
                    nextOperation.add(pvmTransition.getId());
                });
            } else if ("userTask".equals(type) || "endEvent".equals(type)) {
                //TODO 判断是task获取当前的flowid
                LOGGER.debug("节点类型是userTask或者endEvent，flow ID是：{}", tr.getId());
                nextOperation.add(tr.getId());
            }
        }
        return nextOperation;

    }

    /**
     * 完成节点任务
     *
     * @param taskVariables
     * @param processInstanceId
     */
    public void completeTask(Map<String, Object> taskVariables, String processInstanceId) {
        taskVariables.forEach((k, v) -> System.out.println("task参数----> key : " + k + " value : " + v));

        // 设置当前任务信息
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().orderByTaskCreateTime().desc().singleResult();
        LOGGER.debug("task:{},名称为：{}", task.getId(), task.getName());

        taskService.complete(task.getId(), taskVariables);

        LOGGER.debug("Task:{}完成，参数为：{}", task.getId(), taskVariables.toString());

    }
}