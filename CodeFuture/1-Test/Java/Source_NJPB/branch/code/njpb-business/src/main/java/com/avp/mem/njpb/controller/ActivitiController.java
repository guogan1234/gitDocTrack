/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.controller;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import com.avp.mem.njpb.service.activiti.ActivitiService;
import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Amber Wang on 2017-07-07 下午 04:41.
 */
@RestController
public class ActivitiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiController.class);

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

    @Autowired
    private ActivitiService activitiService;

    /**
     * 重新部署流程
     *
     * @return
     */
    @RequestMapping(value = "workflows/redeploy")
    public ResponseEntity<RestBody> redeployAll() {
        ResponseBuilder build = ResponseBuilder.createBuilder();
        try {
            String deploymentName = "workorder processes";

            Deployment deployment = repositoryService.createDeployment()
                    .name(deploymentName)
                    .addClasspathResource("diagrams/workorder/workorder.bpmn")
                    .deploy();

            String deploymentId = deployment.getId();

            LOGGER.debug("流程重新部署成功,流程ID:{}", deploymentId);

            build.setResponseCode(ResponseCode.UPDATE_SUCCEED, "流程【" + deploymentId + "】重新部署成功");
        } catch (Exception e) {
            e.printStackTrace();
            build.setResponseCode(ResponseCode.BAD_REQUEST, "流程重新部署失败");
        }

        return build.getResponseEntity();
    }

    /**
     * 重新部署库存工单流程
     *
     * @return
     */
    @RequestMapping(value = "stockWorkflows/redeploy")
    public ResponseEntity<RestBody> redeployStockAll() {
        ResponseBuilder build = ResponseBuilder.createBuilder();
        try {
            String deploymentNameOfStock = "stockWorkOrder processes";

            Deployment deployment = repositoryService.createDeployment()
                    .name(deploymentNameOfStock)
                    .addClasspathResource("diagrams/workorder/stockWorkOrder.bpmn")
                    .deploy();

            String deploymentId = deployment.getId();

            LOGGER.debug("流程重新部署成功,流程ID:{}", deploymentId);

            build.setResponseCode(ResponseCode.UPDATE_SUCCEED, "流程【" + deploymentId + "】重新部署成功");
        } catch (Exception e) {
            e.printStackTrace();
            build.setResponseCode(ResponseCode.BAD_REQUEST, "流程重新部署失败");
        }

        return build.getResponseEntity();
    }




    /**
     * 重新部署自修保养流程
     *
     * @return
     */
    @RequestMapping(value = "workOrderSelfWorkflows/redeploy")
    public ResponseEntity<RestBody> redeployWorkOrderSelfAll() {
        ResponseBuilder build = ResponseBuilder.createBuilder();
        try {
            String deploymentNameOfStock = "workOrderSelf processes";

            Deployment deployment = repositoryService.createDeployment()
                    .name(deploymentNameOfStock)
                    .addClasspathResource("diagrams/workorder/workOrderSelf.bpmn")
                    .deploy();

            String deploymentId = deployment.getId();

            LOGGER.debug("自修保养流程重新部署成功,流程ID:{}", deploymentId);

            build.setResponseCode(ResponseCode.UPDATE_SUCCEED, "流程【" + deploymentId + "】重新部署成功");
        } catch (Exception e) {
            e.printStackTrace();
            build.setResponseCode(ResponseCode.BAD_REQUEST, "流程重新部署失败");
        }

        return build.getResponseEntity();
    }













    /**
     * 查询角色的待办事项--暂不使用
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "workflows/getTaskByRole")
    public ResponseEntity<RestBody> getTaskByRole(String role) {
        ResponseBuilder build = ResponseBuilder.createBuilder();
        try {
            List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(role).list();

            for (Task task : tasks) {
                LOGGER.debug("Following task is available for sales group:{},group is :{}", task.getName(), role);
                //为工单表中存放的id
                String processInstanceId = task.getProcessInstanceId();

                LOGGER.debug("工单中的流程id为：{}", processInstanceId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            build.setResponseCode(ResponseCode.BAD_REQUEST, "流程重新部署失败");
        }

        return build.getResponseEntity();
    }

    //获取当前人的任务
    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public List<Map<String, Object>> getTasks(@RequestParam String assignee) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).list();

        List<Map<String, Object>> dtos = new ArrayList<>();

        for (Task task : tasks) {
            Map<String, Object> map = new HashMap<>();
            map.put(task.getId(), task.getName());
            dtos.add(map);
        }
        return dtos;
    }
}
