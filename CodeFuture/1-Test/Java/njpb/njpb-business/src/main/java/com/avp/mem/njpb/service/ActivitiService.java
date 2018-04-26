package com.avp.mem.njpb.service;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Amber Wang on 2017-07-04 下午 03:56.
 */
@Service
@Transactional
public class ActivitiService {

//注入为我们自动配置好的服务

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    //开始流程，传入申请者的id以及公司的id
    public void startProcess(Long personId, Long compId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("personId", personId);
        variables.put("compId", compId);
        runtimeService.startProcessInstanceByKey("joinProcess", variables);
    }

    //获得某个人的任务别表
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskCandidateUser(assignee).list();
    }

    //完成任务
    public void completeTasks(Boolean joinApproved, String taskId) {
        Map<String, Object> taskVariables = new HashMap<>();
        taskVariables.put("joinApproved", joinApproved);
        taskService.complete(taskId, taskVariables);
    }
}