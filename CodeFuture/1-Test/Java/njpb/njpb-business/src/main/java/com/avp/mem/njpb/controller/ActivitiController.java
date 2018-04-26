package com.avp.mem.njpb.controller;

import com.avp.mem.njpb.api.rest.ResponseBuilder;
import com.avp.mem.njpb.api.rest.ResponseCode;
import com.avp.mem.njpb.api.rest.RestBody;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Amber Wang on 2017-07-07 下午 04:41.
 */
@RestController
public class ActivitiController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected RepositoryService repositoryService;

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

            logger.debug("流程重新部署成功,流程ID:{}",deploymentId);

            build.setErrorCode(ResponseCode.UPDATE_SUCCEED, "流程【" + deploymentId + "】重新部署成功");
        } catch (Exception e) {
            e.printStackTrace();
            build.setErrorCode(ResponseCode.BAD_REQUEST, "流程重新部署失败");
        }

        return build.getResponseEntity();
    }
}
