package com.avp.mem.njpb.resource.workorder;

import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.ObjWorkOrder;
import com.avp.mem.njpb.reponsitory.workorder.ObjWorkOrderRepository;
import com.avp.mem.njpb.service.activiti.WorkorderReportWorkflowService;
import com.avp.mem.njpb.util.Variable;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工单控制器，包含保存、启动流程
 *
 * @author Amber
 */
@RestController
public class WorkorderController {

    private Logger logger = LoggerFactory.getLogger(WorkorderController.class);

    @Autowired
    protected WorkorderReportWorkflowService workorderReportWorkflowService;

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    protected TaskService taskService;

    @Autowired
    protected ObjWorkOrderRepository objWorkOrderRepository;

    /**
     * 启动报修流程
     *
     * @param workOrder
     */
    @RequestMapping(value = "workFlows/start", method = RequestMethod.POST)
    public String startWorkflow(ObjWorkOrder workOrder) {
        try {
            Integer uid = SecurityUtils.getLoginUserId();

            workOrder.setCreateBy(uid);
            workOrder.setCreateTime(new Date());

            Map<String, Object> variables = new HashMap<>();

            ProcessInstance processInstance = workorderReportWorkflowService.startWorkflow(workOrder, variables);

            logger.info("流程已启动，流程ID：{}", processInstance.getId());
        } catch (ActivitiException e) {
            if (e.getMessage().indexOf("no processes deployed with key") != -1) {
                logger.error("没有部署流程!", e);
            } else {
                logger.error("启动请假流程失败：", e);
            }
        } catch (Exception e) {
            logger.error("启动报修流程失败：", e);
        }
        return "redirect:/oa/leave/apply";
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
    @RequestMapping(value = "workFlows/list/running",method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<ObjWorkOrder> runningList() {

        try {
            List<ObjWorkOrder> workOrders = workorderReportWorkflowService.findRunningProcessInstaces();
            Map<String,Object> map =new HashMap<>();
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
        logger.debug("taskId:{}签收成功", taskId);
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
            logger.error("error on complete task {}, variables={}", new Object[]{taskId, var.getVariableMap(), e});
            return "error";
        }
    }

}
