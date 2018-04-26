package com.avp.mem.njpb.service.activiti;

import com.avp.mem.njpb.entity.ObjWorkOrder;
import com.avp.mem.njpb.reponsitory.workorder.ObjWorkOrderRepository;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 工单报修流程Service
 *
 * @author Amber
 */
@Component
@Transactional
public class WorkorderReportWorkflowService {

    private static Logger logger = LoggerFactory.getLogger(WorkorderReportWorkflowService.class);

    @Value("${business.service.processKey}")
    private String processKey;

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

    /**
     * 启动流程
     *
     * @param entity
     */
    public ProcessInstance startWorkflow(ObjWorkOrder entity, Map<String, Object> variables) {

        objWorkOrderRepository.save(entity);

        logger.debug("save entity: {}", entity);

        String businessKey = entity.getId().toString();

        ProcessInstance processInstance = null;
        try {
            // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
            identityService.setAuthenticatedUserId(entity.getCreateBy().toString());

            processInstance = runtimeService.startProcessInstanceByKey(processKey, businessKey, variables);

            String processInstanceId = processInstance.getId();

            //entity.setProcessInstanceId(processInstanceId);

            logger.debug("start process of {key={}, bkey={}, pid={}, variables={}}", new Object[]{"objWorkOrder", businessKey, processInstanceId, variables});

        } finally {
            identityService.setAuthenticatedUserId(null);
        }
        return processInstance;
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

            ObjWorkOrder objWorkOrderDemo = objWorkOrderRepository.findOneByIdAndRemoveTimeIsNull(Integer.parseInt(businessKey));

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
            ObjWorkOrder objWorkOrderDemo = objWorkOrderRepository.findOneByIdAndRemoveTimeIsNull(new Integer(businessKey));
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
            ObjWorkOrder objWorkOrderDemo = objWorkOrderRepository.findOneByIdAndRemoveTimeIsNull(new Integer(businessKey));
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

}
