package com.avp.mem.njpb.controller;

import com.avp.mem.njpb.api.util.SecurityUtils;
import com.avp.mem.njpb.entity.ObjWorkOrder;
import com.avp.mem.njpb.service.ActivitiService;
import com.avp.mem.njpb.service.activiti.WorkorderReportWorkflowService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amber Wang on 2017-07-04 下午 04:00.
 */
@RestController
public class MyRestController {

    @Autowired
    private ActivitiService myService;

    @Autowired
    protected WorkorderReportWorkflowService workorderReportWorkflowService;

    //开启流程实例
    @RequestMapping(value = "/process/{personId}/{compId}", method = RequestMethod.GET)
    public void startProcessInstance(@PathVariable Long personId, @PathVariable Long compId) {
        myService.startProcess(personId, compId);
    }

    //获取当前人的任务
    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
        List<Task> tasks = myService.getTasks(assignee);

        List<TaskRepresentation> dtos = new ArrayList<>();
        for (Task task : tasks) {
            dtos.add(new TaskRepresentation(task.getId(), task.getName()));
        }
        return dtos;
    }

    //完成任务
    @RequestMapping(value = "/complete/{joinApproved}/{taskId}", method = RequestMethod.GET)
    public String complete(@PathVariable Boolean joinApproved, @PathVariable String taskId) {
        myService.completeTasks(joinApproved, taskId);
        return "ok";
    }

    /**
     * 任务列表
     *
     */
    @RequestMapping(value = "/taskTest1",method = RequestMethod.GET)
    public List<ObjWorkOrder> taskList() {
        Integer userId = SecurityUtils.getLoginUserId();
        List<ObjWorkOrder> workOrders= workorderReportWorkflowService.findTodoTasks(userId.toString());
        return workOrders;
    }

    //Task的dto
    static class TaskRepresentation {
        private String id;
        private String name;

        public TaskRepresentation(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}