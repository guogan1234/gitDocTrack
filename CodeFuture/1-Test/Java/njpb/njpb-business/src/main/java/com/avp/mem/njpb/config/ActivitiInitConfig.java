package com.avp.mem.njpb.config;

import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.repository.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 初始化数据
 *
 * @author Amber
 */
@Component
public class ActivitiInitConfig {

//    protected static final Logger LOGGER = LoggerFactory.getLogger(ActivitiInitConfig.class);

//    @Autowired
//    protected IdentityService identityService;

    @Autowired
    protected RepositoryService repositoryService;

//    @Autowired
//    protected RuntimeService runtimeService;
//
//    @Autowired
//    protected TaskService taskService;
//
//    @Autowired
//    protected ManagementService managementService;
//
//    @Autowired
//    protected ProcessEngineConfigurationImpl processEngineConfiguration;

    @PostConstruct
    public void init() {
        initProcessDefinitions();
    }

   /**
    * 为了让Activiti引擎知道这个流程，我们必须先进行“发布”。
    * 发布意味着引擎会把BPMN 2.0 xml解析成可以执行的东西， “发布包”中的所有流程定义都会添加到数据库中。
    * 这样，当引擎重启时，它依然可以获得“已发布”的流程。
    *
    */
    protected void initProcessDefinitions() {
        String deploymentName = "workorder processes";
        List<Deployment> deploymentList = repositoryService.createDeploymentQuery().deploymentName(deploymentName).list();

        if (deploymentList == null || deploymentList.isEmpty()) {
           Deployment deployment= repositoryService.createDeployment()
                    .name(deploymentName)
                    .addClasspathResource("diagrams/workorder/workorder.bpmn")
                    .deploy();

           deployment.getId();
        }
    }
}