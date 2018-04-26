/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.service.activiti;

import com.avp.mem.njpb.config.ExtensionOperation;
import com.avp.mem.njpb.entity.view.VwUserRole;
import com.avp.mem.njpb.entity.view.VwUserWorkOrder;
import com.avp.mem.njpb.entity.view.VwWorkOrder;
import com.avp.mem.njpb.entity.workorder.ObjWorkOrder;
import com.avp.mem.njpb.repository.sys.VwUserRoleRepository;
import com.avp.mem.njpb.repository.workorder.ObjWorkOrderRepository;
import com.avp.mem.njpb.repository.workorder.VwUserWorkOrderRepository;
import com.avp.mem.njpb.repository.workorder.VwWorkOrderRepository;
import com.avp.mem.njpb.util.BpmnConstants;
import com.avp.mem.njpb.util.BusinessRefData;
import com.avp.mem.njpb.util.Validator;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 工单流程Service：用于获取流程数据
 *
 * @author Amber
 */
@Component
@Transactional
public class WorkOrderWorkflowService {

    private static Logger LOGGER = LoggerFactory.getLogger(WorkOrderWorkflowService.class);

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    protected TaskService taskService;

    @Autowired
    protected HistoryService historyService;

    @Autowired
    protected RepositoryService repositoryService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private ObjWorkOrderRepository objWorkOrderRepository;

    @Autowired
    private VwUserRoleRepository vwUserRoleRepository;

    @Autowired
    private VwUserWorkOrderRepository vwUserWorkOrderRepository;

    @Autowired
    private VwWorkOrderRepository vwWorkOrderRepository;

    @Value("${business.service.processKey}")
    private String processKey;

    /**
     * 查询待办任务--上拉加载更多--无数据权限
     *
     * @param userId        用户ID
     * @param operationTime
     * @return
     */
    @Transactional(readOnly = true)
    public Page<VwWorkOrder> findTodoTasksByUserIdAndOperationTimeLessThan(Integer userId, Date operationTime, Pageable page) {
        List<String> processInstanceIds = new ArrayList<>();

        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId.toString()).list();

        LOGGER.debug("根据当前用户查询待办事项task，数量为：{}", tasks.size());

        tasks.forEach(task -> {
            String processInstanceId = task.getProcessInstanceId();
            //为工单表中存放的id

            processInstanceIds.add(processInstanceId);
            LOGGER.debug("工单中的流程id为：{}", processInstanceId);
        });

        if (processInstanceIds.isEmpty()) {
            LOGGER.debug("当前角色待处理的流程为空");
//            return null;
        }
        Page<VwWorkOrder> vwWorkOrders = vwWorkOrderRepository.findByProcessInstanceIdInAndLastUpdateTimeLessThanOrderByLastUpdateTimeDesc(processInstanceIds, operationTime, page);

        LOGGER.debug("当前用户角色查询流程数量为：{}", processInstanceIds.size());

        return vwWorkOrders;
    }

    /**
     * 查询待办任务--下拉刷新--无数据权限
     *
     * @param userId        用户ID
     * @param operationTime
     * @return
     */
    @Transactional(readOnly = true)
    public List<VwWorkOrder> findTodoTasksByUserIdAndOperationTimeGreaterThan(Integer userId, Date operationTime) {

        List<String> processInstanceIds = new ArrayList<>();

        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId.toString()).list();

        LOGGER.debug("根据当前用户查询待办事项task，数量为：{}", tasks.size());

        tasks.forEach(task -> {
            String processInstanceId = task.getProcessInstanceId();
            //为工单表中存放的id

            processInstanceIds.add(processInstanceId);
            LOGGER.debug("工单中的流程id为：{}", processInstanceId);
        });

        if (processInstanceIds.isEmpty()) {
            LOGGER.debug("当前角色待处理的流程为空");
            return new ArrayList<>();
        }

        List<VwWorkOrder> vwWorkOrders = vwWorkOrderRepository.findByProcessInstanceIdInAndLastUpdateTimeGreaterThanOrderByLastUpdateTimeAsc(processInstanceIds, operationTime);


        LOGGER.debug("当前用户角色查询流程数量为：{}", processInstanceIds.size());

        return vwWorkOrders;
    }

    /**
     * 查询处理中任务--上拉加载更多--有数据权限
     *
     * @param userId        用户ID
     * @param operationTime
     * @return
     */
    @Transactional(readOnly = true)
    public Page<VwUserWorkOrder> findRunningTasksByUserIdAndOperationTimeLessThan(Integer userId, Date operationTime, Pageable page) {
        List<String> processInstanceIds = new ArrayList<>();


        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId.toString()).list();

        LOGGER.debug("根据当前用户查询待办事项task，数量为：{}", tasks.size());

        tasks.forEach(task -> {
            String processInstanceId = task.getProcessInstanceId();
            //为工单表中存放的id

            processInstanceIds.add(processInstanceId);
            LOGGER.debug("工单中的流程id为：{}", processInstanceId);
        });

        if (processInstanceIds.isEmpty()) {
            LOGGER.debug("当前角色待处理的流程为空,手动添加-1");
            processInstanceIds.add("-1");
        }

        //逻辑：查询流程id不在待处理流程中的，且工单状态不是已经完成的工单
        Page<VwUserWorkOrder> vwWorkOrders = vwUserWorkOrderRepository.findByUidAndStatusIdLessThanEqualAndProcessInstanceIdNotInAndLastUpdateTimeLessThanOrderByLastUpdateTimeDesc(userId, BusinessRefData.WOSTATUS_WO_ARRIVE, processInstanceIds, operationTime, page);

        LOGGER.debug("当前用户角色查询流程数量为：{}", processInstanceIds.size());

        return vwWorkOrders;
    }


    /**
     * 查询处理中任务--下拉刷新--有数据权限
     *
     * @param userId        用户ID
     * @param operationTime
     * @return
     */
    @Transactional(readOnly = true)
    public List<VwUserWorkOrder> findRunningTasksByUserIdAndOperationTimeGreaterThan(Integer userId, Date operationTime) {
        List<String> processInstanceIds = new ArrayList<>();

        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId.toString()).list();

        LOGGER.debug("根据当前用户查询待办事项task，数量为：{}", tasks.size());

        tasks.forEach(task -> {
            String processInstanceId = task.getProcessInstanceId();
            //为工单表中存放的id

            processInstanceIds.add(processInstanceId);
            LOGGER.debug("工单中的流程id为：{}", processInstanceId);
        });

        if (processInstanceIds.isEmpty()) {
            LOGGER.debug("当前角色待处理的流程为空,手动添加-1");
            processInstanceIds.add("-1");
        }

        List<VwUserWorkOrder> vwWorkOrders = vwUserWorkOrderRepository.findByUidAndStatusIdLessThanEqualAndProcessInstanceIdNotInAndLastUpdateTimeGreaterThanOrderByLastUpdateTimeAsc(userId, BusinessRefData.WOSTATUS_WO_ARRIVE, processInstanceIds, operationTime);

        LOGGER.debug("当前用户角色查询流程数量为：{}", processInstanceIds.size());

        return vwWorkOrders;
    }


    /**
     * 查询待办任务
     *
     * @param userId 用户ID
     * @return
     */
    @Transactional(readOnly = true)
    public List<ObjWorkOrder> findTodoTasks(String userId) {
        List<ObjWorkOrder> results = new ArrayList<>();

        // 根据当前人的ID查询
        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(userId);
        List<Task> tasks = taskQuery.list();

        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {

            String processInstanceId = task.getProcessInstanceId();

            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();

            if (processInstance == null) {
                continue;
            }

            String businessKey = processInstance.getBusinessKey();
            if (businessKey == null) {
                continue;
            }

            ObjWorkOrder objWorkOrderDemo = objWorkOrderRepository.findOne(Integer.parseInt(businessKey));

            //objWorkOrderDemo.setTask(task);
            //objWorkOrderDemo.setProcessInstance(processInstance);
            //objWorkOrderDemo.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
            results.add(objWorkOrderDemo);
        }

        return results;
    }

    /**
     * 读取运行中的流程
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<ObjWorkOrder> findRunningProcessInstaces() {
        List<ObjWorkOrder> results = new ArrayList<>();
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().processDefinitionKey(processKey).active().orderByProcessInstanceId().desc();
        List<ProcessInstance> list = query.list();

        // 关联业务实体
        for (ProcessInstance processInstance : list) {
            String businessKey = processInstance.getBusinessKey();
            if (businessKey == null) {
                continue;
            }
            ObjWorkOrder objWorkOrderDemo = objWorkOrderRepository.findOne(new Integer(businessKey));
            //objWorkOrderDemo.setProcessInstance(processInstance);
            //objWorkOrderDemo.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
            results.add(objWorkOrderDemo);

            // 设置当前任务信息
            List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().orderByTaskCreateTime().desc().listPage(0, 1);
            //objWorkOrderDemo.setTask(tasks.get(0));
        }

        return results;
    }

    /**
     * 读取已结束中的流程
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<ObjWorkOrder> findFinishedProcessInstaces() {
        List<ObjWorkOrder> results = new ArrayList<ObjWorkOrder>();
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery().processDefinitionKey(processKey).finished().orderByProcessInstanceEndTime().desc();
        List<HistoricProcessInstance> list = query.list();

        // 关联业务实体
        for (HistoricProcessInstance historicProcessInstance : list) {
            String businessKey = historicProcessInstance.getBusinessKey();
            ObjWorkOrder objWorkOrderDemo = objWorkOrderRepository.findOne(new Integer(businessKey));
            // objWorkOrderDemo.setProcessDefinition(getProcessDefinition(historicProcessInstance.getProcessDefinitionId()));
            //objWorkOrderDemo.setHistoricProcessInstance(historicProcessInstance);
            results.add(objWorkOrderDemo);
        }
        return results;
    }

    /**
     * 查询流程定义对象
     *
     * @param processDefinitionId 流程定义ID
     * @return
     */
    protected ProcessDefinition getProcessDefinition(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        return processDefinition;
    }

    /**
     * 查询工单下一步操作--接口不使用
     *
     * @param processInstanceId
     * @param uid
     * @return
     */
    @Deprecated
    public Set<String> findNextOperation(String processInstanceId, Integer uid) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Set<String> nextOperation = new HashSet<>();

        Execution execution = runtimeService.createExecutionQuery().executionId(processInstanceId).singleResult();
        //执行实例
        Object property = PropertyUtils.getProperty(execution, BpmnConstants.ACTIVITI_ID);
        String activityId = "";
        if (property != null) {
            activityId = property.toString();
        }
        LOGGER.debug("流程：{}当前运行的节点id为：{}", processInstanceId, activityId);

        String operationId = activityId + BpmnConstants.EXTENSION_PROPERTY_OPERATIONS;

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
                .singleResult();
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(processInstance.getProcessDefinitionId());

        ActivityImpl activityImpl = processDefinition.findActivity(activityId);

        Map<String, Object> map = activityImpl.getProperties();

        List<ExtensionOperation> extensionOperations = (List<ExtensionOperation>) map.get(operationId);
        LOGGER.debug("获取自定义节点属性，数据量为：{}", extensionOperations.size());

        List<VwUserRole> userRoles = vwUserRoleRepository.findByUserIdAndRoleGrade(uid, BusinessRefData.ROLE_GRADE_PREFABRICATE);
        LOGGER.debug("获取用户角色成功");

        userRoles.forEach(vwUserRole -> {
            List<String> operations = getOperationByRole(extensionOperations, vwUserRole.getRoleName());
            if (Validator.isNotNull(operations)) {
                nextOperation.addAll(operations);
            }

        });

        LOGGER.debug("当前用户对于该工单的下一步操作有{}种", nextOperation.size());

        return nextOperation;
    }

    private List<String> getOperationByRole(List<ExtensionOperation> extensionOperations, String roleName) {
        LOGGER.debug("根据角色:{}判断下一步操作", roleName);
        for (ExtensionOperation extensionOperation : extensionOperations) {
            if (extensionOperation.getRole().equals(roleName)) {
                LOGGER.debug("角色：{}的操作选项为：{}", roleName, extensionOperation.getOperation().toString());
                return extensionOperation.getOperation();
            }
        }
        return null;
    }
}
